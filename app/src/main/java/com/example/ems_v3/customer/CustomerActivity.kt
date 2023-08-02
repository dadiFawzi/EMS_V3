package com.example.ems_v3.customer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ems_v3.AddActivity
import com.example.ems_v3.Main.MainActivity
import com.example.ems_v3.R
import com.example.ems_v3.Report.ReportActivity
import com.example.ems_v3.SettingActivity
import com.example.ems_v3.model.Customer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView

class CustomerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var customerAdapter: CustomerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers_list)
         val addButton = findViewById< FloatingActionButton>(R.id.fab)
        recyclerView = findViewById(R.id.recyclerView)



        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_client

         fun openModifyCustomerActivity(customer: Customer) {
            val intent = Intent(this, ModifyCustomerActivity::class.java)
             intent.putExtra("action","modification")
            intent.putExtra("customerName",customer.name )
             intent.putExtra("customerCity",customer.city )
             intent.putExtra("customerDistance",customer.distance )
             println("############################# intent")
             println(intent.getDoubleExtra("customerDistance",0.0))

startActivity(intent)
        }



        bottomNavigationView.setOnItemSelectedListener (object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        // Start Button1Activity when Button 1 is clicked
                        val intent = Intent(this@CustomerActivity, MainActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        //bottomNavigationView.selectedItemId = R.id.navigation_home
                        return true
                    }

                    R.id.navigation_client-> {
                        // Start Button1Activity when Button 1 is clicked
                        val intent = Intent(this@CustomerActivity, CustomerActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        //bottomNavigationView.selectedItemId = R.id.navigation_home
                        return true
                    }

                    R.id.navigation_report -> {
                        // Start Button2Activity when Button 2 is clicked
                        val intent = Intent(this@CustomerActivity, ReportActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        //   bottomNavigationView.selectedItemId = R.id.navigation_report
                        return true
                    }

                    R.id.navigation_user -> {
                        // Start Button2Activity when Button 2 is clicked
                        val intent = Intent(this@CustomerActivity, SettingActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        //   bottomNavigationView.selectedItemId = R.id.navigation_setting
                        return true
                    }
                }
                return false
            }
        })


        addButton.setOnClickListener{
            val intent = Intent(this, ModifyCustomerActivity::class.java)
            intent.putExtra("action","add")
            startActivity(intent)
        }


        // Sample data for customers (replace with your actual data)
        val customers = listOf(
            Customer("John Doe", "New York", 10.5),
            Customer("Jane Smith", "Los Angeles", 8.2),
            Customer("Robert Johnson", "Chicago", 12.7),
            Customer("John Doe", "New York", 10.5),
        Customer("Jane Smith", "Los Angeles", 8.2),
        Customer("Robert Johnson", "Chicago", 12.7),
        Customer("John Doe", "New York", 10.5),
        Customer("Jane Smith", "Los Angeles", 8.2),
        Customer("Robert Johnson", "Chicago", 12.7),
        Customer("John Doe", "New York", 10.5),
        Customer("Jane Smith", "Los Angeles", 8.2),
        Customer("Robert Johnson", "Chicago", 12.7)
            // Add more customers as needed
        )

        // Initialize and set up the RecyclerView and its adapter
        customerAdapter = CustomerAdapter(customers) { position, action ->
            // Handle edit/delete button clicks here
            if (action == CustomerAction.EDIT) {
                // Handle edit action
                // ...

                println("############################# Customer Activity")
                println(customers[position].distance)
                openModifyCustomerActivity(customers[position])

            } else if (action == CustomerAction.DELETE) {
                // Handle delete action
                // ...
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customerAdapter
    }
}





// Adapter for the RecyclerView




class CustomerAdapter(
    private val customers: List<Customer>,
    private val onActionClickListener: (position: Int, action: CustomerAction) -> Unit
) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val cityTextView: TextView = itemView.findViewById(R.id.cityTextView)
        val distanceTextView: TextView = itemView.findViewById(R.id.distanceTextView)
        val editButton: ImageButton = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)



    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = customers[position]

        holder.nameTextView.text = customer.name
        holder.cityTextView.text = customer.city
        holder.distanceTextView.text = "${customer.distance} km"

        // Set click listeners for edit and delete buttons
        holder.editButton.setOnClickListener {

        }

        holder.deleteButton.setOnClickListener {
            onActionClickListener(position, CustomerAction.DELETE)
        }



        holder.itemView.setOnClickListener {
            // Handle click on the customer item (You can show customer details here if needed)
            // For now, we'll open the ModifyCustomerActivity when clicking the customer item
            onActionClickListener(position, CustomerAction.EDIT)
        }

    }

    override fun getItemCount(): Int {
        return customers.size
    }
}

// Enum class for customer actions
enum class CustomerAction {
    EDIT, DELETE
}
