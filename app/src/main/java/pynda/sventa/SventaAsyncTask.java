package pynda.sventa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by audrius on 31/07/16.
 */
class SventaAsyncTask extends AsyncTask<Object, String, String> {

    private TextView tw;
    volatile boolean toRun = true;
    private Context context;

    SventaAsyncTask(TextView tw, Context context){
        super();
        this.tw = tw;
        this.context = context;
    }

    @Override
    protected String doInBackground(Object[] objects) {
        try {
            Log.d("sventa", "------------");

            while (toRun) {
                NetworkUtils.setMobileDataEnabled(context, false);
                Log.d("sventa", "-----off");
                Thread.sleep(10000L);
                NetworkUtils.setMobileDataEnabled(context, true);
                Log.d("sventa", "-----on");
                Thread.sleep(10000L);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sventa.myminicity.com"));
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);

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

}