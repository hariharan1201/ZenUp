package com.practise.zenup.frags.todo.repo

import com.google.firebase.firestore.DocumentSnapshot

sealed class ToDoState {
    data object Loading : ToDoState()
    data class GetToDo(val todo: MutableList<DocumentSnapshot>) : ToDoState()
    data object Success : ToDoState()
    data object Deleted : ToDoState()
    data object Failed : ToDoState()
}