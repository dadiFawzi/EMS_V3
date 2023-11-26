package com.example.ems_v3.Activities

import AuthInterceptor
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ems_v3.Main.ExpenseAdapter
import com.example.ems_v3.R
import com.example.ems_v3.Main.ExpenseItem
import com.example.ems_v3.databinding.ActivityMainBinding
import com.example.ems_v3.model.HomeData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.Calendar
import java.util.List

interface HomeService {
    @GET("/api/home/homedata")
    fun getdata(): Call<HomeData>

    @GET("/api/home/homedata/{id}")
    fun getuserdata(@Path("id") id: Long): Call<List<HomeData>>


    @DELETE("/api/client/client/delete/{id}")
    fun deleteCustomer(@Path("id") id: Int) : Call<Void>
}
class MainActivity : AppCompatActivity() {

    lateinit var listExpenseItem :List<ExpenseItem>
    private lateinit var addButton: FloatingActionButton
    private lateinit var homeButton: View
    private lateinit var reportButton: View
    private lateinit var settingButton: View
    private lateinit var customerButton: View
     var missions: Any ={""}


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addButton = findViewById(R.id.fab)
        val navView: BottomNavigationView = binding.navView



        //adapter
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewExpenses)
        val scrollView = findViewById<HorizontalScrollView>(R.id.monthsScrollView)
        val sharedPref2 = getSharedPreferences("user", Context.MODE_PRIVATE)
        var userstring = sharedPref2.getString("user_data", null)
        val userIdToFetch = userstring // id of current user


        //     val dataList = getListOfItems() // Update this function to add more items
        //val adapter = HomeAdapter(listExpenseItem)
      //  recyclerView.adapter = adapter
        if (userIdToFetch != null) {
            displayExpenses(userIdToFetch.toLong(),recyclerView)
        }
        else{
            System.err.println("user id is nulll")
        }


        recyclerView.layoutManager = LinearLayoutManager(this)


        // to display months in the top of the main view
        val monthsContainer: LinearLayout = findViewById(R.id.monthsContainer)
        // Get the array of months from resources
        val monthsArray = resources.getStringArray(R.array.months_array)


        // Create TextView elements for each month and add to the LinearLayout
        var m = 0
        for (month in monthsArray) {
            m++
            val textViewMonth = Button(this)

            textViewMonth.text = month

            textViewMonth.textSize = 15f
            textViewMonth.setPadding(0, 0, 0, 0)

            textViewMonth.isClickable = true
            textViewMonth.isFocusable = true
            textViewMonth.setBackgroundResource(R.drawable.month_button)
            monthsContainer.addView(textViewMonth)


            //set default month  is current month
            val calendar = Calendar.getInstance()
            val currentMonth = calendar.get(Calendar.MONTH) + 1
            if (currentMonth == m) {
                textViewMonth.isSelected = true
                //  v.setBackgroundColor(resources.getColor(R.color.selected_month))
                textViewMonth.setBackgroundResource(R.drawable.month_button_selected)

            }
        }

        // Set click listeners for each TextView to handle month selection
        for (i in 0 until monthsContainer.childCount) {
            val textViewMonth = monthsContainer.getChildAt(i) as Button


            textViewMonth.setOnClickListener { v ->
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
                scrollView.smoothScrollTo((i - 1) * textViewMonth.width, 0)


                // Perform actions based on the selected item if needed
                // Handle the click event for month selection
                // You can update the RecyclerView data here based on the selected month
                // For example, filter the data for the selected month and update the adapter
                // recyclerViewExpenses.adapter.notifyDataSetChanged()


            }
        }



        homeButton = findViewById<View>(R.id.navigation_home)
        /*
        reportButton =findViewById<View>(R.id.navigation_report)
*/
        settingButton = findViewById<View>(R.id.navigation_user)
        customerButton = findViewById<View>(R.id.navigation_client)
        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            navView.selectedItemId = 0;
            startActivity(intent)
        }
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        /* reportButton.setOnClickListener{
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
        }*/
        customerButton.setOnClickListener {
            val intent = Intent(this, CustomerActivity::class.java)
            startActivity(intent)
        }
        settingButton.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onRestart() {
        super.onRestart()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_home
    }

fun  displayExpenses(userId :Long  , recyclerView :RecyclerView){

    val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val token = sharedPref.getString("jwt", null)
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(token?.let { AuthInterceptor(it) }) // Pass the token here
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.17:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService = retrofit.create(HomeService::class.java)
    val call = apiService.getuserdata(userId)

    call.enqueue(object : Callback<List<HomeData>> {
        override fun onResponse(call: Call<List<HomeData>>, response: Response<List<HomeData>>) {
            if (response.isSuccessful) {
                val homeDataList = response.body()
                if (homeDataList != null && homeDataList.isNotEmpty()) {
                    val homeData = homeDataList[0]

                    val expenseItem = ExpenseItem(
                        expenseDate = homeData.mission.date.toString(), // Adjust this based on your date format
                        missionTitle = homeData.mission.mission_title,
                        expenseAmount = homeData.expense.amount,
                        customerName = homeData.client.name,
                        expenseList = listOf(Pair(homeData.expenseType.name, homeData.expense.amount))
                    )

                    val expenseAdapter = ExpenseAdapter(listOf(expenseItem))
                    recyclerView.adapter = expenseAdapter
                    System.err.println("home data is successful 1")
                }else {
                    System.err.println("home data is error 0")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                System.err.println("Error response code: ${response.code()}, error body: $errorBody")
                System.err.println("home data is error 1")
                // Handle error
            }
        }

        override fun onFailure(call: Call<List<HomeData>>, t: Throwable) {
            System.err.println("home data is error 2: ${t.message}")
            t.printStackTrace()

            // Handle failure
        }
    })

}




    }



