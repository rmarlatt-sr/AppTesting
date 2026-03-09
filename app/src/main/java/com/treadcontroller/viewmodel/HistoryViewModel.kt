package com.treadcontroller.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.treadcontroller.data.db.WorkoutDao
import com.treadcontroller.data.model.WorkoutSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HistoryViewModel(private val workoutDao: WorkoutDao) : ViewModel() {
    val sessions: Flow<List<WorkoutSession>> = workoutDao.getAllSessions()

    fun deleteSession(id: Long) {
        viewModelScope.launch { workoutDao.deleteSession(id) }
    }
}
