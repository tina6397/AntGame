package com.model;

public class Cell {

    private Content content = Content.EMPTY;
    private int getScentMark = 0;
    private Ant ant = null;
    private boolean dirty = true;
    private Content colony = null;

    public Cell() {                 // need to talk

    }

    public Cell(Content content){
        this.content = content;
    }

    /**
     * If a Cell is rocky, an ant cannot move into it
     * @return true if cell is rocky, false otherwise
     */
    public boolean isRocky() {
        if(content == Content.ROCKY){
            return true;
        }
        return false;
    }

    /**
     * Returns the scent mark at the given cell.
     * (1-7) are black colony scents (7-13) are red. 0 if no
     * scent marker has been placed.
     * @return int representing given scent mark
     */
    public int getScentMark() {
        return getScentMark;
    }

    /**
     * Returns the contents of the Cell.
     * The return type is a char and can be later parsed to int
     * if the cell contains food.
     * @return Content representing contents of cell
     */
    public Content getContents() {
        return content;
    }

    /**
     * Returns ant in the current Cell.
     * @return ant in the current cell
     */
    public Ant getAnt() {
        return ant;
    }

    /**
     * Set Scent mark on the cell
     */
    public void setScentMark(int mark) {
        getScentMark = mark;
    }

    /**
     * Set content on the cell
     */
    public void setContents(Content content) {
            this.content = content;
    }

    /**
     * Set ant id on the cell
     * @param ant the ant to set at cell, null if removing ant from cell
     */
    public void setAnt(Ant ant) {
        this.ant = ant;
    }

    /**
     *  Set anthill at this cell.
     *  @param content one of Contents Enum(In particular, REDHILL and BLACKHILL)
     */
    public void setColonyCell(Content content) {
        colony = content;
    }

    /**
     *  get the content to check this cell belong to part of colony
     *
     */
    public Content isColonyCell() {
        return colony;
    }

}