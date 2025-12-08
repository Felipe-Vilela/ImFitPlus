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
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityPesoIdealBinding
import java.text.DecimalFormat

class PesoIdealActivity : AppCompatActivity() {

    private val apib: ActivityPesoIdealBinding by lazy {
        ActivityPesoIdealBinding.inflate(layoutInflater)
    }

    private lateinit var dadosPessoais: DadosPessoais

    private lateinit var rsarl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(apib.root)

        setSupportActionBar(apib.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.peso_ideal_subtitle)

        rsarl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result -> if (result.resultCode == RESULT_OK){}
        }

        dadosPessoais = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_PERFIL, DadosPessoais::class.java)!!
        }else {
            intent.getParcelableExtra<DadosPessoais>(Constant.EXTRA_PERFIL)!!
        }

        dadosPessoais.let { dados ->
            val pesoIdeal = calcularPesoIdeal(dados.altura!!)
            dados.pesoIdeal = pesoIdeal
            val diferencaPeso = pesoIdeal - dados.peso!!

            val df = DecimalFormat("0.00")
            val pesoIdealFormatado = df.format(pesoIdeal)
            var diferencaPesoFormatada = df.format(diferencaPeso)

            if (diferencaPeso > 0) {
                diferencaPesoFormatada = "+$diferencaPesoFormatada"
            }

            apib.pesoIdealResultadoTv.text = getString(R.string.seu_peso_ideal, pesoIdealFormatado)
            apib.diferencaPesoResultadoTv.text = getString(R.string.diferenca_peso, diferencaPesoFormatada)
        }

        apib.resumoSaudeBt.setOnClickListener {
            rsarl.launch(Intent(this, ResumoSaudeActivity::class.java).apply {
                putExtra(Constant.EXTRA_PERFIL, dadosPessoais)
            })
        }

        apib.voltarBt.setOnClickListener {
            finish()
        }
    }

    private fun calcularPesoIdeal(altura: Double): Double {
        return 22 * (altura * altura)
    }
}