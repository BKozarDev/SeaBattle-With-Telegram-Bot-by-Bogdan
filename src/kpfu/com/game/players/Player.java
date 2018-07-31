package kpfu.com.game.players;


import kpfu.com.game.fieldParts.Cell;
import kpfu.com.game.fieldParts.Field;


public abstract class Player{

    Player opponent;

    Field field;

    boolean myTurn;


    int shipsToKill = 10;

    ShootResult shootResult;

    Player() {
        field = new Field();
    }

    public boolean moves(){
        return myTurn;
    }

    public int getShipsToKill() {
        return shipsToKill;
    }

    public Field getField() {
        return field;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    void changeTurn(){
        myTurn = !myTurn;
        opponent.myTurn = !opponent.myTurn;
    }

    public enum ShootResult {
        MISSED,
        INJURED,
        KILLED
    }

    ShootResult getShot(int x, int y){
        Cell cell = field.getCells()[x][y];
        if (cell.getState() == Cell.CellState.NULL || cell.getState() == Cell.CellState.ALIVE) {
            cell.getShot();
            switch (cell.getState()) {
                case MISSED:
                    return ShootResult.MISSED;
                case INJURED:
                    return ShootResult.INJURED;
                case KILLED:
                    return ShootResult.KILLED;
                default:
                    return null;
            }
        } return null;
    }

    abstract void afterShootingHandling(int x, int y);

    public void reset(){
        field.resetCells();
        shipsToKill = 10;
    }

}
