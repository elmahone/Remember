package com.example.remember.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.remember.R;
import com.example.remember.ZoomLayout;

public class ShowImageActivity extends AppCompatActivity {
    private ZoomLayout zoomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        zoomLayout = (ZoomLayout) findViewById(R.id.activity_show_image);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Uri imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
        imageView.setImageURI(imageUri);
        zoomLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                zoomLayout.init(ShowImageActivity.this);
                return false;
            }
        });

    }
}
