package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.Constant
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.DadosPessoais
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.R
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityDadosPessoaisBinding
import kotlin.math.pow

class DadosPessoaisActivity : BaseActivity() {

    private val adpb: ActivityDadosPessoaisBinding by lazy {
        ActivityDadosPessoaisBinding.inflate(layoutInflater)
    }

    private lateinit var riarl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(adpb.root)

        setSupportActionBar(adpb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.dados_pessoais_subtitle)

        riarl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result -> if (result.resultCode == RESULT_OK){}
        }

        adpb.atividadeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val nivelAtividade = resources.getStringArray(R.array.niveis_atividade)[position]
                    Toast.makeText(
                        this@DadosPessoaisActivity,
                        "Você selecionou: $nivelAtividade",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

        adpb.calcularImcBt.setOnClickListener {
            if (!adpb.confirmarCb.isChecked) {
                Toast.makeText(this, "Confirme que os dados estão corretos antes de prosseguir.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dados = validarEntradas() ?: return@setOnClickListener
            riarl.launch(Intent(this, ResultadoImcActivity::class.java).apply {
                putExtra(Constant.EXTRA_PERFIL, dados)
            })

        }

        adpb.voltarBt.setOnClickListener {
            finish()
        }

    }

    private fun validarEntradas(): DadosPessoais? {
        val nome = adpb.nomeEt.text.toString().trim()
        val idadeStr = adpb.idadeEt.text.toString().trim()
        val pesoStr = adpb.pesoEt.text.toString().trim()
        val alturaStr = adpb.alturaEt.text.toString().trim()
        val nivelAtividadeSelecionado = adpb.atividadeSpinner.selectedItem.toString()

        if (nome.isBlank() || idadeStr.isBlank() || pesoStr.isBlank() || alturaStr.isBlank()) {
            Toast.makeText(this, R.string.erro_campo_vazio, Toast.LENGTH_SHORT).show()
            return null
        }

        val altura = alturaStr.toDoubleOrNull()
        val peso = pesoStr.toDoubleOrNull()
        val idade = idadeStr.toIntOrNull()

        if (altura == null || altura < 0.5 || peso == null || peso <= 0 || idade == null || idade <= 0) {
            Toast.makeText(this, R.string.erro_altura_invalida, Toast.LENGTH_SHORT).show()
            return null
        }

        val sexo = if (adpb.masculinoRb.isChecked) "M" else "F"
        val atividade = nivelAtividadeSelecionado.split(" (").first()
        val imcCalculado = calcularImc(peso, altura)

        return DadosPessoais(
            nome = nome,
            idade = idade,
            sexo = sexo,
            altura = altura,
            peso = peso,
            nivelAtividade = atividade,
            imc = imcCalculado
        )
    }
    private fun calcularImc(peso: Double, altura: Double): Double {
        return peso / altura.pow(2)
    }
}