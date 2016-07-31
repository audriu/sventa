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
    private Context context;
    private static String tag = "Sventa-AT";
    private static final String SVENTA = "http://sventa.myminicity.com/";

    SventaAsyncTask(TextView tw, Context context){
        super();
        this.tw = tw;
        this.context = context;
    }

    @Override
    protected String doInBackground(Object[] objects) {

            while (true) {
                NetworkUtils.setMobileDataEnabled(context, false);
                Log.d(tag, "Network off");
                NetworkUtils.setMobileDataEnabled(context, true);
                Log.d(tag, "Network on");

                publishProgress(NetworkUtils.getCity().toString());

                City city = NetworkUtils.getCity();
                Log.d(tag, "city "+city.toString());

                String url;
                if(city.getPollution() > 0)
                    url = SVENTA + "env";
                else if(city.getUnemployment() > 0)
                    url = SVENTA + "com";
                else if(city.getTransport() > 0)
                    url = SVENTA + "tra";
                else if(city.getCriminality() > 0)
                    url = SVENTA + "sec";
                else
                    url = SVENTA;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                browserIntent.setPackage("com.android.chrome");
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);

                try {Thread.sleep(15000L);} catch (InterruptedException e) {}
            }
    }

    @Override
    protected void onProgressUpdate(String[] values) {
        super.onProgressUpdate(values);
        tw.setText(values[0]);
    }

}