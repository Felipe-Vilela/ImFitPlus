package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.ui

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.R
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.TileHistoricoBinding
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.DadosPessoais

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
        tlViewHolder.nomeTv.text = "Nome: ${dadosPessoais.nome}"
        tlViewHolder.idadeTv.text = "Idade: ${dadosPessoais.idade.toString()}"
        tlViewHolder.sexoTv.text = "Sexo: ${dadosPessoais.sexo}"
        tlViewHolder.alturaTv.text = "Altura: ${dadosPessoais.altura.toString()}"
        tlViewHolder.pesoTv.text = "Peso: ${dadosPessoais.peso.toString()}"
        tlViewHolder.nivelTv.text = "Nivel Atividade: ${dadosPessoais.nivelAtividade}"
        tlViewHolder.imcTv.text = "IMC: ${dadosPessoais.imc.toString()}"
        tlViewHolder.taxaBasalTv.text = "Taxa Basal: ${dadosPessoais.tmb.toString()}"
        tlViewHolder.gastoTv.text = "Gasto calorico ${dadosPessoais.gastoCalorico.toString()}"
        tlViewHolder.pesoIdealTv.text = "Peso Ideal ${dadosPessoais.pesoIdeal.toString()}"
        tlViewHolder.categoriaTv.text = "Categoria: ${dadosPessoais.categoriaImc}"
        tlViewHolder.recomendacaoAguaTv.text = "Recomendação água: ${dadosPessoais.recomendacaoAgua.toString()}"

        return historicoTileView
    }
    private data class TileHistoricoViewHolder(
        val nomeTv: TextView,
        val idadeTv: TextView,
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
}