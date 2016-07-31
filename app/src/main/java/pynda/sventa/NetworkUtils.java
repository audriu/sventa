package pynda.sventa;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;

import pynda.sventa.parsing.City;

class NetworkUtils {

    private static String tag = "Sventa-NU";
    private static String XmlUrl = "http://sventa.myminicity.com/xml";

    static City getCity             () {
        City city = new City();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(XmlUrl).openStream()));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("unemployment")) {
                    city.setUnemployment(getIntValue(line));
                }
                if (line.contains("transport")) {
                    city.setTransport(100 - getIntValue(line));
                }
                if (line.contains("criminality")) {
                    city.setCriminality(getIntValue(line));
                }
                if (line.contains("pollution")) {
                    city.setPollution(getIntValue(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }

    private static Integer getIntValue(String arg) {
        String s = arg;
        s = s.substring(s.indexOf(">") + 1);
        s = s.substring(0, s.indexOf("</"));
        return Integer.parseInt(s);
    }

    static void setMobileDataEnabled(Context context, boolean enabled) {
        try {
            final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final Class conmanClass = Class.forName(conman.getClass().getName());

            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);

            setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
            while (conman.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting() != enabled) {
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
