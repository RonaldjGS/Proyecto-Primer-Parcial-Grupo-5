package com.example.proyectocrud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdaptadorCuenta extends RecyclerView.Adapter<AdaptadorCuenta.CuentaViewHolder> {

    private List<CuentaBanco> cuentaLista;
    private OnItemClickListener listener;

    public AdaptadorCuenta(List<CuentaBanco> cuentaLista, OnItemClickListener listener) {
        this.cuentaLista = cuentaLista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CuentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_cuenta, parent, false);
        return new CuentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuentaViewHolder holder, int position) {
        CuentaBanco cuenta = cuentaLista.get(position);
        holder.tvBanco.setText("Banco: " + cuenta.getBanco());
        holder.tvNombre.setText("Titular: " + cuenta.getNombres());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("es", "US"));
        holder.tvSaldo.setText("Saldo: " + format.format(cuenta.getSaldo()));

        // Botón de editar
        holder.btnEditar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(cuenta);
            }
        });

        // Botón de eliminar
        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(cuenta);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cuentaLista.size();
    }

    public static class CuentaViewHolder extends RecyclerView.ViewHolder {
        TextView tvBanco, tvNombre, tvSaldo;
        Button btnEditar, btnEliminar;

        public CuentaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBanco = itemView.findViewById(R.id.tvBanco);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvSaldo = itemView.findViewById(R.id.tvSaldo);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    public void actualizarLista(List<CuentaBanco> nuevaLista) {
        this.cuentaLista = nuevaLista;
        notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }

    public interface OnItemClickListener {
        void onEditClick(CuentaBanco cuenta);
        void onDeleteClick(CuentaBanco cuenta);
    }
}
