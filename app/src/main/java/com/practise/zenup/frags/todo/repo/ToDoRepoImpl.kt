package com.practise.zenup.frags.todo.repo

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.practise.zenup.model.FirebaseOps
import com.practise.zenup.utils.TODO_FB_PATH
import com.practise.zenup.utils.TODO_SUB_PATH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ToDoRepoImpl @Inject constructor(private val firebaseOps: FirebaseOps): ToDoRepo {
    private val firebaseDatabase = firebaseOps.getFirebaseFireStore().collection(TODO_FB_PATH)
    private var listenerRegistration: ListenerRegistration? = null

    override fun getToDo(): Flow<ToDoState> = flow {
        val userInfo = firebaseOps.getFirebaseAuth().currentUser
        emit(ToDoState.Loading)
        if(userInfo != null) {
            val doc = firebaseDatabase
                .document(userInfo.uid)
                .collection(TODO_SUB_PATH)
                .orderBy("TimeStamp", Query.Direction.DESCENDING)
                .get().await()
            if(!doc.isEmpty)
                emit(ToDoState.GetToDo(doc.documents))
            else {
                emit(ToDoState.Failed)
                emit(ToDoState.GetToDo(mutableListOf()))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun addToDo(todo : String): Flow<ToDoState> = flow {
        val userInfo = firebaseOps.getFirebaseAuth().currentUser
        emit(ToDoState.Loading)
        if(userInfo != null){
            Log.d("USER ID", "${userInfo.uid}")
            val status = suspendCoroutine { state->
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

    override fun removeToDo(id: String, status: Boolean): Flow<ToDoState> = flow {
        val userInfo = firebaseOps.getFirebaseAuth().currentUser
        val statusUpdate = mapOf("Status" to status)
        if(userInfo != null){
            try {
                firebaseDatabase.document(userInfo.uid)
                    .collection(TODO_SUB_PATH).document(id).update(statusUpdate).await()
                emit(ToDoState.Deleted)
            }catch (e: Exception){
                emit(ToDoState.Failed)
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun observeToDo(): Flow<ToDoState> = callbackFlow {
        trySend(ToDoState.Loading)
        val userInfo = firebaseOps.getFirebaseAuth().currentUser
        if (userInfo != null) {
            listenerRegistration = firebaseDatabase
                .document(userInfo.uid)
                .collection(TODO_SUB_PATH)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(ToDoState.Failed).isSuccess
                        return@addSnapshotListener
                    }
                    snapshot?.let {
                        trySend(ToDoState.GetToDo(it.documents)).isSuccess
                    }
                }
        } else {
            trySend(ToDoState.Failed).isSuccess
            close() // Gracefully stop the flow if userInfo is null
        }

        awaitClose {
            listenerRegistration?.remove()
        }
    }.flowOn(Dispatchers.IO)

    override fun closeObserver(): Flow<ToDoState> = flow<ToDoState> {
        listenerRegistration?.remove()
        emit(ToDoState.Success)
    }.flowOn(Dispatchers.IO)


}