package com.example.meet14.repository

import com.example.meet14.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NetworkRepositoryMhs(
    private val firestore: FirebaseFirestore
) : MahasiswaRepository {

    override suspend fun getMahasiswa(): Flow<List<Mahasiswa>> = callbackFlow {
        val mhsCollection = firestore.collection("Mahasiswa")
            .orderBy("nama", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val mhsList = value.documents.mapNotNull {
                        it.toObject(Mahasiswa::class.java)!!
                    }
                    trySend(mhsList)
                }
            }
        awaitClose {
            mhsCollection.remove()
        }
    }