package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DadosPessoais(
    val id: Int? = -1,
    val nome: String = "",
    val idade: Int? = null,
    val sexo: String = "",
    val altura: Double ? = null,
    val peso: Double ? = null,
    val nivelAtividade: String = "",
    var imc: Double? = null,
    var tmb: Double? = null,
    var gastoCalorico: Double? = null,
    var pesoIdeal: Double? = null,
    var categoriaImc: String? = null,
    var recomendacaoAgua: Double? = null
) : Parcelable
