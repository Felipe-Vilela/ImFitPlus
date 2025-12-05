package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.R
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityHistoricoBinding
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.DadosPessoais

class HistoricoActivity : AppCompatActivity() {

    private val ahb: ActivityHistoricoBinding by lazy {
        ActivityHistoricoBinding.inflate(layoutInflater)
    }

    private lateinit var historicoAdapter: HistoricoAdapter
    private var listaHistorico = ArrayList<DadosPessoais>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ahb.root)

        setSupportActionBar(ahb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.historico)

        listaHistorico = arrayListOf(
            DadosPessoais(
                nome = "João",
                idade = 25,
                sexo = "Masculino",
                altura = 1.80,
                peso = 80.0,
                nivelAtividade = "Moderado",
                imc = 24.7,
                tmb = null,
                gastoCalorico = null,
                pesoIdeal = null,
                categoriaImc = "Normal"
            ),
            DadosPessoais(
                nome = "Maria",
                idade = 30,
                sexo = "Feminino",
                altura = 1.65,
                peso = 92.0,
                nivelAtividade = "Baixo",
                imc = 33.8,
                tmb = null,
                gastoCalorico = null,
                pesoIdeal = null,
                categoriaImc = "Obesidade"
            )
        )

        historicoAdapter = HistoricoAdapter(listaHistorico)

        ahb.historicoRv.apply {
            layoutManager = LinearLayoutManager(this@HistoricoActivity)
            adapter = historicoAdapter
        }

        ahb.voltarBt.setOnClickListener { finish() }
    }
}
