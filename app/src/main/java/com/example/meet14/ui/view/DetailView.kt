package com.example.meet14.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun DetailStatus(
    mhsUiState: DetailMhsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
){
    when(mhsUiState){
        is DetailMhsUiState.Success -> {
            DetailMhsLayout(
                mahasiswa = mhsUiState.mahasiswa,
                modifier = modifier,
                onEditClick = {onEditClick(it)}
            )
        }
        is DetailMhsUiState.Loading -> OnLoading(modifier = modifier)
        is DetailMhsUiState.Error -> OnError(retryAction, modifier = modifier)
    }
}

@Composable
fun DetailMhsLayout(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    onDeleteClick: (String) -> Unit = {}
) {
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) } // State untuk menampilkan dialog hapus

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Column utama untuk menyusun detail dan tombol
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // Memberikan ruang untuk tombol bawah
        ) {
            // Membungkus seluruh detail mahasiswa dengan Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEEEEEE)) // Warna abu-abu terang
                    .clip(RoundedCornerShape(8.dp))
                    .padding(16.dp) // Padding di dalam Box
            ) {
                // Komponen Detail Mahasiswa
                ItemDetailMhs(
                    mahasiswa = mahasiswa,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp)) // Spacer setelah detail

            // Tombol Hapus dengan warna abu gelap
            Button(
                onClick = { showDeleteDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF424242) // Warna abu-abu gelap untuk tombol Hapus
                )
            ) {
                Text(text = "Hapus", fontSize = 16.sp, color = Color.White)
            }
        }

        // Tombol Edit di pojok kanan bawah
        IconButton(
            onClick = { onEditClick(mahasiswa.nim) },
            modifier = Modifier
                .align(Alignment.BottomEnd) // Menempatkan tombol di pojok kanan bawah
                .padding(16.dp) // Memberikan padding ke tombol
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Mahasiswa",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Dialog Konfirmasi Hapus
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Konfirmasi Hapus") },
                text = { Text("Apakah Anda yakin ingin menghapus mahasiswa ini?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onDeleteClick(mahasiswa.nim)
                            showDeleteDialog = false
                        }
                    ) {
                        Text("Hapus", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteDialog = false }
                    ) {
                        Text("Batal")
                    }
                }
            )
        }
    }
}