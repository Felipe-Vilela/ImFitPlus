package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityDadosPessoaisBinding
import kotlin.math.pow

class DadosPessoaisActivity : AppCompatActivity() {

    private val adpb: ActivityDadosPessoaisBinding by lazy {
        ActivityDadosPessoaisBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(adpb.root)

        setSupportActionBar(adpb.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.dados_pessoais_subtitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adpb.atividadeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val nivelAtividade = parent?.getItemAtPosition(position).toString()

                    Toast.makeText(
                        this@DadosPessoaisActivity,
                        "Você selecionou: $nivelAtividade",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

        with (adpb) {
            calcularImcBt.setOnClickListener {

                val nome = nomeEt.text.toString().trim()
                val idadeStr = idadeEt.text.toString().trim()
                val pesoStr = pesoEt.text.toString().trim()
                val alturaStr = alturaEt.text.toString().trim()
                val nivelAtividadeSelecionado = atividadeSpinner.selectedItem.toString()

                if (nome.isBlank() || idadeStr.isBlank() || pesoStr.isBlank() || alturaStr.isBlank()) {
                    Toast.makeText(this@DadosPessoaisActivity, R.string.erro_campo_vazio, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val altura = alturaStr.toDoubleOrNull()
                val peso = pesoStr.toDoubleOrNull()
                val idade = idadeStr.toIntOrNull()

                if (altura == null || altura < 0.5 || peso == null || peso <= 0 || idade == null || idade <= 0) {
                    Toast.makeText(this@DadosPessoaisActivity, R.string.erro_altura_invalida, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val sexo = if (masculinoRb.isChecked) "M" else "F"
                val atividade = nivelAtividadeSelecionado.split(" (").first()
                val imcCalculado = peso / altura.pow(2)

                DadosPessoais(
                    nome = nome,
                    idade = idade,
                    sexo = sexo,
                    altura = altura,
                    peso = peso,
                    nivelAtividade = atividade,
                    imc = imcCalculado
                ).let { perfil ->
                    Intent(this@DadosPessoaisActivity, ResultadoImcActivity::class.java).apply {
                        putExtra(Constant.EXTRA_PERFIL, perfil)
                        startActivity(this)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}