package kpfu.com.game.gui;

import kpfu.com.game.SeaBattle;
import kpfu.com.game.fieldParts.Cell;
import kpfu.com.game.fieldParts.Field;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JFrame {


    JPanel player1Field;
    JPanel player2Field;
    JPanel window;

    public static JLabel textLabel;

    private final ImageIcon alive = new ImageIcon(getClass().getResource("res/alive.png"));
    private final ImageIcon killed = new ImageIcon(getClass().getResource("res/killed.png"));
    private final ImageIcon injured = new ImageIcon(getClass().getResource("res/injured.png"));
    private final ImageIcon missed = new ImageIcon(getClass().getResource("res/missed.png"));
    private final ImageIcon nothing = new ImageIcon(getClass().getResource("res/null.png"));

    private static Canvas instance;

    private Canvas() throws HeadlessException {
        super("SeaBattle with telegram bot by CptDrusha");
        setSize(800, 600);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window = new JPanel();



        initializeFields();

        textLabel = new JLabel("My Turn");
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(SeaBattle.getInstance());

        window.setLayout(new GridLayout(2, 2));

        window.add(textLabel);
        window.add(newGameButton);

        add(window);
        setVisible(true);


    }

    public void initializeFields(){
        player1Field = new JPanel();
        player2Field = new JPanel();

        initializeField(player1Field, SeaBattle.computerPlayer.getField());
        initializeField(player2Field, SeaBattle.humanPlayer.getField());

        window.add(player1Field);
        window.add(player2Field);
    }

    private void initializeField(JPanel jPanel, Field field){
        if (field == null) return;
        jPanel.setLayout(new GridLayout(10, 10));
        jPanel.setMaximumSize(new Dimension(400, 400));

        Cell[][] cells = field.getCells();
        for (int i = Field.minXY; i <= Field.maxXY; i++) {
            for (int j = Field.minXY; j <= Field.maxXY; j++) {
                cells[j][i].setIcon(nothing);
                jPanel.add(cells[j][i]);

            }
        }
    }

    public void draw(){

        Field humanField = SeaBattle.humanPlayer.getField();
        Field computerField = SeaBattle.computerPlayer.getField();

        updateField(humanField, false);
        updateField(computerField, true);
    }

    private void updateField(Field field, boolean computerField){
        if (field == null) return;
        Cell[][] cells = field.getCells();
        for (int i = Field.minXY; i <= Field.maxXY; i++) {
            for (int j = Field.minXY; j <= Field.maxXY ; j++) {
                Cell cell = cells[j][i];

                if (computerField){
                    updateComputerCell(cell);
                } else {
                    updateHumanCell(cell);
                }

            }
        }
    }

    private void updateComputerCell(Cell cell){
        Cell.CellState state = cell.getState();

        if (state != null)
            switch (state){
                case NULL:
                    cell.setIcon(nothing);
                    break;
                case INJURED:
                    cell.setIcon(injured);
                    break;
                case KILLED:
                    cell.setIcon(killed);
                    break;
                case MISSED:
                    cell.setIcon(missed);
                    break;
            }

    }

    private void updateHumanCell(Cell cell){
        Cell.CellState state = cell.getState();

        if (state != null)
            switch (state){
                case NULL:
                    cell.setIcon(nothing);
                    break;
                case MISSED:
                    cell.setIcon(missed);
                    break;
                case ALIVE:
                    cell.setIcon(alive);
                    break;
                case INJURED:
                    cell.setIcon(injured);
                    break;
                case KILLED:
                    cell.setIcon(killed);
                    break;
            }
    }

    public static Canvas getInstance(){
        if (instance == null)
            instance = new Canvas();
        return instance;
    }
}
