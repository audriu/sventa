package pynda.sventa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import pynda.sventa.parsing.City;

class SventaAsyncTask extends AsyncTask<Object, String, String> {

    private TextView tw;
    private Context context;
    private NetworkUtils networkUtils;
    private static final String SVENTA = "http://sventa.myminicity.com/";

    SventaAsyncTask(TextView tw, Context context) {
        super();
        this.tw = tw;
        this.context = context;
        networkUtils = new NetworkUtils(context);
    }

    @Override
    protected String doInBackground(Object[] objects) {

            while (true) {
                networkUtils.setMobileDataEnabled(context, true);
                 try {Thread.sleep(1000L);} catch (InterruptedException e) {}

                City city = networkUtils.getCity();
                String url = getUrlPatternFromCity(city);
                publishProgress(city.toString() + "\n" + url);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SVENTA + url));
                browserIntent.setPackage("com.android.chrome");
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);

                try {Thread.sleep(3000L);} catch (InterruptedException e) {}
                networkUtils.setMobileDataEnabled(context, false);
            }
    }

    @Override
    protected void onProgressUpdate(String[] values) {
        super.onProgressUpdate(values);
        tw.setText(values[0]);
        Toast.makeText(context, values[0], Toast.LENGTH_LONG).show();
    }

    private String getUrlPatternFromCity(City city) {
        //todo return the biggest.
        String result = "";
        if (city.getPollution() > 1 )
            result = "env";
        else if (city.getUnemployment() > 1)
            result = "ind";
        else if (city.getTransport() > 1)
            result = "tra";
        else if (city.getCriminality() > 1)
            result = "sec";
        return result;
    }

}