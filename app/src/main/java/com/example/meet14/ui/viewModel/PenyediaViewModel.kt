package com.example.meet14.ui.viewModel

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.meet14.MahasiswaApplications
import com.example.meet14.model.Mahasiswa

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(MahasiswaApplications().container.mahasiswaRepository)}
        initializer { InsertViewModel(MahasiswaApplications().container.mahasiswaRepository) }
    }
    fun CreationExtras.MahasiswaApplications(): MahasiswaApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)
}