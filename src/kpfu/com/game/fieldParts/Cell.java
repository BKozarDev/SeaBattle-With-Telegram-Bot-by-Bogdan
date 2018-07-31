package kpfu.com.game.fieldParts;

import kpfu.com.game.gui.Canvas;

import javax.swing.*;
import java.util.ArrayList;

public class Cell extends JButton{

    private int xCo, yCo;

    private Ship ship;

    private CellState state;

    public Cell(int x, int y, CellState cellState, Ship ship) {
        super();
        this.xCo = x;
        this.yCo = y;
        this.state = cellState;
        this.ship = ship;
    }

    public enum CellState {
        NULL,
        MISSED,
        ALIVE,
        INJURED,
        KILLED
    }

    public boolean isAlive(){
        return state == CellState.ALIVE;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state){
        this.state = state;
        Canvas.getInstance().draw();
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getXCo() {
        return xCo;
    }

    public int getYCo() {
        return yCo;
    }

    public void getShot(){
        switch (state){
            case NULL:
                setState(CellState.MISSED);
                break;
            case ALIVE:
                if (leftAliveNeighboors()) {
                    setState(CellState.INJURED);
                } else {
                    ship.die();
                }
                break;
        }
    }

    public void inititalizeState(CellState state){
        this.state = state;
    }

    @Override
    public int hashCode() {
        return xCo * 37 + yCo * 37 + 37;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Cell)) return false;
        Cell cell = (Cell) obj;

        return (cell.xCo == xCo && cell.yCo == yCo);
    }

    private boolean leftAliveNeighboors(){
        ArrayList<Cell> cells = ship.getCells();
        for (Cell cell : cells){
            if (!cell.equals(this) && cell.isAlive()) return true;
        }
        return false;
    }

}
