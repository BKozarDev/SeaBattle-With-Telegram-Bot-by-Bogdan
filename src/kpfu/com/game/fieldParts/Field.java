package kpfu.com.game.fieldParts;

import kpfu.com.game.factory.FieldFactory;

public class Field {

    public static final int maxXY = 9, minXY = 0;

    private Cell[][] cells = new Cell[maxXY + 1][maxXY + 1];

    public Field() {
        for (int i = minXY; i <= maxXY; i++) {
            for (int j = minXY; j <= maxXY; j++) {
                cells[j][i] = new Cell(j, i, Cell.CellState.NULL, null);
            }
        }

        FieldFactory.setBufferField(cells);
        FieldFactory.createShips(this);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void resetCells(){
        for (int i = minXY; i <= maxXY; i++) {
            for (int j = minXY; j <= maxXY; j++) {
                cells[j][i].setState(Cell.CellState.NULL);
            }
        }

        FieldFactory.setBufferField(cells);
        FieldFactory.createShips(this);
    }
}
