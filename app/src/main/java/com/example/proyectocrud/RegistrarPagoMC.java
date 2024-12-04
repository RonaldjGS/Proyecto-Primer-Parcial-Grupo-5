package com.example.proyectocrud;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrarPagoMC extends AppCompatActivity {

    private ModuloCrediticioHelper crediticioHelper;
    private EditText etMontoPago, etFechaPago, etObservacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_pago_mc);

        crediticioHelper = new ModuloCrediticioHelper(this);
        etMontoPago = findViewById(R.id.etMontoPago);
        etFechaPago = findViewById(R.id.etFechaPago);
        etObservacion = findViewById(R.id.etObservacion);

        int creditoId = getIntent().getIntExtra("credito_id", -1);

        findViewById(R.id.btnGuardarPago).setOnClickListener(v -> {
            double montoPago = Double.parseDouble(etMontoPago.getText().toString());
            String fechaPago = etFechaPago.getText().toString();
            String observacion = etObservacion.getText().toString();

            if (crediticioHelper.registrarPago(creditoId, montoPago, fechaPago, observacion)) {
                Toast.makeText(this, "Pago registrado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al registrar el pago", Toast.LENGTH_SHORT).show();
            }
        });
    }


}