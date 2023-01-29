package com.example.party_maker_android.presentation.fragments.views

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData

class TimePickerFragment: DialogFragment(), OnTimeSetListener {
    val cal = Calendar.getInstance()
    var selectedHour = MutableLiveData<Int>(cal.get(Calendar.HOUR_OF_DAY))
    var selectedMinute = MutableLiveData<Int>(cal.get(Calendar.MINUTE))


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        selectedHour.value = hourOfDay
        selectedMinute.value = minute
    }
}