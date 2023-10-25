/*
package com.example.ems_v3.Activities

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.example.ems_v3.databinding.ActivityLoginBinding

import com.example.ems_v3.R
import com.example.ems_v3.ui.login.LoggedInUserView
import com.example.ems_v3.ui.login.LoginViewModel
import com.example.ems_v3.ui.login.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })
try {
    loginViewModel.loginResult.observe(this@LoginActivity, Observer {
        val loginResult = it ?: return@Observer

        loading.visibility = View.GONE
        if (loginResult.error != null) {
            showLoginFailed(loginResult.error)

            println("########login error ")
        }
        if (loginResult.success != null) {
            updateUiWithUser(loginResult.success)
            println("########login success ")

        }
        setResult(Activity.RESULT_OK)

        //Complete and destroy login activity once successful

        finish()


    })

}catch (e : Exception){
    e.printStackTrace()
}

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName

        // TODO : initiate successful logged in experience


        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
        println("############# failed login ")
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }
}

*/
/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 *//*

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}*/



package com.example.ems_v3.Activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.ems_v3.R
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


data class AuthResponse(val jwt: String)
data class LoginRequest(val username: String, val password: String)

interface AuthService {
    @POST("/api/auth/")
    fun login(@Header("Authorization") basicAuth: String, @Body loginRequest: LoginRequest): Call<String>

}

class LoginActivity : AppCompatActivity() {

    val interceptor = AuthInterceptor()

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()




  /*  private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.17:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()*/


    private val mPermissions = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login)
        if(!hasPermissions(this, *mPermissions)) {
            requestPermissions();
        }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            System.err.println(" ######### login button pressed ##############")
            System.err.println(" ######### username ##############"+username)
            System.err.println(" ######### password ##############"+password)
            // Create the basic authentication string

        /*    val loginRequest = LoginRequest(username, password)
            val gson = Gson() // Gson is used for JSON serialization

// Convert the loginRequest to a JSON string
            val requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(loginRequest))
// Create the basic authentication string
            val basicAuth = "Bearer " + Credentials.basic(username, password)
// Make the authentication request
            val call = authService.authenticate(basicAuth, requestBody)
            */
            val credentials = "$username:$password"
            val basicAuth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.17:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient) // Set the OkHttpClient with the interceptor
                .build()

             val authService = retrofit.create(AuthService::class.java)

            val loginRequest = LoginRequest(username, password)

            val call = authService.login(basicAuth, loginRequest)

            /*call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        // Successful response
                        val authResponse = response.body()
                       *//* val jwt = authResponse?.jwt // Extract JWT from the response*//*
                        val jwt = authResponse ?: "DefaultJWTValue"

                        // Store the JWT securely, for example, in SharedPreferences
                        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("jwt", jwt)
                            apply()
                        }
                        // Redirect to the next screen or perform other actions
                        logDebug("Authentication successful")
                    } else {
                        // Unsuccessful response (e.g., authentication failed)
                        val errorBody = response.errorBody()?.string() ?: ""
                        logError("Authentication failed. Response code: ${response.code()}, Error Body: $errorBody")

                        // Handle authentication failure, e.g., incorrect credentials or server error
                        showErrorToast("Authentication failed. Please check your credentials.")
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    // Network error
                    logError("Network error: ${t.message}")
                    showErrorToast("Network error. Please check your internet connection.")                }
            })*/

            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        // Successful response
                        val authResponse = response.body()

                        if (authResponse != null) {
                            val jwt = authResponse ?: "DefaultJWTValue"

                            // Store the JWT securely, for example, in SharedPreferences
                            val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                            with(sharedPref.edit()) {
                                putString("jwt", jwt)
                                apply()
                            }

                            // Open MainActivity or perform other actions
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)

                            logDebug("Authentication successful")
                        } else {
                            // Handle the case where the AuthResponse is null
                            logError("Authentication response is null")
                            showErrorToast("Authentication failed. Please check your credentials.")
                        }
                    } else {
                        // Unsuccessful response (e.g., authentication failed)
                        val errorBody = response.errorBody()?.string() ?: ""
                        logError("Authentication failed. Response code: ${response.code()}, Error Body: $errorBody")

                        // Handle authentication failure, e.g., incorrect credentials or server error
                        showErrorToast("Authentication failed. Please check your credentials.")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // Network error
                    logError("Network error: ${t.message}")
                    showErrorToast("Network error. Please check your internet connection.")
                }


            })




        }
    }

    // Custom logging function
    fun logDebug(message: String) {
        System.err.println("DEBUG: $message")
    }

    fun logError(message: String) {
        System.err.println("ERROR: $message")
    }

    // Custom function to display error toast
    fun showErrorToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
    private fun requestPermissions() {
        val PERMISSION_ALL = 1
        if (!hasPermissions(this, *mPermissions)) {
            ActivityCompat.requestPermissions(this, mPermissions, PERMISSION_ALL)
        }
    }

    private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }
}


