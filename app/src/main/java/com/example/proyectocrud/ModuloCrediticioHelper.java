package com.example.proyectocrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ModuloCrediticioHelper {

    private static final String TABLE_CREDITOS = "creditos";

    private static final String COL_ID_CREDITOS = "id";
    private static final String COL_MONTO = "monto";
    private static final String COL_TASA_INTERES = "tasa_interes";
    private static final String COL_PLAZO = "plazo";
    private static final String COL_CLIENTE = "cliente";

    private DatabaseHelper dbHelper;

    public ModuloCrediticioHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Método para agregar un crédito
    public boolean agregarCredito(double monto, double tasaInteres, int plazo, String cliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_MONTO, monto);
        values.put(DatabaseHelper.COL_TASA_INTERES, tasaInteres);
        values.put(DatabaseHelper.COL_PLAZO, plazo);
        values.put(DatabaseHelper.COL_CLIENTE, cliente);

        long result = db.insert(DatabaseHelper.TABLE_CREDITOS, null, values);
        return result != -1; // Devuelve true si la inserción fue exitosa
    }

    public Cursor obtenerCreditos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT id, monto, tasa_interes, plazo, cliente FROM " + TABLE_CREDITOS, null);
    }

    // Obtener un crédito por ID
    public Cursor obtenerCreditoPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_CREDITOS + " WHERE " + DatabaseHelper.COL_ID_CREDITOS + " = ?", new String[]{String.valueOf(id)});
    }


    public boolean eliminarCredito(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(TABLE_CREDITOS, COL_ID_CREDITOS + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean actualizarCredito(int id, double monto, double tasaInteres, int plazo, String cliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MONTO, monto);
        values.put(COL_TASA_INTERES, tasaInteres);
        values.put(COL_PLAZO, plazo);
        values.put(COL_CLIENTE, cliente);

        int result = db.update(TABLE_CREDITOS, values, COL_ID_CREDITOS + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean registrarPago(int creditoId, double montoPago, String fechaPago, String observacion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_ID_CREDITO, creditoId);
        values.put(DatabaseHelper.COL_MONTO_PAGO, montoPago);
        values.put(DatabaseHelper.COL_FECHA_PAGO, fechaPago);
        values.put(DatabaseHelper.COL_OBSERVACION, observacion);

        long result = db.insert(DatabaseHelper.TABLE_PAGOS, null, values);

        // Actualizar el saldo restante del crédito
        if (result != -1) {
            actualizarSaldoCredito(creditoId, montoPago);
        }

        return result != -1;
    }

    private void actualizarSaldoCredito(int creditoId, double montoPago) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE " + DatabaseHelper.TABLE_CREDITOS +
                        " SET " + DatabaseHelper.COL_MONTO + " = " + DatabaseHelper.COL_MONTO + " - ?" +
                        " WHERE " + DatabaseHelper.COL_ID_CREDITOS + " = ?",
                new Object[]{montoPago, creditoId});
    }

    public Cursor getPagoPorCred(int creditoId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Obtén la base de datos en modo lectura
        return db.rawQuery(
                "SELECT " + DatabaseHelper.COL_FECHA_PAGO + ", " +
                        DatabaseHelper.COL_MONTO_PAGO + ", " +
                        DatabaseHelper.COL_OBSERVACION +
                        " FROM " + DatabaseHelper.TABLE_PAGOS +
                        " WHERE " + DatabaseHelper.COL_ID_CREDITO + " = ?",
                new String[]{String.valueOf(creditoId)}
        );
    }





}
