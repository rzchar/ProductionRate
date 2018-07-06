package edu.tongji.sse.qyd.Util;

import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;

/**
 * Created by qyd on 2018/4/30.
 */
public class ConnectionAssistant {
    static public void addAuthority(HttpsURLConnection conn) {
        conn.setRequestProperty("Authorization", "Basic cnpjaGFyOmNoYXJsZXM4NjIyMzc2NA==");
    }

    static public void addAuthority(HttpURLConnection conn) {
        conn.setRequestProperty("Authorization", "Basic cnpjaGFyOmNoYXJsZXM4NjIyMzc2NA==");
    }

    public static void spiderLimitCheck(HttpURLConnection conn) {
        //System.out.println("response code: " + connection.getResponseCode());
        try {
            int remainEntityNum = conn.getHeaderFieldInt("x-ratelimit-remaining", 5000);
            Util.log(ConnectionAssistant.class, "x-ratelimit-remaining: " + remainEntityNum);
            if (remainEntityNum < 800 && remainEntityNum >= 400) {
                Thread.sleep(500L);
            }
            if (remainEntityNum < 400 && remainEntityNum >= 200) {
                Thread.sleep(1000L);
            }
            if (remainEntityNum < 200 && remainEntityNum >= 100) {
                Thread.sleep(2000L);
            }
            if (remainEntityNum < 100 && remainEntityNum >= 10) {
                Thread.sleep(10L * 1000L);
            }
            if (remainEntityNum < 10) {
                Thread.sleep(400L * 1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
