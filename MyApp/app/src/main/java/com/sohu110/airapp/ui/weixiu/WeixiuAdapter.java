package com.sohu110.airapp.ui.weixiu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Device;

/**
 * Created by Aaron on 2016/4/23.
 */
public class WeixiuAdapter extends ArrayAdapter<Device> {
    public WeixiuAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_weixiu_item, null);
            holder = new ViewHolder();
            holder.jqName = (TextView) convertView.findViewById(R.id.jq_name);
            holder.coName = (TextView) convertView.findViewById(R.id.co_name);
            holder.icon = (ImageView) convertView.findViewById(R.id.jq_icon);
            holder.jiqiSn = (TextView) convertView.findViewById(R.id.jiqi_sn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Device item = getItem(position);
        holder.coName.setText(item.getCoName());
        int id = position + 1;

        holder.jqName.setText(id + "." + item.getJqName());
        holder.jiqiSn.setText(item.getJiqiSn());

        holder.icon.setImageResource(R.drawable.device_f);

//        if ("F".equals(item.getJqStatus())) {
//            holder.icon.setImageResource(R.drawable.device_f);
//        } else if ("T".equals(item.getJqStatus())) {
//            holder.icon.setImageResource(R.drawable.device_t);
//        }


        return convertView;
    }

    class ViewHolder {
        //机器序列号
        TextView jiqiSn;
        //        //权限（C为控制，R为显示）
//        TextView auth;
        //机器类型名称
        TextView jqName;
        //        //机器经度
//        TextView jqWD;
//        //机器纬度
//        TextView jqJD;
        //运行状态
        TextView jqStatus;
        //        //机器注册日期
//        TextView jiqiNewDate;
//        //机器生产日期
//        TextView jiqiPdate;
//        //归属地区
//        TextView region;
        //客户名称
        TextView coName;
        //图片
        ImageView icon;
    }
}
