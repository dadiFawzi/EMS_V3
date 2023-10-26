package com.example.ems_v3.Activities

import AuthInterceptor
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ems_v3.R
import com.example.ems_v3.model.Customer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path


class CustomerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var customerAdapter: CustomerAdapter
    private var customerList: List<Customer> = mutableListOf() // Initialize an empty list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers_list)
         val addButton = findViewById< FloatingActionButton>(R.id.fab)
         val searchEditText = findViewById<EditText>(R.id.search)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_client

         fun openModifyCustomerActivity(customer: Customer) {
            val intent = Intent(this, ModifyCustomerActivity::class.java)
             intent.putExtra("action","modification")
             intent.putExtra("id",customer.id.toString())
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

                 /*   R.id.navigation_report -> {
                        // Start Button2Activity when Button 2 is clicked
                        val intent = Intent(this@CustomerActivity, ReportActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        //   bottomNavigationView.selectedItemId = R.id.navigation_report
                        return true
                    }*/

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
fun setCustomerAdapter(){
    var filteredCustomers :List<Customer> = customerList ;


    searchEditText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Not needed for this implementation
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            System.err.println("text chaged captured ")
            // Filter the customerList based on the input text
            val searchText = s.toString().trim().toLowerCase()
            System.err.println("text chaged captured  search text : "+searchText)
            val filteredCustomers = customerList.filter { customer ->
                customer.name.toLowerCase().contains(searchText)


            }

        }

        override fun afterTextChanged(p0: Editable?) {
            val searchText = p0.toString().trim().toLowerCase()

            if (searchText.isNotEmpty()) {
                 filteredCustomers = customerList.filter { customer ->
                    customer.name.toLowerCase().contains(searchText)
                }
            } else {
                filteredCustomers = customerList ;
            }

            // Initialize and set up the RecyclerView and its adapter
            customerAdapter = CustomerAdapter(filteredCustomers) { position, action ->
                // Handle edit/delete button clicks here
                if (action == CustomerAction.EDIT) {
                    println("############################# Customer Activity")
                    println(customerList[position].distance)
                    println("***** position ***"+position)

                    openModifyCustomerActivity(filteredCustomers[position])

                } else if (action == CustomerAction.DELETE) {
                    // Handle delete action
                    // ...
                }
            }
            recyclerView.adapter = customerAdapter


        }

    })

    // Initialize and set up the RecyclerView and its adapter
    customerAdapter = CustomerAdapter(filteredCustomers) { position, action ->
        // Handle edit/delete button clicks here
        if (action == CustomerAction.EDIT) {
            println("############################# Customer Activity")
            println(customerList[position].distance)
            openModifyCustomerActivity(customerList[position])

        } else if (action == CustomerAction.DELETE) {
            val customerId = customerList[position].id // Get the customer's ID

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
            val customerService = retrofit.create(CustomerService::class.java)
            val call = customerService.deleteCustomer(customerId.toInt())

            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {

finish()
                        val intent = Intent(this@CustomerActivity, CustomerActivity::class.java)
                        startActivity(intent)



                        // Customer deletion successful, you can update your UI if needed
                    } else {
                        // Handle deletion failure
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Handle network errors
                }
            })




        }
    }
    recyclerView.adapter = customerAdapter




}

        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("jwt", null)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(token?.let { AuthInterceptor(it) })
                   .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.17:8080") // Specify your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val customerService = retrofit.create(CustomerService::class.java)
        val call = customerService.getCustomers()

        call.enqueue(object : Callback<List<Customer>> {
            override fun onResponse(call: Call<List<Customer>>, response: Response<List<Customer>>) {
                if (response.isSuccessful) {
                    System.err.println("response is Successful");
                     var customersResponse =  response.body()
                    if (customersResponse != null) {
                        System.err.println("customerResponse not null");
                        customerList = (customersResponse)
                        // The customerList now contains the retrieved customers
                        System.err.println("customerList : "+customerList);
                   setCustomerAdapter()

                    } else {
                        System.err.println("customerResponse  null");
                        // Handle the case where the response is null
                    }
                } else {
                    System.err.println("response is failed");

                    // Handle the case where the request was not successful (e.g., error response)
                }
            }
            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                // Handle network errors
            }
        })




    }

    override fun onRestart() {
        super.onRestart()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_client
    }
}

// Adapter for the RecyclerView
class CustomerAdapter(
    private val customers: List<Customer>,
    private var onActionClickListener: (position: Int, action: CustomerAction) -> Unit
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
            onActionClickListener(position, CustomerAction.EDIT)
        }

        holder.deleteButton.setOnClickListener {
            onActionClickListener(position, CustomerAction.DELETE)
        }
        holder.itemView.setOnClickListener {
            // Handle click on the customer item (You can show customer details here if needed)
            // For now, we'll open the ModifyCustomerActivity when clicking the customer item
            //onActionClickListener(position, CustomerAction.EDIT)
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


interface CustomerService {
    @GET("/api/client/client")
    fun getCustomers(): Call<List<Customer>>

    @DELETE("/api/client/client/delete/{id}")
    fun deleteCustomer(@Path("id") id: Int) : Call<Void>
}