package com.example.carwashapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carwashapp.adapters.ServiceAdapter;
import com.example.carwashapp.model.Services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListServicesActivity extends AppCompatActivity {

    public static int codcli =0;

    private  static  String url="http://carwash.sisalvarez.com/appFiles/getServices.php";
    List<Services> servicesList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_services);

        recyclerView=findViewById(R.id.recyservice);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        codcli = getIntent().getIntExtra("codcli", 0);

        servicesList=new ArrayList<>();

        obtenerServicios();
    }

    private void obtenerServicios() {
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);

                                servicesList.add(new Services(
                                        jsonObject.getInt("idservice"),
                                        jsonObject.getString("name"),
                                        jsonObject.getDouble("price")
                                ));
                            }
                            ServiceAdapter adapter = new ServiceAdapter(ListServicesActivity.this, servicesList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListServicesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}