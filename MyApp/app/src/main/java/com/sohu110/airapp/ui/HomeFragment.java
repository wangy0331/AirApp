package com.sohu110.airapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sohu110.airapp.LibApplication;
import com.sohu110.airapp.R;
import com.sohu110.airapp.bean.CarouselData;
import com.sohu110.airapp.bean.Device;
import com.sohu110.airapp.bean.Result;
import com.sohu110.airapp.cache.CacheCenter;
import com.sohu110.airapp.service.ServiceCenter;
import com.sohu110.airapp.ui.baojing.BaojingListActivity;
import com.sohu110.airapp.ui.device.DeviceListActivity;
import com.sohu110.airapp.ui.device.DeviceRegisterActivity;
import com.sohu110.airapp.ui.device.DevicerReformActivity;
import com.sohu110.airapp.ui.jieneng.EnergyActivity;
import com.sohu110.airapp.ui.weibao.WeibaoListActivity;
import com.sohu110.airapp.ui.weixiu.WeixiuListActivity;
import com.sohu110.airapp.ui.yujing.YujingListActivity;
import com.sohu110.airapp.utils.Carousel;
import com.sohu110.airapp.widget.LibToast;
import com.sohu110.airapp.widget.LoadProcessDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 * Created by Aaron on 2016/3/28.
 */
public class HomeFragment extends Fragment {

    private Carousel c;
    private List<CarouselData> data;

    // view
    private View view;
    private ImageButton loginBtn;
    private ImageButton settingBtn;
    private TextView mTextView;
    private ViewPager mViewPager;
    private ViewGroup mViewPagerContainer;
    private HomeCycleViewAdapter mCycleViewAdapter;
    private Window window;
    private LinearLayout sbzcBtn;
    private LinearLayout sblbBtn;
    private LinearLayout sbyjBtn;
    private LinearLayout sbbjBtn;
    private LinearLayout wbxxBtn;
    private LinearLayout wxyjBtn;
    private LinearLayout kyjscBtn;
    private LinearLayout kyjwBtn;
    private LinearLayout ysjwBtn;
    private LinearLayout jnzxBtn;
    private LinearLayout jnzjBtn;
    private LinearLayout sbgzBtn;
    private TextView mLoginText;
    private ImageView servicePhone;
    private ImageView mDaySign;
    private ImageButton mSignOut;

    //测试
    private Button testBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        loginBtn = (ImageButton) view.findViewById(R.id.btn_login);
        mSignOut = (ImageButton) view.findViewById(R.id.btn_out);
//        settingBtn = (ImageButton) view.findViewById(R.id.btn_setting);
        mTextView = (TextView) view.findViewById(R.id.test);
        sbzcBtn = (LinearLayout) view.findViewById(R.id.btn_sbzc);
        sblbBtn = (LinearLayout) view.findViewById(R.id.btn_sblb);
        sbyjBtn = (LinearLayout) view.findViewById(R.id.btn_sbyj);
        sbbjBtn = (LinearLayout) view.findViewById(R.id.btn_sbbj);
        wbxxBtn = (LinearLayout) view.findViewById(R.id.btn_wbxx);
        wxyjBtn = (LinearLayout) view.findViewById(R.id.btn_wxyj);
        servicePhone = (ImageView) view.findViewById(R.id.service_phone);
        kyjscBtn = (LinearLayout) view.findViewById(R.id.btn_kyjsc);
        kyjwBtn = (LinearLayout) view.findViewById(R.id.btn_kyjw);
        ysjwBtn = (LinearLayout) view.findViewById(R.id.btn_ysjw);
        jnzxBtn = (LinearLayout) view.findViewById(R.id.btn_jnzx);
        sbgzBtn = (LinearLayout) view.findViewById(R.id.btn_sbgz);
        mDaySign = (ImageView) view.findViewById(R.id.day_Sign);
        jnzjBtn = (LinearLayout) view.findViewById(R.id.btn_jnzj);
        sbgzBtn = (LinearLayout) view.findViewById(R.id.btn_sbgz);

//        mLoginText = (TextView) view.findViewById(R.id.logon_text);

//        testBtn = (Button) view.findViewById(R.id.test);



        mSignOut.setOnClickListener(click);

        mTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        sbzcBtn.setOnClickListener(click);
        sblbBtn.setOnClickListener(click);
        sbyjBtn.setOnClickListener(click);
        sbbjBtn.setOnClickListener(click);
        wbxxBtn.setOnClickListener(click);
        wxyjBtn.setOnClickListener(click);
        servicePhone.setOnClickListener(click);
        kyjscBtn.setOnClickListener(click);
        kyjwBtn.setOnClickListener(click);
        ysjwBtn.setOnClickListener(click);
        jnzxBtn.setOnClickListener(click);
        mDaySign.setOnClickListener(click);
        jnzjBtn.setOnClickListener(click);
        sbgzBtn.setOnClickListener(click);
        loginBtn.setOnClickListener(click);
//        settingBtn.setOnClickListener(click);

//        testBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("123", "123");
//                startActivity(new Intent(getActivity(), DeviceDetailActivity.class));
//            }
//        });


       Handler handler = new Handler();


        initControl();

        data = new ArrayList<CarouselData>();
        String[] urls = new String[]{
                "http://222.92.237.43/images/m_guanggao/1.png",
                "http://222.92.237.43/images/m_guanggao/2.png",
                "http://222.92.237.43/images/m_guanggao/3.png",
                "http://222.92.237.43/images/m_guanggao/4.png",
                "http://222.92.237.43/images/m_guanggao/5.png"
        };
        for (int i = 0; i < urls.length; i++) {
            CarouselData d = new CarouselData();
            d.setImage(urls[i]);
            d.setTitle("测试tile" + i);
            d.setId(i);
            data.add(d);
        }
        c.startup(data);

