package com.irazu.senia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_generar_clave.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btnGenerar
import kotlinx.android.synthetic.main.content_generar_clave.*

class GenerarClave : AppCompatActivity() {
    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generar_clave)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            var intentDeRetorno= Intent()
            intentDeRetorno.putExtra("clave",tvClave.text.toString())
            intentDeRetorno.putExtra("nombre",etNombre2.text.toString())
            setResult(Activity.RESULT_OK,intentDeRetorno)
            finish()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        /*
* OnClickListener Botón GENERAR
*/
        btnGenerar.setOnClickListener{
            var pswd=RandomString.generar(charPool,8)
            tvClave.text = pswd
        }
        if(savedInstanceState != null){
            var tmp=savedInstanceState.getString(MENSAJE)
            tvClave.text = tmp

        }
        btnGenerar.callOnClick()
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run{
            var tmp=tvClave.text.toString()
            putString(MENSAJE,tmp)
        }
        super.onSaveInstanceState(outState)

    }
    companion object{
        val MENSAJE ="mensaje"
    }

}
