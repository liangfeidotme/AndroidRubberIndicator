package com.liangfeizc.rubberindicator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.liangfeizc.RubberIndicator;

public class MainActivity extends AppCompatActivity implements RubberIndicator.OnMoveListener {
    private RubberIndicator mRubberIndicator;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mMotionView = new CirclePathMotionView(this);
        //setContentView(mMotionView);

        setContentView(R.layout.activity_main);
        mRubberIndicator = (RubberIndicator) findViewById(R.id.rubber);
        mTextView = (TextView) findViewById(R.id.focus_position);
        mRubberIndicator.setCount(5, 2);
        mRubberIndicator.setOnMoveListener(this);
        updateFocusPosition();
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

    public void moveToRight(View view) {
        mRubberIndicator.moveToRight();
    }

    public void moveToLeft(View view) {
        mRubberIndicator.moveToLeft();
    }

    @Override
    public void onMovedToLeft() {
        updateFocusPosition();
    }

    @Override
    public void onMovedToRight() {
        updateFocusPosition();
    }

    private void updateFocusPosition() {
        mTextView.setText("Focus Pos: " + mRubberIndicator.getFocusPosition());
    }
}
