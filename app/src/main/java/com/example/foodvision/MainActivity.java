package com.example.foodvision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    int cameraRequestCode = 001;

    Classifier classifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);

        classifier = new Classifier(Utils.assetFilePath(this,"fc101_ts_midres.pt"));

        Button capture = findViewById(R.id.capture);

        capture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,cameraRequestCode);
            }
        });
        dispatchTakePicureIntent();
    }

    protected void dispatchTakePicureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            String pred = getIntent().getStringExtra("pred");

            ImageView imageView = findViewById(R.id.image);
            imageView.setImageBitmap(imageBitmap);

            TextView textView = findViewById(R.id.label);
            textView.setText(pred);
        }
    }
}
