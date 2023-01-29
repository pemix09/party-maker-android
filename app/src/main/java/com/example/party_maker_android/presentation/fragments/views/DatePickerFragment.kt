package com.example.party_maker_android.presentation.fragments.views

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.example.party_maker_android.R
import java.util.*


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    val cal = android.icu.util.Calendar.getInstance()
    var selectedYear = MutableLiveData<Int>(cal.get(android.icu.util.Calendar.YEAR))
    var selectedMonth = MutableLiveData<Int>(cal.get(Calendar.MONTH) + 1)
    var selectedDay = MutableLiveData<Int>(cal.get(Calendar.DAY_OF_MONTH))
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        selectedYear.value = year
        selectedMonth.value = month + 1
        selectedDay.value = day
    }
}