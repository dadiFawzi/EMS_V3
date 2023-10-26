package com.example.ems_v3.Activities

import AuthInterceptor
import com.example.ems_v3.model.Customer
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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import retrofit2.http.Path
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.concurrent.thread

interface SettingService {
    @GET("/api/setting/user/{id}")
    fun getuser(@Path("id") id: Int): Call<User>
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
        val passwordEditText = findViewById<EditText>(R.id.newPasswordEditText)
        val newpasswordEditText = findViewById<EditText>(R.id.newPasswordEditText)




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
                        // Start Button1Activity when Button 1 is clicked
                        val intent = Intent(this@SettingActivity, MainActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                       // bottomNavigationView.selectedItemId = R.id.navigation_home
                        return true
                    }
                /*    R.id.navigation_report -> {
                        // Start Button2Activity when Button 2 is clicked
                        val intent = Intent(this@SettingActivity, ReportActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                       // bottomNavigationView.selectedItemId = R.id.navigation_report
                        return true
                    }*/

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
        profileImageView.setOnClickListener {
            val imageUri = Uri.fromFile(targetFile).toString()
            val intent = Intent(this, FullScreenImageActivity::class.java)
            intent.putExtra("imageUri", imageUri)
            startActivity(intent)
        }

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




    fun onModifyProfileClick(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            imageUri?.let { uri ->
                copyImageAndGenerateThumbnail(uri)
            }
        }
    }
    private fun copyImageAndGenerateThumbnail(imageUri: Uri) {
        val inputStream = contentResolver.openInputStream(imageUri)
        val targetDirectory = File(filesDir, "profile_images")
        targetDirectory.mkdirs()
         targetFile = File(targetDirectory, "profile_picture.jpg")

        inputStream?.use { input ->
            FileOutputStream(targetFile).use { output ->
                input.copyTo(output)
            }
        }

        val thumbnailBitmap = createThumbnail(targetFile)
        val thumbnailFile = File(targetDirectory, "thumbnail.jpg")
        thumbnailBitmap?.compress(Bitmap.CompressFormat.JPEG, 90, FileOutputStream(thumbnailFile))

        // Update UI with the new thumbnail
        profileImageView.setImageBitmap(thumbnailBitmap)
    }

    private fun createThumbnail(imageFile: File): Bitmap? {
        val options = BitmapFactory.Options()
        options.inSampleSize = 16 // Adjust the sample size as needed
        return BitmapFactory.decodeFile(imageFile.absolutePath, options)
    }



}




