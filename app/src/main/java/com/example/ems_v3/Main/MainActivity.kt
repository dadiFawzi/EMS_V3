package com.example.ems_v3.Main

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.ems_v3.AddActivity
import com.example.ems_v3.R
import com.example.ems_v3.Report.ReportActivity
import com.example.ems_v3.Report.ReportAdapter
import com.example.ems_v3.SettingActivity
import com.example.ems_v3.customer.CustomerActivity
import com.example.ems_v3.database.AppDatabase
import com.example.ems_v3.database.UserDao
import com.example.ems_v3.databinding.ActivityMainBinding
import com.example.ems_v3.model.Role
import com.example.ems_v3.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.List

class MainActivity : AppCompatActivity() {




    private lateinit var addButton: FloatingActionButton
    private lateinit var homeButton:View
    private lateinit var reportButton:View
    private lateinit var settingButton:View
    private lateinit var customerButton:View

    lateinit var appDatabase: AppDatabase
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addButton = findViewById(R.id.fab)
       val navView: BottomNavigationView = binding.navView

        //create database
        appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "EMS")
            .build()
        println("################# get all user " )
        Thread {
            val users = listOf(
                User(username = "admin", email = "admin@example.com", password = "admin123", photo = "admin.jpg", role = Role.ADMIN),
                User(username = "backoffice", email = "back@example.com", password = "back123", photo = "back.jpg", role = Role.BACKOFFICE),
                User(username = "user1", email = "user1@example.com", password = "user123", photo = "user1.jpg", role = Role.USER),
                User(username = "user2", email = "user2@example.com", password = "user456", photo = "user2.jpg", role = Role.USER)
            )

            appDatabase.userDao().insertAll(users)

            println(appDatabase.userDao().loadAllUsers().get(0).toString())
            println("################# get all user " )
        }.start()


        //adapter
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewExpenses)
        val dataList = getListOfItems() // Update this function to add more items
        val adapter = HomeAdapter(dataList as List<ExpenseItem>)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)





        // to display months in the top of the main view
        val monthsContainer: LinearLayout = findViewById(R.id.monthsContainer)
        // Get the array of months from resources
        val monthsArray = resources.getStringArray(R.array.months_array)


        // Create TextView elements for each month and add to the LinearLayout
        for (month in monthsArray) {
            val textViewMonth = TextView(this)
            textViewMonth.text = month
            textViewMonth.textSize = 16f
            textViewMonth.setPadding(8, 0, 8, 0)
            textViewMonth.isClickable = true
            textViewMonth.isFocusable = true
            textViewMonth.setTextColor(resources.getColor(android.R.color.black, null))
            monthsContainer.addView(textViewMonth)
        }

        // Set click listeners for each TextView to handle month selection
        for (i in 0 until monthsContainer.childCount) {
            val textViewMonth = monthsContainer.getChildAt(i) as TextView
            textViewMonth.setOnClickListener {
                    v ->
                // Deselect all items
                for (j in 0 until monthsContainer.childCount) {
                    monthsContainer.getChildAt(j).isSelected = false
                    monthsContainer.getChildAt(j).setBackgroundColor(resources.getColor(R.color.white))
                }
                // Select the clicked item
                v.isSelected = true
                v.setBackgroundColor(resources.getColor(R.color.selected_month))


                // Perform actions based on the selected item if needed
                // Handle the click event for month selection
                // You can update the RecyclerView data here based on the selected month
                // For example, filter the data for the selected month and update the adapter
                // recyclerViewExpenses.adapter.notifyDataSetChanged()


            }
        }



        homeButton =findViewById<View>(R.id.navigation_home)
        reportButton =findViewById<View>(R.id.navigation_report)
        settingButton =findViewById<View>(R.id.navigation_user)
        customerButton = findViewById<View>(R.id.navigation_client)


        addButton.setOnClickListener{
        val intent = Intent(this, AddActivity::class.java)
            navView.selectedItemId=0 ;
        startActivity(intent)
    }
        homeButton.setOnClickListener{
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
}
        reportButton.setOnClickListener{
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
        }
        customerButton.setOnClickListener{
            val intent = Intent(this, CustomerActivity::class.java)
            startActivity(intent)
        }
        settingButton.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getListOfItems(): Any {
        val expenses1 = listOf(
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0),
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0)
        )

        val expenses2 = listOf(
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0),
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0)
        )
        val expenses3 = listOf(
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0),
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0)
        )
        val expenses4 = listOf(
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0),
            Pair("Expense Type 3", 70.0),
            Pair("Expense Type 4", 8.0)
        )

// Initialize the list of ExpenseItem
        val expenseItem: kotlin.collections.List<ExpenseItem> = listOf(
            ExpenseItem("2023-07-24", "Business Trip 1", 125.30,"Customer A", expenses1),
            ExpenseItem("2023-07-25", "Business Trip 2", 85.40,"Customer B", expenses2),
            ExpenseItem("2023-07-24", "Business Trip 3", 125.30,"Customer A", expenses3),
            ExpenseItem("2023-07-25", "Business Trip 4", 85.40,"Customer B", expenses4)


        )


        return expenseItem // Two example items in the list

    }
}