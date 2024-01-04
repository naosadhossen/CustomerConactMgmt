package com.lut.student.customerconactmgmt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class AddCustomerActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextAddress, editTextPhone, editTextCountry,tokenBox;
    private Button buttonAddCustomer;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextCountry = findViewById(R.id.editTextCountry);
        buttonAddCustomer = findViewById(R.id.buttonAddCustomer);
        tokenBox=findViewById(R.id.editTextTextMultiLineToken);
        String token=getIntent().getExtras().getString("Token");
        tokenBox.setText(token);

        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        buttonAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomer();
            }
        });
    }

    private void addCustomer() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String country = editTextCountry.getText().toString().trim();

        if (!name.isEmpty() && !email.isEmpty() && !address.isEmpty() && !phone.isEmpty() && !country.isEmpty()) {
            String url = "http://192.168.0.140:8080/customers/addcustomer"; // Replace with your endpoint URL

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("email", email);
                jsonObject.put("address", address);
                jsonObject.put("phone", phone);
                jsonObject.put("country", country);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle success response
                            Toast.makeText(AddCustomerActivity.this, "Customer added successfully", Toast.LENGTH_SHORT).show();
                            Log.d("RESPONSE", response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error response
                            Toast.makeText(AddCustomerActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("ERROR", error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String jwtToken=getIntent().getExtras().getString("Token");
                    //String jwtToken = sharedPreferences.getString("jwtToken", "");
                    headers.put("Authorization",jwtToken);
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);
        } else {
            Toast.makeText(AddCustomerActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(AddCustomerActivity.this, AllCustViewActivity.class);
        String token=getIntent().getExtras().getString("Token");
        intent.putExtra("Token", token) ;
        //intent.putExtra("username", username) ;
        //intent.putExtra("Token", jwtToken) ;
        // intent.putExtra("username", username) ;
        startActivity(intent);
    }
}
