package com.example.musicplayer

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.LoginInputFieldBinding

class EditTextListAdapter(
    private val hints: Array<String>,
    private val inputTypes: Array<Int>
) :
    RecyclerView.Adapter<EditTextListAdapter.InputFieldViewHolder>() {

    private val typedTexts = Array(hints.size) { _ -> "" }

    inner class InputFieldViewHolder(private val binding: LoginInputFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var inputFieldObject: EditText? = null

        init {
            inputFieldObject = binding.listInputField

            inputFieldObject?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    typedTexts[adapterPosition] = p0.toString()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputFieldViewHolder {
        return InputFieldViewHolder(
            LoginInputFieldBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: InputFieldViewHolder, position: Int) {
        holder.inputFieldObject?.hint = hints[position]
        holder.inputFieldObject?.inputType = inputTypes[position]
    }

    fun getTypedTexts() = typedTexts

    override fun getItemCount() = hints.size
}