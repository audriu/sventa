package pynda.sventa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import pynda.sventa.parsing.City;

class SventaAsyncTask extends AsyncTask<Object, String, String> {

    private TextView tw;
    private volatile boolean toRun = true;
    private Context context;
    private static String tag = "Sventa-AT";

    SventaAsyncTask(TextView tw, Context context){
        super();
        this.tw = tw;
        this.context = context;
    }

    @Override
    protected String doInBackground(Object[] objects) {
            Log.d(tag, "doInBackground started");

            while (true) {
                NetworkUtils.setMobileDataEnabled(context, false);
                Log.d(tag, "Network off");
                NetworkUtils.setMobileDataEnabled(context, true);
                Log.d(tag, "Network on");

                publishProgress(NetworkUtils.getCity().toString());

                City city = NetworkUtils.getCity();
                Log.d(tag, "city "+city.toString());

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sventa.myminicity.com/"));
                browserIntent.setPackage("com.android.chrome");
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);

                try {Thread.sleep(10000L);} catch (InterruptedException e) {}
            }
    }

    @Override
    protected void onCancelled(String o) {
        toRun = false;
        super.onCancelled(o);
    }

    @Override
    protected void onProgressUpdate(String[] values) {
        super.onProgressUpdate(values);
        Log.d(tag, "progress updating");
        tw.setText(values[0]);
    }

}