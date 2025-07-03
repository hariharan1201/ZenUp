package com.practise.zenup.frags.todo.repo

import kotlinx.coroutines.flow.Flow

interface ToDoRepo {
    fun getToDo() : Flow<ToDoState>
    fun addToDo(todo: String): Flow<ToDoState>
    fun removeToDo(id: String, status : Boolean) : Flow<ToDoState>
    fun observeToDo() : Flow<ToDoState>
    fun closeObserver() : Flow<ToDoState>
}