package com.sohu110.airapp.ui.device;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sohu110.airapp.DashBoardPressView;
import com.sohu110.airapp.DashBoardTempView;
import com.sohu110.airapp.DashBoardView;
import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.DeviceDetail;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.log.Logger;
import com.sohu110.airapp.service.ServiceCenter;

public class Fragment1 extends Fragment{
	//排气压力(表盘)
	DashBoardPressView dashBoardPressView;
	//机头温度(表盘)
	DashBoardTempView dashBoardTempView;
	//主机电流(表盘)
	DashBoardView dashBoardView;
	//日期
	private TextView date;
	//电机状态
	private TextView dianjiSta;
	//空压机状态
	private TextView kyjSta;
	//风机1状态
	private TextView fengji1Sta;
	//风机2状态
	private TextView fengji2Sta;
	//排气压力
	private TextView paiqiPress;
	//机头温度
	private TextView jitouTemp;
	//主机电流
	private TextView zhujiDl;
	//电机温度
	private TextView dianjiTemp;
	//电气温度
	private TextView dianqiTemp;
	//节能率
	private TextView jienengLv;
	//节能电能
	private TextView jienengDn;
	//实际耗能
	private TextView shijiHn;
	//设备运行
	private TextView shebeiSta;
	//通讯状况
	private TextView tongxunSta;
	//主机电流1
	private TextView zhujiDl1;
	//主机电流2
	private TextView zhujiDl2;
	//主机电流3
	private TextView zhujiDl3;
	//风机1电流1
	private TextView fengji1Dl1;
	//风机1电流2
	private TextView fengji1Dl2;
	//风机1电流3
	private TextView fengji1Dl3;
	//风机2电流1
	private TextView fengji2Dl1;
	//风机2电流2
	private TextView fengji2Dl2;
	//风机2电流3
	private TextView fengji2Dl3;
	//设备电压
	private TextView shebeiDy;
	//加载次数
	private TextView jiazaiCs;
	//累积运行时间
	private TextView leijiYx;
	//累积加载时间
	private TextView leijiJz;
	//本次运行时间
	private TextView benciYx;
	//本次加载时间
	private TextView benciJz;
	//电源频率
	private TextView dianyuanPl;
	//设备功率
	private TextView shebeiGl;
	//每天平均运行时间
	private TextView dayPjSj;
	//客户名称
	private TextView kehuMc;
	//空压机编号
	private TextView kyjBh;
	//空压机品牌
	private TextView kyjpp;
	//主机软件版本
	private TextView rjbb;
	//主机硬件版本
	private TextView yjbb;
	//SN号
	private TextView sn;
	//采集时间
	private TextView caijiTime;
	//报警状态
	private TextView mBaojing;
	private TextView mYujing;
	private TextView bjText;
	private TextView yjText;

	private String dianliu = "A";
	private String wendu = "℃";
	private String mpa = "MPa";
	private String qws = "kMh";
	private String dianya = "V";
	private String hz = "Hz";
	private String gl = "kW";
	private String hour = "h";
	private String lv = "%";

	private static String GUID = "guid";

	private SwipeRefreshLayout mSwipeRefreshLayout;

	private String guid;

	public static Fragment1 newInstance(String guid) {
		Fragment1 fragment = new Fragment1();
		Bundle bundle = new Bundle();
		bundle.putString(GUID, guid);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//获取预警类型编号
		guid = this.getArguments().getString(GUID);
		Log.e("fragment", guid);
	}


	@Override
	public void onResume() {
		super.onResume();
		new DeviceDetailTask(guid).execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_device_data,null );

		//电流
		dashBoardView = (DashBoardView) view.findViewById(R.id.dashBoardView);
		//机头温度
		dashBoardTempView = (DashBoardTempView) view.findViewById(R.id.dashBoardTempView);
		//排气压力
		dashBoardPressView = (DashBoardPressView) view.findViewById(R.id.dashBoardPressView);

