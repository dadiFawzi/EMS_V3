package com.example.ems_v3.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.example.ems_v3.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

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

if (intent.getStringExtra("action").equals("modification")) {
    nameEditText.hint = intent.getStringExtra("customerName")
    cityEditText.hint = intent.getStringExtra("customerCity")
    distanceEditText.hint = intent.getDoubleExtra("customerDistance", 0.0).toString()

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





            // Pass the modified customer data back to the previous activity (CustomersListActivity)
            val intent = Intent()
            intent.putExtra("modified_customer_name", modifiedCustomerName)
            intent.putExtra("modified_customer_city", modifiedCustomerCity)
            intent.putExtra("modified_customer_distance", modifiedCustomerDistance)
            setResult(Activity.RESULT_OK, intent)
            finish() // Finish the activity after saving modifications
        }
    }
}


