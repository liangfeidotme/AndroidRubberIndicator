package com.liangfeizc.rubberindicator;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.liangfeizc.CircleView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private CircleView mPreviousView;
    private CircleView mIndicatorView;
    private CircleView mOuterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreviousView = (CircleView) findViewById(R.id.previous_circle);
        mIndicatorView = (CircleView) findViewById(R.id.indicator_circle);
        mOuterView = (CircleView) findViewById(R.id.outer_circle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_move: {
                moveCircle();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveCircle() {
    }
}
