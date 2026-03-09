package com.treadcontroller.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.treadcontroller.data.db.WorkoutDao
import com.treadcontroller.data.model.WorkoutInterval
import com.treadcontroller.data.model.WorkoutTemplate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutBuilderViewModel(private val workoutDao: WorkoutDao) : ViewModel() {
    val templates: Flow<List<WorkoutTemplate>> = workoutDao.getAllTemplates()

    private val _workoutName = MutableStateFlow("")
    val workoutName: StateFlow<String> = _workoutName

    private val _intervals = MutableStateFlow<List<WorkoutInterval>>(emptyList())
    val intervals: StateFlow<List<WorkoutInterval>> = _intervals

    private var editingTemplateId: Long? = null

    fun setWorkoutName(name: String) { _workoutName.value = name }

    fun addInterval(interval: WorkoutInterval) {
        _intervals.value = _intervals.value + interval
    }

    fun removeInterval(index: Int) {
        _intervals.value = _intervals.value.toMutableList().apply { removeAt(index) }
    }

    fun loadTemplate(template: WorkoutTemplate) {
        editingTemplateId = template.id
        _workoutName.value = template.name
        _intervals.value = template.intervals
    }

    fun saveWorkout() {
        viewModelScope.launch {
            val template = WorkoutTemplate(
                id = editingTemplateId ?: 0,
                name = _workoutName.value.ifBlank { "Custom Workout" },
                intervals = _intervals.value
            )
            if (editingTemplateId != null) {
                workoutDao.updateTemplate(template)
            } else {
                val id = workoutDao.insertTemplate(template)
                editingTemplateId = id
            }
        }
    }

    fun deleteTemplate(template: WorkoutTemplate) {
        viewModelScope.launch { workoutDao.deleteTemplate(template) }
    }

    fun clearBuilder() {
        editingTemplateId = null
        _workoutName.value = ""
        _intervals.value = emptyList()
    }
}
