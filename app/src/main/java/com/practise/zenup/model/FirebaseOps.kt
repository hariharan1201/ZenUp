package com.practise.zenup.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseOps {
    fun getFirebaseAuth (): FirebaseAuth = FirebaseAuth.getInstance()
    fun getFirebaseDB() : FirebaseDatabase = FirebaseDatabase.getInstance()
    fun getFirebaseFireStore() : FirebaseFirestore = FirebaseFirestore.getInstance()
}