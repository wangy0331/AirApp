package com.sohu110.airapp.ui.device;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.sohu110.airapp.view.HorizontalProgressBarWithNumber;

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
	//排气温度
	private TextView paiqiTemp;
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

	//记录编号
//	private TextView jlbh;

	private String dianliu = "A";
	private String wendu = "℃";
	private String mpa = "MPa";
	private String qws = "kWh";
	private String dianya = "V";
	private String hz = "Hz";
	private String gl = "kW";
	private String hour = "h";
	private String lv = "%";

	private static String GUID = "guid";

	//一分钟内刷新表示(trun---能刷新    false---不能刷新)
	private boolean refresh = true;

	//是否退出fragment了
	private boolean isCancel = false;

	private HorizontalProgressBarWithNumber mProgressBar;

	//倒计时
	private CountDownTimer countDown;

	//进度条
	private CountDownTimer countDown1;

	private SwipeRefreshLayout mSwipeRefreshLayout;

	private String guid;

	//时间变量
	private String lshbs = "";

	//离线表示（true-表示离线，false-表示不离线）
	private boolean lixianTs = false;

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

		countDown = new CountDownTimer(11000, 2000) {

			@Override
			public void onTick(long millisUntilFinished) {
				new DeviceDetailJubuTask(guid).execute();
				Log.e("time", String.valueOf(millisUntilFinished / 1000));
				refresh = false;

//				mProgressBar.setProgress((int) (millisUntilFinished/1000));

			}

			/** 倒计时结束后在这里实现activity跳转  */
			@Override
			public void onFinish() {
				Log.e("time", "stop");
				refresh = true;
			}
		};



		countDown1 = new CountDownTimer(11000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
//				new DeviceDetailJubuTask(guid).execute();
//				Log.e("time", String.valueOf(millisUntilFinished / 1000));
//				refresh = false;
				int progress = mProgressBar.getProgress();

				mProgressBar.setMax(10);

				mProgressBar.setProgress(++progress);

			}

			/** 倒计时结束后在这里实现activity跳转  */
			@Override
			public void onFinish() {
				Log.e("time", "stop");
				refresh = true;
			}
		};

	}


	@Override
	public void onStart() {
		super.onStart();
//		new DeviceDetailTask(guid).execute();
		//局部刷新
		if (refresh) {
			new DeviceDetailTask(guid).execute();
		}
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
		paiqiTemp = (TextView) view.findViewById(R.id.jitou_temp);
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
		dayPjSj.setVisibility(View.GONE);
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
//		jlbh = (TextView) view.findViewById(R.id.jlbh);

		mProgressBar = (HorizontalProgressBarWithNumber) view.findViewById(R.id.id_progressbar01);

		mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.detail_list_refresh);

//		if (refresh) {
			mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
//				new DeviceDetailTask(guid).execute();
					Log.e("refresh", String.valueOf(refresh));
					//局部刷新
					if (refresh) {
						new DeviceBiaoshiTask(guid).execute();
						mProgressBar.setProgress(0);
					} else {
						mSwipeRefreshLayout.setRefreshing(true);
						mSwipeRefreshLayout.setRefreshing(false);
					}
				}
			});

		return view;
	}

	@Override
	public void onStop() {
		super.onStop();
		try {
			countDown.cancel();
			countDown1.cancel();
			isCancel = true;
			Log.e("onStop", "close");
		} catch (Exception e) {
//			Log.e("time", "close");
		}
	}

	@Override
	public void onPause() {
		super.onStop();
		try {
			countDown.cancel();
			countDown1.cancel();
			Log.e("onPause", "close");
		} catch (Exception e) {
			Log.e("time", "close");
		}
	}

	/**
	 * 获取详情
	 */
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



						if (item.getDeviceGl() != null) {
							String current = item.getDeviceGl().trim();
							dashBoardView.setCurrent1(Double.valueOf(current.substring(0, current.indexOf("K"))));
							dashBoardView.setCurrent2(Double.valueOf(current.substring(0, current.indexOf("K"))));
						}


						if (item.getZjdl() != null) {
							dashBoardView.setSpeed(Integer.valueOf(item.getZjdl().trim()));
						}
						if (item.getAirTemp() != null) {
							dashBoardTempView.setSpeed(Integer.valueOf(item.getAirTemp().trim()));
						}
						if (item.getAirPress() != null) {
							dashBoardPressView.setSpeed(Float.valueOf(item.getAirPress().trim()));
						}

