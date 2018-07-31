package kpfu.com.game.factory;

import kpfu.com.game.fieldParts.Cell;
import kpfu.com.game.fieldParts.Field;
import kpfu.com.game.fieldParts.Ship;

import java.util.ArrayList;
import java.util.Random;

public class FieldFactory {

    private static Cell[][] bufferField;

    private enum Orientation{
        HORIZONTAL,
        VERTICAL
    }


    public static void setBufferField(Cell[][] bufferField) {
        FieldFactory.bufferField = bufferField;
    }

    public static void createShips(Field field){
        ArrayList<Ship> ships = new ArrayList<>();
        Random random = new Random();

        for (int shipSize = 4; shipSize >= 1 ; --shipSize) {
            int shipsCount = 5 - shipSize;
            for (int i = 0; i < shipsCount; ++i) {
                Orientation orientation;
                int x, y;
                do {
                    orientation = getNextOrientation();
                    if (orientation == Orientation.HORIZONTAL){
                        x = random.nextInt(10 - shipSize + 1);
                        y = random.nextInt(10);
                    } else {
                        x = random.nextInt(10);
                        y = random.nextInt(10 - shipSize + 1);
                    }

                } while (!validPlaceForShip(x, y, shipSize, orientation));

                ships.add(createShip(x, y, shipSize, orientation));
            }

        }

        field.setCells(bufferField);
        bufferField = null;
    }

    public static void initializeBuffer(){
        bufferField = new Cell[Field.maxXY + 1][Field.maxXY + 1];
        for (int i = Field.minXY; i <= Field.maxXY; i++) {
            for (int j = Field.minXY; j <= Field.maxXY; j++) {
                bufferField[j][i] = new Cell(j, i, Cell.CellState.NULL, null);
            }
        }
    }

    private static boolean validPlaceForShip(int x, int y, int size, Orientation orientation){
        int xFrom = x - 1,
            yFrom = y - 1,
            xTo, yTo;

        if (orientation == Orientation.VERTICAL){
            xTo = x + size;
            yTo = y + 1;
            if (xTo - 1 > Field.maxXY) return false;

        } else {
            xTo = x + 1;
            yTo = y + size;
            if (yTo - 1 > Field.maxXY) return false;
        }

        if (xFrom < Field.minXY) xFrom = Field.minXY;
        if (yFrom < Field.minXY) yFrom = Field.minXY;
        if (yTo > Field.maxXY) yTo = Field.maxXY;
        if (xTo > Field.maxXY) xTo = Field.maxXY;

        for (int i = xFrom; i <= xTo; i++) {
            for (int j = yFrom; j <= yTo; j++) {
                if (bufferField[j][i].isAlive()) return false;
            }
        }
        return true;
    }

    private static Ship createShip(int x, int y, int size, Orientation orientation){
        int xTo, yTo;

        if (orientation == Orientation.VERTICAL){
            xTo = x + size - 1;
            yTo = y;
        } else {
            xTo = x;
            yTo = y + size - 1;
        }

        ArrayList<Cell> cells = new ArrayList<>();
        ArrayList<Cell> borders = new ArrayList<>();
        Ship ship = new Ship();
        for (int i = x; i <= xTo; i++) {
            for (int j = y; j <= yTo; j++) {
                bufferField[j][i].inititalizeState(Cell.CellState.ALIVE);
                bufferField[j][i].setShip(ship);
                cells.add(bufferField[j][i]);
            }
        }

        for (int k = x - 1; k <= xTo + 1; k++) {
            for (int l = y - 1; l <= yTo + 1; l++) {
                if (k >= Field.minXY && k <= Field.maxXY && l >= Field.minXY && l <= Field.maxXY)
                    if (bufferField[l][k].getState() != Cell.CellState.ALIVE)
                        if (!borders.contains(bufferField[l][k]))
                            borders.add(bufferField[l][k]);
            }
        }

        ship.setCells(cells);
        ship.setBorders(borders);
        return ship;
    }

    private static Orientation getNextOrientation(){

        Random rnd = new Random();
        if (rnd.nextBoolean())
            return Orientation.HORIZONTAL;
        else
            return Orientation.VERTICAL;
    }

}
