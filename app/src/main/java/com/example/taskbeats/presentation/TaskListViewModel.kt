package com.example.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskbeats.TaskBeatsApplication
import com.example.taskbeats.data.Task
import com.example.taskbeats.data.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskDao: TaskDao): ViewModel() {

    val taskListLiveData: LiveData<List<Task>> = taskDao.getAll()

    fun execute(taskAction: TaskListActivity.TaskAction){
        when (taskAction.actionType) {
            TaskListActivity.ActionType.DELETE.name -> deleteById(taskAction.task!!.id)
            TaskListActivity.ActionType.CREATE.name -> insertIntoDataBase(taskAction.task!!)
            TaskListActivity.ActionType.UPDATE.name -> updateIntoDataBase(taskAction.task!!)
            TaskListActivity.ActionType.DELETE_ALL.name -> deleteAll()
        }

    }
    private fun deleteById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteById(id)
        }

    }

    private fun insertIntoDataBase(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(task)
        }

    }

    private fun updateIntoDataBase(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(task)
        }

    }

    private fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteAll()
        }

    }

    companion object{
        fun create(application: Application): TaskListViewModel{
            val dataBaseInstance = (application as TaskBeatsApplication).getAppDataBase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }
}