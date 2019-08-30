package com.irazu.senia

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_generar_clave.*

import kotlinx.android.synthetic.main.content_generar_clave.*
import kotlinx.android.synthetic.main.dialogo_longitud.*

class GenerarClave : AppCompatActivity(),DialogoLongitud.InterfazDialogo {
    var charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val minusculas = "abcdefghijklmnopqrstuvwxyz"
    val mayusculas = minusculas.toUpperCase()
    val numeros = "0123456789"
    val simbolos = "!·$%&/()=?¿ºª\\|@#~€¬`^*¨Ç´´-/*+[]{}}<>"
    var largo=8
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
            var pswd=RandomString.generar(charPool,largo)
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
    fun barraOnClick(view:View){
        if(view.id == btLongitud.id){
            val dialogo = DialogoLongitud()
            dialogo.show(supportFragmentManager,"NoticeDialogFragment")
            return
        }
        if(!(btMinuscula.isChecked || btMayuscula.isChecked || btNumeros.isChecked || btSimbolos.isChecked))
            btMinuscula.isChecked = true

        if(view is ToggleButton){
            val color = if (view.isChecked)resources.getColor(R.color.barraToggleOn) else resources.getColor(R.color.barraToggleOff)
            view.background = ColorDrawable(color)
        }
        var pool = ""
        if(btMinuscula.isChecked)
            pool += minusculas
        if(btMayuscula.isChecked)
            pool += mayusculas
        if(btNumeros.isChecked)
            pool += numeros
        if(btSimbolos.isChecked)
            pool += simbolos
        charPool = pool.toList()
        btnGenerar.callOnClick()

    }

    companion object{
        val MENSAJE ="mensaje"
    }
    override fun enAccionPositiva(dialogo: DialogoLongitud, n:Int) {
        largo = n
        btnGenerar.callOnClick()

    }

}
