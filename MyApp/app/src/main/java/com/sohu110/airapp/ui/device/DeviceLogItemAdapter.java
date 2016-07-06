package com.sohu110.airapp.ui.device;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceLog;

/**
 * Created by Aaron on 2016/7/3.
 */
public class DeviceLogItemAdapter extends ArrayAdapter<DeviceLog> {

    public DeviceLogItemAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.device_log_item, null);
            holder = new ViewHolder();
            holder.num = (TextView) convertView.findViewById(R.id.device_num);
            holder.date = (TextView) convertView.findViewById(R.id.device_rq);
            holder.sj = (TextView) convertView.findViewById(R.id.device_sj);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DeviceLog item = getItem(position);
        int id = position + 1;

        holder.num.setText(String.valueOf(id));
        holder.date.setText(item.getSjsj());
        holder.sj.setText(item.getSjjl());

        return convertView;
    }

    class ViewHolder {
        //日期
        TextView date;
        //零件
        TextView sj;
        //序号
        TextView num;
    }
}
