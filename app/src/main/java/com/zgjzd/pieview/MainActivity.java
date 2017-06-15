package com.zgjzd.pieview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LineView mPie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPie = (LineView) findViewById(R.id.line);
        List data = new ArrayList();
        data.add(10f);
        data.add(1f);
        data.add(5f);
        data.add(20f);

        List desc = new ArrayList();
        desc.add("1");
        desc.add("2");
        desc.add("3");
        desc.add("4");
        mPie.setData(data,desc);
    }


}
