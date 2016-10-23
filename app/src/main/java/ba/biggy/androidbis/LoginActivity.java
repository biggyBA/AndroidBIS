package ba.biggy.androidbis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import ba.biggy.androidbis.POJO.Sparepart;
import ba.biggy.androidbis.POJO.retrofitServerObjects.LoginServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.LoginServerResponse;
import ba.biggy.androidbis.POJO.User;
import ba.biggy.androidbis.POJO.retrofitServerObjects.SparepartServerResponse;
import ba.biggy.androidbis.POJO.retrofitServerObjects.UserServerResponse;
import ba.biggy.androidbis.SQLite.AndroidDatabaseManager;
import ba.biggy.androidbis.SQLite.DataBaseAdapter;
import ba.biggy.androidbis.SQLite.SparepartListTableController;
import ba.biggy.androidbis.SQLite.UsersTableController;
import ba.biggy.androidbis.retrofitInterface.LoginRequestInterface;
import ba.biggy.androidbis.retrofitInterface.SparepartRequestInterface;
import ba.biggy.androidbis.retrofitInterface.UserRequestInterface;
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
    private CoordinatorLayout coordinatorLayout;
    private SharedPreferences pref;
    private ArrayList<User> userData;
    private ArrayList<Sparepart> sparepartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataBaseAdapter.init(this);


        pref = getApplicationContext().getSharedPreferences(Constants.PREF, 0);

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
                /*if(validateForm()){
                    String username = etUsername.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();
                    loginProcess(username, password);
                }*/

                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                loginProcess(username, password);


            }
        });

        btnLogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                goToDatabaseManager();

                return false;
            }
        });

    }

    private void goToDatabaseManager(){
        Intent intent = new Intent(this, AndroidDatabaseManager.class);
        startActivity(intent);
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




    private void loginProcess(final String username, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoginRequestInterface loginRequestInterface = retrofit.create(LoginRequestInterface.class);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        LoginServerRequest request = new LoginServerRequest();
        request.setUser(user);
        Call<LoginServerResponse> response = loginRequestInterface.operation(request);
        response.enqueue(new Callback<LoginServerResponse>() {
            @Override
            public void onResponse(Call<LoginServerResponse> call, retrofit2.Response<LoginServerResponse> response) {
                LoginServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){

                    /*
                    *
                    *   need to insert current user
                    *
                     */



                    //get array of users properties and insert into users table
                    userData = new ArrayList<>(Arrays.asList(resp.getUser()));
                    UsersTableController usersTableController = new UsersTableController();
                    for (int i = 0; i < userData.size(); i++) {
                        usersTableController.insertUser(userData.get(i));
                    }


                    //get array of spareparts and insert into sparepart table
                    sparepartData = new ArrayList<>(Arrays.asList(resp.getSparepart()));
                    SparepartListTableController sparepartListTableController = new SparepartListTableController();
                    for (int i = 0; i < sparepartData.size(); i++) {
                        sparepartListTableController.insertSparepart(sparepartData.get(i));
                    }


                    Snackbar.make(coordinatorLayout, "Ispravni pristupni podaci", Snackbar.LENGTH_LONG).show();
                    /*SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.SP_IS_LOGGED_IN,true);
                    editor.putString(Constants.SP_USERNAME,username);
                    editor.apply();*/


                }else if (resp.getResult().equals(Constants.FAILURE)){
                    Snackbar.make(coordinatorLayout, "Neispravni pristupni podaci", Snackbar.LENGTH_LONG).show();
                }
                //progress.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(Call<LoginServerResponse> call, Throwable t) {
                //progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(coordinatorLayout, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }



    private void getAllUsers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserRequestInterface request = retrofit.create(UserRequestInterface.class);
        Call<UserServerResponse> call = request.getUser();
        call.enqueue(new Callback<UserServerResponse>() {
            @Override
            public void onResponse(Call<UserServerResponse> call, Response<UserServerResponse> response) {
                UserServerResponse jsonResponse = response.body();
                userData = new ArrayList<>(Arrays.asList(jsonResponse.getUser()));
                UsersTableController usersTableController = new UsersTableController();
                    for (int i = 0; i < userData.size(); i++) {
                        usersTableController.insertUser(userData.get(i));
                    }
            }
            @Override
            public void onFailure(Call<UserServerResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }


    private void getAllSpareparts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SparepartRequestInterface request = retrofit.create(SparepartRequestInterface.class);
        Call<SparepartServerResponse> call = request.getSparepart();
        call.enqueue(new Callback<SparepartServerResponse>() {
            @Override
            public void onResponse(Call<SparepartServerResponse> call, Response<SparepartServerResponse> response) {
                SparepartServerResponse jsonResponse = response.body();
                sparepartData = new ArrayList<>(Arrays.asList(jsonResponse.getSparepart()));
                SparepartListTableController sparepartListTableController = new SparepartListTableController();
                for (int i = 0; i < sparepartData.size(); i++) {
                    sparepartListTableController.insertSparepart(sparepartData.get(i));
                }
                Toast.makeText(LoginActivity.this, "done", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<SparepartServerResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }



}
