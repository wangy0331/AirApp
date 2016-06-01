package com.sohu110.airapp.ui.weibao;

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
 * 维保列表
 * Created by Aaron on 2016/4/23.
 */
public class WeibaoAdapter extends ArrayAdapter<Device> {

    private Button guanzhuBtn;

    private Button quxiaoBtn;

    public WeibaoAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_weibao_item, null);
            holder = new ViewHolder();
            holder.coName = (TextView) convertView.findViewById(R.id.co_name_wb);
            holder.icon = (TextView) convertView.findViewById(R.id.jq_icon_wb);
            holder.jiqiSn = (TextView) convertView.findViewById(R.id.jiqi_sn_wb);
            holder.scBtn = (Button) convertView.findViewById(R.id.shoucang_btn_wb);
            holder.nscBtn = (Button) convertView.findViewById(R.id.not_shoucang_btn_wb);
            holder.mTime = (TextView) convertView.findViewById(R.id.time_wb);
            holder.mTemp = (TextView) convertView.findViewById(R.id.wendu_wb);
            holder.mPress = (TextView) convertView.findViewById(R.id.yali_wb);
            holder.mAirSn = (TextView) convertView.findViewById(R.id.kyj_sn_wb);
            holder.mSta = (TextView) convertView.findViewById(R.id.bjzt_wb);

            guanzhuBtn = (Button) convertView.findViewById(R.id.shoucang_btn);
            quxiaoBtn = (Button) convertView.findViewById(R.id.not_shoucang_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Device item = getItem(position);
        holder.coName.setText(item.getCoName());
        int id = position + 1;

        holder.coName.setText(id + "." + item.getCoName());
        holder.jiqiSn.setText(item.getJiqiSn());


        if ("Y".equals(item.getfSta())) {
            holder.scBtn.setVisibility(View.GONE);
            holder.nscBtn.setVisibility(View.VISIBLE);
        } else if ("N".equals(item.getfSta()) || "".equals(item.getfSta())) {
            holder.scBtn.setVisibility(View.VISIBLE);
            holder.nscBtn.setVisibility(View.GONE);
        }

        holder.icon.setText(item.getJqStatus());

        holder.mPress.setText(item.getPress());
        holder.mTemp.setText(item.getTemp());
        holder.mAirSn.setText(item.getAirSn());

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
                    WeibaoAdapter.this.notifyDataSetChanged();


                } else {
                    WeibaoAdapter.this.notifyDataSetChanged();
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                WeibaoAdapter.this.notifyDataSetChanged();
                Toast.makeText(getContext(), R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
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
                    mDevice.setfSta("N");
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                    WeibaoAdapter.this.notifyDataSetChanged();
                } else {
                    WeibaoAdapter.this.notifyDataSetChanged();
                    Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                WeibaoAdapter.this.notifyDataSetChanged();
                Toast.makeText(getContext(), R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
