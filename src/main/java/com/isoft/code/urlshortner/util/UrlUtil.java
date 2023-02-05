package com.isoft.code.urlshortner.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UrlUtil implements ApplicationListener<WebServerInitializedEvent> {
  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final int BASE = ALPHABET.length();

  private int port;
  private String hostAddress;

  @Override
  public void onApplicationEvent(WebServerInitializedEvent event) {
    port = event.getWebServer().getPort();
    try {
      hostAddress = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
     log.error("Error retrieving host address", e);
    }
  }

  public String generateUrl() {
    String hostAddress = getHostAddress();
    String urlAlias = generateRandomString();
    return "http://" + hostAddress + "/" + urlAlias;
  }

  private String getHostAddress() {
    return hostAddress + ":" + port;
  }

  private String generateRandomString() {
    Random rand = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      sb.append(ALPHABET.charAt(rand.nextInt(BASE)));
    }
    return sb.toString();
  }
}
