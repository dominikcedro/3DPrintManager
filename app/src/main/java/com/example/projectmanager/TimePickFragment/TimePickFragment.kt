package com.example.projectmanager.TimePickFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.projectmanager.R

class TimePickFragment: Fragment() {
    var onTimeChosenListener: OnTimeChosenListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_time_pick, container, false)
        val btChooseTime = view.findViewById<View>(R.id.btChooseTime)
        val timePicker = view.findViewById<TimePicker>(R.id.requestTimePicker)



        btChooseTime.setOnClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            onTimeChosenListener?.onTimeChosen(hour, minute)
            fragmentManager?.beginTransaction()?.remove(this)?.commit()

        }

        return view
    }
}
interface OnTimeChosenListener{
    fun onTimeChosen(hour: Int, minute: Int)
}