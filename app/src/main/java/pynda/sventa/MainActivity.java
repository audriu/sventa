package pynda.sventa;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private AsyncTask at;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        at = new SventaAsyncTask();
        at.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        at.cancel(true);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        at = new SventaAsyncTask();
        at.execute();
    }

    private class SventaAsyncTask extends AsyncTask<Void, String, String> {

        TextView tw = (TextView)findViewById(R.id.text1);
        volatile boolean toRun = true;

        @Override
        protected String doInBackground(Void[] objects) {
            try {
                Log.d("sventa", "------------");

                while (toRun) {
                    setMobileDataEnabled(context, false);
                    Log.d("sventa", "-----off");
                    Thread.sleep(10000L);
                    setMobileDataEnabled(context, true);
                    Log.d("sventa", "-----on");
                    Thread.sleep(10000L);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sventa.myminicity.com"));
                    startActivity(browserIntent);

                    Log.d("sventa", "iter--------");

                    this.publishProgress("PrograssSring");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onCancelled(String o) {
            toRun = false;
            super.onCancelled(o);
        }

        @Override
        protected void onProgressUpdate(String[] values) {
            super.onProgressUpdate(values);
            tw.setText("Hi");
        }

        private void setMobileDataEnabled(Context context, boolean enabled) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}