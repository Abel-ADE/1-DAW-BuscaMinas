/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visteis.minas;

/**
 * Representa unha cela do xogo.
 *
 * @author Abel Iglesias Moure
 */
public class Cell {

    /**
     * Indica se a cela contén unha mina ou non.
     */
    private boolean mined;

    /**
     * Número da fila na que está colocada a cela.
     */
    private int raw;

    /**
     * Número da columna na que está colocada a cela.
     */
    private int column;

    /**
     * Indica o estado da cela. Definiranse nesta clase tres constantes para
     * representar os distintos estados: Tapada (1), marcada (2) e destapada
     * (3).
     */
    private int state;

    /**
     * Define o estado "tapada".
     */
    public static final int COVER = 1;

    /**
     * Define o estado "marcada".
     */
    public static final int MARKED = 2;

    /**
     * Define o estado "destapada".
     */
    public static final int UNCOVERED = 3;

    /**
     * Devolve se a cela conten unha mina.
     *
     * @return se a cela conten unha mina.
     */
    public boolean isMined() {
        return mined;
    }

    /**
     * Sobrescribe se a cela conten unha mina.
     *
     * @param mined se a cela conten unha mina.
     */
    public void setMined(boolean mined) {
        this.mined = mined;
    }

    /**
     * Devolve o estado da cela.
     *
     * @return o estado da cela.
     */
    public int getState() {
        return state;
    }

    /**
     * Sobrescribe o estado da cela.
     *
     * @param state o estado da cela.
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Devolve a fila na que está posicionada a cela.
     *
     * @return a fila na que está posicionada a cela.
     */
    public int getRaw() {
        return raw;
    }

    /**
     * Sobrescribe a fila na que está posicionada a cela.
     *
     * @param raw a fila na que está posicionada a cela.
     */
    public void setRaw(int raw) {
        this.raw = raw;
    }

    /**
     * Devolve a columna na que está posicionada a cela.
     *
     * @return a columna na que está posicionada a cela.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sobrescribe a columna na que está posicionada a cela.
     *
     * @param column a columna na que está posicionada a cela.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Constructor da clase.
     *
     * @param raw o número de fila no que se atopa a cela.
     * @param column o número de columna no que se atopa a cela.
     */
    public Cell(int raw, int column) {
        this.raw = raw;
        this.column = column;
        this.state = COVER;
    }

}
