package com.vaibhavjoshi.todolist.view.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.vaibhavjoshi.todolist.databinding.DialogAddNotesBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("UseGetLayoutInflater", "SetTextI18n")
@AndroidEntryPoint
class DialogAddNotes: DialogFragment() {

    private var _binding: DialogAddNotesBinding? = null
    private val binding get() = _binding!!
    var onAddItem : ((String) -> Unit)? = null
    var onUpdateItem : ((String) -> Unit)? = null
    private var isUpdate : Boolean = false
    private var text : String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddNotesBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        arguments?.let {
            isUpdate = it.getBoolean("IsUpdate",isUpdate)
            text = it.getString("text",text)
        }

        if (!isUpdate){
            binding.addNoteButton.text = "Add Note"
        }else{
            binding.addNoteButton.text = "Update Note"
            binding.addNoteText.setText(text)
        }
        addHostClick()
        return dialog
    }

    // On Start Method
    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun addHostClick(){
        binding.addNoteButton.setOnClickListener {
            if (!isUpdate) {
                onAddItem?.invoke(binding.addNoteText.text.toString().trim())
                dismiss()
            }
            else{
                onUpdateItem?.invoke(binding.addNoteText.text.toString().trim())
                dismiss()
            }
        }
    }
}