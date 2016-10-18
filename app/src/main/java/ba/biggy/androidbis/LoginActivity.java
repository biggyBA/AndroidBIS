package ba.biggy.androidbis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ba.biggy.androidbis.POJO.ServerRequest;
import ba.biggy.androidbis.POJO.ServerResponse;
import ba.biggy.androidbis.POJO.User;
import ba.biggy.androidbis.retrofitInterface.RequestInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Vibrator vib;
    Animation animShake;
    private EditText etUsername, etPassword;
    private TextInputLayout layoutUsername, layoutPassword;
    private Button btnLogin;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

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
                    String email = etUsername.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    loginProcess(email, password);
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


    private void onlineUserLogin() {

        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.trim().equals("success")){

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String test = jsonObject.getString("3");
                        Toast.makeText(LoginActivity.this,test,Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{

                    Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.KEY_USERNAME,username);
                map.put(Constants.KEY_PASSWORD,password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void loginProcess(String email,String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(coordinatorLayout, resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(Constants.SUCCESS)){
                    /*SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN,true);
                    editor.putString(Constants.EMAIL,resp.getUser().getEmail());
                    editor.putString(Constants.NAME,resp.getUser().getName());
                    editor.putString(Constants.UNIQUE_ID,resp.getUser().getUnique_id());
                    editor.apply();
                    goToProfile();*/

                }
                //progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                //progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(coordinatorLayout, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }





}
