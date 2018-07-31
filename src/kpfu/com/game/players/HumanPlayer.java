package kpfu.com.game.players;

import kpfu.com.game.fieldParts.Cell;
import kpfu.com.game.fieldParts.Field;
import kpfu.com.game.gui.MessageManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HumanPlayer extends Player implements ActionListener {

    public HumanPlayer() {
        super();
        myTurn = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (myTurn) {
            Cell cell = (Cell) e.getSource();
            shoot(cell.getXCo(), cell.getYCo());
        }
    }

    public void setActionListnerToCells(){
        Cell[][] cells = opponent.getField().getCells();
        for (int i = Field.minXY; i <= Field.maxXY; i++) {
            for (int j = Field.minXY; j <= Field.maxXY; j++) {
                cells[j][i].addActionListener(this);
            }
        }
    }

    @Override
    public void setOpponent(Player opponent) {
        super.setOpponent(opponent);
        setActionListnerToCells();
    }

    @Override
    void afterShootingHandling(int x, int y) {
        if (shootResult == ShootResult.KILLED) shipsToKill--;
        MessageManager.getInstance().getMessage(true, shootResult);
    }

    @Override
    public void reset() {
        super.reset();
        myTurn = true;
        shipsToKill = 10;
    }

    public void shoot(int x, int y) {
        shootResult = opponent.getShot(x, y);
        if (shootResult == ShootResult.MISSED){
            changeTurn();
        }
        afterShootingHandling(0, 0);
    }
}
