package com.example.ems_v3.Activities

import AuthInterceptor
import retrofit2.Call
import retrofit2.http.GET



import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.ems_v3.R
import com.example.ems_v3.database.AppDatabase
import com.example.ems_v3.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.concurrent.thread

interface SettingService {
    @GET("/api/setting/user/{id}")
    fun getuser(@Path("id") id: Int): Call<User>

    @POST("/api/setting/add")
    fun saveUser(@Body user: User): Call<User>
}




class SettingActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private  lateinit var  modifypicturebutton : FloatingActionButton
    private val PICK_IMAGE_REQUEST = 1 // Request code for gallery picker
    private lateinit var appDatabase: AppDatabase // Initialize your database
    private  lateinit var  targetFile : File
    private  lateinit var user: User ;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user)
        val usernameEditText = findViewById<EditText>(R.id.fullNameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val phoneNumberEditText = findViewById<EditText>(R.id.phoneNumberEditText)
        val passwordEditText = findViewById<EditText>(R.id.oldPasswordEditText)
        val newpasswordEditText = findViewById<EditText>(R.id.newPasswordEditText)
        val saveButton  = findViewById<Button>(R.id.buttonSave)

        saveButton.setOnClickListener {
            System.err.println("save button clicked ")
            val oldpassword = passwordEditText.text.toString()
            System.err.println("user password "+user.password)
            System.err.println("old password "+oldpassword)

            if (user.password == oldpassword) {
                // Retrieve user information from input fields
                var newName = usernameEditText.text.toString()
                val newEmail = emailEditText.text.toString()
                val pohoneNumber = phoneNumberEditText.text.toString()
                val newpassword = newpasswordEditText.text.toString()

                user.username = newName;
                user.email = newEmail;
                user.phone = pohoneNumber;
                user.password = newpassword;

                // Call a function to update the user using Retrofit
                updateUserOnServer(user)

            }else{
                Toast.makeText(this@SettingActivity, "check the old password ", Toast.LENGTH_SHORT).show()

            }


        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_user
        bottomNavigationView.setOnItemSelectedListener (object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_client -> {
                        // Start Button1Activity when Button 1 is clicked
                        val intent = Intent(this@SettingActivity, CustomerActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        //bottomNavigationView.selectedItemId = R.id.navigation_home
                        return true
                    }
                    R.id.navigation_home -> {
                        val intent = Intent(this@SettingActivity, MainActivity::class.java)
                        startActivity(intent)

                        return true
                    }


                    R.id.navigation_user -> {
                        // Start Button2Activity when Button 2 is clicked
                        val intent = Intent(this@SettingActivity, SettingActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                     //   bottomNavigationView.selectedItemId = R.id.navigation_setting
                        return true
                    }
                }
                return false
            }
        })
        appDatabase = AppDatabase.getInstance(this)
        modifypicturebutton = findViewById(R.id.fab)
        profileImageView = findViewById(R.id.avatarImageView)
        modifypicturebutton.setOnClickListener{
    onModifyProfileClick(profileImageView)
}
        /*profileImageView.setOnClickListener {
            val imageUri = Uri.fromFile(targetFile).toString()
            val intent = Intent(this, FullScreenImageActivity::class.java)
            intent.putExtra("imageUri", imageUri)
            startActivity(intent)
        }*/

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
        val settingService = retrofit.create(SettingService::class.java)
        //
        val userIdToFetch = 1 // Replace with the actual user ID

        // Make the API request to get the user by ID
        val call = settingService.getuser(userIdToFetch)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    System.err.println("response is Successful");
                    var customersResponse =  response.body()
                    if (customersResponse != null) {
                        System.err.println("customerResponse not null");
                        user = (customersResponse)
                        // The customerList now contains the retrieved customers
                        System.err.println("customerList : "+user);
                        System.err.println("customers: "+user);

                        usernameEditText.setText(user.username) ;
                        emailEditText.setText(user.email);
                        phoneNumberEditText.setText(user.phone);

                        fetchImageFromApi(user.photo_link)





                    } else {
                        System.err.println("customerResponse  null");
                        // Handle the case where the response is null
                    }
                } else {
                    System.err.println("response is failed");

                    // Handle the case where the request was not successful (e.g., error response)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })



    }

    private fun updateUserOnServer(updatedUser: User) {
        System.err.println("update user fun start ")

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
        // Create a Retrofit service interface for updating user information
        val userService = retrofit.create(SettingService::class.java)

        // Make the API call to update the user
        val call = userService.saveUser(updatedUser)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                System.err.println("request sent  to modify user ")

                if (response.isSuccessful) {
                    System.err.println("response is successful  ")

                    val user = response.body()
                    if (user != null ) {
                        // Update was successful, handle accordingly (e.g., show a success message)
                        showToast("User updated successfully")
                    } else {
                        // Update was not successful, handle accordingly (e.g., show an error message)
                        showToast("User update failed")
                    }
                } else {
                    // Handle the case where the API request was not successful
                    showToast("Failed to update user")
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                // Handle network errors (e.g., no internet connection)
                // code show error bacause the api response is void
               // showToast("Network error: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    fun onModifyProfileClick(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            imageUri?.let { uri ->
                // Update UI with the selected image using Glide
                Glide.with(this)
                    .load(uri)
                    .into(profileImageView)

                // Optionally, you can also fetch the image from the API URL here
                fetchImageFromApi(user.photo_link)

            }
        }
    }

    private fun fetchImageFromApi(photoLink: String) {
        val apiImageUrl = "http://192.168.1.17:8080/api/setting/get-avatar/$photoLink"

        // Use Glide to load and display the image from the API URL
        Glide.with(this)
            .load(apiImageUrl)
            .into(profileImageView)
    }




}




