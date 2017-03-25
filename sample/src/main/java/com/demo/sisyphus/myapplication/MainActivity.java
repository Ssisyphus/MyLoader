package com.demo.sisyphus.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.sisyphus.myloader.MyLoader;

public class MainActivity extends AppCompatActivity {

    private MyLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loader = (MyLoader) findViewById(R.id.my_loader);
        loader.startAnimation();

    }
}
