package kpfu.com.game.fieldParts;

import java.util.ArrayList;

public class Ship {

    private ArrayList<Cell> cells;

    private ArrayList<Cell> borders;

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void die(){
        for (Cell cell : cells) {
            cell.setState(Cell.CellState.KILLED);
        }

        for (Cell cell : borders) {
            cell.setState(Cell.CellState.MISSED);
        }
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public void setBorders(ArrayList<Cell> borders) {
        this.borders = borders;
    }
}
