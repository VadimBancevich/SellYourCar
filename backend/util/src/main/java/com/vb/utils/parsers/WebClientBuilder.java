package com.vb.utils.parsers;

import com.gargoylesoftware.htmlunit.WebClient;

public class WebClientBuilder {

    public static WebClient getWebClient() {
        WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        return webClient;
    }

    private WebClientBuilder() {
    }
}
