package com.example.meet14.ui.view

import com.example.meet14.navigation.DestinasiNavigasi

object DestinasiDetail : DestinasiNavigasi{
    override val route = "Detail_mhs"
    override val titleRes = "Detail Mahasiswa"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}