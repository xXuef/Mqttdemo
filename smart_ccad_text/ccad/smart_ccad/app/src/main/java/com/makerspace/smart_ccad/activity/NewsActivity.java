package com.makerspace.smart_ccad.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.makerspace.smart_ccad.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 *   人防快询
 */

public class NewsActivity extends BaseActivity {
    int[] ints = {R.mipmap.news1, R.mipmap.news2, R.mipmap.news3, R.mipmap.news4};
    String[] strings = {
            "富阳区人防办开展2017年度人防重点镇（街）工作目标考核",
            "市人防办圆满完成杭州市2017年度基层人防骨干培训",
            "市发改委调研我办信用建设工作",
            "杭州市召开全国防化产品企业座谈会"};


    @Override
    public void setCustomToolbar(Toolbar mToolbar) {
        mToolbar.setTitle("人防快讯");

    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_news);
//        getSupportActionBar().hide();
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new Myadapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    class Myadapter extends BaseAdapter {


        public class ViewHolder {
            public ImageView img;
            public TextView desc;
            public TextView date;

        }

        @Override
        public int getCount() {
            return ints.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(NewsActivity.this, R.layout.item_news, null);
                holder.img = convertView.findViewById(R.id.imagview);
                holder.desc = convertView.findViewById(R.id.title);
                holder.date = convertView.findViewById(R.id.info_item_date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.img.setImageResource(ints[position]);
            holder.desc.setText(strings[position]);


            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
            String dateString = formatter.format(new Date());

            holder.date.setText(dateString);

            return convertView;
        }
    }
}
