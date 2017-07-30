package io.catter2.login;

import android.util.Log;

public class LoginUseCase {
    /**
     * @param username
     * @param password
     * @return A unique identifiable token representing user associated to the given username.
     * Will return null if username or password are invalid.
     */
    public String login(String username, String password) {
        Log.d("LoginUseCase", "Login: " + username + ", " + password);
        try {
            LoginService service = new LoginService();
            String token = service.login(username, password);
            Log.d("LoginUseCase", "Login token: " + token);
            return token;
        } catch (Exception e) {
            Log.e("LoginUseCase", "Login failed");
            return null;
        }
    }
}
