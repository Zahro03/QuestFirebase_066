package com.example.meet14.ui.view

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meet14.ui.viewModel.FormState
import com.example.meet14.ui.viewModel.InsertViewModel
import com.example.meet14.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState
    val uiEvent = viewModel.uiEvent
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState) {
        when (uiState){
            is FormState.Success -> {
                println(
                    "InsertMhsView: uiState is FormState.Success,navigate to home" + uiState.message
                )
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }
        }
    }
}