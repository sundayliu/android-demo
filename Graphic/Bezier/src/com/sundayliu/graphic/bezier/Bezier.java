package com.sundayliu.graphic.bezier;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class Bezier extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onBezierView(View view) {
        Intent intent = new Intent(this, BezierViewActivity.class);
        startActivity(intent);
    }

    public void onDropPagerIndicator(View view) {
        Intent intent = new Intent(this, DropPagerIndicatorActivity.class);
        startActivity(intent);
    }

    public void onBezierEvaluator(View view) {
        Intent intent = new Intent(this, BezierEvaluatorActivity.class);
        startActivity(intent);
    }
    
    public void onClipPath(View view) {
        Intent intent = new Intent(this, ClipingActivity.class);
        startActivity(intent);
    }
}
