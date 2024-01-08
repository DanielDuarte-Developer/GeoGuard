package tvy.danielduarte.elderylocationprogram

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tvy.danielduarte.elderylocationprogram.R

class DentroPerfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dentro_perfil)
        var nomePerfil : String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            nomePerfil = intent.getSerializableExtra("nomePerfil", SelecaoPerfil::class.java).toString()!!
        } else {
            nomePerfil = ((intent.getSerializableExtra("nomePerfil") as SelecaoPerfil).toString())!!

        }
    }
}