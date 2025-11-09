package com.example.passgenertor

import android.R.attr.text
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.passgenertor.data.Password
import com.example.passgenertor.data.PasswordViewModel
import com.example.passgenertor.fragments.list.ListFragment



class MainActivity : AppCompatActivity() {
    private lateinit var mUserViewModel: PasswordViewModel
    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupMainView()
    }

    private fun setupMainView(){
        setContentView(R.layout.activity_main)
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val Button_generate: Button = findViewById<Button>(R.id.button_generate)
        val password_field: EditText = findViewById<EditText>(R.id.password_field)
        val password_length_text: TextView = findViewById<TextView>(R.id.textView2)
        val length_seek: SeekBar = findViewById<SeekBar>(R.id.seekBar)
        val Button_save: Button = findViewById<Button>(R.id.button_save_generated)
        val button_show_all: Button = findViewById<Button>(R.id.pass_list_button)

        length_seek.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                password_length_text.text = "Довжина паролю: " + length_seek.progress
            }

            override fun onStartTrackingTouch(seek: SeekBar){
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })

        Button_generate.setOnClickListener{
            password_field.setText(generatePassword(length_seek.progress))
        }

        password_field.setOnClickListener{
            copyToClipboard(clipboardManager, "text", password_field.text.toString())

        }

        Button_save.setOnClickListener{
            setupAddView(password_field.text.toString())
        }

        button_show_all.setOnClickListener{
            val listFragment = ListFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, listFragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }

    }


     fun setupAddView(pass: String="Pass"){
        mUserViewModel = ViewModelProvider(this)[PasswordViewModel::class.java]
        setContentView(R.layout.fragment_add)
        val password_field_saving :EditText = findViewById<EditText>(R.id.password_saving_field)
        val url_save: EditText = findViewById<EditText>(R.id.url_saving_field)
        val button_back = findViewById<Button>(R.id.button_back_to_main_from_add)
        val button_save_to_db: Button =findViewById<Button>(R.id.save_to_db_button)
        button_back.setOnClickListener{
            setupMainView()
        }
        if(pass !="Press button generate") {
            password_field_saving.setText(pass)
        }
        button_save_to_db.setOnClickListener{
            val url = url_save.text.toString()
            val password = password_field_saving.text.toString()
            if(password!="") {
                val newPass = Password(0, url, password)
                mUserViewModel.addPassword(newPass)
                Toast.makeText(this, "Пароль збережено", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Поле паролю пусте", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun copyToClipboard(clipboardManager: ClipboardManager, label: String, text: String) {
        val clip = ClipData.newPlainText(label, text)
        clipboardManager.setPrimaryClip(clip)
    }
}



fun generatePassword(length: Int=8 ): String {
    val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val password = StringBuilder()

    repeat(length) {
        val randomIndex = (0 until charset.length).random()
        password.append(charset[randomIndex])
    }
    return password.toString()
}


