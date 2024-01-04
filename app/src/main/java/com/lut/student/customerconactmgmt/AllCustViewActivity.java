
package com.lut.student.customerconactmgmt;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllCustViewActivity extends AppCompatActivity {

    private ListView listView;
    private RequestQueue requestQueue;
    private String jwtToken;
    private CustomerAdapter adapter;
    private ArrayList<Customer> customerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cust_view);

        listView = findViewById(R.id.custListView);
        requestQueue = Volley.newRequestQueue(this);

        // Get JWT token from your shared preferences or any other method
        jwtToken = "YOUR_JWT_TOKEN"; // Replace with your JWT token

        customerList = new ArrayList<>();
        adapter = new CustomerAdapter(this, customerList);
        listView.setAdapter(adapter);

        getAllCustomers();
    }

    private void getAllCustomers() {
        String url = "http://192.168.0.140:8080/customers/getallcustomers"; // Replace with your endpoint URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            parseCustomers(response);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AllCustViewActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ERROR", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String jwtToken=getIntent().getExtras().getString("Token");
                headers.put("Authorization", jwtToken);
                return headers;
            }
        };

        requestQueue.add(jsonArrayRequest);
    }

    private void parseCustomers(JSONArray customers) throws JSONException {
        for (int i = 0; i < customers.length(); i++) {
            JSONObject customerObject = customers.getJSONObject(i);
            String name = customerObject.getString("name");
            String email = customerObject.getString("email");
            String address = customerObject.getString("address");
            String phone = customerObject.getString("phone");
            String country = customerObject.getString("country");

            Customer customer = new Customer(name, email, address, phone, country);
            customerList.add(customer);
        }
    }
}
