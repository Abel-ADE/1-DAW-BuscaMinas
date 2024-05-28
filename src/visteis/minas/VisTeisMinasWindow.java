/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visteis.minas;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * Interfaz gráfica do xogo.
 *
 * @author Abel Iglesias Moure
 */
public class VisTeisMinasWindow extends javax.swing.JFrame {

    //Definimos cantidad de minas
    public static final int LOW_MINES = 8;
    public static final int MEDIUM_MINES = 20;
    public static final int HIGHT_MINES = 40;

    //Definimos tamaños para las celdas
    public static final int LOW_CELL = 6;
    public static final int MEDIUM_CELL = 8;
    public static final int HIGHT_CELL = 10;

    private Game game;
    private JToggleButton[][] cellButtons;

    /**
     * Creates new form VisTeisMinasWindow
     */
    public VisTeisMinasWindow() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/visteis/minas/bomb.png")).getImage());
        setLocationRelativeTo(null);
        setVisible(true);
        jMenuNewGameActionPerformed(null);
    }

    /**
     * Mostra ao usuario un diálogo para que seleccione o nivel de xogo, crea un
     * novo obxecto Game e rexenera o panel do xogo co unha nova matriz de
     * JToogleButtons.
     */
    public void startNewGame() {
        String[] options = new String[]{"Alto", "Medio", "Baixo"};
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel label = new JLabel("Indica o nivel de dificultade");
        panel.add(label);

        int option = JOptionPane.showOptionDialog(this, panel, "Nivel de xogo",
                JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[2]);

        switch (option) {
            //Nivel de dificultad alto
            case 0:
                game = new Game(HIGHT_CELL, HIGHT_CELL, HIGHT_MINES);
                break;
            //Nivel de dificultad medio
            case 1:
                game = new Game(MEDIUM_CELL, MEDIUM_CELL, MEDIUM_MINES);
                break;
            //Nivel de dificultad baixo
            case 2:
                game = new Game(LOW_CELL, LOW_CELL, LOW_MINES);
                break;
            default:
                int raws = game.getRaws();
                int columns = game.getColumns();
                int mines = game.getMines();
                game = new Game(raws, columns, mines);
        }
        //Reseteamos o panel de xogo
        jPanelBody.removeAll();
        //Pomos un grid axeitado
        jPanelBody.setLayout(new GridLayout(game.getRaws(), game.getColumns()));

        cellButtons = new JToggleButton[game.getRaws()][game.getColumns()];
        //Xeramos os botóns e os almacenamos no array de botóns e os amosamos na pantalla
        for (int i = 0; i < game.getRaws(); i++) {
            for (int j = 0; j < game.getColumns(); j++) {
                JToggleButton button = new JToggleButton();
                button.setName(i + "," + j);
                button.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        cellButtonActionPerformed(evt);
                    }
                });
                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        cellButtonMouseClicked(evt);
                    }
                });
                cellButtons[i][j] = button;
                jPanelBody.add(button);
            }
        }
        jPanelBody.updateUI();
    }

    /**
     * Actualiza no panel do xogo os JToogleButtons das celas que están
     * destapadas e mostra unha mina ou o número de minas adxacentes.
     */
    private void updatePanel() {
        for (int i = 0; i < game.getRaws(); i++) {
            for (int j = 0; j < game.getColumns(); j++) {

                JToggleButton button = cellButtons[i][j];
                Cell cell = game.getCell(i, j);

                if (cell.getState() == Cell.UNCOVERED) {
                    button.setSelected(true);
                    if (cell.isMined()) {
                        button.setIcon(new ImageIcon(getClass().getResource("/visteis/minas/bomb.png")));
                    } else {
                        button.setText(Integer.toString(game.getAdjacentMines(game.getCell(i, j))));
                        button.setEnabled(false);
                    }
                }
            }
        }
    }

    /**
     * Finaliza a partida, mostrando unha mensaxe ao usuario e dando a opción de
     * xogar outra partida ou pechar a aplicación.
     *
     * @param message a mensaxe que se mostra ao usuario.
     */
    private void finishGame(String message) {
        int option = JOptionPane.showConfirmDialog(this, message, "Fin da partida", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);

        switch (option) {
            case 0:
                startNewGame();
                break;
            default:
                dispose();
        }
    }

    /**
     * Método que captura o clic co botón dereito sobre un JToogleButton do
     * panel do xogo, marcando ou desmarcando o botón , e modificando o estado
     * da cela correspondente.
     *
     * @param evt e evento.
     */
    private void cellButtonMouseClicked(java.awt.event.MouseEvent evt) {
        JToggleButton button;
        button = (JToggleButton) evt.getSource();
        int raw = Character.getNumericValue(button.getName().charAt(0));
        int column = Character.getNumericValue(button.getName().charAt(2));

        Cell cell = game.getCell(raw, column);

        int stateCell = cell.getState();

        switch (evt.getButton()) {
            case 3:
                //Botón derecho
                switch (stateCell) {
                    case Cell.COVER:
                        cell.setState(Cell.MARKED);
                        button.setIcon(new ImageIcon(getClass().getResource("/visteis/minas/warning.png")));
                        break;
                    case Cell.MARKED:
                        cell.setState(Cell.COVER);
                        button.setIcon(null);
                        break;
                }
                break;
        }
    }

    /**
     * Captura o evento do ratón sobre os botóns.
     *
     * @param evt o evento.
     */
    private void cellButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JToggleButton button;
        button = (JToggleButton) evt.getSource();
        int raw = Character.getNumericValue(button.getName().charAt(0));
        int column = Character.getNumericValue(button.getName().charAt(2));

        Cell cell = game.getCell(raw, column);

        int stateCell = cell.getState();

        //Botón izquierdo
        switch (stateCell) {
            case Cell.MARKED:
                button.setIcon(null);
            default:
                openCell(cell);

        }
    }

    /**
     * Abre unha cela. Se a a cela ten unha mina, destapa todas as minas e
     * finaliza a partida (chamando ao método anterior).
     *
     * @param cell
     */
    private void openCell(Cell cell) {
        //Abre a celda
        cell.setState(Cell.UNCOVERED);
        //Se ten unha mina
        if (cell.isMined()) {
            game.openAllMines();
            //Se non ten unha mina e o número de minas adxacentes é 0
        } else if (game.getAdjacentMines(cell) == 0) {
            ArrayList<Cell> AdjacentCells = game.getAdjacentCells(cell);
            //Aplico esta misma función a cada unha das celas adxacentes
            for (Cell AdjacentCell : AdjacentCells) {
                //Sempre e cando a cela non fora aberta antes ou non estea marcada
                if (AdjacentCell.getState() != Cell.UNCOVERED && AdjacentCell.getState() != Cell.MARKED) {
                    openCell(AdjacentCell);
                }
            }
        }
        updatePanel();

        if (!game.checkCellsToOpen() || cell.isMined()) {
            finishGame("¿Desexas volver a xogar?");
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

        jPanelBody = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuNewGame = new javax.swing.JMenuItem();
        jMenuExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VisTeisMinas");
        setMinimumSize(new java.awt.Dimension(800, 600));

        javax.swing.GroupLayout jPanelBodyLayout = new javax.swing.GroupLayout(jPanelBody);
        jPanelBody.setLayout(jPanelBodyLayout);
        jPanelBodyLayout.setHorizontalGroup(
            jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 702, Short.MAX_VALUE)
        );
        jPanelBodyLayout.setVerticalGroup(
            jPanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 477, Short.MAX_VALUE)
        );

        getContentPane().add(jPanelBody, java.awt.BorderLayout.CENTER);

        jMenuFile.setText("Ficheiro");

        jMenuNewGame.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuNewGame.setText("Nova partida");
        jMenuNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuNewGameActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuNewGame);

        jMenuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuExit.setText("Saír");
        jMenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuExit);

        jMenuBar1.add(jMenuFile);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuNewGameActionPerformed
        startNewGame();
    }//GEN-LAST:event_jMenuNewGameActionPerformed

    private void jMenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExitActionPerformed
        dispose();
    }//GEN-LAST:event_jMenuExitActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VisTeisMinasWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisTeisMinasWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisTeisMinasWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisTeisMinasWindow.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisTeisMinasWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuExit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuNewGame;
    private javax.swing.JPanel jPanelBody;
    // End of variables declaration//GEN-END:variables
}
