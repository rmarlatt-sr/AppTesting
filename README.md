# TreadController

Android app for controlling a NordicTrack EXP 7i treadmill via its integrated Android 9 (API 28) tablet. Provides custom workout control, interval training, and workout history — an alternative to the built-in iFit interface.

## Target Device

- **NordicTrack EXP 7i** treadmill with built-in Android tablet
- **Android 9 (API 28)** — MediaTek chipset
- **Screen resolution**: 1024x600
- The treadmill runs iFit's custom Android firmware with a GlassOS service layer (`com.ifit.eru`) that manages hardware communication

## Architecture

```
com.treadcontroller/
├── LauncherActivity.kt          # Home screen: choose TreadController or iFit
├── MainActivity.kt              # Main app with bottom navigation
├── data/
│   ├── model/
│   │   ├── TreadmillState.kt    # Connection, workout, fan state enums + data class
│   │   └── WorkoutModels.kt     # WorkoutInterval, WorkoutTemplate, WorkoutSession
│   ├── db/
│   │   ├── AppDatabase.kt       # Room database (singleton)
│   │   ├── WorkoutDao.kt        # CRUD for templates and sessions
│   │   └── Converters.kt        # JSON type converters for Room
│   ├── CouchToMarathonProgram.kt  # 24-week marathon training plan (96 workouts)
│   ├── PresetWorkouts.kt        # Built-in workout templates
│   └── TommyRivsWorkouts.kt     # Additional preset workouts
├── service/
│   ├── TreadmillService.kt      # Interface for treadmill control
│   ├── MockTreadmillService.kt  # Simulated treadmill for development
│   ├── GlassOsService.kt        # Real treadmill IPC via iFit's eru service (WIP)
│   ├── WorkoutForegroundService.kt  # Keeps workout alive in background
│   ├── BootReceiver.kt          # BOOT_COMPLETED receiver
│   └── BootReadyService.kt      # Boot initialization service
├── viewmodel/
│   ├── DashboardViewModel.kt    # Manual treadmill control
│   ├── ActiveWorkoutViewModel.kt # Interval workout execution + recording
│   ├── WorkoutBuilderViewModel.kt # Template creation/editing
│   └── HistoryViewModel.kt      # Past workout sessions
└── ui/
    ├── screens/
    │   ├── DashboardScreen.kt    # Speed/incline controls, stats, start/pause/stop
    │   ├── ActiveWorkoutScreen.kt # Interval timer, progress, targets
    │   ├── WorkoutBuilderScreen.kt # Create/edit workout templates
    │   └── HistoryScreen.kt      # View/delete past sessions
    ├── theme/
    │   └── Theme.kt              # Dark theme, color palette
    └── util/
        └── FlowExt.kt           # collectAsStatePolled, KeepFrameClockAlive
```

## Screens

| Screen | Description |
|--------|-------------|
| **Launcher** | Home screen with animated buttons to choose TreadController or launch iFit |
| **Dashboard** | Manual control — speed/incline sliders, live stats (time, distance, calories, HR), start/pause/stop, fan control |
| **Builder** | Create interval workouts — set duration, speed, incline per interval. Save as templates |
| **Workout** | Execute a saved template — shows current/next interval, countdown timer, progress bar, live stats |
| **History** | View completed workout sessions with expandable detail cards |

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material 3
- **Navigation**: Navigation Compose
- **Database**: Room (SQLite)
- **Async**: Kotlin Coroutines + StateFlow
- **Min SDK**: 28 (Android 9)
- **Target SDK**: 36
- **Build**: Gradle 8.5, AGP 8.2.2

## Building

### Prerequisites