        return view;
    }

    private void initControl() {
        c = (Carousel) view.findViewById(R.id.crs);
        c.setCallback(new Carousel.ClickCallback() {
            @Override
            public void perform(int id, int position) throws Throwable {

//                Toast.makeText(getContext(), "id:" + id + "position" + position + "title:" + data.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        if (CacheCenter.getCurrentUser() != null) {
//            //已登录
//            loginBtn.setImageDrawable(getResources().getDrawable(R.drawable.sign_out));
//        } else {
//            //未登录
//            loginBtn.setImageDrawable(getResources().getDrawable(R.drawable.login));
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (CacheCenter.getCurrentUser() != null) {
            loginBtn.setVisibility(View.GONE);
            mSignOut.setVisibility(View.VISIBLE);
        } else {
            loginBtn.setVisibility(View.VISIBLE);
            mSignOut.setVisibility(View.GONE);
        }
    }

    OnClickListener click = new OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    LoginActivity.open(getActivity());
                    break;
//                case R.id.btn_setting:
//                    showPopupWindow(v);
//                    break;
                case R.id.btn_out:

                    // 设置对话框标题
                    new android.app.AlertDialog.Builder(getActivity()).setTitle(R.string.tishi)

                            // 设置显示的内容
                            .setMessage(R.string.sign_out)

                                    // 添加确定按钮
                            .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {

                                // 确定按钮的响应事件
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CacheCenter.removeCurrentUser();
                                    loginBtn.setVisibility(View.VISIBLE);
                                    mSignOut.setVisibility(View.GONE);
                                }
                                // 添加返回按钮
                            }).setNegativeButton(R.string.btnReturn, null).show();// 在按键响应事件中显示此对话框
                    break;
                case R.id.btn_sbzc:
                    if (CacheCenter.getCurrentUser() != null) {
                        startActivity(new Intent(getActivity(), DeviceRegisterActivity.class));
                    } else {
                        LoginActivity.open(getActivity());
                        loginBtn.setVisibility(View.GONE);
                        mSignOut.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.btn_sblb:
                    if (CacheCenter.getCurrentUser() != null) {
                        startActivity(new Intent(getActivity(), DeviceListActivity.class));
                    } else {
                        LoginActivity.open(getActivity());
                    }
                    break;
                case R.id.btn_sbyj:
                    if (CacheCenter.getCurrentUser() != null) {
                        startActivity(new Intent(getActivity(), YujingListActivity.class));
                    } else {
                        LoginActivity.open(getActivity());
                    }
                    break;
                case R.id.btn_sbbj:
                    if (CacheCenter.getCurrentUser() != null) {
                        startActivity(new Intent(getActivity(), BaojingListActivity.class));
                    } else {
                        LoginActivity.open(getActivity());
                    }
                    break;
                case R.id.btn_wbxx:
                    if (CacheCenter.getCurrentUser() != null) {
                        startActivity(new Intent(getActivity(), WeibaoListActivity.class));
                    } else {
                        LoginActivity.open(getActivity());
                    }
                    break;
                case R.id.btn_wxyj:
                    if (CacheCenter.getCurrentUser() != null) {
                        startActivity(new Intent(getActivity(), WeixiuListActivity.class));
                    } else {
                        LoginActivity.open(getActivity());
                    }
                    break;
                case R.id.btn_jnzj:
                    startActivity(new Intent(getActivity(), EnergyActivity.class));
                    break;
                case R.id.btn_sbgz:
                    startActivity(new Intent(getActivity(), DevicerReformActivity.class));
                    break;
                case R.id.day_Sign:
                    if (CacheCenter.getCurrentUser() != null) {
                        if (LibApplication.getInstance().isNetworkConnected()) {
                            new DaySignTask().execute();
                        } else {
                            LibToast.show(getActivity(),R.string.not_network);
                        }
                    } else {
                        LoginActivity.open(getActivity());
                    }
                    break;
                case R.id.service_phone:
                    showDialog();
                    break;
                case R.id.btn_kyjsc:
                    Intent intent3 = new Intent();
                    intent3.setAction("android.intent.action.VIEW");
                    Uri content_url3 = Uri.parse("http://www.71168.com/");
                    intent3.setData(content_url3);
                    startActivity(intent3);
                    break;
                case R.id.btn_kyjw:
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("http://www.51comp.com/");
                    intent.setData(content_url);
                    startActivity(intent);
                    break;
                case R.id.btn_ysjw:
                    Intent intent1 = new Intent();
                    intent1.setAction("android.intent.action.VIEW");
                    Uri content_url1 = Uri.parse("http://www.compressor.cn/");
                    intent1.setData(content_url1);
                    startActivity(intent1);
                    break;
                case R.id.btn_jnzx:
                    startActivity(new Intent(getActivity(), EnergyStarActivity.class));
                    break;
            }
        }

    };


    private void showDialog() {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.callPhone)
                .setMessage(R.string.service_phone).setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intentPhone = new Intent(Intent.ACTION_CALL,
                                Uri.parse("tel:" + getString(R.string.service_phone)));
                        startActivity(intentPhone);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();
    }


    private void showPopupWindow(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getContext()).inflate(
                R.layout.activity_pop_window, null);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                200, LayoutParams.WRAP_CONTENT, true);

        // 设置按钮的点击事件
        LinearLayout more = (LinearLayout) contentView.findViewById(R.id.more);
        more.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "button is pressed",
                        Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        LinearLayout login = (LinearLayout) contentView.findViewById(R.id.pop_login);

        mLoginText = (TextView) contentView.findViewById(R.id.logon_text);

        if (CacheCenter.getCurrentUser() != null) {
            mLoginText.setText("登出");
            login.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    CacheCenter.removeCurrentUser();
                    popupWindow.dismiss();
                }
            });
        } else {
            login.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    popupWindow.dismiss();
                }
            });
        }


        LinearLayout out = (LinearLayout) contentView.findViewById(R.id.pop_out);

        out.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置对话框标题
                new AlertDialog.Builder(getActivity()).setTitle(R.string.tishi)

                        // 设置显示的内容
                        .setMessage(R.string.sign_out)

                                // 添加确定按钮
                        .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {

                            // 确定按钮的响应事件
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                                popupWindow.dismiss();
                            }
                            // 添加返回按钮
                        }).setNegativeButton(R.string.btnReturn, null).show();// 在按键响应事件中显示此对话框
            }
        });

        popupWindow.setTouchable(true);

        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        backgroundAlpha(0.4f);

        //添加pop窗口关闭事件
        popupWindow.setOnDismissListener(new poponDismissListener());

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));

        
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view, 0, 0);

    }


    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     * @author cg
     *
     */
    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }



    class DaySignTask extends AsyncTask<Void, Void, Result<List<Device>>> {

        LoadProcessDialog mLoadDialog;

        public DaySignTask() {
            mLoadDialog = new LoadProcessDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadDialog.show();
        }

        @Override
        protected Result<List<Device>> doInBackground(Void... params) {
            try {
                return ServiceCenter.daySign();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Result<List<Device>> result) {
            super.onPostExecute(result);
            mLoadDialog.dismiss();
            if (result != null) {
                if (result.isSuceed()) {

                    Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), R.string.member_register_network, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
