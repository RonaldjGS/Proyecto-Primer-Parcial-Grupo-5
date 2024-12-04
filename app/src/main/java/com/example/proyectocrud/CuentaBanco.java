package com.example.proyectocrud;

public class CuentaBanco {

    private String id;
    private String cedula;
    private String nombres;
    private String apellidos;
    private int edad;
    private String fecha_nacimiento;
    private double saldo;
    private String banco;
    private String correo;
    private String password;



    public CuentaBanco(String id , String cedula,String nombres, String apellidos, int edad, String fecha_nacimiento, Double saldo, String banco, String correo,String password) {
        this.id = id;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.fecha_nacimiento = fecha_nacimiento;
        this.saldo = saldo;
        this.banco = banco;
        this.correo = correo;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }

    public double getSaldo() {
        return saldo;
    }
    public String getBanco() {
        return banco;
    }

}
