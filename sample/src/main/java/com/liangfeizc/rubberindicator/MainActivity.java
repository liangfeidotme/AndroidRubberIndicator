package com.liangfeizc.rubberindicator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.liangfeizc.RubberIndicator;

public class MainActivity extends AppCompatActivity {
    private RubberIndicator mRubberIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mMotionView = new CirclePathMotionView(this);
        //setContentView(mMotionView);

        setContentView(R.layout.activity_main);
        mRubberIndicator = (RubberIndicator) findViewById(R.id.rubber);
        mRubberIndicator.setCount(5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void moveIndicator(View view) {
        mRubberIndicator.move();
    }
}
