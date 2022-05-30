package com.example.carwashapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.carwashapp.ListMyCarActivity;
import com.example.carwashapp.ListMyReservesActivity;
import com.example.carwashapp.R;
import com.example.carwashapp.model.Reservas;

import java.util.List;

public class UserMyReservaPending extends RecyclerView.Adapter<UserMyReservaPending.ViewHolder>{

    Context mCtx;
    private List<Reservas> reservasList;
    public UserMyReservaPending(Context mCtx,List<Reservas>reservasList){
        this.mCtx=mCtx;
        this.reservasList=reservasList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.list_reserva_pending,null);

        return new UserMyReservaPending.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservas reservas=reservasList.get(position);
        holder.tvfecha.setText("Reserva: " + reservas.getFecha());
        holder.tvservicio.setText(reservas.getNombreservicio());
        holder.tvhora.setText("Hora: " + reservas.getHora()+ " hrs");
        holder.tvvehiculo.setText(reservas.getVehiculo());
        holder.tvprice.setText("Precio: " + reservas.getPrecio());
        holder.viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                CharSequence[] dialogItem = {"Cancelar Reserva"};
                builder.setTitle(reservas.getFecha());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i){

                            case 0:
                                MensajeEliminar(reservas.getCodcit(), reservas.getCodcli(), reservas.getFecha());
                                break;

                        }

                    }
                });

                builder.create().show();
            }
        });

    }

    private void MensajeEliminar(int codcit, int codcli, String fecha) {
        new AlertDialog.Builder(mCtx)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Cancelar Reserva")
                .setMessage("¿Estas Seguro de cancelar este reserva?" + "\n" +
                        "con Fecha :" + fecha )
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelarReserva(codcit, codcli);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    private void cancelarReserva(int codcit, int codcli) {

        StringRequest request = new StringRequest(Request.Method.POST,
                "http://carwash.sisalvarez.com/appFiles/cancelreserva.php?codcit=" + codcit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("registro eliminado")){
                            ListMyReservesActivity.ListReservas.finish();
                            Toast.makeText(mCtx, "cancelado correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mCtx, ListMyReservesActivity.class);
                            intent.putExtra("codcli", codcli);
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
        return reservasList.size();
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvfecha,tvservicio, tvhora, tvvehiculo, tvestado, tvprice;
        CardView cardReser;
        View viewHolder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvfecha=itemView.findViewById(R.id.txtFecha);
            tvservicio=itemView.findViewById(R.id.txtServicio);
            tvhora=itemView.findViewById(R.id.txtHora);
            tvvehiculo=itemView.findViewById(R.id.txtVehiculo);
            tvprice=itemView.findViewById(R.id.txtprice);
            cardReser=itemView.findViewById(R.id.cardReserva);
            viewHolder = itemView;

        }
    }
}
