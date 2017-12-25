package com.imac.androidcomponentsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentMap fragment = new FragmentMap();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, FragmentMap.class.getName()).commit();
        }

    }
}
