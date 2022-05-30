package com.example.carwashapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carwashapp.ListMyCarActivity;
import com.example.carwashapp.R;
import com.example.carwashapp.model.Vehiculos;

import java.util.List;

public class UserVehicleAdapter extends RecyclerView.Adapter<UserVehicleAdapter.ViewHolder>{

    Context mCtx;
    private List<Vehiculos> autoList;
    public UserVehicleAdapter(Context mCtx,List<Vehiculos>autoList){
        this.mCtx=mCtx;
        this.autoList=autoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.list_cars,null);

        return new UserVehicleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Vehiculos vehiculos=autoList.get(position);
        holder.tvmodelo.setText("Modelo: "+vehiculos.toString());
        holder.tvmodelo.setTextColor(Color.BLACK);
        holder.tvmarca.setText(vehiculos.getMarca());
        holder.viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                CharSequence[] dialogItem = {"Eliminar Vehiculo"};
                builder.setTitle(vehiculos.toString());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i){

                            case 0:
                                MensajeEliminar(vehiculos.getCodveh(), vehiculos.getCodcli());
                                break;

                        }

                    }
                });

                builder.create().show();
            }
        });

    }

    private void MensajeEliminar(int idcar, int iduser) {
        new AlertDialog.Builder(mCtx)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Eliminar Vehiculo")
                .setMessage("¿Estas Seguro de eliminar este vehiculo?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarVehiculo(idcar, iduser);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    private void eliminarVehiculo(int idcar, int iduser) {

        StringRequest request = new StringRequest(Request.Method.POST, "http://carwash.sisalvarez.com/appFiles/deletevehiculo.php?codveh=" + idcar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("registro eliminado")){
                            ListMyCarActivity.ListCars.finish();
                            Toast.makeText(mCtx, "eliminado correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mCtx, ListMyCarActivity.class);
                            intent.putExtra("codcli", iduser);
                            mCtx.startActivity(intent);



                        }
                        else{
                            Toast.makeText(mCtx, "no se puedo eliminar", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(request);

    }

    @Override
    public int getItemCount() {
        return autoList.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvmodelo,tvmarca;
        View viewHolder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmodelo=itemView.findViewById(R.id.txtnamecar);
            tvmarca=itemView.findViewById(R.id.txtmarca);
            viewHolder = itemView;
        }
    }
}
