package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy (){
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     }
}