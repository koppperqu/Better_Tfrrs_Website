package com.BetterTfrrsWebsite;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class UrlGenerator {

    private String baseUrl = "";
    //private final String baseUrl = "http://localhost:8080/";

    public String generateUrl(String name) throws UnsupportedEncodingException {
        return baseUrl + urlEncode(name);
    }

    public String generateUrlNewParent(String name, String parent) throws UnsupportedEncodingException {
        return baseUrl + "../" + parent + "/" + urlEncode(name);
    }

    private String urlEncode(String data) throws UnsupportedEncodingException {
        return URLEncoder.encode(data, StandardCharsets.UTF_8);
    }
}