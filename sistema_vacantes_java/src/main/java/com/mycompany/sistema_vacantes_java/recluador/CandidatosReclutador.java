/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistema_vacantes_java.recluador;

import com.mycompany.sistema_vacantes_java.conexion.ConexionBD;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Yaelita
 */

public class CandidatosReclutador extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CandidatosReclutador.class.getName());

    /**
     * Creates new form CandidatosReclutador
     */
    public CandidatosReclutador() {
        initComponents();
        configurarMenuContextual();
        cargarCandidatos(null);
        cargarEstadisticas();
         this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    private void configurarMenuContextual() {
        javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();

        javax.swing.JMenuItem itemAceptar = new javax.swing.JMenuItem("Aceptar candidato");
        javax.swing.JMenuItem itemRechazar = new javax.swing.JMenuItem("Rechazar candidato");
        javax.swing.JMenuItem itemEntrevista = new javax.swing.JMenuItem("Agendar entrevista");

        itemAceptar.addActionListener(e -> cambiarEstadoSeleccionado("Aceptada"));
        itemRechazar.addActionListener(e -> cambiarEstadoSeleccionado("Rechazada"));
        itemEntrevista.addActionListener(e -> agendarEntrevistaSeleccionado());

        menu.add(itemAceptar);
        menu.add(itemRechazar);
        menu.addSeparator();
        menu.add(itemEntrevista);

        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mostrarMenu(evt);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                mostrarMenu(evt);
            }

            private void mostrarMenu(java.awt.event.MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    int fila = jTable2.rowAtPoint(evt.getPoint());
                    if (fila >= 0) {
                        jTable2.setRowSelectionInterval(fila, fila);
                        menu.show(jTable2, evt.getX(), evt.getY());
                    }
                }
            }
        });
    }

    private void cambiarEstadoSeleccionado(String nuevoEstado) {
        int fila = jTable2.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona un candidato de la lista.", "Sin selección", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        int postulacionId = (int) jTable2.getValueAt(fila, 0);
        try {
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement("UPDATE postulaciones SET estado = ? WHERE id = ?");
            ps.setString(1, nuevoEstado);
            ps.setInt(2, postulacionId);
            ps.executeUpdate();
            ps.close();
            con.close();

            javax.swing.JOptionPane.showMessageDialog(this, "Postulación actualizada a \"" + nuevoEstado + "\".");
            cargarCandidatos(jTextField1.getText().trim().isEmpty() ? null : jTextField1.getText().trim());
            cargarEstadisticas();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "No se pudo actualizar la postulación.\n" + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agendarEntrevistaSeleccionado() {
        int fila = jTable2.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona un candidato de la lista.", "Sin selección", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        int postulacionId = (int) jTable2.getValueAt(fila, 0);
        String candidato = String.valueOf(jTable2.getValueAt(fila, 1));

        javax.swing.JTextField fechaField = new javax.swing.JTextField("aaaa-mm-dd hh:mm");
        javax.swing.JComboBox<String> modalidadCombo = new javax.swing.JComboBox<>(new String[]{"Virtual", "Presencial"});
        javax.swing.JTextField notasField = new javax.swing.JTextField();

        Object[] formulario = {
            "Candidato: " + candidato,
            "Fecha y hora (aaaa-mm-dd hh:mm):", fechaField,
            "Modalidad:", modalidadCombo,
            "Notas:", notasField
        };

        int opcion = javax.swing.JOptionPane.showConfirmDialog(this, formulario, "Agendar Entrevista", javax.swing.JOptionPane.OK_CANCEL_OPTION);
        if (opcion == javax.swing.JOptionPane.OK_OPTION) {
            try {
                java.sql.Timestamp fechaHora = java.sql.Timestamp.valueOf(fechaField.getText().trim() + ":00");

                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO entrevistas (postulacion_id, fecha_hora, modalidad, notas, estado) VALUES (?, ?, ?, ?, 'Programada')");
                ps.setInt(1, postulacionId);
                ps.setTimestamp(2, fechaHora);
                ps.setString(3, (String) modalidadCombo.getSelectedItem());
                ps.setString(4, notasField.getText().trim());
                ps.executeUpdate();
                ps.close();
                con.close();

                javax.swing.JOptionPane.showMessageDialog(this, "Entrevista agendada correctamente.");
                cargarEstadisticas();
            } catch (IllegalArgumentException iae) {
                javax.swing.JOptionPane.showMessageDialog(this, "La fecha debe tener el formato aaaa-mm-dd hh:mm (ej. 2026-08-01 10:30).", "Formato inválido", javax.swing.JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "No se pudo agendar la entrevista.\n" + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Gestion de Candidatos");

        jPanel3.setBackground(new java.awt.Color(204, 153, 255));

        jLabel2.setText("Total");

        jLabel3.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 153, 255));

        jLabel4.setText("En Revision");

        jLabel5.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(204, 153, 255));

        jLabel6.setText("Entrevistas");

        jLabel7.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(204, 153, 255));

        jLabel8.setText("Contratados");

        jLabel9.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jLabel10.setText("Lista de candidatos");

        jButton1.setBackground(new java.awt.Color(204, 153, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton3.setBackground(new java.awt.Color(204, 153, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setText("Borrar Candidato");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(153, 0, 153));
        jButton4.setText("Regresar");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(160, 160, 160)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(29, 29, 29)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addComponent(jLabel10)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton1)))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jButton3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String texto = jTextField1.getText().trim();
        cargarCandidatos(texto.isEmpty() ? null : texto);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.setVisible(false); 
        InicioReclutador inicio = new InicioReclutador();
        inicio.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int fila = jTable2.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona un candidato de la lista para borrarlo.", "Sin selección", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        int postulacionId = (int) jTable2.getValueAt(fila, 0);
        String candidato = String.valueOf(jTable2.getValueAt(fila, 1));

        int confirmar = javax.swing.JOptionPane.showConfirmDialog(this, "¿Borrar la postulación de \"" + candidato + "\"?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
            try {
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement("DELETE FROM postulaciones WHERE id = ?");
                ps.setInt(1, postulacionId);
                ps.executeUpdate();
                ps.close();
                con.close();

                javax.swing.JOptionPane.showMessageDialog(this, "Postulación eliminada.");
                cargarCandidatos(null);
                cargarEstadisticas();
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "No se pudo borrar la postulación.\n" + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cargarCandidatos(String filtro) {
        try {
            Connection con = ConexionBD.conectar();
            StringBuilder sql = new StringBuilder(
                "SELECT p.id, c.nombre, c.correo, v.trabajo, p.estado, p.fecha_postulacion " +
                "FROM postulaciones p " +
                "JOIN candidatos c ON p.candidato_id = c.id " +
                "JOIN vacantes v ON p.vacante_id = v.id ");
            if (filtro != null) {
                sql.append("WHERE c.nombre LIKE ? ");
            }
            sql.append("ORDER BY p.fecha_postulacion DESC");

            PreparedStatement ps = con.prepareStatement(sql.toString());
            if (filtro != null) {
                ps.setString(1, "%" + filtro + "%");
            }
            ResultSet rs = ps.executeQuery();

            DefaultTableModel modelo = new DefaultTableModel(
                    new Object[]{"ID", "Candidato", "Correo", "Vacante", "Estado", "Fecha"}, 0);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("trabajo"),
                    rs.getString("estado"),
                    rs.getTimestamp("fecha_postulacion")
                });
            }

            jTable2.setModel(modelo);

            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "No se pudieron cargar los candidatos.\n" + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarEstadisticas() {
        try {
            Connection con = ConexionBD.conectar();

            PreparedStatement ps1 = con.prepareStatement("SELECT COUNT(*) FROM postulaciones");
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) jLabel3.setText(String.valueOf(rs1.getInt(1)));
            rs1.close();
            ps1.close();

            PreparedStatement ps2 = con.prepareStatement("SELECT COUNT(*) FROM postulaciones WHERE estado = 'Pendiente'");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) jLabel5.setText(String.valueOf(rs2.getInt(1)));
            rs2.close();
            ps2.close();

            PreparedStatement ps3 = con.prepareStatement("SELECT COUNT(*) FROM entrevistas");
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) jLabel7.setText(String.valueOf(rs3.getInt(1)));
            rs3.close();
            ps3.close();

            PreparedStatement ps4 = con.prepareStatement("SELECT COUNT(*) FROM postulaciones WHERE estado = 'Aceptada'");
            ResultSet rs4 = ps4.executeQuery();
            if (rs4.next()) jLabel9.setText(String.valueOf(rs4.getInt(1)));
            rs4.close();
            ps4.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new CandidatosReclutador().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
