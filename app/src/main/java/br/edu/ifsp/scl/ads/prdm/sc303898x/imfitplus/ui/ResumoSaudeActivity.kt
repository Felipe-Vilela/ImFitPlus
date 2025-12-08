package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.Constant
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.DadosPessoais
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.R
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityResumoSaudeBinding
import java.text.DecimalFormat

class ResumoSaudeActivity : AppCompatActivity() {

    private val arsb: ActivityResumoSaudeBinding by lazy {
        ActivityResumoSaudeBinding.inflate(layoutInflater)
    }
    lateinit var dadosPessoais: DadosPessoais

    private lateinit var harl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(arsb.root)

        setSupportActionBar(arsb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.resumo_saude_subtitle)

        harl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result -> if (result.resultCode == RESULT_OK){}
        }

        dadosPessoais = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_PERFIL, DadosPessoais::class.java)!!
        }else {
            intent.getParcelableExtra<DadosPessoais>(Constant.EXTRA_PERFIL)!!
        }

        dadosPessoais.let { dados ->
            val imcFormatado = DecimalFormat("0.00").format(dados.imc!!)
            val pesoIdealFormatado = DecimalFormat("0.00").format(dados.pesoIdeal!!)
            val gcdFormatado = DecimalFormat("0").format(dados.gastoCalorico!!)
            val recomendacaoAgua = calcularRecomendacaoAgua()
            val recomendacaoAguaFormatado = DecimalFormat("0.0").format(recomendacaoAgua)
            dados.recomendacaoAgua = recomendacaoAgua

            arsb.nameTv.text = getString(R.string.resultado_nome, dados.nome)
            arsb.imcTv.text = getString(R.string.resultado_imc, imcFormatado)
            arsb.categoriaTv.text = getString(R.string.resultado_categoria, dados.categoriaImc)
            arsb.pesoIdealTv.text = getString(R.string.peso_ideal, pesoIdealFormatado)
            arsb.gastoCaloricoTv.text = getString(R.string.resultado_gcd, gcdFormatado)
            arsb.recomendacaoAguaTv.text = getString(R.string.recomendacao_ingestao_agua, recomendacaoAguaFormatado)
        }

        arsb.historicoBt.setOnClickListener {
            harl.launch(Intent(this, HistoricoActivity::class.java).apply {
                putExtra(Constant.EXTRA_PERFIL, dadosPessoais)
            })
        }

        arsb.voltarBt.setOnClickListener {
            finish()
        }
    }
    private fun calcularRecomendacaoAgua(): Double {
        return (dadosPessoais.peso!! * 35) /1000
    }
}