//						dashBoardView.setSpeed(Integer.valueOf(item.getZjdl().trim()));
//						dashBoardTempView.setSpeed(Integer.valueOf(item.getAirTemp().trim()));
//						dashBoardPressView.setSpeed(Float.valueOf(item.getAirPress().trim()));

//						if (item.getNowTime() != null) {
//							if (!lshbs.equals(item.getJlbh())) {
//								caijiTime.setText(item.getNowTime().trim());
//							}
//						}
						if (item.getDianjiSta() != null) {
							dianjiSta.setText(item.getDianjiSta().trim());
						}
						if (item.getAirSta() != null) {
							kyjSta.setText(item.getAirSta().trim());
						}
						if (item.getFengjiSta1() != null) {
							fengji1Sta.setText(item.getFengjiSta1().trim());
						}
						if (item.getFengjiSta2() != null) {
							fengji2Sta.setText(item.getFengjiSta2().trim());
						}
						if (item.getAirPress() != null) {
							paiqiPress.setText(item.getAirPress().trim() + mpa);
						}
						if (item.getAirTemp() != null) {
							paiqiTemp.setText(item.getAirTemp().trim() + wendu);
						}
						if (item.getZjdl() != null) {
							zhujiDl.setText(item.getZjdl().trim() + dianliu);
						}
						if (item.getDianjiTemp() != null) {
							dianjiTemp.setText(item.getDianjiTemp().trim() + wendu);
						}
						if (item.getModleTemp() != null) {
							dianqiTemp.setText(item.getModleTemp().trim() + wendu);
						}
						if (item.getJienengLv() != null) {
							jienengLv.setText(item.getJienengLv().trim() + lv);
						}
						if (item.getJienengDianneng() != null) {
							jienengDn.setText(item.getJienengDianneng().trim() + qws);
						}
						if (item.getSjHaoneng() != null) {
							shijiHn.setText(item.getSjHaoneng().trim() + qws);
						}
						if (item.getDeviceSta() != null) {
							shebeiSta.setText(item.getDeviceSta().trim());
						}
