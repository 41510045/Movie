package cn.edu.swufe.music;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MovieDetail extends AppCompatActivity implements Runnable {

    private Bitmap img;
    private String html;
    private String text;
    private String score;
    private List<HashMap<String, String>> comments = new ArrayList<HashMap<String, String>>();
    private String content;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Intent intent = getIntent();
        byte[] img_byte = intent.getByteArrayExtra("img");
        img = BitmapFactory.decodeByteArray(img_byte, 0, img_byte.length);
        html = intent.getStringExtra("html");
        Thread t = new Thread(this);
        t.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TextView detail = findViewById(R.id.detail);
        TextView score1 = findViewById(R.id.score);
        TextView introduction = findViewById(R.id.introduction);
        ImageView imageView = findViewById(R.id.imageView2);
        UnScrollListView listView = findViewById(R.id.list_view2);
        imageView.setImageBitmap(img);
        detail.setText(text);
        introduction.setText(content);
        score1.setText(score);
        MyAdapter myAdapter = new MyAdapter(MovieDetail.this, comments);
        listView.setAdapter(myAdapter);
        listView.setFocusable(false);
    }

    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(html).get();
            Element info = doc.getElementById("info");
            Elements indents = doc.getElementsByClass("indent");
            Element indent = indents.get(1);
            Elements rating_self = doc.getElementsByClass("rating_self clearfix");

            score = rating_self.text().split(" ")[0];
            Log.i("movie", "score = " + score);

            content = indent.text();
            Elements comments = doc.getElementsByClass("comment");
            for (Element comment : comments) {
                //Log.i("movie","comment = "+comment.text());
                String[] spilt = comment.text().split(" ");
                for (int i = 2; i < spilt.length; i++) {
                    HashMap map = new HashMap<String, String>();
                    switch (i) {
                        case 2:
                            map.put("userName", spilt[i]);
                            break;
                        case 3:
                            map.put("KG", spilt[i]);
                            break;
                        case 4:
                            map.put("date", spilt[i]);
                            break;
                        case 5:
                            map.put("content", "      " + spilt[i]);
                            break;

                        default:
                            String string = (String) map.get("content");
                            Log.i("movie", "comment" + i + " = " + string + spilt[i]);
                            map.put("content", string + spilt[i]);
                            break;

                    }
                    this.comments.add(map);
                }

            }
            //Log.i("movie",""+indent.text());
            //Log.i("movie","info = "+info.text());
            String[] split = info.text().split(" ");
            List<String> list = new ArrayList<String>();
            for (String s : split) {
                if (s.contains(":")) {
                    list.add("\n");
                    list.add(s);
                } else {
                    list.add(s);
                }
                //Log.i("movie"," "+s);
            }
            String[] newStr = list.toArray(new String[1]);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < newStr.length; i++) {
                stringBuffer.append(newStr[i]);
            }
            text = stringBuffer.toString();
            //Log.i("movie"," "+text);

        } catch (IOException e) {
            e.printStackTrace();
        }


        countDownLatch.countDown();
    }

}