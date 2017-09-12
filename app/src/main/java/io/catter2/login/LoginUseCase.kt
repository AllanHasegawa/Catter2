package io.catter2.login

import android.util.Log

class LoginUseCase {
    /**
     * @param username
     * @param password
     * @return A unique identifiable token representing user associated to the given username.
     * Will return null if username or password are invalid.
     */
    fun login(username: String, password: String): String? {
        Log.d("LoginUseCase", "Login: $username, $password")
        return try {
            val service = LoginService()
            val token = service.login(username, password)
            Log.d("LoginUseCase", "Login token: " + token)
            token
        } catch (e: Exception) {
            Log.e("LoginUseCase", "Login failed")
            null
        }

    }
}
