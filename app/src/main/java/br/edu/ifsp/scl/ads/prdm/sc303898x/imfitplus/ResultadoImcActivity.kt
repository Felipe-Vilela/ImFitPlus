package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityResultadoImcBinding
import java.text.DecimalFormat

class ResultadoImcActivity : AppCompatActivity() {

    private val arib: ActivityResultadoImcBinding by lazy {
        ActivityResultadoImcBinding.inflate(layoutInflater)
    }
    lateinit var dadosPessoais: DadosPessoais

    private lateinit var gcarl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(arib.root)

        setSupportActionBar(arib.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.resultado_imc_subtitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        gcarl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result -> if (result.resultCode == RESULT_OK){}
        }

        dadosPessoais = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_PERFIL, DadosPessoais::class.java)!!
        }else {
            intent.getParcelableExtra<DadosPessoais>(Constant.EXTRA_PERFIL)!!
        }

        dadosPessoais.let { dados ->
            arib.nameTv.text = getString(R.string.resultado_nome, dados.nome)

            val imcFormatado = DecimalFormat("0.00").format(dados.imc!!)
            arib.imcTv.text = getString(R.string.resultado_imc, imcFormatado)

            val categoria = calcularCategoriaImc(dados.imc!!)
            arib.categoriaTv.text = getString(R.string.resultado_categoria, categoria)
        }


        arib.voltarBt.setOnClickListener {
            finish()
        }

        arib.gastoCaloricoBt.setOnClickListener {
            gcarl.launch(Intent(this, GastoCaloricoActivity::class.java).apply {
                putExtra(Constant.EXTRA_PERFIL, dadosPessoais)
            })
        }

    }

    private fun calcularCategoriaImc(imc: Double): String {
        return when {
            imc < 18.5 -> getString(R.string.abaixo_do_peso)
            imc < 25.0 -> getString(R.string.normal)
            imc < 30.0 -> getString(R.string.sobrepeso)
            else -> getString(R.string.obesidade)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
