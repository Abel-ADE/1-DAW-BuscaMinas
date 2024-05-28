/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visteis.minas;

import java.util.Scanner;

/**
 * Representa a interface do xogo en modo texto.
 *
 * @author Abel Iglesias Moure
 */
public class VisTeisMinasMenu {

    /**
     * Filas do xogo.
     */
    public static final int RAWS = 6;

    /**
     * Columnas do xogo.
     */
    public static final int COLUMNS = 6;

    /**
     * Número de minas do xogo.
     */
    public static final int MINES = 8;

    /**
     * Método que mostra o panel do xogo.
     *
     * @param game referencia ao obxecto xogo.
     */
    private void showPanel(Game game) {
        System.out.println("--- Estás xogando ao VisTeis Minas ---");
        System.out.println("---        Estado do panel         ---");
        System.out.println();

        System.out.println("   0 1 2 3 4 5 ");
        System.out.println("  -------------");

        for (int i = 0; i < RAWS; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < COLUMNS; j++) {

                switch (game.getCell(i, j).getState()) {
                    case 1:
                        System.out.print(" |");
                        break;
                    case 2:
                        System.out.print("!|");
                        break;
                    case 3:
                        if (game.getCell(i, j).isMined()) {
                            System.out.print("*|");
                        } else {
                            System.out.print(game.getAdjacentMines(game.getCell(i, j)) + "|");
                        }
                        break;
                }
            }
            System.out.println();
            System.out.println("  -------------");
        }
    }

    /**
     * Método que empeza unha nova partida.
     */
    public void startNewGame() {
        Scanner scan = new Scanner(System.in);
        int raw, column;
        boolean exit = false;
        boolean loseGame = false;

        Game game = new Game(RAWS, COLUMNS, MINES);

        do {

            //Mostro o panel
            showPanel(game);

            //Pido unha acción
            System.out.println("Introduce a acción (s:Saír, m:Marcar unha cela, d: Desmarcar unha cela, a: abrir unha cela):");
            char action = scan.nextLine().charAt(0);

            //Se o carácter introducido non é valido
            if (action != 'm' && action != 'd' && action != 'a' && action != 's') {
                //Mostro unha mensaxe de erro
                System.out.println("A acción introducida non é válida!");
                //Senón executo a acción do usuario
            } else {

                switch (action) {
                    case 'm': //Marcar unha cela

                        System.out.println("Introduce a fila da cela:");
                        raw = scan.nextInt();
                        scan.nextLine();

                        System.out.println("Introduce a columna da cela:");
                        column = scan.nextInt();
                        scan.nextLine();

                        if (game.getCell(raw, column).getState() == Cell.COVER) {
                            game.getCell(raw, column).setState(Cell.MARKED);
                        }
                        break;

                    case 'd': //Desmarcar unha cela

                        System.out.println("Introduce a fila da cela:");
                        raw = scan.nextInt();
                        scan.nextLine();

                        System.out.println("Introduce a columna da cela:");
                        column = scan.nextInt();
                        scan.nextLine();

                        if (game.getCell(raw, column).getState() == Cell.MARKED) {
                            game.getCell(raw, column).setState(Cell.COVER);
                        }

                        break;
                    case 'a': //Abrir unha cela

                        System.out.println("Introduce a fila da cela:");
                        raw = scan.nextInt();
                        scan.nextLine();

                        System.out.println("Introduce a columna da cela:");
                        column = scan.nextInt();
                        scan.nextLine();

                        game.openCell(game.getCell(raw, column));

                        //Se abro una cela cunha mina
                        if (game.getCell(raw, column).isMined()) {
                            //Mostro todas as minas da partida
                            game.openAllMines();
                            //Recargo a pantalla
                            showPanel(game);

                            System.out.println("Perdiches!! O xogo rematou");
                            loseGame = true;
                        } else if (!game.checkCellsToOpen()) {
                            System.out.println("Felicidades!! Gañaches a partida");
                        }
                        break;
                    case 's': //Saír
                        exit = true;
                        break;
                }
            }

        } while (!exit && game.checkCellsToOpen() && !loseGame);

        System.out.println("Queres xogar de novo?(s/n)");
        char newGame = scan.nextLine().charAt(0);

        if (newGame == 's') {
            System.out.println();
            startNewGame();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean typeText = false;
        
        if (args.length>=0) {
            for (String arg : args) {
                if (arg.equals("text")) {
                    typeText = true;
                }
            }
        }
        
        if (typeText) {
            VisTeisMinasMenu teisMinas = new VisTeisMinasMenu();
            teisMinas.startNewGame();
        }else{
            VisTeisMinasWindow.main(args);
        }
    }

}
