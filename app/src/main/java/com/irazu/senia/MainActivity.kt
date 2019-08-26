package com.irazu.senia

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    var mapa = mutableMapOf<String,String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvMensaje.text = "-------"

        btnGenerar.setOnClickListener{
            var pswd=RandomString.generar(charPool,8)
            tvMensaje.text = pswd
        }
        if(savedInstanceState != null){
            var tmp=savedInstanceState.getString(MENSAJE)
            tvMensaje.text = tmp
        }
        btnGuardar.setOnClickListener{
            var tmp=etNombre.text
            if(etNombre.text == null || etNombre.text.length == 0){
                Toast.makeText(this,"Debe definir un nombre", Toast.LENGTH_LONG).show()
                //return@setOnClickListener
            } else {
                mapa.put(etNombre.text.toString(), tvMensaje.text.toString())
            }
        }
        btnObtener.setOnClickListener{
            if(mapa.containsKey(etNombre.text.toString())) {
                tvMensaje.text = mapa.get(etNombre.text.toString())
            }
            else{
                tvMensaje.text = "Clave no encontrada"
            }


        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run{
            var tmp=tvMensaje.text.toString()
            putString(MENSAJE,tmp)
        }
        super.onSaveInstanceState(outState)

    }
companion object{
    val MENSAJE ="mensaje"
}
}

