package org.crypto_parser.bot;

import org.crypto_parser.parser.CryptoParser;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class CryptoBot implements LongPollingSingleThreadUpdateConsumer {
    private TelegramClient telegramClient = new OkHttpTelegramClient(System.getenv("CRYPTO_BOT_TOKEN"));

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();
            String coinName = "";
            String message = "";

            switch (userMessage) {
                case "Kaspa":
                case "/kas":
                    coinName = "Kaspa";
                    break;
                case "Sedra":
                case "/sdr":
                    coinName = "Sedra";
                    break;
                case "Bugna":
                case "/bga":
                    coinName = "Bugna";
                    break;
            }

            if (coinName.isEmpty())
                message = "Здравствуйте, выберите любую криптовалюту из предложенных и я выведу Вам ее актуальный курс";
            else {
                CryptoParser parser = new CryptoParser(coinName);
                message = parser.getPrice();
            }

            sendMessage(message, chatID);
        }
    }

    private void sendMessage(String msg, long chatID) {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatID)
                .text(msg)
                .build();
        sendMessage.setReplyMarkup(ReplyKeyboardMarkup
                .builder()
                .keyboardRow(new KeyboardRow("Kaspa", "Sedra", "Bugna"))
                .resizeKeyboard(true)
                .build());

        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
}
