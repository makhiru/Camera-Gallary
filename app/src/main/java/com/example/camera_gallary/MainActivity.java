package com.example.camera_gallary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorPrivacyManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ImageView image;
    Button btncamera, btngallary;
    VideoView video;
    TextView textView;

    public static final int CAMERA_CODE = 100;
    public static final int GALLARY_CODE = 200;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.image);
        btncamera = findViewById(R.id.btncamera);
        btngallary = findViewById(R.id.btngallary);
        video = findViewById(R.id.video);
        textView = findViewById(R.id.textview);

        //vidro online :

        // String path = "android.resource://" + getPackageName() + "/raw/video";
        String onlinepath = "https://mobstatus.com/wp-content/uploads/2022/10/dhokha-dhadi-full-screen-status-_-new-romantic-status-_-love-song-4k-status-_-new-_status-_viral720P_HD.mp4";

        //vidro offline :

        // Uri viewuri = Uri.parse(onlinepath);
        Uri onlineviewuri = Uri.parse(onlinepath);

        video.setVideoURI(onlineviewuri);
        video.start();

        MediaController mediaController = new MediaController(this);
        video.setMediaController(mediaController);
        mediaController.setAnchorView(video);

        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(icamera, CAMERA_CODE);
            }
        });
        btngallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent igallary = new Intent(Intent.ACTION_PICK);
                igallary.setType("image/*");
                startActivityForResult(igallary, GALLARY_CODE);
            }
        });

        //sensor phone :
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager != null) {
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (sensor != null) {
                sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_NORMAL);
            }
        } else {
            Toast.makeText(this, "Sensor Service not Detactive!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CODE) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(bitmap);
            }

            if (requestCode == GALLARY_CODE) {
                image.setImageURI(data.getData());
            }
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            textView.setText("x: " + event.values[0] + "\ny: " + event.values[1] + "\nz: " + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}