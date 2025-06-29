package com.practise.zenup.model

import com.google.firebase.auth.FirebaseAuth

class FirebaseOps {
    fun getFirebaseAuth (): FirebaseAuth = FirebaseAuth.getInstance()
}