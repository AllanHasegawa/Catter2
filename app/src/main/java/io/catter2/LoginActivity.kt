package io.catter2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import io.catter2.di.UserKodein
import io.catter2.favorites_activity.FavoritesActivity
import io.catter2.login.LoginUseCase
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via username/password.
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_password_et.setOnEditorActionListener { _, actionId, _ ->
            (actionId == R.id.login || actionId == EditorInfo.IME_NULL)
                    .also {
                        if (it) {
                            attemptLogin()
                        }
                    }
        }

        login_sign_in_bt.setOnClickListener { attemptLogin() }
    }

    override fun onResume() {
        UserKodein.clear()
        super.onResume()
    }

    private fun attemptLogin() {
        login_error_tv.visibility = View.GONE
        val username = login_username_actv.text.toString()
        val password = login_password_et.text.toString()

        val uc = LoginUseCase()
        val token = uc.login(username, password)

        when (token) {
            null -> login_error_tv.visibility = View.VISIBLE
            else -> {
                UserKodein.initialize(token)
                FavoritesActivity.launch(this)
            }
        }
    }
}

