package kpfu.com.game.gui;

import kpfu.com.game.SeaBattle;
import kpfu.com.game.players.Player;

import java.util.ArrayList;
import java.util.Random;

public class MessageManager {

    private static MessageManager instance;

    private ArrayList<String> onHumanMiss;

    private ArrayList<String> onHumanInjure;

    private ArrayList<String> onHumanKill;


    private ArrayList<String> onPCMiss;

    private ArrayList<String> onPCInjure;

    private ArrayList<String> onPCKill;

    private ArrayList<String> onPCWin;

    private ArrayList<String> onHumanWin;


    public SeaBattle.Bot_ bot_;
    public static Player.ShootResult state;


    public static MessageManager getInstance(){
        if (instance == null)
            instance = new MessageManager();
        return instance;
    }

    public void getMessage(boolean human, Player.ShootResult shootResult){
        state = shootResult;
        bot_ = new SeaBattle.Bot_();
        if (shootResult == null) return;
        int index;
        Random random = new Random();
        if (human){
            switch (shootResult){
                case MISSED:
                    index = random.nextInt(onHumanMiss.size());
                    Canvas.textLabel.setText(onHumanMiss.get(index));
                    bot_.sendMsg(Canvas.textLabel.getText());
                    break;
                case INJURED:
                    index = random.nextInt(onHumanInjure.size());
                    Canvas.textLabel.setText(onHumanInjure.get(index));
                    bot_.sendMsg(Canvas.textLabel.getText());
                    break;
                case KILLED:
                    index = random.nextInt(onHumanKill.size());
                    Canvas.textLabel.setText(onHumanKill.get(index));
                    bot_.sendMsg(Canvas.textLabel.getText());
                    break;
            }
        } else {
            switch (shootResult){
                case MISSED:
                    index = random.nextInt(onPCMiss.size());
                    Canvas.textLabel.setText(onPCMiss.get(index));
                    bot_.sendMsg(Canvas.textLabel.getText());
                    break;
                case INJURED:
                    index = random.nextInt(onPCInjure.size());
                    Canvas.textLabel.setText(onPCInjure.get(index));
                    bot_.sendMsg(Canvas.textLabel.getText());
                    break;
                case KILLED:
                    index = random.nextInt(onPCKill.size());
                    Canvas.textLabel.setText(onPCKill.get(index));
                    bot_.sendMsg(Canvas.textLabel.getText());
                    break;
            }
        }
    }

    public void getWinMessage(boolean humanWin){
        Random random = new Random();
        int index;
        if (humanWin){
            index = random.nextInt(onHumanWin.size());
            Canvas.textLabel.setText(onHumanWin.get(index));
        } else {
            index = random.nextInt(onPCWin.size());
            Canvas.textLabel.setText(onPCWin.get(index));
        }
    }

    public void getStartMessage(){
        Canvas.textLabel.setText("New Game! Let's begin! Your turn!");
    }

    private MessageManager(){
        initOnHumanInjure();
        initOnHumanKill();
        initOnHumanMiss();
        initOnHumanWin();
        initOnPCInjure();
        initOnPCKill();
        initOnPCMiss();
        initOnPCWin();
    }

    private void initOnHumanMiss(){
        onHumanMiss = new ArrayList<>();
        onHumanMiss.add("MISS");
        onHumanMiss.add("Oh NO... MISS");
        onHumanMiss.add("Nope :P");
    }

    private void initOnHumanInjure(){
        onHumanInjure = new ArrayList<>();
        onHumanInjure.add("Ouch! Hit!");
        onHumanInjure.add("HIT!");
    }

    private void initOnHumanKill(){
        onHumanKill = new ArrayList<>();
        onHumanKill.add("Kill");
        onHumanKill.add("Oh no MY SHIP");
    }

    private void initOnPCMiss(){
        onPCMiss = new ArrayList<>();
        onPCMiss.add("I MISS!");
    }

    private void initOnPCInjure(){
        onPCInjure = new ArrayList<>();
        onPCInjure.add("I HIT U!");
        onPCInjure.add("Haha! Hit U!");
    }

    private void initOnPCKill(){
        onPCKill = new ArrayList<>();
        onPCKill.add("I Killed your ship!");
    }

    private void initOnPCWin(){
        onPCWin = new ArrayList<>();
        onPCWin.add("I WIN!");
        onPCWin.add("YES! I WIN!");
    }

    private void initOnHumanWin(){
        onHumanWin = new ArrayList<>();
        onHumanWin.add("Okey... You win!");
        onHumanWin.add("You Win!");
    }







}
