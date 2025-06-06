package com.bga.its_bg_art.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bga.its_bg_art.Pedido;
import com.bga.its_bg_art.R;

import java.util.ArrayList;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {
    private List<Pedido> pedidos;
    private List<Pedido> pedidosFiltrados;
    private OnPedidoClickListener listener;
    private String[] estadosDisponibles = {"Pendiente", "Pagado", "Preparado", "Enviado", "Recibido", "Market"};

    public interface OnPedidoClickListener {
        void onPedidoClick(Pedido pedido);
    }

    public PedidoAdapter(List<Pedido> pedidos) {
        this.pedidos = pedidos;
        this.pedidosFiltrados = new ArrayList<>(pedidos);
    }

    public void setOnPedidoClickListener(OnPedidoClickListener listener) {
        this.listener = listener;
    }

    public void filtrarPorEstado(String estado) {
        pedidosFiltrados.clear();
        if (estado.equals("Todos")) {
            pedidosFiltrados.addAll(pedidos);
        } else {
            for (Pedido p : pedidos) {
                if (p.getEstado().equalsIgnoreCase(estado)) {
                    pedidosFiltrados.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido p = pedidosFiltrados.get(position);
        holder.txtNombre.setText(p.getNombre());
        holder.txtTotal.setText("Total recibido: " + p.getTotal() + "€");
        holder.txtPago.setText("Pago: " + p.getPago());

        // Configurar el spinner con el adaptador personalizado
        EstadoSpinnerAdapter spinnerAdapter = new EstadoSpinnerAdapter(holder.itemView.getContext(), estadosDisponibles);
        holder.spinnerEstado.setAdapter(spinnerAdapter);

        // Seleccionar el estado actual en el spinner
        for (int i = 0; i < estadosDisponibles.length; i++) {
            if (estadosDisponibles[i].equals(p.getEstado())) {
                holder.spinnerEstado.setSelection(i);
                break;
            }
        }

        // Configurar color de la barra lateral
        int color = getColorByEstado(p.getEstado(), holder.itemView.getContext());
        holder.barraLateral.setBackgroundColor(color);

        // Listener para cambios en el spinner
        holder.spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nuevoEstado = estadosDisponibles[position];
                p.setEstado(nuevoEstado);

                // Actualizar color de la barra lateral cuando cambie el estado
                int nuevoColor = getColorByEstado(nuevoEstado, holder.itemView.getContext());
                holder.barraLateral.setBackgroundColor(nuevoColor);

                // Aquí puedes agregar lógica para actualizar en base de datos si es necesario
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

        // Manejar click en el item (excluyendo el spinner)
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPedidoClick(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pedidosFiltrados.size();
    }

    static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtTotal, txtPago;
        Spinner spinnerEstado;  // CAMBIO: Era TextView txtEstado
        View barraLateral;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtPago = itemView.findViewById(R.id.txtPago);
            spinnerEstado = itemView.findViewById(R.id.spinnerEstado);  // CAMBIO: Era txtEstado
            barraLateral = itemView.findViewById(R.id.barraLateral);
        }
    }

    private int getColorByEstado(String estado, Context ctx) {
        switch (estado) {
            case "Pagado": return Color.parseColor("#D7ECFF");
            case "Preparado": return Color.parseColor("#E9D9F5");
            case "Pendiente": return Color.parseColor("#CFCFCF");
            case "Enviado": return Color.parseColor("#F7EFD0");
            case "Recibido": return Color.parseColor("#D2F5D0");
            case "Market": return Color.parseColor("#F8D5D5");
            default: return Color.LTGRAY;
        }
    }
}