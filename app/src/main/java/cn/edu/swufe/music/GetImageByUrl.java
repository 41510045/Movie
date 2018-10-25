package cn.edu.swufe.music;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class GetImageByUrl implements Runnable{

    private String url;
    private Bitmap img;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public Bitmap getUrlImage(String url) {
        this.url = url;
        img = null;
        Thread t = new Thread(this);
        t.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("movie","img"+img);

        return img;
    }


    @Override
    public void run() {
        try {
            URL picurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) picurl.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();
            InputStream is = conn.getInputStream();
            img = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
    }
}