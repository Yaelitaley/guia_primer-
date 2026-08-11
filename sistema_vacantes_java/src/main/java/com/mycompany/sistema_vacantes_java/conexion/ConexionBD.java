package com.mycompany.sistema_vacantes_java.conexion;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Yaelita
 */


import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    private static final String URL =
            "jdbc:mysql://localhost:3306/gestor_reclutamiento";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {

        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

    }
}

