package com.practise.zenup.frags.todo.repo

import kotlinx.coroutines.flow.Flow

interface ToDoRepo {
    fun getToDo() : Flow<ToDoState>
    fun addToDo(todo: String): Flow<ToDoState>
}