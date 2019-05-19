package com.mer.opengl.openglex;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.mer.opengl.R;

public class MainActivity extends Activity {

    GLSurfaceView glview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        glview = findViewById(R.id.glview);
        MyGLRenderer myGLRenderer = new MyGLRenderer(this);
        glview.setRenderer(myGLRenderer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glview.onResume();
    }

    @Override
    protected void onPause() {
        glview.onPause();
        super.onPause();
    }

}