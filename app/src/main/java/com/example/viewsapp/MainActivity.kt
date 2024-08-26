package com.example.viewsapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.myButton)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        val radioButton = findViewById<RadioButton>(R.id.radioButton)
        val nextActivityButton = findViewById<Button>(R.id.nextActivity)
        val alertDialogButton = findViewById<Button>(R.id.alertDialogButton)
        val dateButton = findViewById<Button>(R.id.dateButton)
        val dateTextview = findViewById<TextView>(R.id.dateTextview)
        val timeButton = findViewById<Button>(R.id.timeButton)
        val timeTextview = findViewById<TextView>(R.id.timeTextview)
        val popupMenuButton = findViewById<Button>(R.id.popupMenuButton)

        val spinner = findViewById<Spinner>(R.id.spinnerItems)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        button.setOnClickListener {
            Toast.makeText(this, "Button clicked...", Toast.LENGTH_SHORT).show()
        }

        nextActivityButton.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Checkbox checked", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Checkbox Unchecked", Toast.LENGTH_SHORT).show()
            }
        }

        radioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "RadiBox checked...", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "RadioBox Unchecked...", Toast.LENGTH_SHORT).show()
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textView.text = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
//                TODO("Not yet implemented")
            }
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                Toast.makeText(this@MainActivity, selectedItem, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
//                TODO("Not yet implemented")
            }

        }

        alertDialogButton.setOnClickListener {
            val builder = androidx.appcompat.app.AlertDialog.Builder(this)
            builder.setTitle("Alert!!")
            builder.setMessage("Alert message...")
            builder.setPositiveButton("Ok") { dialog, _ ->
                Toast.makeText(this, "Ok clicked...", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                Toast.makeText(this, "Cancel clicked...", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.show()
        }

        dateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedYear/${selectedMonth + 1}/$selectedDay"
                    dateTextview.text = selectedDate
                }, year, month, day)

            datePickerDialog.show()
        }

        timeButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                timeTextview.text = selectedTime
            }, hour, minute, true)

            timePickerDialog.show()
        }

        popupMenuButton.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.menuInflater.inflate(R.menu.main_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.about_menu -> {
                        Toast.makeText(this, "About clicked...", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.settings_menu -> {
                        Toast.makeText(this, "Settings clicked...", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.profile_menu -> {
                        Toast.makeText(this, "Profile clicked...", Toast.LENGTH_SHORT).show()
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}