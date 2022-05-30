package com.example.carwashapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carwashapp.adapters.UserVehicleAdapter;
import com.example.carwashapp.model.Vehiculos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListMyCarActivity extends AppCompatActivity {

    UserVehicleAdapter adapter;
    public  static Activity ListCars;

    public static List<Vehiculos> autosList;
    RecyclerView recyclerView;

    int codcli = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_my_car);

        codcli = getIntent().getIntExtra("codcli", 0);

        recyclerView=findViewById(R.id.recyCar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        autosList=new ArrayList<>();
        ListCars = this;

        cargarVehiculos(codcli);
    }

    public void cargarVehiculos(int codcli) {
        String url="http://carwash.sisalvarez.com/appFiles/getCars.php?codcli="+codcli;
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);

                                autosList.add(new Vehiculos(
                                        jsonObject.getInt("codveh"),
                                        jsonObject.getString("marcaveh"),
                                        jsonObject.getString("modeloveh"),
                                        jsonObject.getInt("codcli")
                                ));


                            }

                            adapter = new UserVehicleAdapter(ListMyCarActivity.this, autosList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}