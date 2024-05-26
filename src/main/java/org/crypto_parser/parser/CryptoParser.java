package org.crypto_parser.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class CryptoParser {
    private String coinName;
    private String errorMessage = "Не удалось поучить стоимость валюты, попробуйте еще раз";

    public CryptoParser(String coinName) {
        this.coinName = coinName;
    }

    public String getPrice() {
        String url = getURLbyCoinName();
        if (url.isEmpty()) return errorMessage;

        try {
            Document document = Jsoup.connect(url).get();
            Element element = document.select("app-asset-price").first();
            if (element != null) return element.text();
            else return errorMessage;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return errorMessage;
    }

    private String getURLbyCoinName() {
        switch (coinName) {
            case "Kaspa":
                return "https://coincodex.com/crypto/kaspa/";
            case "Sedra":
                return "https://coincodex.com/crypto/sedracoin/";
            case "Bugna":
                return "https://coincodex.com/crypto/bugna/";
            default:
                return "";
        }
    }
}
