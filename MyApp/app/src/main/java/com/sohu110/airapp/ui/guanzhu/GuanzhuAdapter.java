package com.sohu110.airapp.ui.guanzhu;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.widget.LoadProcessDialog;

import java.util.List;

/**
 * 关注列表
 * Created by Aaron on 2016/5/6.
 */
public class GuanzhuAdapter extends ArrayAdapter<Device> {
    private Button guanzhuBtn;

    private Button quxiaoBtn;

    public GuanzhuAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_device_item, null);
            holder = new ViewHolder();
//            holder.jqName = (TextView) convertView.findViewById(R.id.jq_name);
            holder.coName = (TextView) convertView.findViewById(R.id.co_name);
            holder.icon = (ImageView) convertView.findViewById(R.id.jq_icon);
            holder.jiqiSn = (TextView) convertView.findViewById(R.id.jiqi_sn);
            holder.scBtn = (Button) convertView.findViewById(R.id.shoucang_btn);
            holder.nscBtn = (Button) convertView.findViewById(R.id.not_shoucang_btn);

            guanzhuBtn = (Button) convertView.findViewById(R.id.shoucang_btn);
            quxiaoBtn = (Button) convertView.findViewById(R.id.not_shoucang_btn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Device item = getItem(position);
//        holder.coName.setText(item.getCoName());
        int id = position + 1;

        holder.coName.setText(id + "." + item.getCoName());
        holder.jiqiSn.setText(item.getJiqiSn());

        if ("F".equals(item.getJqStatus())) {
            holder.icon.setImageResource(R.drawable.device_f);
        } else if ("T".equals(item.getJqStatus())) {
            holder.icon.setImageResource(R.drawable.device_t);
        }

        if ("Y".equals(item.getfSta())) {
            holder.scBtn.setVisibility(View.GONE);
            holder.nscBtn.setVisibility(View.VISIBLE);
        } else if ("N".equals(item.getfSta()) || "".equals(item.getfSta())) {
            holder.scBtn.setVisibility(View.VISIBLE);
            holder.nscBtn.setVisibility(View.GONE);
        }


        final String jqid = item.getJiqiSn();


        holder.nscBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 设置对话框标题
                new android.app.AlertDialog.Builder(getContext()).setTitle(R.string.tishi)

                        // 设置显示的内容
                        .setMessage(R.string.quxiaoguanzhu)

                                // 添加确定按钮
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {

                            // 确定按钮的响应事件
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new NotSCTask(item).execute();
                            }
                            // 添加返回按钮
                        }).setNegativeButton(R.string.btnReturn, null).show();// 在按键响应事件中显示此对话框

            }
        });


        return convertView;
    }

    class ViewHolder {
        //机器序列号
        TextView jiqiSn;
        //        //权限（C为控制，R为显示）
//        TextView auth;
        //机器类型名称
//        TextView jqName;
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
        //关注
        Button scBtn;
        //取消关注
        Button nscBtn;

    }

    class NotSCTask extends AsyncTask<Void, Void, Result> {

        Device mDevice;
        LoadProcessDialog mLoadDialog;

        public NotSCTask(Device device) {
            mDevice = device;
            mLoadDialog = new LoadProcessDialog(getContext());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<List<Device>> doInBackground(Void... params) {
            try {
                return ServiceCenter.deviceNSC(mDevice.getJiqiSn());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            if (result != null) {
                if (result.isSuceed()) {
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                    GuanzhuAdapter.this.remove(mDevice);
                } else {
                    GuanzhuAdapter.this.notifyDataSetChanged();
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                GuanzhuAdapter.this.notifyDataSetChanged();
                Toast.makeText(getContext(), R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
