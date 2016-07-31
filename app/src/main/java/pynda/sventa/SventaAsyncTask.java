package pynda.sventa;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import pynda.sventa.parsing.City;

class SventaAsyncTask extends AsyncTask<Object, String, String> {

    private TextView tw;
    volatile boolean toRun = true;
    private Context context;
    private static String tag = "Sventa---";

    SventaAsyncTask(TextView tw, Context context){
        super();
        this.tw = tw;
        this.context = context;
    }

    @Override
    protected String doInBackground(Object[] objects) {
        try {
            Log.d(tag, "doInBackground started");

            while (toRun) {
//                Thread.sleep(8000L);
//                NetworkUtils.setMobileDataEnabled(context, false);
//                Log.d(tag, "Network off");
//                Thread.sleep(3000L);
//                NetworkUtils.setMobileDataEnabled(context, true);
//                Log.d(tag, "Network on");

                Thread.sleep(3000L);
                publishProgress(NetworkUtils.getXml().toString());

                City city = NetworkUtils.getXml();
                Log.d(tag, "city polution"+city.toString());

/*
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sventa.myminicity.com/env"));
                browserIntent.setPackage("com.android.chrome");
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);
*/
                Log.d(tag, "Iteration finished");
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
        Log.d(tag, "progress updating");
        tw.setText(values[0]);
    }

}