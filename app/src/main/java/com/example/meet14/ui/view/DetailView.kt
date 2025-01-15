package com.example.meet14.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meet14.navigation.DestinasiNavigasi

object DestinasiDetail : DestinasiNavigasi{
    override val route = "Detail_mhs"
    override val titleRes = "Detail Mahasiswa"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMhsScreen(
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    detailViewModel.getMahasiswaByNim()
                }
            )
        },
    )
    { innerPadding ->
        DetailStatus(
            mhsUiState = detailViewModel.detailMhsUiState,
            retryAction = {detailViewModel.getMahasiswaByNim() },
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize(),
            onEditClick = onEditClick

        )
    }
}