- Android Studio (for SDK and JBR)
- Android SDK with API 36 platform installed
- Java 17 (bundled with Android Studio's JBR)

### Build Debug APK

```bash
cd Z:/Git/AppTesting
JAVA_HOME="C:/Program Files/Android/Android Studio/jbr" \
ANDROID_HOME="C:/Users/RyanMarlatt/AppData/Local/Android/Sdk" \
./gradlew assembleDebug
```

Output: `app/build/outputs/apk/debug/app-debug.apk`

### Deploy to Treadmill

The treadmill connects via ADB over WiFi at `192.168.4.35:5555`.

```bash
# Connect (if not already connected)
adb connect 192.168.4.35:5555

# Install
MSYS_NO_PATHCONV=1 adb install -r app/build/outputs/apk/debug/app-debug.apk

# Launch
MSYS_NO_PATHCONV=1 adb shell am start -n com.treadcontroller/.LauncherActivity
```

**Note**: In Git Bash on Windows, prefix ADB commands with `MSYS_NO_PATHCONV=1` to prevent path conversion of Android package paths.

## Treadmill Integration

### Service Layer

The app communicates with the treadmill through the `TreadmillService` interface:

```kotlin
interface TreadmillService {
    val state: StateFlow<TreadmillState>
    suspend fun connect()
    suspend fun startWorkout()
    suspend fun pauseWorkout()
    suspend fun resumeWorkout()
    suspend fun stopWorkout()
    suspend fun setSpeed(mph: Double)
    suspend fun setIncline(percent: Double)
    suspend fun setFan(state: FanState)
}
```

**MockTreadmillService** — Used for development. Simulates speed/incline ramping, elapsed time, distance, calories, and heart rate.

**GlassOsService** — (WIP) Connects to the real treadmill hardware via iFit's `eru` system service using IPC/protobuf communication.

### iFit System Services

The treadmill runs these iFit services:
- `com.ifit.eru` — Core hardware control service (motor, incline, fan, sensors)
- `com.ifit.glassos_service` — App navigation and accessibility service
- `com.ifit.standalone` / `com.ifit.arda` — iFit workout apps

APK analysis files for reverse-engineering the IPC protocol are in `apk_analysis/` (not committed to git).

## Known Issues

### Button Double-Tap Required (Active — Unresolved)

**Problem**: On the treadmill's Android 9 tablet, all button taps require two clicks to register. The first tap shows a focus outline but doesn't trigger the onClick. The second tap on the same button fires the action.

**Root Cause Investigation**:
- NOT the iFit accessibility service (`glassos_appnavigation_service`) — disabling it doesn't fix the issue
- NOT Compose's Choreographer/recomposition scheduling — the issue affects the touch input layer, not UI updates
- iFit's `eru` service maintains an overlay window (`Window #4`) above all apps with `WATCH_OUTSIDE_TOUCH` flag, causing all touch events to arrive with `FLAG_WINDOW_IS_OBSCURED` (flags=2)
- Setting `filterTouchesWhenObscured = false` recursively on all views did not fix it
- Stripping `FLAG_WINDOW_IS_OBSCURED` from MotionEvents in `dispatchTouchEvent` did not fix it
- The issue does NOT affect iFit's own apps, suggesting they have special handling or the eru overlay treats them differently

**Attempted Fixes** (none resolved the issue):
1. Compose `Button` → `Box` + `Modifier.clickable(indication = null)`
2. `Modifier.pointerInteropFilter` with raw MotionEvent handling
3. Native Android `Button` via `AndroidView`
4. `view.invalidate()` / `view.requestLayout()` after state changes
5. `KeepFrameClockAlive()` — infinite animation to force Choreographer frames
6. `collectAsStatePolled()` — Handler-based StateFlow polling
7. Local `uiWorkoutState` mutableStateOf for instant UI response
8. `importantForAccessibility = NO_HIDE_DESCENDANTS`
9. `filterTouchesWhenObscured = false` on all views recursively
10. Stripping `FLAG_WINDOW_IS_OBSCURED` from MotionEvents before dispatch
11. Disabling iFit's accessibility service via ADB

**Next Steps to Try**:
- Investigate the `eru` overlay window behavior — it may use `TYPE_APPLICATION_OVERLAY` or `TYPE_SYSTEM_ALERT` and intercept the first DOWN event for its own focus tracking
- Try `WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL` on our activity window
- Try running our app in a different window layer
- Check if iFit apps set specific window flags that prevent eru overlay interference
- Consider building a minimal test APK (single native Android Button, no Compose) to isolate whether this is a Compose issue or system-wide

### Android 9 Compose Compatibility

Several Compose features required workarounds for Android 9:

- **Compose `Stack.pop` crash**: `ArrayIndexOutOfBoundsException` in `ComposerImpl.exitGroup` caused by conditional composable tree changes (`if/else` blocks, `return@Column`). Fixed by ensuring all screens render a stable composable tree structure.
- **StateFlow recomposition**: `collectAsState()` doesn't reliably trigger recomposition when StateFlow emits from background threads. Workaround: `collectAsStatePolled()` uses a Handler-based timer to poll the StateFlow and `KeepFrameClockAlive()` runs an infinite animation to force Choreographer frames.

### GlassOS Service Integration (Not Started)

The `GlassOsService.kt` needs to be implemented to communicate with the real treadmill hardware. This requires reverse-engineering the protobuf IPC protocol used by iFit's `eru` service. Proto definition files extracted from the APKs are available in `apk_analysis/proto_defs/`.

## Workout Programs

### Couch to Marathon (24 weeks)

A progressive training plan in `CouchToMarathonProgram.kt`:

| Phase | Weeks | Focus |
|-------|-------|-------|
| Walk to Run | 1-6 | Run/walk intervals, build base |
| Build Endurance | 7-12 | Longer runs, tempo intervals |
| Marathon Prep | 13-20 | Race-pace training, long runs up to 120 min |
| Peak & Taper | 21-24 | Peak mileage then taper for race day |

- 96 total workouts (4 per week)
- Deload weeks at weeks 4, 8, 12, 16, 20
- Progressive incline on tempo intervals (1.0-3.0%)
- Each workout is a `WorkoutTemplate` with timed intervals

### Preset Workouts

Additional templates in `PresetWorkouts.kt` and `TommyRivsWorkouts.kt` for quick access to common training sessions.
