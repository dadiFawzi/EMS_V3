package com.example.ems_v3.Activities

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = retrieveToken() // Implement your logic to retrieve the token

        // Clone the original request and add the "Authorization" header
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        val response = chain.proceed(newRequest)

        // Handle the response here if needed
        handleResponse(response)

        return response
    }

    private fun retrieveToken(): String? {
        // Implement your logic to retrieve the token from SharedPreferences or other sources
        return "your_access_token_here"
    }

    private fun handleResponse(response: Response) {
        // Handle the response here if needed
        if (!response.isSuccessful) {
            // Handle error responses
        }
    }
}
