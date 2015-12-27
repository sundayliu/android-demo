package com.sundayliu.graphic.bezier;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
//import android.support.v7.app.ActionBarActivity;
//import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

/**
 * Created by gavin on 15-4-2.
 */
public class BezierEvaluatorActivity extends Activity {
    
    public void startAnimation(){
        ValueAnimator animator = ValueAnimator.ofInt(0,20,0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                // TODO Auto-generated method stub
                int value = (Integer)(arg0.getAnimatedValue());
                Log.e("TAG", "the value is " + value);
                
            }
        });
        animator.setDuration(1000); // time
        animator.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_evaluator);

        final TextView textView = (TextView) findViewById(R.id.textView);

        PointF p0 = new PointF(100, 100);
        PointF p1 = new PointF(400, 100);
        PointF p2 = new PointF(400, 400);
        PointF p3 = new PointF(100, 400);

        BezierEvaluator evaluator = new BezierEvaluator(p0, p1, p2, p3);

        ValueAnimator animator = ValueAnimator.ofObject(evaluator, p0, p3)
                .setDuration(4500);
        animator.setRepeatCount(100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                textView.setX(pointF.x);
                textView.setY(pointF.y);
            }

        });

        animator.start();

    }

}
