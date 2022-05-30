package com.example.carwashapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashapp.R;
import com.example.carwashapp.model.Reservas;

import java.util.List;

public class UserMyReserva extends RecyclerView.Adapter<UserMyReserva.ViewHolder>{

    Context mCtx;
    private List<Reservas> reservasList;
    public UserMyReserva(Context mCtx,List<Reservas>reservasList){
        this.mCtx=mCtx;
        this.reservasList=reservasList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.list_my_reservas,null);

        return new UserMyReserva.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservas reservas=reservasList.get(position);
        holder.tvfecha.setText("Reserva: " + reservas.getFecha());
        holder.tvservicio.setText(reservas.getNombreservicio());
        holder.tvhora.setText("Hora: " + reservas.getHora()+ " hrs");
        holder.tvvehiculo.setText(reservas.getVehiculo());
        holder.tvprice.setText("Precio: " + reservas.getPrecio());
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
