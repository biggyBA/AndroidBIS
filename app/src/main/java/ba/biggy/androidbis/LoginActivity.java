package ba.biggy.androidbis;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Vibrator vib;
    Animation animShake;
    private EditText etUsername, etPassword;
    private TextInputLayout layoutUsername, layoutPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        layoutUsername = (TextInputLayout) findViewById(R.id.layout_username);
        layoutPassword = (TextInputLayout) findViewById(R.id.layout_password);

        btnLogin = (Button) findViewById(R.id.btn_login);

        animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()){
                    goToMainActivity();
                }
            }
        });

    }


    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private boolean validateForm(){
        if(!validateUsername()){
            etUsername.setAnimation(animShake);
            etUsername.startAnimation(animShake);
            vib.vibrate(120);
            return false;
        }else if (!validatePassword()) {
            etPassword.setAnimation(animShake);
            etPassword.startAnimation(animShake);
            vib.vibrate(120);
            return false;
        }else {
            return true;
        }
    }



    private boolean validateUsername() {
        if (etUsername.getText().toString().trim().isEmpty()) {
            layoutUsername.setErrorEnabled(true);
            layoutUsername.setError(getString(R.string.login_error_username));
            etUsername.setError(getString(R.string.login_error_username_desc));
            return false;
        }
        layoutUsername.setErrorEnabled(false);
        return true;
    }

    private boolean validatePassword() {
        if (etPassword.getText().toString().trim().isEmpty()) {
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError(getString(R.string.login_error_password));
            etPassword.setError(getString(R.string.login_error_password_desc));
            requestFocus(etPassword);
            return false;
        }
        layoutPassword.setErrorEnabled(false);
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



}
