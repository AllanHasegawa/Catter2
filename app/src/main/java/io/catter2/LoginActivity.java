package io.catter2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.catter2.di.AppDIComponent;
import io.catter2.di.SharedPrefFavoritesRepoDIModule;
import io.catter2.di.UserDIComponent;
import io.catter2.favorites_activity.FavoritesActivity;
import io.catter2.login.LoginUseCase;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView usernameActv;
    private EditText passwordEt;
    private TextView errorTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameActv = (AutoCompleteTextView) findViewById(R.id.login_username_actv);
        passwordEt = (EditText) findViewById(R.id.login_password_et);
        passwordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        errorTv = (TextView) findViewById(R.id.login_error_tv);

        Button usernameSignInButton = (Button) findViewById(R.id.login_sign_in_bt);
        usernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    protected void onResume() {
        if (UserDIComponent.get() != null) {
            UserDIComponent.get().close();
        }
        super.onResume();
    }

    private void attemptLogin() {
        errorTv.setVisibility(View.GONE);
        String username = usernameActv.getText().toString();
        String password = passwordEt.getText().toString();

        LoginUseCase uc = new LoginUseCase();
        String token = uc.login(username, password);

        if (token != null) {
            UserDIComponent.initialize(new SharedPrefFavoritesRepoDIModule(AppDIComponent.get(), token));
            FavoritesActivity.launch(this);
        } else {
            errorTv.setVisibility(View.VISIBLE);
        }
    }
}

