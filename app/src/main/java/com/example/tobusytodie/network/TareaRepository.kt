package com.example.tobusytodie.network

import com.example.tobusytodie.core.ResultWrapper
import com.example.tobusytodie.core.safeCall
import com.example.tobusytodie.model.Tarea
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await


class TareaRepository @Inject constructor(

    private val firestore: FirebaseFirestore
) {
    private val tareasCollection = firestore.collection("Tareas")

    suspend fun createTarea(tarea: Tarea): ResultWrapper<Void> = safeCall {
        tareasCollection.document(tarea.id).set(tarea).await()
    }

    suspend fun getTareas(id: String? = null): ResultWrapper<Any> = safeCall {
        if (id != null) {
            val doc = tareasCollection.document(id).get().await()
            doc.toObject(Tarea::class.java) ?: throw Exception("Tarea no encontrada")
        } else {
            val snapshot = tareasCollection.get().await()
            snapshot.toObjects(Tarea::class.java)
        }
    }

    /*
            * suspend fun getTareaById(id: String): ResultWrapper<Tarea> = safeCall {
            val doc = tareasCollection.document(id).get().await()
            doc.toObject(Tarea::class.java) ?: throw Exception("Tarea no encontrada")
        }

        suspend fun getAllTareas(): ResultWrapper<List<Tarea>> = safeCall {
            val snapshot = tareasCollection.get().await()
            snapshot.toObjects(Tarea::class.java)
        }
        */


    suspend fun updateTarea(tarea: Tarea): ResultWrapper<Void> = safeCall {
        tareasCollection.document(tarea.id).set(tarea).await()
    }
    suspend fun deleteTarea(id: String): ResultWrapper<Void> = safeCall {
        tareasCollection.document(id).delete().await()
    }

}