package org.crypto_parser.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CryptoParser {
    private String coinName;
    private Map<String, String> urls = new HashMap<String, String>();
    private String errorMessage = "Не удалось поучить стоимость валюты, попробуйте еще раз";

    public CryptoParser(String coinName) {
        this.coinName = coinName;
        urls.put("Kaspa", "https://coincodex.com/crypto/kaspa/");
        urls.put("Sedra", "https://coincodex.com/crypto/sedracoin/");
        urls.put("Bugna", "https://coincodex.com/crypto/bugna/");
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
        return urls.get(coinName);
    }
}
