package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.TileHistoricoBinding
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.DadosPessoais
import java.text.DecimalFormat

class HistoricoAdapter(
    private val lista: List<DadosPessoais>
) : RecyclerView.Adapter<HistoricoAdapter.Holder>() {

    inner class Holder(val binding: TileHistoricoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = TileHistoricoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = lista[position]
        val df = DecimalFormat("0.00")

        holder.binding.apply {
            nomeTv.text = "Nome: ${item.nome}"
            idadeTv.text = "Idade: ${item.idade}"
            sexoTv.text = "Sexo: ${item.sexo}"
            alturaTv.text = "Altura: ${df.format(item.altura)} m"
            pesoTv.text = "Peso: ${df.format(item.peso)} kg"
            nivelTv.text = "Nível de atividade: ${item.nivelAtividade}"
            imcTv.text = "IMC: ${df.format(item.imc)}"
            taxaBasalTv.text = "Taxa metabólica basal: ${item.tmb}"
            gastoTv.text = "Gasto calórico: ${item.gastoCalorico}"
            pesoIdealTv.text = "Peso ideal: ${item.pesoIdeal}"
            categoriaTv.text = "Categoria: ${item.categoriaImc}"
        }
    }
}
