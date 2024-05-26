package org.crypto_parser;

import org.crypto_parser.bot.CryptoBot;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        String botToken = System.getenv("CRYPTO_BOT_TOKEN");
        try {
            TelegramBotsLongPollingApplication cryptoBot = new TelegramBotsLongPollingApplication();
            cryptoBot.registerBot(botToken, new CryptoBot());
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
}