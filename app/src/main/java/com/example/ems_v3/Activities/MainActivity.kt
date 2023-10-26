package com.example.ems_v3.Activities

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginRight
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.ems_v3.R
import com.example.ems_v3.Main.ExpenseItem
import com.example.ems_v3.Main.HomeAdapter
import com.example.ems_v3.database.AppDatabase
import com.example.ems_v3.databinding.ActivityMainBinding
import com.example.ems_v3.model.Customer
import com.example.ems_v3.model.Expense
import com.example.ems_v3.model.ExpenseType
import com.example.ems_v3.model.Role
import com.example.ems_v3.model.User
import com.example.ems_v3.model.mission.Mission
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar
import java.util.List

class MainActivity : AppCompatActivity() {




    private lateinit var addButton: FloatingActionButton
    private lateinit var homeButton:View
    private lateinit var reportButton:View
    private lateinit var settingButton:View
    private lateinit var customerButton:View


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addButton = findViewById(R.id.fab)
       val navView: BottomNavigationView = binding.navView

        //create database
        var appDatabase: AppDatabase =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "EMS")
            .build()

// Thread to insert sample data into DB
      /*  Thread {

            val users = listOf(
                User(username = "admin", email = "admin@example.com", password = "admin123", photo_link = "admin.jpg", jobTitle =  "ADMIN" , phone = "55852" ,  role = "ADMIN"),
                User(username = "backoffice", email = "back@example.com", password = "back123", photo_link = "back.jpg",  jobTitle =  "ADMIN" , phone = "55852" ,  role = "ADMIN"),
                User(username = "user1", email = "user1@example.com", password = "user123", photo_link = "admin.jpg", jobTitle =  "ADMIN" , phone = "55852" ,  role = "ADMIN"),
                User(username = "user2", email = "user2@example.com", password = "user456", photo_link = "admin.jpg", jobTitle =  "ADMIN" , phone = "55852" ,  role = "ADMIN")
            )
            val sampleCustomers = listOf(
                Customer(
                    name = "John Smith",
                    city = "New York",
                    distance = 10.5
                ),
                Customer(
                    name = "Alice Johnson",
                    city = "Los Angeles",
                    distance = 8.2
                ),
                Customer(
                    name = "Robert Brown",
                    city = "Chicago",
                    distance = 5.0
                ),
                Customer(
                    name = "Emily Davis",
                    city = "Houston",
                    distance = 12.7
                ),
                Customer(
                    name = "Michael Wilson",
                    city = "Miami",
                    distance = 6.8
                )
            )
            val sampleExpenses = listOf(
                Expense(
                    expenseType = ExpenseType.restaurent,
                    ammount = 25.0f,
                    comment = "Lunch at the restaurant"
                ),
                Expense(
                    expenseType = ExpenseType.taxi,
                    ammount = 10.5f,
                    comment = "Bus fare"
                ),
                Expense(
                    expenseType = ExpenseType.other,
                    ammount = 15.75f,
                    comment = "Movie night"
                ),
                Expense(
                    expenseType = ExpenseType.other,
                    ammount = 50.0f,
                    comment = "Weekly grocery shopping"
                ),
                Expense(
                    expenseType = ExpenseType.other,
                    ammount = 75.0f,
                    comment = "Electricity bill"
                ),
                Expense(
                    expenseType = ExpenseType.rentCar,
                    ammount = 200.0f,
                    comment = "Weekend getaway"
                ),
                Expense(
                    expenseType = ExpenseType.other,
                    ammount = 30.0f,
                    comment = "Doctor's visit"
                ),
                Expense(
                    expenseType = ExpenseType.hotel,
                    ammount = 60.0f,
                    comment = "New pair of shoes"
                ),
                Expense(
                    expenseType = ExpenseType.fuel,
                    ammount = 800.0f,
                    comment = "Monthly rent"
                ),
                Expense(
                    expenseType = ExpenseType.fuel,
                    ammount = 50.0f,
                    comment = "Miscellaneous expenses"
                )
            )
            val sampleMissions = listOf(
                Mission(
                    missionTitle = "Client Meeting",
                    missionComment = "Discuss project requirements with the client.",
                    idUser = 1,
                    idExpense = 3
                ),
                Mission(
                    missionTitle = "Market Research",
                    missionComment = "Conduct market research for new product launch.",
                    idUser = 2,
                    idExpense = 2
                ),
                Mission(
                    missionTitle = "Training Session",
                    missionComment = "Lead a training session for new employees.",
                    idUser = 3,
                    idExpense = 1
                ),
                Mission(
                    missionTitle = "Product Presentation",
                    missionComment = "Present our latest product to potential investors.",
                    idUser = 1,
                    idExpense = 4
                ),
                Mission(
                    missionTitle = "Team Building",
                    missionComment = "Organize team building activities for the department.",
                    idUser = 4,
                    idExpense = 5
                )
            )
            appDatabase.userDao().insertAll(users)
            appDatabase.customerDao().insertAllCustomer(sampleCustomers)
            appDatabase.expenseDao().insertAllExpense(sampleExpenses)
            appDatabase.missionDao().insertAll(sampleMissions)

            println(appDatabase.userDao().loadAllUsers().get(0).toString())
            println("################# get all user " )
        }.start()
*/

        //adapter
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewExpenses)
        val scrollView = findViewById<HorizontalScrollView>(R.id.monthsScrollView)

        val dataList = getListOfItems() // Update this function to add more items
        val adapter = HomeAdapter(dataList as List<ExpenseItem>)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)





        // to display months in the top of the main view
        val monthsContainer: LinearLayout = findViewById(R.id.monthsContainer)
        // Get the array of months from resources
        val monthsArray = resources.getStringArray(R.array.months_array)


        // Create TextView elements for each month and add to the LinearLayout
        var m = 0
        for (month in monthsArray) {
              m++
            val  textViewMonth = Button(this)

            textViewMonth.text = month

            textViewMonth.textSize = 15f
            textViewMonth.setPadding(0, 0, 0, 0)

            textViewMonth.isClickable = true
            textViewMonth.isFocusable = true
            textViewMonth.setBackgroundResource(R.drawable.month_button)
                        monthsContainer.addView(textViewMonth)



            //set default month  is current month
            val calendar = Calendar.getInstance()
            val currentMonth = calendar.get(Calendar.MONTH)+1
            if (currentMonth == m) {
                textViewMonth.isSelected = true
                //  v.setBackgroundColor(resources.getColor(R.color.selected_month))
                textViewMonth.setBackgroundResource(R.drawable.month_button_selected)

            }
        }

        // Set click listeners for each TextView to handle month selection
        for (i in 0 until monthsContainer.childCount) {
            val textViewMonth = monthsContainer.getChildAt(i) as Button


            textViewMonth.setOnClickListener {
                    v ->
                // Deselect all items
                for (j in 0 until monthsContainer.childCount) {
                    monthsContainer.getChildAt(j).isSelected = false
                    monthsContainer.getChildAt(j).setBackgroundResource(R.drawable.month_button)
                }
                // Select the clicked item
                v.isSelected = true
                //  v.setBackgroundColor(resources.getColor(R.color.selected_month))
                v.setBackgroundResource(R.drawable.month_button_selected)

                val position = monthsContainer.childCount

                // Scroll the ScrollView to the target position
              //  scrollView.smoothScrollTo( (i-1)*textViewMonth.width,0)
                scrollView.smoothScrollTo( (i-1)*textViewMonth.width,0)





                // Perform actions based on the selected item if needed
                // Handle the click event for month selection
                // You can update the RecyclerView data here based on the selected month
                // For example, filter the data for the selected month and update the adapter
                // recyclerViewExpenses.adapter.notifyDataSetChanged()


            }
        }



        homeButton =findViewById<View>(R.id.navigation_home)
/*
        reportButton =findViewById<View>(R.id.navigation_report)
*/
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
       /* reportButton.setOnClickListener{
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
        }*/
        customerButton.setOnClickListener{
            val intent = Intent(this, CustomerActivity::class.java)
            startActivity(intent)
        }
        settingButton.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onRestart() {
        super.onRestart()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_home
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