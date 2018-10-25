package cn.edu.swufe.music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DBHelper dbHelper;
    private String TBNAME = "tb_movies";

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void add(MovieItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("html",item.getHtml());
        values.put("title",item.getTitle());
        values.put("date",item.getDate());
        values.put("type",item.getType());
        values.put("country",item.getCountry());
        values.put("num",item.getNum());
        //values.put("img",item.getImg());
        values.put("url",item.getUrl());
        db.insert(TBNAME,null,values);
        db.close();
    }

    public void addALL(List<MovieItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for(MovieItem item : list){
            ContentValues values = new ContentValues();
            values.put("html",item.getHtml());
            values.put("title",item.getTitle());
            values.put("date",item.getDate());
            values.put("type",item.getType());
            values.put("country",item.getCountry());
            values.put("num",item.getNum());
            //values.put("img",item.getImg());
            values.put("url",item.getUrl());
            db.insert(TBNAME,null,values);
        }
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,"ID=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public void update(MovieItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("html",item.getHtml());
        values.put("title",item.getTitle());
        values.put("date",item.getDate());
        values.put("type",item.getType());
        values.put("country",item.getCountry());
        values.put("num",item.getNum());
        //values.put("img",item.getImg());
        values.put("url",item.getUrl());
        db.update(TBNAME,values,"ID=?",new String[]{String.valueOf(item.getId())});
    }

    public List<MovieItem> listAll(){
        List<MovieItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<MovieItem>();
            while(cursor.moveToNext()){
                MovieItem item = new MovieItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setHtml(cursor.getString(cursor.getColumnIndex("html")));
                item.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                item.setType(cursor.getString(cursor.getColumnIndex("type")));
                item.setDate(cursor.getString(cursor.getColumnIndex("date")));
                item.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                item.setNum(cursor.getString(cursor.getColumnIndex("num")));
                item.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;

    }

    public MovieItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        MovieItem item = null;
        if(cursor!=null && cursor.moveToFirst()){
            item = new MovieItem();
            item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            item.setHtml(cursor.getString(cursor.getColumnIndex("html")));
            item.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            item.setType(cursor.getString(cursor.getColumnIndex("type")));
            item.setDate(cursor.getString(cursor.getColumnIndex("date")));
            item.setCountry(cursor.getString(cursor.getColumnIndex("country")));
            item.setNum(cursor.getString(cursor.getColumnIndex("num")));
            item.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            cursor.close();
        }
        db.close();
        return item;
    }
}
