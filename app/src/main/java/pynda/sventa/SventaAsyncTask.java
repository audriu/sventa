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
    private static String TAG = "Sventa-AT";
    private static final String SVENTA = "http://sventa.myminicity.com/";

    SventaAsyncTask(TextView tw, Context context) {
        super();
        this.tw = tw;
        this.context = context;
    }

    @Override
    protected String doInBackground(Object[] objects) {

            while (true) {
                NetworkUtils.setMobileDataEnabled(context, false);
                NetworkUtils.setMobileDataEnabled(context, true);

                City city = NetworkUtils.getCity();
                publishProgress(city.toString());

                String url = getUrlPatternFromCity(city);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SVENTA + url));
                browserIntent.setPackage("com.android.chrome");
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);

                Log.d(TAG, "city " + city.toString() + "  param:  " + url);
                try {Thread.sleep(7000L);} catch (InterruptedException e) {}
            }
    }

    @Override
    protected void onProgressUpdate(String[] values) {
        super.onProgressUpdate(values);
        tw.setText(values[0]);
    }

    private String getUrlPatternFromCity(City city) {
        //todo return the biggest.
        String result = "";
        if (city.getPollution() > 0 )
            result = "env";
        else if (city.getUnemployment() > 0)
            result = "ind";
        else if (city.getTransport() > 0)
            result = "tra";
        else if (city.getCriminality() > 0)
            result = "sec";
        return result;
    }

}