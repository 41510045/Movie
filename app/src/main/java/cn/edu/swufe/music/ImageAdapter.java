package cn.edu.swufe.music;

import android.graphics.Bitmap;
import android.widget.BaseAdapter;

import java.net.BindException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ImageAdapter extends BaseAdapter {
    // 要显示的数据的集合
    private List<HashMap<String, Object>> data;
    // 接受上下文
    private Context context;
    // 声明内部类对象
    private ViewHolder viewHolder;

    public ImageAdapter(Context context, List<HashMap<String, Object>> data) {
        this.context = context;
        this.data = data;
    }

    // 返回的总个数
    @Override
    public int getCount() {
        return data.size();
    }

    // 返回每个条目对应的数据
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    // 返回的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 返回这个条目对应的控件对象
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.list_item, null);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.date = convertView.findViewById(R.id.date);
            viewHolder.country = convertView.findViewById(R.id.country);
            viewHolder.type = convertView.findViewById(R.id.type);
            viewHolder.num = convertView.findViewById(R.id.num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HashMap<String, Object> map = data.get(position);
        String title = map.get("title").toString();
        String date = map.get("date").toString();
        String type = map.get("type").toString();
        String country = map.get("country").toString();
        String num = map.get("num").toString();

        viewHolder.imageView.setImageBitmap((Bitmap) map.get("img"));
        viewHolder.title.setText("片名："+title);
        viewHolder.date.setText("日期："+date);
        viewHolder.type.setText("类型："+type);
        viewHolder.country.setText("地区："+country);
        viewHolder.num.setText("想看："+num);
        return convertView;
    }

    /**
     * 内部类 记录单个条目中所有属性
     */
    class ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView type;
        public TextView country;
        public TextView num;
        public TextView date;
    }

}
