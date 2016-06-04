package com.cleartax.cleartaxtweets.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.cleartax.cleartaxtweets.R;
import com.cleartax.cleartaxtweets.ui.fragment.MainActivityFragment;

public class MainActivity extends AppCompatActivity {

    private static ProgressBar progressBar;
    private static MainActivityFragment mainActivityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainActivityFragment = new MainActivityFragment();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProgressBar(){
//        if(progressBar != null){
        Log.e("progress", "show is called");
            progressBar.setVisibility(View.VISIBLE);
//        }
    }

    public void hideProgressBar(){
        Log.e("progres", "hide is called");
        if(progressBar != null)
            progressBar.setVisibility(View.INVISIBLE);
    }
}
