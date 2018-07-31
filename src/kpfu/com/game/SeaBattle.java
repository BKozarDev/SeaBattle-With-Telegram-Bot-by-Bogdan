package kpfu.com.game;

import javafx.scene.Parent;
import kpfu.com.game.fieldParts.Cell;
import kpfu.com.game.gui.Canvas;
import kpfu.com.game.players.ComputerPlayer;
import kpfu.com.game.players.HumanPlayer;
import kpfu.com.game.gui.MessageManager;


import kpfu.com.game.players.Player;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.groupadministration.GetChatMemberCount;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import java.util.regex.*;


public class SeaBattle implements ActionListener, Runnable {

    private Thread lastGame;

    public static boolean reset = false;
    public static boolean starting_game = false;

    public static ComputerPlayer computerPlayer = new ComputerPlayer();

    public static HumanPlayer humanPlayer = new HumanPlayer();

    private static SeaBattle game = getInstance();

    private static Logger log = Logger.getLogger(Bot_.class.getName());

    public SeaBattle(){
        humanPlayer.setOpponent(computerPlayer);
        computerPlayer.setOpponent(humanPlayer);
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new Bot_());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }

    void playGame(){
        lastGame = new Thread(this);
        lastGame.start();
        starting_game = true;
    }

    public static SeaBattle getInstance(){
        if (game == null) game = new SeaBattle();
        return game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        reset = true;
        if (!lastGame.isAlive()){
            lastGame = new Thread(this);
            lastGame.start();
        }
    }

    @Override
    public void run() {
        Canvas.getInstance().draw();
        MessageManager.getInstance().getStartMessage();

        boolean shootComanded = false;
        while(true){
            if (reset){
                reset = false;
                computerPlayer.reset();
                humanPlayer.reset();
                Canvas.getInstance().draw();
                MessageManager.getInstance().getStartMessage();
            }
            if (computerPlayer.moves() && !shootComanded) {
                computerPlayer.shoot();
                shootComanded = true;
            } else {
                shootComanded = false;
            }
            if (computerPlayer.getShipsToKill() == 0 || humanPlayer.getShipsToKill() == 0) {
                MessageManager.getInstance().getWinMessage(humanPlayer.getShipsToKill() == 0);
                break;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public static class Bot_ extends TelegramLongPollingBot {

        private final String botToken = "550764450:AAHbrTDVC8IRJKjbty8KcvTcZ29HqWdepmk";
        private final String botName = "sb_dru_bot";

        public static long chatId;

        @Override
        public void onUpdateReceived(Update update) {
            GetChatMemberCount getChatMemberCount = new GetChatMemberCount().setChatId(update.getMessage().getChatId());
            Integer count_M = 0;

            try {
                count_M = execute(getChatMemberCount);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            if (update.hasMessage() && update.getMessage().hasText() && count_M < 3) {
                String message_text = update.getMessage().getText();
                chatId = update.getMessage().getChatId();

                if(message_text.equals("/start")){
                    if(!starting_game)
                        game.playGame();
                    else {
                        sendMsg("Game is already running");
                    }
                } else if (message_text.equals("/help")) {
                    SendMessage sendMessage = new SendMessage()
                            .setChatId(chatId)
                            .setText("If you want start - write /start, to begin a new game - write /ng or /new \nUse '/shoot *cell*' to het a shot \nUse /end to end this round");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if(message_text.equals("/ng") || message_text.equals("/new")){
                    if(!game.lastGame.isAlive()) {
                        game.playGame();
                        reset = true;
                    }

                        sendMsg(Canvas.textLabel.getText());
                } else if(game.lastGame.isAlive()){
                    if(message_text.contains("/shoot ")){
                        int length = message_text.length();

                        char[] chars = new char[2];
                        int count = 0;
                        for (int i = 7; i < length; i++){
                            chars[count] = message_text.charAt(i);
                            count++;
                        }
                        int x = -1;
                        int y = Character.getNumericValue(chars[1]) - 1;
                        if(y > 10 || y < 0) {
                            y = -1;
                        }

                        switch (chars[0]){
                            case 'a':
                                x = 0;
                                break;
                            case 'b':
                                x = 1;
                                break;
                            case 'c':
                                x = 2;
                                break;
                            case 'd':
                                x = 3;
                                break;
                            case 'e':
                                x = 4;
                                break;
                            case 'f':
                                x = 5;
                                break;
                            case 'g':
                                x = 6;
                                break;
                            case 'h':
                                x = 7;
                                break;
                            case 'i':
                                x = 8;
                                break;
                            case 'j':
                                x = 9;
                                break;
                                default:
                                    x = -1;
                                    break;
                        }
                        if(x == -1 || y == -1){
                            sendMsg("Incorrect coordinates");
                        } else
                            humanPlayer.shoot(x, y);
                    }
                    if (message_text.equals("/end")){
                        game.lastGame.stop();
                    }
                } else {
                    sendMsg("Unknown command");
                }

            }
        }

        @Override
        public String getBotUsername() {
            return botName;
        }

        @Override
        public String getBotToken() {
            return botToken;
        }

        public boolean Send(String s){
            sendMsg(s);
            return true;
        }

        public synchronized void sendMsg(String s){
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(chatId);
            sendMessage.setText(s);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
