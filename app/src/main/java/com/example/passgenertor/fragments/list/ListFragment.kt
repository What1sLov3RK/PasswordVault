package com.example.passgenertor.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passgenertor.R
import com.example.passgenertor.data.Password
import com.example.passgenertor.data.PasswordViewModel


class ListFragment : Fragment(), ListAdapter.OnDeleteClickListener {

    private lateinit var mPasswordViewModel: PasswordViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mUserViewModel = ViewModelProvider(this)[PasswordViewModel::class.java]
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        val adapter = ListAdapter()
        val closeButton = view.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.remove(this)
            transaction.commit()
        }
        recyclerView.adapter = adapter
        adapter.onDeleteClickListener = this
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mPasswordViewModel = ViewModelProvider(this)[PasswordViewModel::class.java]
        mPasswordViewModel.readAllData.observe(viewLifecycleOwner, Observer { passwords ->
            adapter.setData(passwords)
        })
        return view
    }

    override fun onDeleteClick(password: Password) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Видалити пароль")
            .setMessage("Ви впевнені що хочете видалити пароль?")
            .setPositiveButton("Так") { _, _ ->
                mPasswordViewModel.deletePassword(password)
                Toast.makeText(requireContext(), "Пароль видалено", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Ні") { _, _ -> }
            .show()
    }
}