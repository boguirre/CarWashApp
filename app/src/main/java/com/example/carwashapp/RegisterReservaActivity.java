package com.example.carwashapp;

import static java.lang.String.format;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carwashapp.model.Hour;
import com.example.carwashapp.model.Vehiculos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class RegisterReservaActivity extends AppCompatActivity {

    Switch swReservaPostAct;
    Switch swShowCarSavePost;
    Switch swShowCarSaveAct;
    LinearLayout lnrShowPost;
    LinearLayout lnrShowAct;
    LinearLayout lnrFormPost;
    LinearLayout lnrFormAct;
    LinearLayout lnrShowCarSavePost;
    LinearLayout lnrShowCarSaveAct;

    EditText edtModeloPost, edtMarcaPost, edtModeloAct, edtMarcaAct;
    Button btnsavecarpost, btnsavecarAct, btnSaveReserva;
    TextView txtnameService;

    Spinner spinnerDate, spinnerTime, spinnerVehiculoAct, spinnerVehiculoPost, spinnerHoradisp;
    ArrayList<String> spinnerArray;
    ArrayList<Vehiculos> listavehicle;
    ArrayList<Hour> listahoras;
    final int STARTHOUR = 9;
    int codcli;
    int idservice;
    int citas;
    Double price;
    String nameService;

    private int dia, mes, año;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_reserva);

        swReservaPostAct = findViewById(R.id.swPostAct);
        swShowCarSavePost = findViewById(R.id.swShowCarSavePost);
        swShowCarSaveAct = findViewById(R.id.swShowCarSaveAct);
        lnrShowPost = findViewById(R.id.lnrShowPost);
        lnrShowAct = findViewById(R.id.lnrShowAct);
        lnrFormPost = findViewById(R.id.lnrShowFormPost);
        lnrFormAct = findViewById(R.id.lnrShowFormAct);
        lnrShowCarSavePost = findViewById(R.id.lnrShowCarSavePost);
        lnrShowCarSaveAct = findViewById(R.id.lnrShowCarSaveAct);
        spinnerDate = findViewById(R.id.spFechaPost);
        spinnerTime = findViewById(R.id.spHoraPost);
        spinnerVehiculoAct = findViewById(R.id.spShowCarAct);
        spinnerVehiculoPost = findViewById(R.id.spShowCarPost);
        spinnerHoradisp = findViewById(R.id.spHoraDisp);

        btnsavecarAct = findViewById(R.id.btnSaveAct);
        btnsavecarpost = findViewById(R.id.btnSavePost);
        btnSaveReserva = findViewById(R.id.btnSaveReserva);

        edtMarcaPost = findViewById(R.id.edtMarcaPost);
        edtModeloPost = findViewById(R.id.edtModeloPost);
        edtMarcaAct = findViewById(R.id.edtMarcaAct);
        edtModeloAct = findViewById(R.id.edtModeloAct);
        txtnameService = findViewById(R.id.txtNameservice);


        codcli = getIntent().getIntExtra("codcli", 0);
        idservice = getIntent().getIntExtra("idservice", 0);
        nameService = getIntent().getStringExtra("nombre");
        price = getIntent().getDoubleExtra("precio", 0);

        txtnameService.setText(nameService);

        ValidarCita();


        swReservaPostAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!swReservaPostAct.isChecked()){
                    lnrShowAct.setVisibility(View.GONE);
                    lnrShowPost.setVisibility(View.VISIBLE);
                    btnSaveReserva.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            registrarReservaPost();
                        }
                    });
                }else {
                    lnrShowAct.setVisibility(View.VISIBLE);
                    cargarHorasDisp();
                    lnrShowPost.setVisibility(View.GONE);

                }
            }
        });

        swShowCarSavePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swShowCarSavePost.isChecked()){
                    lnrFormPost.setVisibility(View.GONE);
                    cargarVehiculosPost(codcli);
                    lnrShowCarSavePost.setVisibility(View.VISIBLE);
                    btnSaveReserva.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            registrarReservaPost();
                        }
                    });
                }
                else {
                    lnrFormPost.setVisibility(View.VISIBLE);
                    lnrShowCarSavePost.setVisibility(View.GONE);
                    btnSaveReserva.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            registrarReservaWithVehiclePost();
                        }
                    });
                }
            }
        });

        swShowCarSaveAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swShowCarSaveAct.isChecked()){
                    lnrFormAct.setVisibility(View.GONE);
                    cargarVehiculosAct(codcli);
                    lnrShowCarSaveAct.setVisibility(View.VISIBLE);
                    btnSaveReserva.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            registrarReservaAct();
                        }
                    });
                }
                else if (!swShowCarSaveAct.isChecked()){
                    lnrFormAct.setVisibility(View.VISIBLE);
                    lnrShowCarSaveAct.setVisibility(View.GONE);
                    btnSaveReserva.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            registrarReservaWithVehicleAct();
                        }
                    });
                }
            }
        });

        spFecha();
        spHoras();

        btnsavecarpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarVehiculoPost();
            }
        });
        btnsavecarAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarVehiculoAct();
            }
        });



    }

    private void spHoras(){
        String url="http://carwash.sisalvarez.com/appFiles/getHours.php?idservice="+idservice;
        listahoras = new ArrayList<Hour>();
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                Hour h = new Hour();
                                JSONObject jsonObject = array.getJSONObject(i);
                                h.setIdhour(array.getJSONObject(i).getInt("idfranja"));
                                h.setHoraini(array.getJSONObject(i).getString("horaini") + " - "+
                                        array.getJSONObject(i).getString("horafin"));
                                listahoras.add(h);


                            }

                            ArrayAdapter<Hour> hv = new ArrayAdapter<Hour>(RegisterReservaActivity.this,
                                    R.layout.custom_spinner,listahoras);
                            hv.setDropDownViewResource(R.layout.custom_spinner_dropdown);
                            spinnerTime.setAdapter(hv);

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

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void spFecha(){
        Calendar calendar = Calendar.getInstance();
        spinnerArray = new ArrayList<>();

        spinnerArray.add("<Selecciona una fecha>");

        int monthNumber = calendar.get(Calendar.MONTH);

        int i = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, 1);

        while (i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            String date = twoDigits(calendar.get(Calendar.YEAR)) + "-" + twoDigits(monthNumber + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.DATE, 1);
            spinnerArray.add(date);
            i++;
        }

        spinnerDate = findViewById(R.id.spFechaPost);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                spinnerArray
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerDate.setAdapter(adapter);

    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    public void cargarVehiculosPost(int codcli) {
        String url="http://carwash.sisalvarez.com/appFiles/getCars.php?codcli="+codcli;
        listavehicle = new ArrayList<Vehiculos>();
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                Vehiculos v = new Vehiculos();
                                JSONObject jsonObject = array.getJSONObject(i);
                                v.setCodveh(array.getJSONObject(i).getInt("codveh"));
                                v.setModelo(array.getJSONObject(i).getString("modeloveh")+ " " + array.getJSONObject(i).getString("marcaveh"));
                                listavehicle.add(v);


                            }

                            ArrayAdapter<Vehiculos> av = new ArrayAdapter<Vehiculos>(RegisterReservaActivity.this,
                                    R.layout.custom_spinner,listavehicle);
                            av.setDropDownViewResource(R.layout.custom_spinner_dropdown);
                            spinnerVehiculoPost.setAdapter(av);

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

    public void cargarVehiculosAct(int codcli) {
        String url="http://carwash.sisalvarez.com/appFiles/getCars.php?codcli="+codcli;
        listavehicle = new ArrayList<Vehiculos>();
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                Vehiculos v = new Vehiculos();
                                JSONObject jsonObject = array.getJSONObject(i);
                                v.setModelo(array.getJSONObject(i).getString("modeloveh")+ " " + array.getJSONObject(i).getString("marcaveh"));
                                listavehicle.add(v);


                            }

                            ArrayAdapter<Vehiculos> av = new ArrayAdapter<Vehiculos>(RegisterReservaActivity.this,
                                    R.layout.custom_spinner,listavehicle);
                            av.setDropDownViewResource(R.layout.custom_spinner_dropdown);
                            spinnerVehiculoAct.setAdapter(av);

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

    public void cargarHorasDisp() {
        String url="http://carwash.sisalvarez.com/appFiles/getHours.php?idservice="+idservice;
        listahoras = new ArrayList<Hour>();
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                Hour h = new Hour();
                                JSONObject jsonObject = array.getJSONObject(i);
                                h.setIdhour(array.getJSONObject(i).getInt("idfranja"));
                                h.setHoraini(array.getJSONObject(i).getString("horaini") + " - "+
                                        array.getJSONObject(i).getString("horafin"));
                                listahoras.add(h);


                            }

                            ArrayAdapter<Hour> hv = new ArrayAdapter<Hour>(RegisterReservaActivity.this,
                                    R.layout.custom_spinner,listahoras);
                            hv.setDropDownViewResource(R.layout.custom_spinner_dropdown);
                            spinnerHoradisp.setAdapter(hv);

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


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ValidarCita() {
        String url="http://carwash.sisalvarez.com/appFiles/getCitasUser.php?dates="+fechaActual()+
                "&codcli="+codcli+"&idservice="+idservice;
        listahoras = new ArrayList<Hour>();
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
                            if(citas>=1){
                                swReservaPostAct.setEnabled(false);
                                MensajeDeayuda();
                            }
                            else {
                                swReservaPostAct.setEnabled(true);
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

    private void registrarVehiculoPost() {
        String url ="http://carwash.sisalvarez.com/appFiles/registrarVehiculo.php";

        final String modelo = edtModeloPost.getText().toString();
        final String marca = edtMarcaPost.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        if (modelo.isEmpty() || marca.isEmpty()){
            Toast.makeText(this, "Ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.equalsIgnoreCase("registro correcto")) {
                        Toast.makeText(RegisterReservaActivity.this, "Vehiculo Guardado", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    } else {
                        Toast.makeText(RegisterReservaActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Toast.makeText(RegisterReservaActivity.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterReservaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("marca", marca);
                    params.put("modelo", modelo);
                    params.put("codcli", codcli+"");
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterReservaActivity.this);
            requestQueue.add(request);
        }
    }

    private void registrarVehiculoAct() {
        String url ="http://carwash.sisalvarez.com/appFiles/registrarVehiculo.php";

        final String modelo = edtModeloAct.getText().toString();
        final String marca = edtMarcaAct.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        if (modelo.isEmpty() || marca.isEmpty()){
            Toast.makeText(this, "Ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.equalsIgnoreCase("registro correcto")) {
                        Toast.makeText(RegisterReservaActivity.this, "Vehiculo Guardado", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    } else {
                        Toast.makeText(RegisterReservaActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Toast.makeText(RegisterReservaActivity.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterReservaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("marca", marca);
                    params.put("modelo", modelo);
                    params.put("codcli", codcli+"");
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterReservaActivity.this);
            requestQueue.add(request);
        }
    }

    private void registrarReservaPost() {
        String url ="http://carwash.sisalvarez.com/appFiles/registrarReservaAct.php";

        final String fecha = spinnerDate.getSelectedItem().toString();
        Hour hour = (Hour) spinnerTime.getSelectedItem();
        final int idhour = hour.getIdhour();
        final String vehiculo = spinnerVehiculoPost.getSelectedItem().toString();
        final String estado="1";


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        if (spinnerDate.getSelectedItem().toString().trim().equals("<Selecciona una fecha>") || spinnerTime.getSelectedItem().toString().trim().equals("<Selecciona una hora>")){
            Toast.makeText(this, "Seleccionar todos los campos", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("registro correcto")) {
                        Toast.makeText(RegisterReservaActivity.this, "Datos correctos", Toast.LENGTH_SHORT).show();
                        finish();
                        progressDialog.dismiss();

                    } else {
                        Toast.makeText(RegisterReservaActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Toast.makeText(RegisterReservaActivity.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterReservaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("dates", fecha);
                    params.put("idhour", idhour+"");
                    params.put("codcli", codcli+"");
                    params.put("idservice", idservice+"");
                    params.put("vehicle", vehiculo);
                    params.put("estado", estado);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterReservaActivity.this);
            requestQueue.add(request);
        }
    }

    private void registrarReservaAct() {
        String url ="http://carwash.sisalvarez.com/appFiles/registrarReservaAct.php";

        Hour hv = (Hour) spinnerHoradisp.getSelectedItem();
        final int idhora = hv.getIdhour();
        final String vehiculo = spinnerVehiculoAct.getSelectedItem().toString();
        final String estado="2";


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        if (spinnerHoradisp.getSelectedItem().toString().trim().equals("")){
            Toast.makeText(this, "Seleccionar todos los campos", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("registro correcto")) {
                        Toast.makeText(RegisterReservaActivity.this, "Datos correctos", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(RegisterReservaActivity.this, PaymentActivity.class);
                        intent.putExtra("price", price);
                        startActivity(intent);
                        progressDialog.dismiss();

                    } else {
                        Toast.makeText(RegisterReservaActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Toast.makeText(RegisterReservaActivity.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterReservaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("dates", fechaActual());
                    params.put("idhour", idhora+"");
                    params.put("codcli", codcli+"");
                    params.put("idservice", idservice+"");
                    params.put("vehicle", vehiculo);
                    params.put("estado", estado);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterReservaActivity.this);
            requestQueue.add(request);
        }
    }

    private void registrarReservaWithVehiclePost() {
        String url ="http://carwash.sisalvarez.com/appFiles/registrarReservaAct.php";

        final String fecha = spinnerDate.getSelectedItem().toString();
        Hour hour = (Hour) spinnerTime.getSelectedItem();
        final int idhour = hour.getIdhour();
        final String modelopost = edtModeloPost.getText().toString();
        final String marcapost = edtMarcaPost.getText().toString();
        final String estado="1";


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        if (modelopost.isEmpty() || marcapost.isEmpty() ||
                spinnerDate.getSelectedItem().toString().trim().equals("<Selecciona una fecha>") || spinnerTime.getSelectedItem().toString().trim().equals("<Selecciona una hora>")){
            Toast.makeText(this, "Seleccionar todos los campos", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("registro correcto")) {
                        Toast.makeText(RegisterReservaActivity.this, "Datos correctos", Toast.LENGTH_SHORT).show();
                        finish();
                        progressDialog.dismiss();

                    } else {
                        Toast.makeText(RegisterReservaActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Toast.makeText(RegisterReservaActivity.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterReservaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("dates", fecha);
                    params.put("idhour", idhour+"");
                    params.put("codcli", codcli+"");
                    params.put("idservice", idservice+"");
                    params.put("vehicle", modelopost +" " +marcapost);
                    params.put("estado", estado);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterReservaActivity.this);
            requestQueue.add(request);
        }
    }

    private void registrarReservaWithVehicleAct() {
        String url ="http://carwash.sisalvarez.com/appFiles/registrarReservaAct.php";

        Hour hv = (Hour) spinnerHoradisp.getSelectedItem();
        final int idhora = hv.getIdhour();
        final String modeloact = edtModeloAct.getText().toString();
        final String marcaact = edtMarcaAct.getText().toString();
        final String estado="2";


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        if (modeloact.isEmpty() || marcaact.isEmpty()){
            Toast.makeText(this, "Seleccionar todos los campos", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("registro correcto")) {
                        Toast.makeText(RegisterReservaActivity.this, "Datos correctos", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(RegisterReservaActivity.this, PaymentActivity.class);
                        intent.putExtra("price", price);
                        progressDialog.dismiss();

                    } else {
                        Toast.makeText(RegisterReservaActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Toast.makeText(RegisterReservaActivity.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterReservaActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("dates", fechaActual());
                    params.put("idhour", idhora+"");
                    params.put("codcli", codcli+"");
                    params.put("idservice", idservice+"");
                    params.put("vehicle", modeloact +" " +marcaact);
                    params.put("estado", estado);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterReservaActivity.this);
            requestQueue.add(request);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String fechaActual(){
        Calendar fecha = Calendar.getInstance();
        dia=fecha.get(Calendar.DAY_OF_MONTH);
        mes=fecha.get(Calendar.MONTH);
        año=fecha.get(Calendar.YEAR);
        String fechaact = (String) (año + "-"+ (mes+1) +"-"+dia);
        return fechaact;


    }

    public void MensajeDeayuda() {
        new AlertDialog.Builder(RegisterReservaActivity.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Mensaje")
                .setMessage("Usted tiene una reserva activa en la fecha actual.Puede reservar para un dia posterior")
                .setPositiveButton("ok", null).show();


    }


}