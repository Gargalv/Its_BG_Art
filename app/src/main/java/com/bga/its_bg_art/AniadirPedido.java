package com.bga.its_bg_art;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Arrays;
import java.util.List;

public class AniadirPedido extends AppCompatActivity {

    private LinearLayout containerLaminas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_pedido);

        containerLaminas = findViewById(R.id.containerLaminas);

        // Generar por defecto una seleccion de lamina
        agregarLamina();

        // Boton para añadir una nueva seleccion de lamina
        findViewById(R.id.btnAnadirLamina).setOnClickListener(v -> agregarLamina());

        // Boton de cerrar actividad
        findViewById(R.id.btnCerrar).setOnClickListener(v -> finish());

        //Generar spinner pago
        Spinner spinner = findViewById(R.id.spinnerMetodoPago);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.metodos_pago,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void agregarLamina() {
        View laminaView = getLayoutInflater().inflate(R.layout.item_lamina, null);

        TextView textLaminaSeleccionada = laminaView.findViewById(R.id.textLaminaSeleccionada);
        textLaminaSeleccionada.setOnClickListener(v -> mostrarBottomSheetSeleccion(textLaminaSeleccionada));

        containerLaminas.addView(laminaView);
    }

    private void mostrarBottomSheetSeleccion(TextView textViewDestino) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_laminas, null);

        ListView listView = view.findViewById(R.id.listaLaminas);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        List<String> laminas = Arrays.asList("Alhelí", "Caracola", "Clavel", "Conchas", "Diente de León",
                "Espumilla", "Jaramago", "Lavanda", "Malva", "Margarita", "Trébol Violeta", "Tulbaghia", "Tulipán");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, laminas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            textViewDestino.setText(laminas.get(position));
            bottomSheetDialog.dismiss();
        });

        btnCancelar.setOnClickListener(v -> bottomSheetDialog.dismiss());

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }



}
