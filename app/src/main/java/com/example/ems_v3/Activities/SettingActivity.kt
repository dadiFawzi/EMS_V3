package com.example.ems_v3.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ems_v3.R
import com.example.ems_v3.database.AppDatabase
import com.example.ems_v3.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import java.io.ByteArrayOutputStream
import kotlin.concurrent.thread

class SettingActivity : AppCompatActivity() {

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var profileImageView: ImageView
    private  lateinit var  modifypicturebutton : FloatingActionButton
    private val PICK_IMAGE_REQUEST = 1 // Request code for gallery picker
    private lateinit var appDatabase: AppDatabase // Initialize your database



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user)
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
                    R.id.navigation_report -> {
                        // Start Button2Activity when Button 2 is clicked
                        val intent = Intent(this@SettingActivity, ReportActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                       // bottomNavigationView.selectedItemId = R.id.navigation_report
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

         }
    fun onModifyProfileClick(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            profileImageView.setImageURI(imageUri)

            // Convert image to Base64-encoded string
            val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            val imageByteArray = bitmapToByteArray(imageBitmap)
            val base64Image = byteArrayToBase64(imageByteArray)

         thread {    // Store the Base64-encoded image in the database
             val userDao = appDatabase.userDao()
             val user: User
             user = userDao.getUserById(1)!! // Replace with the actual user ID
             user.photo = base64Image
             userDao.updateUser(user)

         }
        }
    }
    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    private fun byteArrayToBase64(byteArray: ByteArray): String {
        return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
    }


}


