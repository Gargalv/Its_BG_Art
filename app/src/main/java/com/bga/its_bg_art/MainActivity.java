package com.bga.its_bg_art;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bga.its_bg_art.Adapter.PedidoAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PedidoAdapter.OnPedidoClickListener {
    private RecyclerView recyclerPedidos;
    private PedidoAdapter adapter;
    private LinearLayout filtroContainer;
    private TextView textFiltro;
    private Button nuevoPedidoBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias UI
        recyclerPedidos = findViewById(R.id.recyclerPedidos);
        filtroContainer = findViewById(R.id.filtroContainer);
        textFiltro = findViewById(R.id.textFiltroSeleccionado);
        nuevoPedidoBttn = findViewById(R.id.btnNuevoPedido);

        // Datos simulados
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido("Marta", 35, "Vinted", "Pendiente"));
        pedidos.add(new Pedido("Pedro", 35, "Vinted", "Pagado"));
        pedidos.add(new Pedido("Ana", 35, "Vinted", "Preparado"));
        pedidos.add(new Pedido("Luis", 35, "Vinted", "Enviado"));
        pedidos.add(new Pedido("Carmen", 35, "Vinted", "Recibido"));
        pedidos.add(new Pedido("Jorge", 35, "Vinted", "Market"));

        // Configurar RecyclerView
        adapter = new PedidoAdapter(pedidos);
        adapter.setOnPedidoClickListener(this);
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));
        recyclerPedidos.setAdapter(adapter);

        // Opciones del filtro
        final String[] opcionesFiltro = {"Todos", "Pendiente", "Pagado", "Preparado", "Enviado", "Recibido", "Market"};

        // Al pulsar el contenedor del filtro
        filtroContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                for (int i = 0; i < opcionesFiltro.length; i++) {
                    popupMenu.getMenu().add(0, i, i, opcionesFiltro[i]);
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String seleccion = opcionesFiltro[item.getItemId()];
                        textFiltro.setText(seleccion);
                        adapter.filtrarPorEstado(seleccion);
                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        //Configurar Boton Nuevo Pedido
        nuevoPedidoBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AniadirPedido.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPedidoClick(Pedido pedido) {
        mostrarBottomSheet(pedido);
    }

    private void mostrarBottomSheet(Pedido pedido) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        // Referencias a las vistas del bottom sheet
        TextView txtNombreDetalle = bottomSheetView.findViewById(R.id.txtNombreDetalle);
        TextView txtEstadoDetalle = bottomSheetView.findViewById(R.id.txtEstadoDetalle);
        TextView txtPagoDetalle = bottomSheetView.findViewById(R.id.txtPagoDetalle);
        TextView txtTotalDetalle = bottomSheetView.findViewById(R.id.txtTotalDetalle);
        TextView txtGastosEnvio = bottomSheetView.findViewById(R.id.txtGastosEnvio);
        TextView txtComision = bottomSheetView.findViewById(R.id.txtComision);
        TextView txtDetalles = bottomSheetView.findViewById(R.id.txtDetalles);
        ImageView btnCerrar = bottomSheetView.findViewById(R.id.btnCerrar);
        LinearLayout containerLaminas = bottomSheetView.findViewById(R.id.containerLaminas);

        // Configurar datos
        txtNombreDetalle.setText(pedido.getNombre());
        txtEstadoDetalle.setText(pedido.getEstado());
        txtPagoDetalle.setText(pedido.getPago());
        txtTotalDetalle.setText(pedido.getTotal() + "€");
        txtGastosEnvio.setText("35€");
        txtComision.setText("35€");
        txtDetalles.setText("Insertar detalles como el email, RRSS");

        // Configurar color del estado
        int color = getColorByEstado(pedido.getEstado());
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(12 * getResources().getDisplayMetrics().density);
        txtEstadoDetalle.setBackground(drawable);

        // Agregar láminas de ejemplo
        agregarLamina(containerLaminas, "😊 Margarita (A5)");
        agregarLamina(containerLaminas, "🔵 Lavanda (A4)");

        // Botón cerrar
        btnCerrar.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.show();
    }

    private void agregarLamina(LinearLayout container, String texto) {
        TextView textView = new TextView(this);
        textView.setText(texto);
        textView.setTextSize(14);
        textView.setPadding(0, 4, 0, 4);
        textView.setTextColor(getResources().getColor(android.R.color.black));
        container.addView(textView);
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