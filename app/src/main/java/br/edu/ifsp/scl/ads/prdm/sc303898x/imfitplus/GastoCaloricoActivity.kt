package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityGastoCaloricoBinding

class GastoCaloricoActivity : AppCompatActivity() {

    private val agcb: ActivityGastoCaloricoBinding by lazy{
        ActivityGastoCaloricoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(agcb.root)

    }
}