package com.hitesh.snohassigment

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.navigation.NavigationView
import com.hitesh.snohassigment.databinding.ActivityMain2Binding
import com.hitesh.snohassigment.databinding.BottomsheetAddItemBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private lateinit var binding2: BottomsheetAddItemBinding

    private lateinit var database: TheProjectDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        database = TheProjectDatabase.getDatabase(this)

        binding.appBarMain.fab.setOnClickListener {
            showBottomSheetDialog()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity2, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun showBottomSheetDialog() {

        binding2 = BottomsheetAddItemBinding.inflate(layoutInflater)

        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(binding2.root)
        bottomSheetDialog.show()
        bottomSheetDialog.setCanceledOnTouchOutside(true)

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        lateinit var date: String

        binding2.etDate.setOnClickListener {
            val today = MaterialDatePicker.todayInUtcMilliseconds()
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = today
            calendar[Calendar.YEAR] = 2022
            val startDate = calendar.timeInMillis

            calendar.timeInMillis = today
            calendar[Calendar.YEAR] = 2050
            val endDate = calendar.timeInMillis

            val constraints: CalendarConstraints = CalendarConstraints.Builder()
                .setOpenAt(startDate)
                .setStart(startDate)
                .setEnd(endDate)
                .build()

            val datePickerBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker
                .Builder
                .datePicker()
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText("Select date of task")
                .setCalendarConstraints(constraints)
            val datePicker = datePickerBuilder.build()
            datePicker.show(supportFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener {
                date = sdf.format(it)
                binding2.etDate.setText(date)
            }
        }

        binding2.btnSave.setOnClickListener {
            if (binding2.etTask.text!!.isEmpty()) {
                binding2.etTaskBox.requestFocus()
                binding2.etTaskBox.error = "Please enter task name"
            }
            if (binding2.etDate.text!!.isEmpty()) {
                binding2.etDateBox.requestFocus()
                binding2.etDateBox.error = "Please enter task due date"
            }
            if (binding2.etDescription.text!!.isEmpty()) {
                binding2.etDescriptionBox.requestFocus()
                binding2.etDescriptionBox.error = "Please enter task description"
            }
            if(!binding2.etTask.text!!.isEmpty() && !binding2.etDate.text!!.isEmpty() && !binding2.etDescription.text!!.isEmpty()){
                GlobalScope.launch {
                    database.theProjectDao().insertTask(
                        TheProject(
                            0,
                            binding2.etTask.text.toString(),
                            date,
                            binding2.etDescription.text.toString()
                        )
                    )
                }
                bottomSheetDialog.dismiss()
            }
        }
    }

}