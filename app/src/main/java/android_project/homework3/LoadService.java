package android_project.homework3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by egorsafronov on 00.11.2016
 */

public class LoadService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final String BASE_URI = "http://files.geometria.ru/pics/original/033/670/33670035.jpg";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final File photo = new File(getFilesDir(), "duck.jpg");
        if (!photo.exists()) {
            new Thread(new Runnable() {
                        public void run() {
                            InputStream in = null;
                            FileOutputStream out = null;
                            try {
                                in = new BufferedInputStream((new URL(BASE_URI)).openStream());
                                out = new FileOutputStream(photo);
                                byte[] buffer = new byte[1024];
                                int bufferLength = 0;
                                while ((bufferLength = in.read(buffer)) > 0) {
                                    out.write(buffer, 0, bufferLength);
                                }
                                in.close();
                                out.close();
                                sendBroadcast(new Intent(MainActivity.LOADED_INTENT));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).start();
        }
        return START_STICKY;
    }
}
