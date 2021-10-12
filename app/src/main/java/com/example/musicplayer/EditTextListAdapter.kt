package com.example.musicplayer

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class EditTextListAdapter(
    private val hints: Array<String>,
    private val inputTypes: Array<Int>
) :
    RecyclerView.Adapter<EditTextListAdapter.InputFieldViewHolder>() {

    private val typedTexts = Array(hints.size) { _ -> "" }

    inner class InputFieldViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var inputFieldObject: EditText? = null

        init {
            inputFieldObject = itemView.findViewById(R.id.list_input_field)

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
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.login_input_field, parent, false)
        return InputFieldViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InputFieldViewHolder, position: Int) {
        holder.inputFieldObject?.hint = hints[position]
        holder.inputFieldObject?.inputType = inputTypes[position]
    }

    fun getTypedTexts() = typedTexts

    override fun getItemCount() = hints.size
}