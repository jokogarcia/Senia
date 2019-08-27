package com.irazu.senia

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    var mapa = mutableMapOf<String,String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvMensaje.text = "-------"
        //Crear / cargar archivo de preferencias compartidas
        var prefs:SharedPreferences = getSharedPreferences("MisClaves", Context.MODE_PRIVATE)

        //Cargar valores guardados en mapa de claves
        var copy = prefs.all
        for((clave,valor) in copy){
            mapa.put(clave, valor as String)
        }

        //Crear interfaz para Spinner
        var identificadores = ArrayList(mapa.keys)
        val adaptador = ArrayAdapter(this,android.R.layout.simple_spinner_item,identificadores)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spIdentificador.adapter=adaptador
/*
* OnClickListener Botón GENERAR
*/
        btnGenerar.setOnClickListener{
            var pswd=RandomString.generar(charPool,8)
            tvMensaje.text = pswd
        }
        if(savedInstanceState != null){
            var tmp=savedInstanceState.getString(MENSAJE)
            tvMensaje.text = tmp
        }
/*
* OnClickListener Botón GUARDAR
*/

        btnGuardar.setOnClickListener{
            var nombre=etNombre.text.toString()
            var pass=tvMensaje.text.toString()
            if(nombre == null || nombre.length == 0){
                Toast.makeText(this,"Debe definir un nombre", Toast.LENGTH_LONG).show()
                //return@setOnClickListener
            } else {
                mapa.put(nombre, pass)
            }
            //Almacenar en SharedPreferences
            var editor = prefs.edit()
            editor.putString(nombre,pass)
            editor.commit()

            //Actualizar SPINNER
            adaptador.add(nombre)
            adaptador.notifyDataSetChanged()

        }
/*
* OnClickListener Botón OBTENER
*/

        btnObtener.setOnClickListener{
            val valorSeleccionado = spIdentificador.selectedItem.toString()
            if(mapa.containsKey(valorSeleccionado)) {
                tvMensaje.text = mapa.get(valorSeleccionado)
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

