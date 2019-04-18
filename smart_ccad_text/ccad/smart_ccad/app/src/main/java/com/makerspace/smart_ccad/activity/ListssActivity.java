package com.makerspace.smart_ccad.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.makerspace.smart_ccad.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 人防知识
 */
public class ListssActivity extends BaseActivity {




    @Override
    public void setCustomToolbar(Toolbar mToolbar) {
        mToolbar.setTitle("人防知识");
    }

    @Override
    protected void initCustomView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_listss);

//        getSupportActionBar().hide();
        String type = getIntent().getStringExtra("type");
        ListView listVieww = (ListView) findViewById(R.id.list);

        listVieww.setAdapter(new Myadapter());
    }

    private List<String> getData() {

        List<String> data = new ArrayList<String>();
        data.add("假如空袭来临，我们该往哪里躲?");
        data.add("人民防空的战略意义");
        data.add("空袭后的灾害和破坏区的特点");
        data.add("空袭后的抢救抢险工作");
        data.add("开展人民防空教育的意义");
        data.add("地震灾害的应急防护方法");
        return data;
    }

    class Myadapter extends BaseAdapter {


        public class ViewHolder {
             TextView desc;
             TextView date;

        }

        @Override
        public int getCount() {
            return getData().size();
        }

        @Override
        public Object getItem(int position) {
            return getData().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ListssActivity.Myadapter.ViewHolder holder ;
            if (convertView == null) {
                holder = new ListssActivity.Myadapter.ViewHolder();
                convertView = View.inflate(ListssActivity.this, R.layout.item_listss, null);
                holder.desc = convertView.findViewById(R.id.listss_item_info);
                holder.date = convertView.findViewById(R.id.listss_item_date);
                convertView.setTag(holder);
            } else {
                holder = (ListssActivity.Myadapter.ViewHolder) convertView.getTag();
            }


            holder.desc.setText(getData().get(position));


            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
            String dateString = formatter.format(new Date());

            holder.date.setText(dateString);

            return convertView;
        }
    }

}
