package com.lut.student.customerconactmgmt;// MainActivity.java

// MainActivity.java

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    private EditText textBox;
    private Button loginButton;
    private ApiService apiService;
   private GetProfile getProfile;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        textBox = findViewById(R.id.editTextTextMultiLine);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.140:8080/") // Use 10.0.2.2 for the Android Emulator to refer to localhost
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        apiService = retrofit.create(ApiService.class);

        loginButton.setOnClickListener(view -> attemptLogin());
    }
    private void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("jwtToken", token);
        editor.apply();
    }

    // Method to retrieve JWT token from SharedPreferences
    private String getSavedToken() {
        return sharedPreferences.getString("jwtToken", "");
    }
    private void attemptLogin() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        LoginRequest loginRequest = new LoginRequest(username, password);
        Gson gson = new Gson();
        String jsonBody = gson.toJson(loginRequest);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);
        Call<TokenResponse> call = apiService.loginUser(requestBody);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
               Toast.makeText(MainActivity.this, response.raw().toString(), Toast.LENGTH_LONG).show();
                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    //String name=tokenResponse.
                    String jwtToken = tokenResponse.getToken();
                    saveToken(jwtToken);
                    Toast.makeText(MainActivity.this, "JWT Token: " + tokenResponse.getToken(), Toast.LENGTH_LONG).show();
                    // Handle the JWT token (store it securely, proceed to next screen, etc.)

                    textBox.setText(response.body()+"---Token---:"+jwtToken);
                    //Move to Profile Window
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("Token", jwtToken) ;
                    intent.putExtra("username", username) ;
                    startActivity(intent);
                } else {
                    // Handle error responses
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                // Handle network failures
                Toast.makeText(MainActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
    });

}}

