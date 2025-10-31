package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityResumoSaudeBinding
import java.text.DecimalFormat

class ResumoSaudeActivity : AppCompatActivity() {

    private val arsb: ActivityResumoSaudeBinding by lazy {
        ActivityResumoSaudeBinding.inflate(layoutInflater)
    }

    lateinit var dadosPessoais: DadosPessoais

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(arsb.root)

        setSupportActionBar(arsb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.resumo_saude_subtitle)

        dadosPessoais = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_PERFIL, DadosPessoais::class.java)!!
        }else {
            intent.getParcelableExtra<DadosPessoais>(Constant.EXTRA_PERFIL)!!
        }

        dadosPessoais.let { dados ->
            arsb.nameTv.text = getString(R.string.resultado_nome, dados.nome)

            val imcFormatado = DecimalFormat("0.00").format(dados.imc!!)
            arsb.imcTv.text = getString(R.string.resultado_imc, imcFormatado)

        }



    }
}