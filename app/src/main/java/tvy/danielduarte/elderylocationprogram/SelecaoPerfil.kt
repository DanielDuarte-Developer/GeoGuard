package tvy.danielduarte.elderylocationprogram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import java.io.Serializable

class SelecaoPerfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selecao_perfil)
        val novoBtn = LayoutInflater.from(this).inflate(R.layout.selecionar_perfil,null)
        novoBtn.findViewById<TextView>(R.id.txtEscolherPerfil).setOnClickListener(View.OnClickListener { v ->

    }

    fun criarPerfil(view: View) {
        val inte = Intent(this, CriacaoPerfil::class.java)
        startActivity(inte)
    }
}