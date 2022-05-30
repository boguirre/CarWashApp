package com.example.carwashapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carwashapp.adapters.UserMyReserva;
import com.example.carwashapp.adapters.UserMyReservaAcept;
import com.example.carwashapp.adapters.UserMyReservaPending;
import com.example.carwashapp.model.Hour;
import com.example.carwashapp.model.Reservas;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListMyReservesActivity extends AppCompatActivity {

    UserMyReserva adapter;
    UserMyReservaAcept adapterAcept;
    UserMyReservaPending adapterPend;
    FloatingActionButton fabpend, fabacept, fabatend;
    LinearLayout lnrimageerror;
    TextView txtnamereserve;

    public static List<Reservas> listreservas;
    public static List<Reservas> listreservasAcept;
    public static List<Reservas> listreservasPend;
    public  static Activity ListReservas;
    RecyclerView recyclerView, rycAcept, rycPending;

    int codcli = 0;
    int citas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_my_reserves);

        codcli = getIntent().getIntExtra("codcli", 0);

        recyclerView=findViewById(R.id.recyReserva);
        rycAcept=findViewById(R.id.recyReservaAcept);
        rycPending=findViewById(R.id.recyReservaPending);
        recyclerView.setHasFixedSize(true);
        rycAcept.setHasFixedSize(true);
        rycPending.setHasFixedSize(true);
        fabatend = findViewById(R.id.fabatend);
        fabacept = findViewById(R.id.fabacept);
        fabpend = findViewById(R.id.fabpending);
        txtnamereserve= findViewById(R.id.txtnamereserva);
        lnrimageerror = findViewById(R.id.lnerrorimage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rycAcept.setLayoutManager(new LinearLayoutManager(this));
        rycPending.setLayoutManager(new LinearLayoutManager(this));
        listreservas=new ArrayList<>();
        listreservasAcept=new ArrayList<>();
        listreservasPend=new ArrayList<>();
        ListReservas = this;

        cargarreservas(codcli);
        cargarreservasAceptads(codcli);
        cargarreservasPending(codcli);
        fabacept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                rycPending.setVisibility(View.GONE);
                rycAcept.setVisibility(View.VISIBLE);
                lnrimageerror.setVisibility(View.GONE);
                txtnamereserve.setText("Lista de Reservas Aceptadas");
                CantReservaAceptada();

            }
        });

        fabatend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rycAcept.setVisibility(View.GONE);
                rycPending.setVisibility(View.GONE);
                lnrimageerror.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                txtnamereserve.setText("Lista de Reservas Atendidas");
                CantReservaAtendidas();
            }
        });

        fabpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rycAcept.setVisibility(View.GONE);
                rycPending.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                lnrimageerror.setVisibility(View.GONE);
                txtnamereserve.setText("Lista de Reservas Pendientes");
                CantReservaPendientes();
            }
        });

    }

    public void cargarreservas(int codcli) {
        String url="http://carwash.sisalvarez.com/appFiles/getReservas.php?codcli="+codcli;
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);

                                listreservas.add(new Reservas(
                                        jsonObject.getInt("idbook"),
                                        jsonObject.getString("nombreService"),
                                        jsonObject.getString("fecha_reserva"),
                                        jsonObject.getString("hora_reserva"),
                                        jsonObject.getString("precioservice"),
                                        jsonObject.getString("estado"),
                                        jsonObject.getString("vehiculo"),
                                        jsonObject.getInt("codcli")
                                ));


                            }

                            adapter = new UserMyReserva(ListMyReservesActivity.this, listreservas);
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

    public void cargarreservasAceptads(int codcli) {
        String url="http://carwash.sisalvarez.com/appFiles/getReservaAceppted.php?codcli="+codcli;
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);

                                listreservasAcept.add(new Reservas(
                                        jsonObject.getInt("idbook"),
                                        jsonObject.getString("nombreService"),
                                        jsonObject.getString("fecha_reserva"),
                                        jsonObject.getString("hora_reserva"),
                                        jsonObject.getString("precioservice"),
                                        jsonObject.getString("estado"),
                                        jsonObject.getString("vehiculo"),
                                        jsonObject.getInt("codcli")
                                ));


                            }

                            adapterAcept = new UserMyReservaAcept(ListMyReservesActivity.this, listreservasAcept);
                            rycAcept.setAdapter(adapterAcept);

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

    public void cargarreservasPending(int codcli) {
        String url="http://carwash.sisalvarez.com/appFiles/getReservaPending.php?codcli="+codcli;
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);

                                listreservasPend.add(new Reservas(
                                        jsonObject.getInt("idbook"),
                                        jsonObject.getString("nombreService"),
                                        jsonObject.getString("fecha_reserva"),
                                        jsonObject.getString("hora_reserva"),
                                        jsonObject.getString("precioservice"),
                                        jsonObject.getString("estado"),
                                        jsonObject.getString("vehiculo"),
                                        jsonObject.getInt("codcli")
                                ));


                            }

                            adapterPend = new UserMyReservaPending(ListMyReservesActivity.this, listreservasPend);
                            rycPending.setAdapter(adapterPend);


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

    public void CantReservaAceptada() {
        String url="http://carwash.sisalvarez.com/appFiles/getNumReg.php?codcli="+codcli+"&estado=2";
        citas = 0;
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                citas = jsonObject.getInt("cita");


                            }
                            if(citas<1){
                                lnrimageerror.setVisibility(View.VISIBLE);
                            }

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

    public void CantReservaPendientes() {
        String url="http://carwash.sisalvarez.com/appFiles/getNumReg.php?codcli="+codcli+"&estado=1";
        citas = 0;
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                citas = jsonObject.getInt("cita");


                            }
                            if(citas<1){
                                lnrimageerror.setVisibility(View.VISIBLE);
                            }

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

    public void CantReservaAtendidas() {
        String url="http://carwash.sisalvarez.com/appFiles/getNumReg.php?codcli="+codcli+"&estado=3";
        citas = 0;
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                citas = jsonObject.getInt("cita");


                            }
                            if(citas<1){
                                lnrimageerror.setVisibility(View.VISIBLE);
                            }


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