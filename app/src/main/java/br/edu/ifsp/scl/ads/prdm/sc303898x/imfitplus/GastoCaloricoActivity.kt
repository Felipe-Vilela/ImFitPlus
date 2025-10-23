package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityGastoCaloricoBinding
import java.text.DecimalFormat

class GastoCaloricoActivity : AppCompatActivity() {

    private val agcb: ActivityGastoCaloricoBinding by lazy{
        ActivityGastoCaloricoBinding.inflate(layoutInflater)
    }

    lateinit var dadosPessoais: DadosPessoais

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(agcb.root)

        setSupportActionBar(agcb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.gasto_calorico_subtitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dadosPessoais = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_PERFIL, DadosPessoais::class.java)!!
        }else {
            intent.getParcelableExtra<DadosPessoais>(Constant.EXTRA_PERFIL)!!
        }

        dadosPessoais.let { dados ->

            val fatorAtividade = obterFatorAtividade(dados.nivelAtividade)
            val tmb = calcularTmb(dados.peso, dados.altura, dados.idade, dados.sexo)
            val gcd = calcularGcd(tmb, fatorAtividade)

            dados.tmb = tmb
            dados.gastoCalorico = gcd

            val tmbFormatado = DecimalFormat("0").format(tmb)
            val gcdFormatado = DecimalFormat("0").format(gcd)

            agcb.tmbTv.text = getString(R.string.resultado_tmb, tmbFormatado)
            agcb.gcdTv.text = getString(R.string.resultado_gcd, gcdFormatado)
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
            (10 * peso) + (6.25 * (altura * 100)) - (5 * idade) + 5
        } else {
            (10 * peso) + (6.25 * (altura * 100)) - (5 * idade) - 161
        }
    }

    private fun calcularGcd(tmb: Double, fatorAtividade: Double): Double {
        return tmb * fatorAtividade
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}