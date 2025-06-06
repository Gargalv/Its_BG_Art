package com.bga.its_bg_art.Adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bga.its_bg_art.R;

public class EstadoSpinnerAdapter extends BaseAdapter {
    private Context context;
    private String[] estados;
    private LayoutInflater inflater;

    public EstadoSpinnerAdapter(Context context, String[] estados) {
        this.context = context;
        this.estados = estados;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return estados.length;
    }

    @Override
    public Object getItem(int position) {
        return estados[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Vista para el item seleccionado (cuando el spinner está cerrado)
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_item_layout, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(estados[position]);

        // Configurar color de fondo según el estado
        int color = getColorByEstado(estados[position]);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(12 * context.getResources().getDisplayMetrics().density);
        textView.setBackground(drawable);
        textView.setTextColor(android.graphics.Color.BLACK);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Vista para los items del dropdown (cuando el spinner está desplegado)
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_dropdown_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(estados[position]);

        // Configurar color de fondo según el estado para el dropdown
        int color = getColorByEstado(estados[position]);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(8 * context.getResources().getDisplayMetrics().density);
        textView.setBackground(drawable);
        textView.setTextColor(android.graphics.Color.BLACK);

        // Añadir un efecto de selección suave
        convertView.setOnClickListener(v -> {
            // El click se maneja automáticamente por el Spinner
        });

        return convertView;
    }

    private int getColorByEstado(String estado) {
        switch (estado) {
            case "Pagado": return android.graphics.Color.parseColor("#D7ECFF");
            case "Preparado": return android.graphics.Color.parseColor("#E9D9F5");
            case "Pendiente": return android.graphics.Color.parseColor("#CFCFCF");
            case "Enviado": return android.graphics.Color.parseColor("#F7EFD0");
            case "Recibido": return android.graphics.Color.parseColor("#D2F5D0");
            case "Market": return android.graphics.Color.parseColor("#F8D5D5");
            default: return android.graphics.Color.LTGRAY;
        }
    }
}