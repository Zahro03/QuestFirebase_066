package com.example.meet14.repository

import com.example.meet14.model.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface MahasiswaRepository {
   suspend fun getMahasiswa(): Flow<List<Mahasiswa>>
   suspend fun insertMahasiswa(mahasiswa: Mahasiswa)
   suspend fun getMahasiswaByNim(nim: String): Flow<Mahasiswa>
   suspend fun deleteMahasiswa(mahasiswa: Mahasiswa)
   suspend fun updateMahasiswa(mahasiswa: Mahasiswa)

}