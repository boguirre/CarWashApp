package com.example.carwashapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.carwashapp.model.UserManagment;
import com.example.carwashapp.model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    Button btnLogin;
    EditText edtEmail, edtPassword;
    TextView btnGoRegister;
    ProgressDialog progressDialog;
    RequestQueue rq;
    UserManagment userManagment;
    JsonRequest jrq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmailLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.txtGoRegister);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        userManagment = new UserManagment(this);



        rq = Volley.newRequestQueue(LoginActivity.this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });

        btnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        int nightModeFlags = LoginActivity.this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                /* si esta activo el modo oscuro lo desactiva */
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        MensajeDeayuda();
        Toast.makeText(this, "No se encontro el usuario" + error.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {

        Users user = new Users();
        Toast.makeText(this, "Se ingreso con " + edtEmail.getText().toString(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
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

        userManagment.UserSessonManage(user.getNombrecli(), user.getApellidocli(), user.getCodcli());

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra("nombrecli", user.getNombrecli());
        intent.putExtra("codcli", user.getCodcli());
//        intent.putExtra("email", user.getEmail());
//        intent.putExtra("apellidocli", user.getApellidocli());
//        intent.putExtra("telefono", user.getTelefono());
//        intent.putExtra("numdoc", user.getNumdoc());
//        intent.putExtra("password", user.getPassword());
        startActivity(intent);

    }

    private void iniciarSesion() {

        String email = edtEmail.getText().toString();
        String pw = edtPassword.getText().toString();

        if (email.isEmpty() || pw.isEmpty()){
            Toast.makeText(this, "Por favor ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }
        else {


            String url = "http://carwash.sisalvarez.com/appFiles/loginuser.php?email="+email+
                    "&password="+pw;
            progressDialog.show();
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);

        }


    }

    public void MensajeDeayuda() {
        new AlertDialog.Builder(LoginActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Acceso Denegado")
                .setMessage("Correo y/o password incorrectos, debe tener una cuenta para ingresar")
                .setPositiveButton("Registrarse", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Intentar de nuevo", null)
                .show();

    }

    public void cesionIni(){
        SharedPreferences sharedPreferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        boolean estado = true;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("estado_usu", estado);
        editor.commit();
    }
}