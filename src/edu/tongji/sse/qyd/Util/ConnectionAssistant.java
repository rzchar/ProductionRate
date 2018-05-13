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
}
