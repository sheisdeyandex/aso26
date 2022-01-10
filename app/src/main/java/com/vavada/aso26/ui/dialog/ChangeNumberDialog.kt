package com.vavada.aso26.ui.dialog

import android.content.Context
import android.os.Bundle
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.DialogFragment

import com.vavada.aso26.interfaces.UiChangeInterface
import com.vavada.aso26.databinding.ChangeNumberDialogBinding
import com.vavada.aso26.interfaces.dialogChangeInterface
import com.vavada.aso26.ui.MainActivity
import com.vavada.aso26.ui.fragment.RegisterFragment


class ChangeNumberDialog(context: Context, changeNumberTitle:String, changeNumberNo:String, changeNumberYes:String, changeInterface: dialogChangeInterface) : DialogFragment() {
var  context1:Context?=null
    var changeNumberTitle:String = ""
    var changeNumberNo:String = ""
    var changeNumberYes:String = ""
     var changeInterface: dialogChangeInterface
    init {
        this.changeNumberTitle = changeNumberTitle
        this.changeNumberYes = changeNumberYes
        this.changeNumberNo = changeNumberNo
        this.context1 = context
        this.changeInterface = changeInterface
    }
    private var _binding: ChangeNumberDialogBinding? = null
    private val binding get() = _binding!!
   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = ChangeNumberDialogBinding.inflate(inflater, container,false)
       val view= binding.root
       requireDialog().getWindow()!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
hideSystemUI()
binding.btnNo.text = changeNumberNo
       binding.btnYes.text = changeNumberYes
       binding.txtDia.text = changeNumberTitle
       requireDialog().setCanceledOnTouchOutside(true)
binding.btnNo.setOnClickListener { dismiss() }
binding.btnYes.setOnClickListener {
    dismiss()
    changeInterface.show(requireContext())
}

      // binding.btnYes.setOnClickListener {   Log.d("sukawtf", "")}
       return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        dialog.let { super.onDismiss(it) }
    }

    override fun onCancel(dialog: DialogInterface) {
        dialog.let { super.onCancel(it) }
    }
    private fun hideSystemUI() {
        requireDialog().window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }
        requireDialog().window?.let {
            WindowInsetsControllerCompat(it, requireDialog().window!!.decorView).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}