package com.example.passgenertor.fragments.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.passgenertor.R
import com.example.passgenertor.data.PasswordViewModel


class addFragment : Fragment() {

    private lateinit var mUserViewModel: PasswordViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        return view
    }

    private fun insertDataToDatabase() {

    }

}