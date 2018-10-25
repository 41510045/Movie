package cn.edu.swufe.music;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener {

    private List<HashMap<String, Object>> movie_list = new ArrayList<HashMap<String, Object>>();
    private ListView listView;
    public static final String TAG = "movie";
    private int date;
    private int today;
    private List<MovieItem> lists;
    DBManager dbManager = new DBManager(this);
    SharedPreferences sharedPreferences;
    Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);
        sharedPreferences = getSharedPreferences("mydate", Activity.MODE_PRIVATE);
        date = sharedPreferences.getInt("date",0);
        Calendar calendar = Calendar.getInstance();
        today = calendar.get(Calendar.DATE);
        Log.i(TAG,"date = " + date);
        Log.i(TAG,"today = " + today);
        if (date==0){
            final Thread t = new Thread(this);
            t.start();
            Log.i(TAG,"第一次刷新。。。。。。。。。。。。");
        }else if(today<=date){
            Log.i(TAG,"读取本地数据。。。。。。。。。。。");
            lists = dbManager.listAll();
            Bitmap img;
            for(MovieItem item :lists){
                GetImageByUrl getImageByUrl = new GetImageByUrl();
                img = getImageByUrl.getUrlImage(item.getUrl());
                HashMap<String,Object> map = new HashMap<String, Object>();
                map.put("html",item.getHtml());
                map.put("img",img);
                map.put("date",item.getDate());
                map.put("title",item.getTitle());
                map.put("type",item.getType());
                map.put("country",item.getCountry());
                map.put("num",item.getNum());
                movie_list.add(map);
            }
            BaseAdapter adapter = new ImageAdapter(MainActivity.this,movie_list);
            //设置适配器
            listView.setAdapter(adapter);
        }else {
            dbManager.deleteAll();
            final Thread t = new Thread(this);
            t.start();
            Log.i(TAG,"重新刷新数据。。。。。。。。。。。");
        }

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 1){
                    //movie_list = (List<HashMap<String, Object>>) msg.obj;
                    lists = dbManager.listAll();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String a[] = dbManager.findById(1).getDate().split("[\u4e00-\u9fcc]+");
                    Log.i(TAG,"date = "+a[1]);
                    editor.putInt("date",Integer.parseInt(a[1]));
                    editor.commit();

                    Bitmap img ;
                    for(MovieItem item :lists){
                        GetImageByUrl getImageByUrl = new GetImageByUrl();
                        HashMap<String,Object> map = new HashMap<String, Object>();
                        img = getImageByUrl.getUrlImage(item.getUrl());
                        map.put("html",item.getHtml());
                        map.put("img",img);
                        map.put("date",item.getDate());
                        map.put("title",item.getTitle());
                        map.put("type",item.getType());
                        map.put("country",item.getCountry());
                        map.put("num",item.getNum());
                        movie_list.add(map);
                    }
                    /*for(HashMap<String, Object> map : movie_list){
                        Log.i(TAG,"handle...........");
                        Log.i(TAG,"html：" + map.get("html"));
                        Log.i(TAG,"url：" + map.get("url"));
                        Log.i(TAG,"上映日期：" + map.get("date"));
                        Log.i(TAG,"片名：" + map.get("title"));
                        Log.i(TAG,"类型：" + map.get("type"));
                        Log.i(TAG,"地区：" + map.get("country"));
                        Log.i(TAG,"想看人数：" + map.get("num"));
                    }*/
                    BaseAdapter adapter = new ImageAdapter(MainActivity.this,movie_list);
                    //设置适配器
                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

        listView.setOnItemClickListener(this);
    }
    @Override
    public void run() {
        Log.i(TAG,"run..........");
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String, Object>>();
        try {
            Document doc = Jsoup.connect("https://movie.douban.com/coming").get();
            Elements tables = doc.getElementsByTag("table");
            Element table = tables.get(1);
            Elements tds = table.getElementsByTag("td");
            for(int i = 0;i<tds.size();i+=5){
                if((1+i/5)>4){break;}
                Log.i(TAG,"第"+(1+i/5)+"项");
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+1);
                Element td3 = tds.get(i+2);
                Element td4 = tds.get(i+3);
                Element td5 = tds.get(i+4);

                Elements as = td2.getElementsByTag("a");
                Element a = as.get(0);

                String html = a.absUrl("href");

                Document doc1 = Jsoup.connect(html).get();
                Elements imgs = doc1.getElementsByTag("img");
                Element img = imgs.get(0);

                String url = img.attr("abs:src");
                String date = td1.text();
                String title = td2.text();
                String type = td3.text();
                String country = td4.text();
                String num = td5.text();

                //Log.i(TAG,"html：" + html);
                //Log.i(TAG,"url：" + url);
                //Log.i(TAG,"上映日期：" + date);
                //Log.i(TAG,"片名：" + title);
                //Log.i(TAG,"类型：" + type);
                //Log.i(TAG,"地区：" + country);
                //Log.i(TAG,"想看人数：" + num);

                //HashMap map = new HashMap<String ,Object >();
                //GetImageByUrl getImageByUrl = new GetImageByUrl();
                //Log.i(TAG,"img "+getImageByUrl.getUrlImage(url));
                /*map.put("html",html);
                map.put("img",getImageByUrl.getUrlImage(url));
                map.put("date",date);
                map.put("title",title);
                map.put("type",type);
                map.put("country",country);
                map.put("num",num);
                list.add(map);*/
                MovieItem movieItem = new MovieItem(html,date,title,type,country,num,url);
                dbManager.add(movieItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG,"run  send message..........");
        Message msg = handler.obtainMessage(1);
        //msg.obj = list;
        handler.sendMessage(msg);
        Log.i(TAG,"run  end..........");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String,Object> map = (HashMap<String, Object>) listView.getItemAtPosition(position);
        Bitmap img = (Bitmap) map.get("img");
        String html = (String) map.get("html");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        Intent MD = new Intent(this,MovieDetail.class);
        MD.putExtra("img",byteArrayOutputStream.toByteArray());
        MD.putExtra("html",html);
        startActivity(MD);
    }
}

