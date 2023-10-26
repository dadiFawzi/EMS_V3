package com.example.ems_v3.Activities

import AuthInterceptor
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ems_v3.R
import com.example.ems_v3.model.Customer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClientService {
    // Add a new client
    @POST("/api/client/client/add")
    fun addClient(@Body client: Customer): Call<Customer>

    @DELETE(" /client/delete/{id}")
    fun deleteCustomer(@Path("id") id: Int) : Call<Customer>

}
class ModifyCustomerActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var distanceEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_customer)

        nameEditText = findViewById(R.id.NameEditText)
        cityEditText = findViewById(R.id.CityEditText)
        distanceEditText = findViewById(R.id.DistanceEditText)
        System.err.println("############################# Modify Customer Activity")


            if (intent.getStringExtra("action").equals("modification")) {
           nameEditText.setText( intent.getStringExtra("customerName"))
           cityEditText.setText( intent.getStringExtra("customerCity") )
           distanceEditText.setText( intent.getDoubleExtra("customerDistance", 0.0).toString() )
         }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_client
        bottomNavigationView.setOnItemSelectedListener (object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_client-> {
                        // Start Button1Activity when Button 1 is clicked
                        val intent = Intent(this@ModifyCustomerActivity, CustomerActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        //bottomNavigationView.selectedItemId = R.id.navigation_home
                        return true
                    }
                    R.id.navigation_home -> {
                        // Start Button1Activity when Button 1 is clicked
                        val intent = Intent(this@ModifyCustomerActivity, MainActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        //bottomNavigationView.selectedItemId = R.id.navigation_home

                        return true
                    }
               /*     R.id.navigation_report -> {
                        // Start Button2Activity when Button 2 is clicked
                        val intent = Intent(this@ModifyCustomerActivity, ReportActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        // bottomNavigationView.selectedItemId = R.id.navigation_report
                        return true
                    }*/

                    R.id.navigation_user -> {
                        // Start Button2Activity when Button 2 is clicked
                        val intent = Intent(this@ModifyCustomerActivity, SettingActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        // bottomNavigationView.selectedItemId = R.id.navigation_setting
                        return true
                    }
                }
                return false
            }
        })

        // Set click listener on the Save button
        val saveButton = findViewById<Button>(R.id.CustomerSaveButton)
        saveButton.setOnClickListener {
            // Save the modified customer data
            val modifiedCustomerName = nameEditText.text.toString()
            val modifiedCustomerCity = cityEditText.text.toString()
            val modifiedCustomerDistance = distanceEditText.text.toString().toDoubleOrNull() ?: 0.0

            val c : Customer = Customer(intent.getStringExtra("id")?.toLong() ?: 0,modifiedCustomerName,modifiedCustomerCity,modifiedCustomerDistance)


            val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("jwt", null)
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(token?.let { AuthInterceptor(it) }) // Pass the token here
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.17:8080") // Specify your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            val ClientService = retrofit.create(ClientService::class.java)
            val call = ClientService.addClient(c)

            call.enqueue(object : Callback<Customer> {
                override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                    if (response.isSuccessful) {
                        val clientResponse = response.body()
                        if (clientResponse != null ) {
                            // Add/update was successful, handle accordingly (e.g., show a success message)
                            Toast.makeText(this@ModifyCustomerActivity, "Client updated successfully ", Toast.LENGTH_SHORT).show()

                        } else {
                            // Add/update was not successful, handle accordingly (e.g., show an error message)
                            Toast.makeText(this@ModifyCustomerActivity, "Client updated Failed ", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle the case where the API request was not successful
                        Toast.makeText(this@ModifyCustomerActivity, "Client updated Failed ", Toast.LENGTH_SHORT).show()
                    }
                    finish() // Finish the activity after saving modifications

                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    Toast.makeText(this@ModifyCustomerActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    finish() // Finish the activity after saving modifications

                }
            })
        }






        }
    }


