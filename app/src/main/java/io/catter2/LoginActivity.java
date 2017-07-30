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

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * A dummy authentication store containing known user names and passwords.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "me:123:token9", "yo:hunter2:token2"
    };

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

    private void attemptLogin() {
        errorTv.setVisibility(View.GONE);
        String username = usernameActv.getText().toString();
        String password = passwordEt.getText().toString();

        for (String credential : DUMMY_CREDENTIALS) {
            String[] split = credential.split(":");
            if (username.equals(split[0]) && password.equals(split[1])) {
                String token = split[2];
                FavoritesActivity.launch(this, token, false);
                return;
            }
        }

        errorTv.setVisibility(View.VISIBLE);
    }
}

