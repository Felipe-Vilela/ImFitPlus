package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityPesoIdealBinding

class PesoIdealActivity : AppCompatActivity() {

    private val apib: ActivityPesoIdealBinding by lazy {
        ActivityPesoIdealBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(apib.root)

        setSupportActionBar(apib.toolbarIn.toolbar)
        supportActionBar?.subtitle = getString(R.string.peso_ideal_subtitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}