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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashapp.ListServicesActivity;
import com.example.carwashapp.R;
import com.example.carwashapp.RegisterReservaActivity;
import com.example.carwashapp.model.Services;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.PlayerViewnHolder>{

    private Context mCtx;
    private List<Services> servicesList;
    public ServiceAdapter(Context mCtx,List<Services>servicesList){
        this.mCtx=mCtx;
        this.servicesList=servicesList;
    }

    @NonNull
    @Override
    public PlayerViewnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.list_services,null);

        return new PlayerViewnHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewnHolder holder, int position) {
        Services services=servicesList.get(position);
        holder.tvname.setText(services.getName());
        holder.tvname.setTextColor(Color.WHITE);
        holder.tvprice.setText("Precio: $ " + services.getPrice()+"");

        holder.viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                CharSequence[] dialogItem = {"Realizar Reserva"};
                builder.setTitle(services.getName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i){

                            case 0:

                                Intent intent =  new Intent(mCtx, RegisterReservaActivity.class);
                                intent.putExtra("idservice", services.getIdservice());
                                intent.putExtra("nombre", services.getName());
                                intent.putExtra("precio", services.getPrice());
                                intent.putExtra("codcli", ListServicesActivity.codcli);
                                mCtx.startActivity(intent);
                                break;

                        }

                    }
                });

                builder.create().show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    static class PlayerViewnHolder extends  RecyclerView.ViewHolder{
        TextView tvname,tvprice;
        View viewHolder;
        public PlayerViewnHolder(@NonNull View itemView) {
            super(itemView);

            tvname=itemView.findViewById(R.id.txtNomservice);
            tvprice=itemView.findViewById(R.id.txtPrecio);
            viewHolder = itemView;
        }
    }
}
