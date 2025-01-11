package com.example.meet14.repository

import com.example.meet14.model.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface MahasiswaRepository {
    suspend fun getMahasiswa(): Flow<List<Mahasiswa>>
    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)
    suspend fun updateMahasiswa(nim:String,mahasiswa: Mahasiswa)
    suspend fun deleteMahasiswa(nim:String)
    suspend fun getMahasiswaByNIM(nim:String): Flow<Mahasiswa>
}