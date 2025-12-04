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
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityGastoCaloricoBinding
import java.text.DecimalFormat

class GastoCaloricoActivity : AppCompatActivity() {

    private val agcb: ActivityGastoCaloricoBinding by lazy{
        ActivityGastoCaloricoBinding.inflate(layoutInflater)
    }

    lateinit var dadosPessoais: DadosPessoais

    private lateinit var piarl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(agcb.root)

        setSupportActionBar(agcb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.gasto_calorico_subtitle)

        piarl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result -> if (result.resultCode == RESULT_OK){}
        }

        dadosPessoais = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_PERFIL, DadosPessoais::class.java)!!
        }else {
            intent.getParcelableExtra<DadosPessoais>(Constant.EXTRA_PERFIL)!!
        }

        val fatorAtividade = obterFatorAtividade(dadosPessoais.nivelAtividade)
        val tmb = calcularTmb(dadosPessoais.peso, dadosPessoais.altura, dadosPessoais.idade, dadosPessoais.sexo)
        val gcd = calcularGcd(tmb, fatorAtividade)

        dadosPessoais.tmb = tmb
        dadosPessoais.gastoCalorico = gcd

        val tmbFormatado = DecimalFormat("0").format(tmb)
        val gcdFormatado = DecimalFormat("0").format(gcd)

        agcb.tmbTv.text = getString(R.string.resultado_tmb, tmbFormatado)
        agcb.gcdTv.text = getString(R.string.resultado_gcd, gcdFormatado)

        agcb.pesoIdealBt.setOnClickListener {
            piarl.launch(Intent(this, PesoIdealActivity::class.java).apply {
                putExtra(Constant.EXTRA_PERFIL, dadosPessoais)
            })
        }

        agcb.voltarBt.setOnClickListener {
            finish()
        }

    }

    private fun obterFatorAtividade(nivel: String): Double {
        return when (nivel.uppercase()) {
            "SEDENTÁRIO" -> 1.2
            "LEVEMENTE ATIVO" -> 1.375
            "MODERADAMENTE ATIVO" -> 1.55
            "MUITO ATIVO" -> 1.725
            "EXTREMAMENTE ATIVO" -> 1.9
            else -> 1.2
        }
    }

    private fun calcularTmb(peso: Double, altura: Double, idade: Int, sexo: String): Double {
        return if (sexo.uppercase() == "M") {
            66 + (13.7 * peso) + (5 * altura * 100) - (6.8 * idade)
        } else {
            655 + (9.6 * peso) + (1.8 * altura * 100) - (4.7 * idade)
        }
    }

    private fun calcularGcd(tmb: Double, fatorAtividade: Double): Double {
        return tmb * fatorAtividade
    }
}