package com.example.projectmanager.DatePickFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.projectmanager.R


class RequestDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.request_details_fragment, container, false)
        val tvRequestSubject = view.findViewById<TextView>(R.id.tvRequestSubject)
        val tvRequestAuthor = view.findViewById<TextView>(R.id.tvRequestAuthor)
        val tvRequestStartDate = view.findViewById<TextView>(R.id.tvRequestStart)
        val tvRequestEndDate = view.findViewById<TextView>(R.id.tvRequestEndDate)
        val tvRequestFilament = view.findViewById<TextView>(R.id.tvRequestFilament)
        val btnConfirm = view.findViewById<Button>(R.id.btnConfirm)
        // retrieve data from bundle and assign to textView
        val bundle = this.arguments
        val subject = bundle?.getString("subject")
        val author = bundle?.getString("author")
        val startDate = bundle?.getString("startDate")

        btnConfirm.setOnClickListener {
            @Suppress("DEPRECATION")
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
        return view
    }
}

