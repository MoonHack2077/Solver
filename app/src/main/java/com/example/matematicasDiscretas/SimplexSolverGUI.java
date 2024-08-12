/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.example.matematicasDiscretas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author USER
 */
public class SimplexSolverGUI extends javax.swing.JFrame {

    // Campos de entrada para la función objetivo y restricciones
    private JTextField txtObjectiveX, txtObjectiveY;
    private JTextField txtConstraint1X, txtConstraint1Y, txtConstraint1RHS;
    private JTextField txtConstraint2X, txtConstraint2Y, txtConstraint2RHS;

    // Campos de salida para los resultados
    private JLabel lblResult, lblXValue, lblYValue;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimplexSolverGUI::new);
    }

    public SimplexSolverGUI() {
        // Crear la ventana principal
        JFrame frame = new JFrame("Simplex Solver");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Campos de entrada para la función objetivo
        JLabel lblObjective = new JLabel("Función objetivo: Z = ");
        lblObjective.setBounds(20, 20, 150, 20);
        frame.add(lblObjective);

        txtObjectiveX = new JTextField();
        txtObjectiveX.setBounds(150, 20, 50, 20);
        frame.add(txtObjectiveX);

        JLabel lblX = new JLabel("x +");
        lblX.setBounds(210, 20, 20, 20);
        frame.add(lblX);

        txtObjectiveY = new JTextField();
        txtObjectiveY.setBounds(240, 20, 50, 20);
        frame.add(txtObjectiveY);

        JLabel lblY = new JLabel("y");
        lblY.setBounds(300, 20, 20, 20);
        frame.add(lblY);

        // Campos de entrada para la primera restricción
        JLabel lblConstraint1 = new JLabel("Restricción 1: ");
        lblConstraint1.setBounds(20, 60, 150, 20);
        frame.add(lblConstraint1);

        txtConstraint1X = new JTextField();
        txtConstraint1X.setBounds(150, 60, 50, 20);
        frame.add(txtConstraint1X);

        JLabel lblConstraint1X = new JLabel("x +");
        lblConstraint1X.setBounds(210, 60, 20, 20);
        frame.add(lblConstraint1X);

        txtConstraint1Y = new JTextField();
        txtConstraint1Y.setBounds(240, 60, 50, 20);
        frame.add(txtConstraint1Y);

        JLabel lblConstraint1Y = new JLabel("y <=");
        lblConstraint1Y.setBounds(300, 60, 30, 20);
        frame.add(lblConstraint1Y);

        txtConstraint1RHS = new JTextField();
        txtConstraint1RHS.setBounds(340, 60, 50, 20);
        frame.add(txtConstraint1RHS);

        // Campos de entrada para la segunda restricción
        JLabel lblConstraint2 = new JLabel("Restricción 2: ");
        lblConstraint2.setBounds(20, 100, 150, 20);
        frame.add(lblConstraint2);

        txtConstraint2X = new JTextField();
        txtConstraint2X.setBounds(150, 100, 50, 20);
        frame.add(txtConstraint2X);

        JLabel lblConstraint2X = new JLabel("x +");
        lblConstraint2X.setBounds(210, 100, 20, 20);
        frame.add(lblConstraint2X);

        txtConstraint2Y = new JTextField();
        txtConstraint2Y.setBounds(240, 100, 50, 20);
        frame.add(txtConstraint2Y);

        JLabel lblConstraint2Y = new JLabel("y <=");
        lblConstraint2Y.setBounds(300, 100, 30, 20);
        frame.add(lblConstraint2Y);

        txtConstraint2RHS = new JTextField();
        txtConstraint2RHS.setBounds(340, 100, 50, 20);
        frame.add(txtConstraint2RHS);

        // Botón para calcular el valor óptimo
        JButton btnSolve = new JButton("Resolver");
        btnSolve.setBounds(150, 150, 100, 30);
        frame.add(btnSolve);

        // Campos para mostrar los resultados
        lblResult = new JLabel("Valor óptimo de Z: ");
        lblResult.setBounds(20, 200, 200, 20);
        frame.add(lblResult);

        lblXValue = new JLabel("x = ");
        lblXValue.setBounds(20, 230, 100, 20);
        frame.add(lblXValue);

        lblYValue = new JLabel("y = ");
        lblYValue.setBounds(150, 230, 100, 20);
        frame.add(lblYValue);

        // Acción del botón "Resolver"
        btnSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSimplex();
            }
        });

        frame.setVisible(true);
    }

    private void solveSimplex() {
        try {
            double cx = Double.parseDouble(txtObjectiveX.getText());
            double cy = Double.parseDouble(txtObjectiveY.getText());

            double[] objectiveFunction = {cx, cy};

            double[][] constraints = {
                {
                    Double.parseDouble(txtConstraint1X.getText()),
                    Double.parseDouble(txtConstraint1Y.getText()),
                    Double.parseDouble(txtConstraint1RHS.getText())
                },
                {
                    Double.parseDouble(txtConstraint2X.getText()),
                    Double.parseDouble(txtConstraint2Y.getText()),
                    Double.parseDouble(txtConstraint2RHS.getText())
                }
            };

            double[] result = solveSimplexAlgorithm(objectiveFunction, constraints);

            lblResult.setText("Valor óptimo de Z: " + result[0]);
            lblXValue.setText("x = " + result[1]);
            lblYValue.setText("y = " + result[2]);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double[] solveSimplexAlgorithm(double[] objectiveFunction, double[][] constraints) {
        double maxZ = 0;
        double[] optimalValues = new double[3];

        for (double x = 0; x <= constraints[0][2]; x++) {
            for (double y = 0; y <= constraints[1][2]; y++) {
                if (checkConstraints(x, y, constraints)) {
                    double Z = objectiveFunction[0] * x + objectiveFunction[1] * y;
                    if (Z > maxZ) {
                        maxZ = Z;
                        optimalValues[0] = Z;
                        optimalValues[1] = x;
                        optimalValues[2] = y;
                    }
                }
            }
        }

        return optimalValues;
    }

    private boolean checkConstraints(double x, double y, double[][] constraints) {
        for (double[] constraint : constraints) {
            if (constraint[0] * x + constraint[1] * y > constraint[2]) {
                return false;
            }
        }
        return true;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SimplexSolverGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SimplexSolverGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SimplexSolverGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SimplexSolverGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SimplexSolverGUI().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
