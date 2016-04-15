package com.dz.airapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dz.airapp.R;
import com.dz.airapp.bean.CarouselData;
import com.dz.airapp.service.HttpService;
import com.dz.airapp.ui.device.DeviceDetailActivity;
import com.dz.airapp.ui.device.DeviceListActivity;
import com.dz.airapp.ui.device.DeviceRegisterActivity;
import com.dz.airapp.utils.Carousel;

import org.json.JSONObject;

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

    //测试
    private Button testBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        loginBtn = (ImageButton) view.findViewById(R.id.btn_login);
        settingBtn = (ImageButton) view.findViewById(R.id.btn_setting);
        mTextView = (TextView) view.findViewById(R.id.test);
        sbzcBtn = (LinearLayout) view.findViewById(R.id.btn_sbzc);
        sblbBtn = (LinearLayout) view.findViewById(R.id.btn_sblb);

        testBtn = (Button) view.findViewById(R.id.test);


        sbzcBtn.setOnClickListener(click);
        sblbBtn.setOnClickListener(click);

        loginBtn.setOnClickListener(click);
        settingBtn.setOnClickListener(click);

        testBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("123", "123");
                startActivity(new Intent(getActivity(), DeviceDetailActivity.class));
            }
        });



        Handler handler = new Handler();


        initControl();

        data = new ArrayList<CarouselData>();
        String[] urls = new String[]{
                "http://222.92.237.43/images/m_guanggao/1.png",
                "http://222.92.237.43/images/m_guanggao/2.png",
                "http://222.92.237.43/images/m_guanggao/3.png",
                "http://222.92.237.43/images/m_guanggao/4.png"
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

                Toast.makeText(getContext(), "id:" + id + "position" + position + "title:" + data.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initData() {

    }


    OnClickListener click = new OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    LoginActivity.open(getActivity());
                    Toast.makeText(getContext(),"登录", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_setting:
                    showPopupWindow(v);
                    Toast.makeText(getContext(),"设置", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_sbzc:
                    startActivity(new Intent(getActivity(), DeviceRegisterActivity.class));
                    break;
                case R.id.btn_sblb:
                    startActivity(new Intent(getActivity(), DeviceListActivity.class));
                    break;
            }
        }

    };

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

        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                popupWindow.dismiss();
            }
        });

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






    String jsonTest;


    private void sendRequestWithHttpClient() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
//                    String url = "http://222.92.237.43:1880/showdata.html";
                    String url = "http://222.92.237.43:1880/showdata.asq";
//                    String url = "http://222.92.237.43/air_ws/WS.asmx/sendSms";
                    JSONObject obj = new JSONObject();

//                    obj.put("key","");
//                    obj.put("userid","admin");
//                    obj.put("pwd","123");

                    obj.put("sqlcommand", "select * from Users order by UserID");
                    obj.put("databaseid", "test");

                    String response = HttpService.post(url, obj);

//                    String response = HttpService.sendMsg("18662181836");

//                    Log.e("aaron",response);
                    jsonTest = response;
//                    xiaohualist = JsonParser.parseJSONWithJSONObject(response);
//                    xiaohuaArray = new String[xiaohualist.size()];
//                    for (int a = 0; a < xiaohualist.size(); a++) {
//                        xiaohuaArray[a] = xiaohualist.get(a).getContent();
//                    }
                    myHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }



    Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mTextView.setText(jsonTest);




                    Log.e("xiancheng", "走起");
                    break;

                default:
                    break;
            }
        }
    };

}
