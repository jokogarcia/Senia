package com.irazu.senia

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    var mapa = mutableMapOf<String,String>()
    lateinit var prefs:SharedPreferences
    lateinit var adaptador:ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Crear / cargar archivo de preferencias compartidas
        prefs = getSharedPreferences("MisClaves", Context.MODE_PRIVATE)

        //Cargar valores guardados en mapa de claves
        val copy = prefs.all
        for ((clave, valor) in copy) {
            mapa.put(clave, valor as String)
        }

        //Crear interfaz para Spinner
        adaptador = ArrayAdapter(this, R.layout.elemento_spinner, ArrayList(mapa.keys))
        adaptador.setDropDownViewResource(R.layout.elemento_spinner_desplegado)
        spIdentificador.adapter = adaptador
        spIdentificador.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val valorSeleccionado = parent?.adapter?.getItem(position).toString()
                if (mapa.containsKey(valorSeleccionado)) {
                    tvMensaje.text = mapa.get(valorSeleccionado)
                }

            }

        }

/*
* OnClickListener Botón GENERAR
*/
        ibtnGenerar.setOnClickListener {
            val intent = Intent(this, GenerarClave::class.java)
            startActivityForResult(intent, GENERAR_CLAVE)
        }
        if (savedInstanceState != null) {
            val tmp = savedInstanceState.getString(MENSAJE)
            tvMensaje.text = tmp
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run{
            val tmp=tvMensaje.text.toString()
            putString(MENSAJE,tmp)
        }
        super.onSaveInstanceState(outState)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        if (requestCode == GENERAR_CLAVE) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                val nuevaClave = data?.extras?.getString("clave") ?: ""
                val nuevoNombre = data?.extras?.getString("nombre") ?: ""
                agregarValores(nuevoNombre,nuevaClave)
            }
        }
    }
    fun agregarValores(nombre:String,clave:String){

        if(nombre =="" || clave == "")
            return
        //Agregar al mapa actual
        mapa.put(nombre, clave)

        //Agregar a almacenamiento permanente
        val editor = prefs.edit()
        editor.putString(nombre, clave)
        editor.commit()

        //Actualizar SPINNER
        adaptador.add(nombre)
        adaptador.notifyDataSetChanged()
    }
    companion object{
        val MENSAJE ="mensaje"
        val GENERAR_CLAVE = 0
    }

}