//						if (item.getTongxunSta() != null) {
//							tongxunSta.setText(item.getTongxunSta().trim());
//						}

						if (item.getTongxunSta() != null) {
							tongxunSta.setText(item.getTongxunSta().trim());
							if ("离线".equals(item.getTongxunSta().trim())) {
								lixianTs = true;
							}
						}

						if (item.getZhujiDl1() != null) {
							zhujiDl1.setText(item.getZhujiDl1().trim() + dianliu);
						}
						if (item.getZhujiDl2() != null) {
							zhujiDl2.setText(item.getZhujiDl2().trim() + dianliu);
						}
						if (item.getZhujiDl3() != null) {
							zhujiDl3.setText(item.getZhujiDl3().trim() + dianliu);
						}
						if (item.getFengji1Dl1() != null) {
							fengji1Dl1.setText(item.getFengji1Dl1().trim() + dianliu);
						}
						if (item.getFengji1Dl2() != null) {
							fengji1Dl2.setText(item.getFengji1Dl2().trim() + dianliu);
						}
						if (item.getFengji1Dl3() != null) {
							fengji1Dl3.setText(item.getFengji1Dl3().trim() + dianliu);
						}
						if (item.getFengji2Dl1() != null) {
							fengji2Dl1.setText(item.getFengji2Dl1().trim() + dianliu);
						}
						if (item.getFengji2Dl2() != null) {
							fengji2Dl2.setText(item.getFengji2Dl2().trim() + dianliu);
						}
						if (item.getFengji2Dl3() != null) {
							fengji2Dl3.setText(item.getFengji2Dl3().trim() + dianliu);
						}
						if (item.getCurrentDy() != null) {
							shebeiDy.setText(item.getCurrentDy().trim() + dianya);
						}
						if (item.getJiazaiCs() != null) {
							jiazaiCs.setText(item.getJiazaiCs().trim());
						}
						if (item.getLeijiYx() != null) {
							leijiYx.setText(item.getLeijiYx().trim() + hour);
						}
						if (item.getLeijiJz() != null) {
							leijiJz.setText(item.getLeijiJz().trim() + hour);
						}
						if (item.getBenciYx() != null) {
							benciYx.setText(item.getBenciYx().trim() + hour);
						}
						if (item.getBenciJz() != null) {
							benciJz.setText(item.getBenciJz().trim() + hour);
						}
						if (item.getDianyuanPl() != null) {
							dianyuanPl.setText(item.getDianyuanPl().trim() + hz);
						}
						if (item.getDeviceGl() != null) {
							shebeiGl.setText(item.getDeviceGl().trim());
						}
						if (item.getDayPjSj() != null) {
							dayPjSj.setText(item.getDayPjSj().trim() + hour);
						}
						if (item.getKehuMc() != null) {
							kehuMc.setText(item.getKehuMc().trim());
						}
						if (item.getKyjBianhao() != null) {
							kyjBh.setText(item.getKyjBianhao().trim());
						}
						if (item.getKyjPp() != null) {
							kyjpp.setText(item.getKyjPp().trim());
						}
						if (item.getRjbb() != null) {
							rjbb.setText(item.getRjbb().trim());
						}
						if (item.getYjbb() != null) {
							yjbb.setText(item.getYjbb().trim());
						}
						if (item.getJqQiSn() != null) {
							sn.setText(item.getJqQiSn().trim());
						}


						if (item.getStatus() != null) {
							if (!"".equals(item.getStatus())) {

								if ("无预警".equals(item.getStatus())) {
									bjText.setText("");
									yjText.setText(item.getStatus());
								} else if ("无报警".equals(item.getStatus())) {
									bjText.setText(item.getStatus());
									yjText.setText("");
								} else if ("无".equals(item.getStatus())) {
									bjText.setText("");
									yjText.setText("");
								}else {
									if ("报".equals(item.getStatus().trim().substring(0,2))) {
										bjText.setText(item.getStatus());
									} else if ("预".equals(item.getStatus().trim().substring(0,2))) {
										yjText.setText(item.getStatus());
									}
								}
							}
						}

						//局部刷新
						if (!lixianTs) {
							new DeviceBiaoshiTask(guid).execute();
						}
					}

				} else {
					if (!isCancel) {
						Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				if (!isCancel) {
					Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
				}
			}
		}

	}


	/**
	 * 局部刷新
	 */
	class DeviceDetailJubuTask extends AsyncTask<Void, Void, Result<DeviceDetail>> {

		private String jiqiSn;

		public DeviceDetailJubuTask(String guid) {
			jiqiSn = guid;
		}

		@Override
		protected Result<DeviceDetail> doInBackground(Void... params) {
			try{
				return ServiceCenter.getDetailJubu(jiqiSn);
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

						dashBoardView.setSpeed(Integer.valueOf(item.getZjdl().trim()));
						dashBoardTempView.setSpeed(Integer.valueOf(item.getAirTemp().trim()));
						dashBoardPressView.setSpeed(Float.valueOf(item.getAirPress().trim()));


						if (item.getStatus() != null) {
							if (!"".equals(item.getStatus())) {

								if ("无预警".equals(item.getStatus())) {
									bjText.setText("");
									yjText.setText(item.getStatus());
								} else if ("无报警".equals(item.getStatus())) {
									bjText.setText(item.getStatus());
									yjText.setText("");
								} else if ("无".equals(item.getStatus())) {
									bjText.setText("");
									yjText.setText("");
								}else {
									if ("报".equals(item.getStatus().trim().substring(0,2))) {
										bjText.setText(item.getStatus());
									} else if ("预".equals(item.getStatus().trim().substring(0,2))) {
										yjText.setText(item.getStatus());
									}
								}
							}
						}

						if (item.getJqQiSn() != null) {
							sn.setText(item.getJqQiSn().trim());
						}
						if (item.getAirPress() != null) {
							paiqiPress.setText(item.getAirPress().trim() + mpa);
						}
						if (item.getZjdl() != null) {
							zhujiDl.setText(item.getZjdl().trim() + dianliu);
						}
						if (item.getNowTime() != null) {
							if (lixianTs) {
								caijiTime.setText(item.getZxbsj().trim());
							} else {
								if (!lshbs.equals(item.getJlbh())) {
									caijiTime.setText(item.getNowTime().trim());
								}
							}
						}
						if (item.getAirTemp() != null) {
							paiqiTemp.setText(item.getAirTemp().trim() + wendu);
						}
						if (item.getDianjiTemp() != null) {
							dianjiTemp.setText(item.getDianjiTemp().trim() + wendu);
						}
						if (item.getDianjiSta() != null) {
							dianjiSta.setText(item.getDianjiSta().trim());
						}
						if (item.getAirSta() != null) {
							kyjSta.setText(item.getAirSta().trim());
						}
						if (item.getFengjiSta1() != null) {
							fengji1Sta.setText(item.getFengjiSta1().trim());
						}
						if (item.getFengjiSta2() != null) {
							fengji2Sta.setText(item.getFengjiSta2().trim());
						}


						if (item.getFengji1Dl1() != null) {
							fengji1Dl1.setText(item.getFengji1Dl1());
						}

						if (item.getFengji1Dl2() != null) {
							fengji1Dl2.setText(item.getFengji1Dl2());
						}

						if (item.getFengji1Dl3() != null) {
							fengji1Dl3.setText(item.getFengji1Dl3());
						}

						if (item.getFengji2Dl1() != null) {
							fengji2Dl1.setText(item.getFengji2Dl1());
						}

						if (item.getFengji2Dl2() != null) {
							fengji2Dl2.setText(item.getFengji2Dl2());
						}

						if (item.getFengji2Dl3() != null) {
							fengji2Dl3.setText(item.getFengji2Dl3());
						}

						if (item.getZhujiDl1() != null) {
							zhujiDl1.setText(item.getZhujiDl1());
						}

						if (item.getZhujiDl2() != null) {
							zhujiDl2.setText(item.getZhujiDl2());
						}

						if (item.getZhujiDl3() != null) {
							zhujiDl3.setText(item.getZhujiDl3());
						}

						if (item.getTongxunSta() != null) {
							tongxunSta.setText(item.getTongxunSta().trim());
							if ("离线".equals(item.getTongxunSta().trim())) {
								lixianTs = true;
							}
						}

					}

				} else {
					if (!isCancel) {
						Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
						countDown.cancel();
					}
				}
			} else {
				if (!isCancel) {
					Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
					countDown.cancel();
				}
			}
		}

	}

	/**
	 * 判断是否局部刷新
	 */
	class DeviceBiaoshiTask extends AsyncTask<Void, Void, Result<DeviceDetail>> {

		private String jiqiSn;

		public DeviceBiaoshiTask(String guid) {
			jiqiSn = guid;
		}

		@Override
		protected Result<DeviceDetail> doInBackground(Void... params) {
			try{
				return ServiceCenter.getDetailStatus(jiqiSn);
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
				//如果code返回是1  就表示可以刷新
				if (result.isSuceed()) {

					Log.e("code", String.valueOf(result.getCode()));

					countDown.start();
					countDown1.start();


				}
				else {
					if (!isCancel) {
						Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
			} else {
				if (!isCancel) {
					Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
				}
			}
		}

	}



}
