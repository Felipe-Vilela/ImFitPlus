package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.ui

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.icu.text.UFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.R
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.TileHistoricoBinding
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.DadosPessoais
import java.text.DecimalFormat

class HistoricoAdapter(
    context: Context,
    private val historicolist: MutableList<DadosPessoais>
) : ArrayAdapter<DadosPessoais>(
    context,
    R.layout.tile_historico,
    historicolist
) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val dadosPessoais = historicolist[position]
        var historicoTileView = convertView

        if (historicoTileView == null){
            TileHistoricoBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            ).apply {
                historicoTileView = root
                val tileHistoricoViewHolder = TileHistoricoViewHolder(
                    nomeTv,
                    idadeTv,
                    fcMaxTv,
                    zonaLeveTv,
                    queimaGorduraTv,
                    aerobicaTv,
                    anaerobicaTv,
                    sexoTv,
                    alturaTv,
                    pesoTv,
                    nivelTv,
                    imcTv,
                    taxaBasalTv,
                    gastoTv,
                    pesoIdealTv,
                    categoriaTv,
                    recomendacaoAguaTv)

                historicoTileView.tag = tileHistoricoViewHolder
            }
        }

        val tlViewHolder = historicoTileView?.tag as TileHistoricoViewHolder

        val df = DecimalFormat("0.00")
        val fcMax = fcMax(dadosPessoais.idade!!)

        tlViewHolder.nomeTv.text = "Nome: ${dadosPessoais.nome}"
        tlViewHolder.idadeTv.text = "Idade: ${dadosPessoais.idade}"
        tlViewHolder.fcMaxTv.text = "Fc Max: $fcMax"
        tlViewHolder.zonaLeveTv.text = zonaLeve(fcMax)
        tlViewHolder.queimaGorduraTv.text = zonaQueimaGordura(fcMax)
        tlViewHolder.aerobicaTv.text = zonaAerobica(fcMax)
        tlViewHolder.anaerobicaTv.text = zonaAnaerobica(fcMax)
        tlViewHolder.sexoTv.text = "Sexo: ${dadosPessoais.sexo}"
        tlViewHolder.nivelTv.text = "Nivel Atividade: ${dadosPessoais.nivelAtividade}"
        tlViewHolder.categoriaTv.text = "Categoria: ${dadosPessoais.categoriaImc}"
        tlViewHolder.alturaTv.text = "Altura: ${df.format(dadosPessoais.altura ?: 0.0)}"
        tlViewHolder.pesoTv.text = "Peso: ${df.format(dadosPessoais.peso ?: 0.0)}"
        tlViewHolder.imcTv.text = "IMC: ${df.format(dadosPessoais.imc ?: 0.0)}"
        tlViewHolder.taxaBasalTv.text = "Taxa Basal: ${df.format(dadosPessoais.tmb ?: 0.0)}"
        tlViewHolder.gastoTv.text = "Gasto calorico: ${df.format(dadosPessoais.gastoCalorico ?: 0.0)}"
        tlViewHolder.pesoIdealTv.text = "Peso Ideal: ${df.format(dadosPessoais.pesoIdeal ?: 0.0)}"
        tlViewHolder.recomendacaoAguaTv.text = "Recomendação água: ${df.format(dadosPessoais.recomendacaoAgua ?: 0.0)}"

        return historicoTileView
    }
    private data class TileHistoricoViewHolder(
        val nomeTv: TextView,
        val idadeTv: TextView,
        val fcMaxTv: TextView,
        val zonaLeveTv: TextView,
        val queimaGorduraTv: TextView,
        val aerobicaTv: TextView,
        val anaerobicaTv: TextView,
        val sexoTv: TextView,
        val alturaTv: TextView,
        val pesoTv: TextView,
        val nivelTv: TextView,
        val imcTv: TextView,
        val taxaBasalTv: TextView,
        val gastoTv: TextView,
        val pesoIdealTv: TextView,
        val categoriaTv: TextView,
        val recomendacaoAguaTv: TextView
    )

    private fun fcMax(idade: Int): Int {
        return 220 - idade
    }

    private fun zonaLeve(fcMax: Int): String {
        val freq50 = fcMax * 0.5
        val freq60 = fcMax * 0.6
        return "Zona Leve entre: $freq50 - $freq60"
    }

    private fun zonaQueimaGordura(fcMax: Int): String {
        val freq60 = fcMax * 0.6
        val freq70 = fcMax * 0.7
        return "Zona Queima Gordura entre: $freq60 - $freq70"
    }

    private fun zonaAerobica(fcMax: Int): String {
        val freq70 = fcMax * 0.7
        val freq80 = fcMax * 0.8
        return "Zona Aerobica entre: $freq70 - $freq80"
    }

    private fun zonaAnaerobica(fcMax: Int): String {
        val freq80 = fcMax * 0.8
        val freq90 = fcMax * 0.9
        return "Zona Anaerobica entre: $freq80 - $freq90"
    }
}