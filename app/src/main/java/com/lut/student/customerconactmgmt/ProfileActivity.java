package com.lut.student.customerconactmgmt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private TextView usernamebox;
    private EditText textBox;
    private Button addCustBtn;
    private Button viewAllCustBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        // Call method to get profile details
        getProfile();
        addCustBtn=findViewById(R.id.addCustBtn);
        addCustBtn.setOnClickListener(view -> addCustomer());
        viewAllCustBtn=findViewById(R.id.viewAllCustBtn);
        viewAllCustBtn.setOnClickListener(view->viewAllCust());
    }
private void addCustomer(){
    //Move to Add Customer Window
    Intent intent = new Intent(ProfileActivity.this, AddCustomerActivity.class);
    String token=getIntent().getExtras().getString("Token");
    intent.putExtra("Token", token) ;
    //intent.putExtra("username", username) ;
    //intent.putExtra("Token", jwtToken) ;
   // intent.putExtra("username", username) ;
    startActivity(intent);
}

private void viewAllCust(){
        Intent intent = new Intent(ProfileActivity.this, AllCustViewActivity.class);
        String token=getIntent().getExtras().getString("Token");
        intent.putExtra("Token", token) ;
        //intent.putExtra("username", username) ;
        //intent.putExtra("Token", jwtToken) ;
        // intent.putExtra("username", username) ;
        startActivity(intent);
    }
    private void getProfile() {
        String jwtToken = getToken();
        String token=getIntent().getExtras().getString("Token");
        if (!token.isEmpty()) {
            String url = "http://192.168.0.140:8080/users/profile"; // Replace with your endpoint URL

            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle successful response
                            Toast.makeText(ProfileActivity.this, "Response: " + response, Toast.LENGTH_SHORT).show();
                            //JSONObject jsonObject= null;
                            try {
                                JSONObject userObj = new JSONObject(response);
                                //JSONArray usrArray=userObj.getJSONArray("user");
                                //Toast.makeText(ProfileActivity.this, usrArray.toString(), Toast.LENGTH_SHORT).show();
                                String user=userObj.getString("user");
                                JSONObject person = new JSONObject(user);
                                String name=person.getString("name");
                                Toast.makeText(ProfileActivity.this, name, Toast.LENGTH_SHORT).show();

                                usernamebox = findViewById(R.id.textView);
                                usernamebox.setText("Welcome,  "+name);
                                textBox = findViewById(R.id.editTextTextMultiLineJWT);
                                textBox.setText("Here is your token.....   "+token);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            // Parse and process the response JSON object as needed
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error response
                            Toast.makeText(ProfileActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    // Add Authorization header with JWT token
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization",  token);
                    return headers;
                }
            };

            requestQueue.add(stringRequest);
        } else {
            // No token available, request login or handle appropriately
            Toast.makeText(ProfileActivity.this, "Please login first", Toast.LENGTH_SHORT).show();
        }
    }

    private String getToken() {
        return sharedPreferences.getString("jwtToken", "");
    }
}
