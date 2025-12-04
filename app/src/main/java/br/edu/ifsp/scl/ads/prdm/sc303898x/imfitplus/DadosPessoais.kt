package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DadosPessoais(
    val nome: String,
    val idade: Int,
    val sexo: String,
    val altura: Double,
    val peso: Double,
    val nivelAtividade: String,
    var imc: Double? = null,
    var tmb: Double? = null,
    var gastoCalorico: Double? = null,
    var pesoIdeal: Double? = null,
    var categoriaImc: String? = null
) : Parcelable
