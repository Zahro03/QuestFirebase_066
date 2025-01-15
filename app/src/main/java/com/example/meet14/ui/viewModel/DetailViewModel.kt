package com.example.meet14.ui.viewModel

import com.example.meet14.model.Mahasiswa

// Kelas sealed untuk merepresentasikan berbagai status UI pada DetailMhs
sealed class DetailMhsUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailMhsUiState()
    object Error : DetailMhsUiState()
    object Loading : DetailMhsUiState()
}