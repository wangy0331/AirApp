package com.sohu110.airapp.ui.yujing;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.widget.LoadProcessDialog;

import java.util.List;

/**
 * 设备预警
 * Created by Aaron on 2016/4/23.
 */
public class YujingListAdapter extends ArrayAdapter<Device> {

    private Button guanzhuBtn;

    private Button quxiaoBtn;

    private String mText = "无";

    public YujingListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_yujing_item, null);
            holder = new ViewHolder();
            holder.coName = (TextView) convertView.findViewById(R.id.co_name_yj);
            holder.icon = (TextView) convertView.findViewById(R.id.jq_icon_yj);
            holder.jiqiSn = (TextView) convertView.findViewById(R.id.jiqi_sn_yj);
            holder.scBtn = (Button) convertView.findViewById(R.id.shoucang_btn_yj);
            holder.nscBtn = (Button) convertView.findViewById(R.id.not_shoucang_btn_yj);
            holder.mTime = (TextView) convertView.findViewById(R.id.time_yj);
            holder.mTemp = (TextView) convertView.findViewById(R.id.wendu_yj);
            holder.mPress = (TextView) convertView.findViewById(R.id.yali_yj);
            holder.mAirSn = (TextView) convertView.findViewById(R.id.kyj_sn_yj);
            holder.mSta = (TextView) convertView.findViewById(R.id.yjzt_yj);

            guanzhuBtn = (Button) convertView.findViewById(R.id.shoucang_btn);
            quxiaoBtn = (Button) convertView.findViewById(R.id.not_shoucang_btn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Device item = getItem(position);
        int id = position + 1;

        if ("".equals(item.getCoName())) {
            holder.coName.setText(id + "." + mText);
        } else {
            holder.coName.setText(id + "." + item.getCoName());
        }

        if ("".equals(item.getJiqiSn())) {
            holder.jiqiSn.setText(mText);
        } else {
            holder.jiqiSn.setText(item.getJiqiSn());
        }

        if ("".equals(item.getShijian())) {
            holder.mTime.setText(mText);
        } else {
            holder.mTime.setText(item.getShijian());
        }
        if (!"".equals(item.getXinxi())) {
            holder.mSta.setText(item.getXinxi().substring(5,item.getXinxi().length()));
        } else {
            holder.mSta.setText(mText);
        }


//        if ("Y".equals(item.getfSta())) {
            holder.scBtn.setVisibility(View.GONE);
//            holder.nscBtn.setVisibility(View.VISIBLE);
//        } else if ("N".equals(item.getfSta()) || "".equals(item.getfSta())) {
//            holder.scBtn.setVisibility(View.VISIBLE);
            holder.nscBtn.setVisibility(View.GONE);
//        }

        if ("".equals(item.getJqStatus())) {
            holder.icon.setText(mText);
        } else {
            holder.icon.setText(item.getJqStatus());
        }

        if ("".equals(item.getPress())) {
            holder.mPress.setText(mText);
        } else {
            holder.mPress.setText(Double.valueOf(item.getPress())/100 + " MPa");
        }

        if ("".equals(item.getTemp())) {
            holder.mTemp.setText(mText);
        } else {
            holder.mTemp.setText(item.getTemp() + "℃");
        }

        if ("".equals(item.getAirSn())) {
            holder.mAirSn.setText(mText);
        } else {
            holder.mAirSn.setText(item.getAirSn());
        }


        holder.scBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShoucangTask(item).execute();
            }
        });

        holder.nscBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NotSCTask(item).execute();
            }
        });

        return convertView;
    }

    class ViewHolder {
        //机器序列号
        TextView jiqiSn;
        //运行状态
        TextView jqStatus;
        //客户名称
        TextView coName;
        //运行状态
        TextView icon;
        //关注
        Button scBtn;
        //取消关注
        Button nscBtn;
        //时间
        TextView mTime;
        //温度
        TextView mTemp;
        //压力
        TextView mPress;
        //空压机编号
        TextView mAirSn;
        //状态
        TextView mSta;
    }


    /**
     * 收藏
     */
    class ShoucangTask extends AsyncTask<Void, Void, Result> {

        String jqSN;
        Device mDevice;
        LoadProcessDialog mLoadDialog;

        public ShoucangTask(Device device) {
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
                return ServiceCenter.deviceSC(mDevice.getJiqiSn());
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


                    mDevice.setfSta("Y");
                    YujingListAdapter.this.notifyDataSetChanged();


                } else {
                    YujingListAdapter.this.notifyDataSetChanged();
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                YujingListAdapter.this.notifyDataSetChanged();
                Toast.makeText(getContext(), R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 取消收藏
     */
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
                    mDevice.setfSta("N");
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                    YujingListAdapter.this.notifyDataSetChanged();
                } else {
                    YujingListAdapter.this.notifyDataSetChanged();
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                YujingListAdapter.this.notifyDataSetChanged();
                Toast.makeText(getContext(), R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
