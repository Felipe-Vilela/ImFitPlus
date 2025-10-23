package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityPesoIdealBinding

class PesoIdealActivity : AppCompatActivity() {

    private val apib: ActivityPesoIdealBinding by lazy {
        ActivityPesoIdealBinding.inflate(layoutInflater)
    }

    lateinit var dadosPessoais: DadosPessoais

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(apib.root)

        setSupportActionBar(apib.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.peso_ideal_subtitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dadosPessoais = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constant.EXTRA_PERFIL, DadosPessoais::class.java)!!
        }else {
            intent.getParcelableExtra<DadosPessoais>(Constant.EXTRA_PERFIL)!!
        }
    }
}