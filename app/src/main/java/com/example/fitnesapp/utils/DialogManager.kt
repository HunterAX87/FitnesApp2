package com.example.fitnesapp.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.WeightDialogBinding
import java.nio.file.attribute.AclEntry.Builder

object DialogManager {
    fun showDialog(context: Context, massageId: Int, listener:Listener){

        val builder= AlertDialog.Builder(context)
        var dialog: AlertDialog?= null
        builder.setTitle(R.string.alert)
        builder.setMessage(massageId)

        builder.setPositiveButton(R.string.clean){ _,_->
            listener.onClick()
            dialog?.dismiss()
        }
        builder.setNegativeButton(R.string.cancel){ _,_->
            dialog?.dismiss()
        }
        dialog=builder.create()
        dialog.show()
    }

    fun showWeightDialog(context: Context, listener:WeightListener){

        val builder= AlertDialog.Builder(context)
        var dialog=builder.create()
        val binding= WeightDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setView(binding.root)

        binding.apply {
            bCancel.setOnClickListener {
                dialog.dismiss()
            }
            bSave.setOnClickListener {
                listener.onClick(edWeight.text.toString())
                dialog.dismiss()
            }
        }

        dialog.show()
    }


    interface Listener{
        fun onClick()
    }
    interface WeightListener{
        fun onClick(weight: String)
    }
}