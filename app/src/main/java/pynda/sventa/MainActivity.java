package pynda.sventa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AsyncTask at;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        at = new SventaAsyncTask((TextView)findViewById(R.id.text1), this.getApplicationContext());
        at.execute();
    }
}