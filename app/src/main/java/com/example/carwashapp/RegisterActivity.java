package com.example.carwashapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText textNombre, textApellido, textTelefono, textEmail, textNumdoc, textPassword;
    Button btnRegistrarUser;
    TextView txtGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textNombre = findViewById(R.id.edtNombre);
        textApellido = findViewById(R.id.edtApellido);
        textTelefono = findViewById(R.id.edtTelefono);
        textEmail = findViewById(R.id.edtEmail);
        textNumdoc = findViewById(R.id.edtNumdoc);
        textPassword = findViewById(R.id.edtPassword);
        btnRegistrarUser = findViewById(R.id.btnRegisterUser);
        txtGoLogin = findViewById(R.id.btnGoLogin);

        btnRegistrarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registarUsuario();
            }
        });
    }

    private void registarUsuario() {

        String url ="http://carwash.sisalvarez.com/appFiles/registrarUsuario.php";

        final String nombre = textNombre.getText().toString();
        final String apellido = textApellido.getText().toString();
        final String telefono = textTelefono.getText().toString();
        final String email = textEmail.getText().toString();
        final String numdoc = textNumdoc.getText().toString();
        final String password = textPassword.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");

        if (!nombre.isEmpty() && !apellido.isEmpty() && !telefono.isEmpty() && !email.isEmpty()  && !numdoc.isEmpty() && !password.isEmpty()){
            if (isEmailValid(email)){
                if (password.length()>=6){
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.equalsIgnoreCase("Registro correcto")) {
                                Toast.makeText(RegisterActivity.this, "Datos correctos", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                finish();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("nombrecli", nombre);
                            params.put("apellidocli", apellido);
                            params.put("tele", telefono);
                            params.put("numdoc", numdoc);
                            params.put("email", email);
                            params.put("password", password);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                    requestQueue.add(request);
                }else{
                    Toast.makeText(this, "La contraseña debe ser minimo 6 digitos", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Email ivalido", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Ingresar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}