package com.mycompany.sistema_vacantes_java.conexion;

/**
 * Guarda en memoria los datos de la persona que inició sesión
 * (candidato, reclutador o administrador) mientras la aplicación
 * está abierta. Como es una aplicación de escritorio de un solo
 * usuario a la vez, basta con campos estáticos.
 *
 * @author Yaelita
 */
public class Sesion {

    // Candidato
    public static int candidatoId = -1;
    public static String candidatoNombre = "";
    public static String candidatoCorreo = "";

    // Reclutador
    public static int reclutadorId = -1;
    public static String reclutadorNombre = "";
    public static String reclutadorCorreo = "";

    // Administrador
    public static int adminId = -1;
    public static String adminNombre = "";
    public static String adminCorreo = "";

    /** Limpia todos los datos de sesión (usar al cerrar sesión). */
    public static void cerrar() {
        candidatoId = -1;
        candidatoNombre = "";
        candidatoCorreo = "";

        reclutadorId = -1;
        reclutadorNombre = "";
        reclutadorCorreo = "";

        adminId = -1;
        adminNombre = "";
        adminCorreo = "";
    }
}
