package com.example.projectmanager.DatePickFragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.projectmanager.R


class DatePickFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_date_pick, container, false)
        val btChooseDate = view.findViewById<Button>(R.id.btChooseDate)
        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
        datePicker.setOnDateChangedListener{ view, year, monthOfYear, dayOfMonth ->

        val bundle = Bundle()
        bundle.putInt("day", datePicker.dayOfMonth)
        bundle.putInt("month", datePicker.month)
        bundle.putInt("year", datePicker.year)
        this.arguments = bundle
        }

        btChooseDate.setOnClickListener {
            // Close the fragment
            fragmentManager?.popBackStack()
        }
        return view
    }
}