package com.sohu110.airapp.ui.weixiu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceLog;

/**
 * 维保信息
 * Created by Aaron on 2016/5/29.
 */
public class WeixiuDetailItemAdapter extends ArrayAdapter<DeviceLog> {

    public WeixiuDetailItemAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_lishi_item, null);
            holder = new ViewHolder();
            holder.num = (TextView) convertView.findViewById(R.id.num);
            holder.date = (TextView) convertView.findViewById(R.id.rq);
            holder.zt = (TextView) convertView.findViewById(R.id.log_zt);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DeviceLog item = getItem(position);
        int id = position + 1;

        holder.num.setText(String.valueOf(id));
        holder.date.setText(item.getSj());
        holder.zt.setText(item.getZt());

        return convertView;
    }

    class ViewHolder {
        //日期
        TextView date;
        //零件
        TextView zt;
        //序号
        TextView num;
    }
}
