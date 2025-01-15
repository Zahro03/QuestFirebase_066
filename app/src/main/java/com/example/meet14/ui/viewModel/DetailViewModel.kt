package com.example.meet14.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.meet14.model.Mahasiswa
import com.example.meet14.repository.MahasiswaRepository

// Kelas sealed untuk merepresentasikan berbagai status UI pada DetailMhs
sealed class DetailMhsUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailMhsUiState()
    object Error : DetailMhsUiState()
    object Loading : DetailMhsUiState()
}

// ViewModel untuk mengelola data dan status UI pada DetailMhs
class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
) : ViewModel() {
    private val nim: String = checkNotNull(savedStateHandle["nim"])
    var detailMhsUiState: DetailMhsUiState by mutableStateOf(DetailMhsUiState.Loading)
        private set

    init {
        getMahasiswaByNim()
    }