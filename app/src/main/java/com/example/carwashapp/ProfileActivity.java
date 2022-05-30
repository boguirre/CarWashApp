package com.example.carwashapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carwashapp.model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    EditText textNombre, textApellido, textTelefono, textEmail, textNumdoc, textPassword;
    Button btnActualizar;
    RequestQueue rq;
    JsonRequest jrq;
    int codcli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textNombre = findViewById(R.id.edtNombre);
        textApellido = findViewById(R.id.edtApellido);
        textTelefono = findViewById(R.id.edtTelefono);
        textEmail = findViewById(R.id.edtEmail);
        textNumdoc = findViewById(R.id.edtNumdoc);
        textPassword = findViewById(R.id.edtPassword);
        btnActualizar = findViewById(R.id.btnActualizar);

        rq = Volley.newRequestQueue(ProfileActivity.this);

        codcli = getIntent().getIntExtra("codcli", 0);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        obtenerUser();
    }

    private void update() {
        String url ="http://carwash.sisalvarez.com/appFiles/updateprofile.php";

        final String nombre = textNombre.getText().toString();
        final String apellido = textApellido.getText().toString();
        final String telefono = textTelefono.getText().toString();
        final String email = textEmail.getText().toString();
        final String numdoc = textNumdoc.getText().toString();
        final String password = textPassword.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Actualizando");

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || email.isEmpty()  || numdoc.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("codcli", codcli+"");
                    params.put("nombrecli", nombre);
                    params.put("apellidocli", apellido);
                    params.put("tele", telefono);
                    params.put("email", email);
                    params.put("numdoc", numdoc);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
            requestQueue.add(request);
        }



    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "No se encontro el usuario" + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Users user = new Users();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            user.setCodcli(jsonObject.optInt("codcli"));
            user.setApellidocli(jsonObject.optString("apellidocli"));
            user.setTelefono(jsonObject.optString("tele"));
            user.setEmail(jsonObject.optString("email"));
            user.setNumdoc(jsonObject.optString("numdoc"));
            user.setPassword(jsonObject.optString("password"));
            user.setNombrecli(jsonObject.optString("nombrecli"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        textNombre.setText(user.getNombrecli());
        textApellido.setText(user.getApellidocli());
        textTelefono.setText(user.getTelefono());
        textNumdoc.setText(user.getNumdoc());
        textEmail.setText(user.getEmail());
        textPassword.setText(user.getPassword());

    }

    private void obtenerUser() {

            String url = "http://carwash.sisalvarez.com/appFiles/getUser.php?codcli="+codcli;
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);
        }





}