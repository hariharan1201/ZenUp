package com.practise.zenup.utils

val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

fun isValidEmail(email: String): Boolean  = emailRegex.matches(email)