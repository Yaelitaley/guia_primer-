/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistema_vacantes_java;

/**
 *
 * @author Yaelita
 */
public class Sistema_vacantes_java {

    public static void main(String[] args) {
        /* Usa Nimbus si está disponible para que la interfaz se vea mejor */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            // Si Nimbus no está disponible, se usa el look and feel por defecto.
        }

        java.awt.EventQueue.invokeLater(() ->
            new com.mycompany.sistema_vacantes_java.inicio.inicio().setVisible(true)
        );
    }
}
