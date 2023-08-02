package com.example.ems_v3.Report

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.itextpdf.text.Document
import com.itextpdf.text.PageSize
import java.io.File
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ems_v3.Main.MainActivity
import com.example.ems_v3.R
import com.example.ems_v3.SettingActivity
import com.example.ems_v3.customer.CustomerActivity
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.List


class ReportActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.report)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val dataList = getListOfItems() // Update this function to add more items
        val adapter = ReportAdapter(dataList as List<ExpenseItem>)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


      /*  exportButton.setOnClickListener {
            if (isWriteStoragePermissionGranted()) {
                exportToPDF()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }*/


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportToPDF()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }




        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.selectedItemId = R.id.navigation_report


        bottomNavigationView.setOnItemSelectedListener (object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        // Start Button1Activity when Button 1 is clicked
                        val intent = Intent(this@ReportActivity, MainActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        //bottomNavigationView.selectedItemId = R.id.navigation_home
                        return true
                    }

                    R.id.navigation_client-> {
                        // Start Button1Activity when Button 1 is clicked
                        val intent = Intent(this@ReportActivity, CustomerActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                        //bottomNavigationView.selectedItemId = R.id.navigation_home
                        return true
                    }

                    R.id.navigation_report -> {
                        // Start Button2Activity when Button 2 is clicked
                        val intent = Intent(this@ReportActivity, ReportActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                     //   bottomNavigationView.selectedItemId = R.id.navigation_report
                        return true
                    }

                    R.id.navigation_user -> {
                        // Start Button2Activity when Button 2 is clicked
                        val intent = Intent(this@ReportActivity, SettingActivity::class.java)
                        startActivity(intent)
                        // Set the clicked item as selected
                     //   bottomNavigationView.selectedItemId = R.id.navigation_setting
                        return true
                    }
                }
                return false
            }
        })
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
        val expenseItem: kotlin.collections.List<com.example.ems_v3.Main.ExpenseItem> = listOf(
            com.example.ems_v3.Main.ExpenseItem(
                "2023-07-24",
                "Business Trip 1",
                125.30,
                "Customer A",
                expenses1
            ),
            com.example.ems_v3.Main.ExpenseItem(
                "2023-07-25",
                "Business Trip 2",
                85.40,
                "Customer B",
                expenses2
            ),
            com.example.ems_v3.Main.ExpenseItem(
                "2023-07-24",
                "Business Trip 3",
                125.30,
                "Customer A",
                expenses3
            ),
            com.example.ems_v3.Main.ExpenseItem(
                "2023-07-25",
                "Business Trip 4",
                85.40,
                "Customer B",
                expenses4
            )


        )

        return expenseItem// Two example items in the list

    }

    private fun isWriteStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun exportToPDF() {

        val document = Document(PageSize.A4)

        val pdfFileName = "example.pdf"
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(filePath, pdfFileName)

        try {
            val outputStream: OutputStream = FileOutputStream(file)
            PdfWriter.getInstance(document, outputStream)
            document.open()

            // Add content to the PDF here
            // For example, you can add text, images, tables, etc.

            // For this example, let's add a simple text to the PDF
            val paragraph = com.itextpdf.text.Paragraph("Hello, this is a sample PDF.")
            document.add(paragraph)

            document.close()
            Toast.makeText(this, "PDF Exported Successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error exporting PDF", Toast.LENGTH_SHORT).show()
        }


    }
}