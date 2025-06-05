package com.bga.its_bg_art.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.txtEstado.setText(p.getEstado());

        int color = getColorByEstado(p.getEstado(), holder.itemView.getContext());

        // Crear un GradientDrawable para mantener la forma redondeada
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(12 * holder.itemView.getContext().getResources().getDisplayMetrics().density);

        holder.txtEstado.setBackground(drawable);
        holder.barraLateral.setBackgroundColor(color);

        // Manejar click en el item
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
        TextView txtNombre, txtTotal, txtPago, txtEstado;
        View barraLateral;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtPago = itemView.findViewById(R.id.txtPago);
            txtEstado = itemView.findViewById(R.id.txtEstado);
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