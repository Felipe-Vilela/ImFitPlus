package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.ui

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.R
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.controller.HistoricoController
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityHistoricoBinding
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.Constant.EXTRA_PERFIL
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.DadosPessoais

class HistoricoActivity : AppCompatActivity() {

    private val ahb: ActivityHistoricoBinding by lazy {
        ActivityHistoricoBinding.inflate(layoutInflater)
    }

    private val historicoList: MutableList<DadosPessoais> = mutableListOf()

    private val historicoAdapter: HistoricoAdapter by lazy {
        HistoricoAdapter(this, historicoList)
    }

    private val historicoController: HistoricoController by lazy {
        HistoricoController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ahb.root)

        setSupportActionBar(ahb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.historico)

        fillHistoricoList()

        ahb.historicoLv.adapter = historicoAdapter

        val dadosPessoais: DadosPessoais? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(EXTRA_PERFIL, DadosPessoais::class.java)
        } else {
            intent?.getParcelableExtra<DadosPessoais>(EXTRA_PERFIL)
        }

        dadosPessoais?.let { dp ->
            val position = historicoList.indexOfFirst { it.id == dp.id }
            if (position == -1) {
                historicoList.add(dp)
                historicoController.insertHistorico(dp)
            } else {
                historicoList[position] = dp
                historicoController.modifyHistorico(dp)
            }
            historicoAdapter.notifyDataSetChanged()
        }

        ahb.voltarBt.setOnClickListener {
            finish()
        }
    }

    private fun fillHistoricoList(){
        historicoList.clear()
        Thread{
            historicoList.addAll(historicoController.getHistoricos())
            historicoAdapter.notifyDataSetChanged()
        }.start()
    }
}