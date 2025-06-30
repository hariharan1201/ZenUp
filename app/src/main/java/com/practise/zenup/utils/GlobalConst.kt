package com.practise.zenup.utils

val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
const val TODO_FB_PATH = "ZenUp_ToDo"
const val TODO_SUB_PATH = "todos"

fun isValidEmail(email: String): Boolean  = emailRegex.matches(email)