		caijiTime = (TextView) view.findViewById(R.id.now_time);
		dianjiSta = (TextView) view.findViewById(R.id.djzt);
		kyjSta = (TextView) view.findViewById(R.id.kyjzt);
		fengji1Sta = (TextView) view.findViewById(R.id.fj1zt);
		fengji2Sta = (TextView) view.findViewById(R.id.fj2zt);
		paiqiPress = (TextView) view.findViewById(R.id.paiqi_press);
		jitouTemp = (TextView) view.findViewById(R.id.jitou_temp);
		zhujiDl = (TextView) view.findViewById(R.id.zj_dl);
		dianjiTemp = (TextView) view.findViewById(R.id.dianji_temp);
		dianqiTemp = (TextView) view.findViewById(R.id.dianqi_temp);
		jienengLv = (TextView) view.findViewById(R.id.jnl_text);
		jienengDn = (TextView) view.findViewById(R.id.jienengDn);
		shijiHn = (TextView) view.findViewById(R.id.shijiHn);
		shebeiSta = (TextView) view.findViewById(R.id.shebei_sta);
		tongxunSta = (TextView) view.findViewById(R.id.tongxun_sta);
		zhujiDl1 = (TextView) view.findViewById(R.id.zhuji1_dl1);
		zhujiDl2 = (TextView) view.findViewById(R.id.zhuji1_dl2);
		zhujiDl3 = (TextView) view.findViewById(R.id.zhuji1_dl3);
		fengji1Dl1 = (TextView) view.findViewById(R.id.fengji1_dl1);
		fengji1Dl2 = (TextView) view.findViewById(R.id.fengji1_dl2);
		fengji1Dl3 = (TextView) view.findViewById(R.id.fengji1_dl3);
		fengji2Dl1 = (TextView) view.findViewById(R.id.fengji2_dl1);
		fengji2Dl2 = (TextView) view.findViewById(R.id.fengji2_dl2);
		fengji2Dl3 = (TextView) view.findViewById(R.id.fengji2_dl3);
		shebeiDy = (TextView) view.findViewById(R.id.shebeiDy);
		jiazaiCs = (TextView) view.findViewById(R.id.jzcs);
		leijiYx = (TextView) view.findViewById(R.id.ljyxsj);
		leijiJz = (TextView) view.findViewById(R.id.ljjzsj);
		benciYx = (TextView) view.findViewById(R.id.bcyxsj);
		benciJz = (TextView) view.findViewById(R.id.bcjzsj);
		dianyuanPl = (TextView) view.findViewById(R.id.dypl);
		shebeiGl = (TextView) view.findViewById(R.id.sbgl);
		dayPjSj = (TextView) view.findViewById(R.id.mtpjyxsj);
		kehuMc = (TextView) view.findViewById(R.id.khmc_text);
		kyjBh = (TextView) view.findViewById(R.id.kyjbh_text);
		kyjpp = (TextView) view.findViewById(R.id.kyjpp_text);
		rjbb = (TextView) view.findViewById(R.id.zjrjbb);
		yjbb = (TextView) view.findViewById(R.id.zjyjbb);
		sn = (TextView) view.findViewById(R.id.sn_text);
		mBaojing = (TextView) view.findViewById(R.id.baojing);
		mYujing = (TextView) view.findViewById(R.id.yujing);
		bjText = (TextView) view.findViewById(R.id.baojing_text);
		yjText = (TextView) view.findViewById(R.id.yujing_text);

		mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.detail_list_refresh);

		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new DeviceDetailTask(guid).execute();
			}
		});

		return view;
	}

	class DeviceDetailTask extends AsyncTask<Void, Void, Result<DeviceDetail>> {

		private String jiqiSn;

		public DeviceDetailTask(String guid) {
			jiqiSn = guid;
		}

		@Override
		protected Result<DeviceDetail> doInBackground(Void... params) {
			try{
				return ServiceCenter.getDetail(jiqiSn);
			} catch (Exception e) {
				Logger.e("", "", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Result<DeviceDetail> result) {
			super.onPostExecute(result);
			mSwipeRefreshLayout.setRefreshing(false);
			if (result != null) {
				if (result.isSuceed()) {

					DeviceDetail item = result.getData();

					if (item != null) {

						dashBoardView.setSpeed(Integer.valueOf(item.getZhujiDl1().trim()));
						dashBoardTempView.setSpeed(Integer.valueOf(item.getAirTemp().trim()));
						dashBoardPressView.setSpeed(Float.valueOf(item.getAirPress().trim()));

						caijiTime.setText(item.getNowTime().trim());
						dianjiSta.setText(item.getDianjiSta().trim());
						kyjSta.setText(item.getAirSta().trim());
						fengji1Sta.setText(item.getFengjiSta1().trim());
						fengji2Sta.setText(item.getFengjiSta2().trim());
						paiqiPress.setText(item.getAirPress().trim() + mpa);
						jitouTemp.setText(item.getAirTemp().trim() + wendu);
						zhujiDl.setText(item.getZhujiDl1().trim() + dianliu);
						dianjiTemp.setText(item.getDianjiTemp().trim() + wendu);
						dianqiTemp.setText(item.getModleTemp().trim() + wendu);
						jienengLv.setText(item.getJienengLv().trim() + lv);
						jienengDn.setText(item.getJienengDianneng().trim() + qws);
						shijiHn.setText(item.getSjHaoneng().trim() + qws);
						shebeiSta.setText(item.getDeviceSta().trim());
						tongxunSta.setText(item.getTongxunSta().trim());
						zhujiDl1.setText(item.getZhujiDl1().trim() + dianliu);
						zhujiDl2.setText(item.getZhujiDl2().trim() + dianliu);
						zhujiDl3.setText(item.getZhujiDl3().trim() + dianliu);
						fengji1Dl1.setText(item.getFengji1Dl1().trim() + dianliu);
						fengji1Dl2.setText(item.getFengji1Dl2().trim() + dianliu);
						fengji1Dl3.setText(item.getFengji1Dl3().trim() + dianliu);
						fengji2Dl1.setText(item.getFengji2Dl1().trim() + dianliu);
						fengji2Dl2.setText(item.getFengji2Dl2().trim() + dianliu);
						fengji2Dl3.setText(item.getFengji2Dl3().trim() + dianliu);
						shebeiDy.setText(item.getCurrentDy().trim() + dianya);
						jiazaiCs.setText(item.getJiazaiCs().trim());
						leijiYx.setText(item.getLeijiYx().trim() + hour);
						leijiJz.setText(item.getLeijiJz().trim() + hour);
						benciYx.setText(item.getBenciYx().trim() + hour);
						benciJz.setText(item.getBenciJz().trim() + hour);
						dianyuanPl.setText(item.getDianyuanPl().trim() + hz);
						shebeiGl.setText(item.getDeviceGl().trim());
						dayPjSj.setText(item.getDayPjSj().trim() + hour);
						kehuMc.setText(item.getKehuMc().trim());
						kyjBh.setText(item.getKyjBianhao().trim());
						kyjpp.setText(item.getKyjPp().trim());
						rjbb.setText(item.getRjbb().trim());
						yjbb.setText(item.getYjbb().trim());
						sn.setText(item.getKyjSn().trim());
						if (!"".equals(item.getStatus())) {
							if ("报警状态".equals(item.getStatus().trim().substring(0,4))) {
								bjText.setText(item.getStatus().substring(5, item.getStatus().length()));
							} else if ("预警状态".equals(item.getStatus().trim().substring(0,4))) {
								yjText.setText(item.getStatus().substring(5, item.getStatus().length()));
							}
						}
					}

				} else {
					Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
			}
		}

	}

}
