package com.sundayliu.unittestandlog;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.util.Log;

public class MyTest extends AndroidTestCase{
    private static final String TAG = "MyTest";
    public void testSave() throws Throwable{
        int i = 4 + 8;
        Log.d(TAG, "i = " + i);
        Assert.assertEquals(12,i);
    }
}