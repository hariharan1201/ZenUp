package com.practise.zenup.frags.todo.repo

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.practise.zenup.model.FirebaseOps
import com.practise.zenup.utils.TODO_FB_PATH
import com.practise.zenup.utils.TODO_SUB_PATH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ToDoRepoImpl @Inject constructor(private val firebaseOps: FirebaseOps): ToDoRepo {
    private val firebaseDatabase = firebaseOps.getFirebaseFireStore().collection(TODO_FB_PATH)
    private val userInfo = firebaseOps.getFirebaseAuth().currentUser

    override fun getToDo(): Flow<ToDoState> = flow<ToDoState> {
        emit(ToDoState.Loading)
        if(userInfo != null) {
            val doc = firebaseDatabase
                .document(userInfo.uid)
                .collection(TODO_SUB_PATH)
                .orderBy("TimeStamp", Query.Direction.DESCENDING)
                .get().await()
            if(!doc.isEmpty)
                emit(ToDoState.GetToDo(doc.documents))
            else
                emit(ToDoState.Failed)
        }
    }.flowOn(Dispatchers.IO)

    override fun addToDo(todo : String): Flow<ToDoState> = flow<ToDoState> {
        emit(ToDoState.Loading)
        if(userInfo != null){
            Log.d("FIREBASE", userInfo.uid)
            val status = suspendCoroutine<Boolean> { state->
                firebaseDatabase.document(userInfo.uid)
                    .collection(TODO_SUB_PATH)
                    .add(mapOf("ToDo" to todo,
                        "Status" to false,
                        "TimeStamp" to FieldValue.serverTimestamp()))
                    .addOnCompleteListener { state.resume(it.isSuccessful) }
            }
            if(status) emit(ToDoState.Success) else emit(ToDoState.Failed)
        }
    }.flowOn(Dispatchers.IO)
}