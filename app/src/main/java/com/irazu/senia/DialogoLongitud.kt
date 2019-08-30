package com.irazu.senia

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import kotlinx.android.synthetic.main.dialogo_longitud.*
import java.lang.ClassCastException
import java.lang.IllegalStateException


class DialogoLongitud : AppCompatDialogFragment(){
    internal lateinit var listener : InterfazDialogo
    private var _view: View? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(activity)
        var inflater = activity?.layoutInflater
        var view = inflater?.inflate(R.layout.dialogo_longitud,null)
        return activity?.let{
            builder.setView(view)
                .setTitle("Longitud de contraseña")
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id ->
                        //Accion Negativa
                    })
                .setPositiveButton("Listo",
                    DialogInterface.OnClickListener { dialog, id ->
                        //Accion Positiva
                        val et = view!!.findViewById<EditText>(R.id.etLargo)
                        val n = et.text.toString().toInt()
                        listener.enAccionPositiva(this,n)


                    })
            builder.create()
        }?: throw IllegalStateException("La actividad no puede ser nula")

    }


    interface InterfazDialogo{
        fun enAccionPositiva(dialogo:DialogoLongitud, n:Int)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try{
            listener = context as InterfazDialogo
        }catch (e: ClassCastException){
            throw(ClassCastException(context.toString()+"debe implementar la interfaz IntefazDialogo"))
        }
    }
}