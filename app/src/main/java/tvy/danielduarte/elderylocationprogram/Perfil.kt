package tvy.danielduarte.elderylocationprogram

import android.graphics.Bitmap
import android.location.Location

class Perfil {
    var nome: String ?= null
    var imagem: Bitmap ?= null
    var localizacaoAtual: Location ?= null
    var centroPerimetro: Location ?= null
    var raioPerimetro: Double ?= null
    var tipoNotificacao: Int ?= null
}