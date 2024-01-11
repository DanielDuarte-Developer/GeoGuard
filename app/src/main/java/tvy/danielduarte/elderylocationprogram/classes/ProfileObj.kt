package tvy.danielduarte.elderylocationprogram

import android.graphics.Bitmap
import android.location.Location

class ProfileObj (Nome: String, Imagem: Bitmap, LocallizacaoAtual: Location, CentroPerimetro: Location, RaioPerimetro:Double, TipoNotificacao: Int){
    val nome: String = Nome
    val imagem: Bitmap = Imagem
    val localizacaoAtual: Location = LocallizacaoAtual
    val centroPerimetro: Location = CentroPerimetro
    val raioPerimetro: Double = RaioPerimetro
    val tipoNotificacao: Int = TipoNotificacao
}