package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.ui

import android.content.Intent
import android.os.Bundle
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.R
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    private val amb: ActivityMainBinding by lazy (){
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        setSupportActionBar(amb.toolbarIn.toolbar)

        supportActionBar?.subtitle = getString(R.string.boas_vindas_subtitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        amb.comeceBt.setOnClickListener {
            var intent = Intent(this, DadosPessoaisActivity::class.java)
            startActivity(intent)
        }

    }
}