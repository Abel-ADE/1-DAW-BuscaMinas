/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package visteis.minas;

import java.util.ArrayList;

/**
 * Representa o xogo.
 *
 * @author Abel Iglesias Moure
 */
public class Game {

    /**
     * Representa a matriz de obxectos de tipo cela.
     */
    private Cell[][] cells;

    /**
     * Indica o número de filas do xogo.
     */
    private int raws;

    /**
     * Indica o número de columnas do xogo.
     */
    private int columns;

    /**
     * Indica o número de minas do xogo.
     */
    private int mines;

    /**
     * Devolve o array de obxectos cela.
     *
     * @return o array de obxectos cela.
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Sobrescribe o array de obxectos cela.
     *
     * @param cells o array de obxectos cela.
     */
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    /**
     * Devolve o número de filas que ten o xogo.
     *
     * @return o número de filas que ten o xogo.
     */
    public int getRaws() {
        return raws;
    }

    /**
     * Devolve o número de columnas que ten o xogo.
     *
     * @return o número de filas que ten o xogo.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Devolve o número de minas que ten o xogo.
     *
     * @return o número de minas que ten o xogo.
     */
    public int getMines() {
        return mines;
    }

    /**
     * (Constructor)Crea unha nova partida. Inicializará a matriz de celas e
     * usará o método fillMines(int mines) para repartir as minas.
     *
     * @param raws número de filas do xogo.
     * @param columns número de columnas do xogo.
     * @param mines número de minas do xogo.
     */
    public Game(int raws, int columns, int mines) {

        this.raws = raws;
        this.columns = columns;
        this.mines = mines;

        cells = new Cell[raws][columns];

        for (int i = 0; i < raws; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        fillMines(mines);
    }

    /**
     * Devolve a cela que está nunha fila e columna determinadas.
     *
     * @param raw fila da cela.
     * @param column columna da cela.
     * @return o obxecto cela desa posición.
     */
    public Cell getCell(int raw, int column) {

        return cells[raw][column];
    }

    /**
     * Obtén a lista de celas adxacentes da cela que se recibe como parámetro.
     *
     * @param cell a cela da que queremos obter as adxacentes.
     * @return un ArrayList coas celas adxacentes.
     */
    public ArrayList<Cell> getAdjacentCells(Cell cell) {

        ArrayList<Cell> AdjacentCells = new ArrayList<>();

        //Fila da cela
        int rawCell = cell.getRaw();

        //Columna da cela
        int columnCell = cell.getColumn();

        //Cantidad de iteraccións sobre a fila e a columna
        int maxRaw, maxCol;

        //Comprobo que non me saia do xogo pola esquerda (filas negativas) e añado o número de iteraccións máximo sobre a fila.
        if ((rawCell - 1) >= 0) {
            rawCell--;
            maxRaw = 3;
        } else {
            maxRaw = 2;
        }

        ////Comprobo que non me saia do xogo por arriba (columnas negativas) e añado o número de iteraccións máximo sobre a columna.
        if ((columnCell - 1) >= 0) {
            columnCell--;
            maxCol = 3;
        } else {
            maxCol = 2;
        }

        //Recorro todas as celas adxacentes incluída a cela actual.
        for (int i = rawCell; i < rawCell + maxRaw && i < raws; i++) {
            for (int j = columnCell; j < columnCell + maxCol && j < columns; j++) {
                //Añádoa a lista de celas adxacentes
                AdjacentCells.add(getCell(i, j));
            }
        }
        //Elimino a cela actual da lista de celas adxacentes.
        AdjacentCells.remove(cell);
        //Devolvo a lista de celas adxacentes.
        return AdjacentCells;
    }

    /**
     * Obtén a suma das minas das celas adxacentes á cela que se recibe como
     * parámetro. Fará uso do método anterior.
     *
     * @param cell a cela da que queremos calcular as minas adxacentes.
     * @return o número de minas adxacentes.
     */
    public int getAdjacentMines(Cell cell) {
        //Obteño a colección de celas adxacentes
        ArrayList<Cell> AdjacentCells = getAdjacentCells(cell);
        //Creo a variable que me indica o número de minas nas celas adxacentes
        int AdjacentMines = 0;

        //Para cada cela adxacente
        for (Cell AdjacentCell : AdjacentCells) {
            //Comprobo se ten unha mina
            if (AdjacentCell.isMined()) {
                //Actualizo o número de minas nas celas adxacentes
                AdjacentMines++;
            }
        }
        //Unha vez comprobo todas as celas adxacentes, devolvo o resultado
        return AdjacentMines;
    }

    /**
     * Destapa unha cela, e no caso de que o número de minas adxacentes sexa
     * cero, destapa todas as celas adxacentes que non estean destapadas. È un
     * método recursivo, xa que se chama a si mesmo no seu código.
     *
     * @param cell a cela que queremos destapar.
     */
    public void openCell(Cell cell) {
        //Destapo a cela actual
        cell.setState(Cell.UNCOVERED);
        //Obteño o número de minas adxacentes
        int AdjacentMines = getAdjacentMines(cell);
        //Se non hay minas
        if (AdjacentMines == 0) {
            //Obteño as celas adxacentes
            ArrayList<Cell> AdjacentCells = getAdjacentCells(cell);
            //Aplico esta misma función a cada unha das celas adxacentes
            for (Cell AdjacentCell : AdjacentCells) {
                //Sempre e cando a cela non fora aberta antes
                if (AdjacentCell.getState() != Cell.UNCOVERED) {
                    openCell(AdjacentCell);
                }
            }
        }
    }

    /**
     * Destapa todas as celas que teñan minas. Usarémolo cando o xogador perda
     * para mostrarlle en que celas estaban as minas.
     */
    public void openAllMines() {
        //Recorro cada unha das celas do xogo
        for (int i = 0; i < raws; i++) {
            for (int j = 0; j < columns; j++) {
                //Se a cela ten mina
                if (getCell(i, j).isMined()) {
                    //Destápoa
                    getCell(i, j).setState(Cell.UNCOVERED);
                }
            }
        }
    }

    /**
     * Comproba se quedan celas sen minas por destapar. Usarémolo para saber se
     * o xogador gañou a partida.
     *
     * @return (true) se quedan celas sen minas por destapar.
     */
    public boolean checkCellsToOpen() {
        //Variable que indica se quedan minas por destapar
        boolean CellsToOpen = false;

        //Recorro cada unha das celas do xogo hata que atope celas sen minas
        for (int i = 0; i < raws && !CellsToOpen; i++) {
            for (int j = 0; j < columns && !CellsToOpen; j++) {
                //Se a cela non ten mina
                if (!getCell(i, j).isMined()) {
                    //Aínda quedan celas sen minas por destapar
                    CellsToOpen = true;
                }
            }
        }
        //Devolvo se quedan minas por destapar
        return CellsToOpen;
    }

    /**
     * Coloca no array de celas o número de minas indicado, repartíndoas de
     * forma aleatoria.
     *
     * @param mines o número de minas do xogo.
     */
    private void fillMines(int mines) {

        //Número de minas que vou a colocar no xogo
        int numberOFMines = mines;

        do {
            //Genero un valor aleatorio para a posición da fila
            int generatedRaw = new java.util.Random().nextInt(raws);
            //Genero un valor aleatorio para a posición da columna
            int generatedColumn = new java.util.Random().nextInt(columns);

            //Comprobo se esa celda non ten mina
            if (!cells[generatedRaw][generatedColumn].isMined()) {
                //Se non ten mina póñolle unha
                cells[generatedRaw][generatedColumn].setMined(true);
                //Elimino a mina do número de minas pendientes de colocar
                numberOFMines--;
            }
            //Repito hata que coloque todas as minas
        } while (numberOFMines != 0);

    }
}