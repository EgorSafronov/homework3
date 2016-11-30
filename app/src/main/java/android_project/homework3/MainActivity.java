package android_project.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    BroadcastReceiver loadReceiver;
    BroadcastReceiver receiver;


    public static final String LOADED_INTENT = "DOWNLOADED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File photo = new File(getFilesDir(), "duck.jpg");
        textView = (TextView)findViewById(R.id.text_view);
        imageView = (ImageView)findViewById(R.id.image_view);
        if (photo.exists()) {
           showImage();
        } else {
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        }
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                File photo = new File(getFilesDir(), "duck.jpg");
                if (photo.exists()) {
                    showImage();
                }
                startService(new Intent(MainActivity.this, LoadService.class));
            }
        };
        loadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                File photo = new File(getFilesDir(), "duck.jpg");
                if (photo.exists()) {
                    showImage();
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED));
        registerReceiver(loadReceiver, new IntentFilter(LOADED_INTENT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(loadReceiver);
    }
    void showImage() {
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageBitmap(BitmapFactory.decodeFile(getFilesDir() + "/duck.jpg"));
        textView.setVisibility(View.INVISIBLE);
    }
}
