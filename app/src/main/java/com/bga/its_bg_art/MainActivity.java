package com.bga.its_bg_art;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerPedidos;
    private PedidoAdapter adapter;
    private LinearLayout filtroContainer;
    private TextView textFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias UI
        recyclerPedidos = findViewById(R.id.recyclerPedidos);
        filtroContainer = findViewById(R.id.filtroContainer);
        textFiltro = findViewById(R.id.textFiltroSeleccionado);

        // Datos simulados
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido("Pedro", 35, "Vinted", "Pendiente"));
        pedidos.add(new Pedido("Pedro", 35, "Vinted", "Pagado"));
        pedidos.add(new Pedido("Pedro", 35, "Vinted", "Preparado"));
        pedidos.add(new Pedido("Pedro", 35, "Vinted", "Enviado"));
        pedidos.add(new Pedido("Pedro", 35, "Vinted", "Recibido"));
        pedidos.add(new Pedido("Pedro", 35, "Vinted", "Market"));

        // Configurar RecyclerView
        adapter = new PedidoAdapter(pedidos);
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
                        textFiltro.setText(seleccion); // Cambia el texto visible
                        adapter.filtrarPorEstado(seleccion); // Aplica filtro
                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }
}
