package cn.edu.swufe.music;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<HashMap<String, String>> data;
    private Context context;
    private ViewHolder viewHolder;

    public MyAdapter(Context context, List<HashMap<String, String>> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            viewHolder = new ViewHolder();
            //convertView = View.inflate(context, R.layout.comment, parent);
            convertView = LayoutInflater.from(context).inflate(R.layout.comment,parent,false);
            viewHolder.userName = convertView.findViewById(R.id.userName);
            viewHolder.KG = convertView.findViewById(R.id.KG);
            viewHolder.date = convertView.findViewById(R.id.date);
            viewHolder.content = convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HashMap<String,String> map = data.get(position);
        String userName =map.get("userName");
        viewHolder.userName.setText(userName);
        viewHolder.KG.setText(map.get("KG"));
        viewHolder.date.setText(map.get("date"));
        viewHolder.content.setText(map.get("content"));

        return convertView;
    }

    class ViewHolder {
        public TextView userName;
        public TextView KG;
        public TextView date;
        public TextView content;
    }
}
