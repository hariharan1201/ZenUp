package com.practise.zenup.frags.todo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practise.zenup.frags.todo.repo.ToDoRepo
import com.practise.zenup.frags.todo.repo.ToDoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ToDoViewModel @Inject constructor(private val toDoRepo: ToDoRepo): ViewModel() {
    private val _todoState = MutableLiveData<ToDoState>()
    val todoState = _todoState

    fun getToDos() {
        viewModelScope.launch {
            toDoRepo.getToDo().collectLatest { _todoState.value = it }
        }
    }

    fun setToDo(todo: String) {
        viewModelScope.launch {
            toDoRepo.addToDo(todo).collectLatest { _todoState.value = it }
        }
    }

    fun removeToDo(id : String, status : Boolean){
        viewModelScope.launch {
            toDoRepo.removeToDo(id, status).collectLatest { _todoState.value = it }
        }
    }

    fun observeToDo() {
        viewModelScope.launch {
            toDoRepo.observeToDo().collectLatest { _todoState.value = it }
        }
    }

}