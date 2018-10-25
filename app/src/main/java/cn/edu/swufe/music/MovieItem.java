package cn.edu.swufe.music;

import android.graphics.Bitmap;

public class MovieItem {
    private int id;
    private String html;
    private String date;
    private String title;
    private String type;
    private String country;
    private String num;
    private String url;
    //private Bitmap img;


    public MovieItem(){
        super();
        html = "";
        date = "";
        title = "";
        type = "";
        country = "";
        num = "";
        url = "";
        //img = null;


    }

    public MovieItem( String html, String date, String title, String type, String country, String num,String url){
        super();
        this.html = html;
        this.date = date;
        this.title = title;
        this.type = type;
        this.country = country;
        this.num = num;
        this.url = url;
        //this.img = img;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getHtml(){
        return html;
    }

    public void setHtml(String html){
        this.html = html;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getNum(){
        return num;
    }

    public void setNum(String num){
        this.num = num;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    /*public Bitmap getImg(){
        return img;
    }

    public void setImg(Bitmap img){
        this.img = img;
    }*/
}
