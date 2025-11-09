package com.example.passgenertor.fragments.list


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.passgenertor.data.Password
import com.example.passgenertor.R


class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var passList = emptyList<Password>()
    var onDeleteClickListener: OnDeleteClickListener? = null
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return passList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = passList[position]
        val urlTextView = holder.itemView.findViewById<TextView>(R.id.urlTextView)
        val passwordTextView = holder.itemView.findViewById<TextView>(R.id.passwordTextView)
        val deleteButton = holder.itemView.findViewById<Button>(R.id.deleteButton)

        deleteButton.setOnClickListener {
            onDeleteClickListener?.onDeleteClick(currentItem)
        }
        urlTextView.text = currentItem.url
        passwordTextView.text = currentItem.password

        urlTextView.setOnClickListener {
            copyToClipboard(holder.itemView.context, "Password", currentItem.url)
        }
    }

    fun setData(password: List<Password>) {
        this.passList = password
        notifyDataSetChanged()
    }

    private fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(context, "Пароль скопійовано", Toast.LENGTH_SHORT).show()
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(password: Password)
    }
}
