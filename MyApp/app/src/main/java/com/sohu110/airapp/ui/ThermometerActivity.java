package com.sohu110.airapp.ui;

import android.os.Bundle;

import com.sohu110.airapp.R;

public class ThermometerActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);

        setTitle("节能之星");
//
//        ImageView infoOperatingIV = (ImageView)findViewById(R.id.infoOperating);
//
//        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.tip);
//        LinearInterpolator lin = new LinearInterpolator();
//        operatingAnim.setInterpolator(lin);
//
//        if (operatingAnim != null) {
//            infoOperatingIV.startAnimation(operatingAnim);
//        }
    }



//    private Button anim;
//
//    private Button color;
//
//    private Button icon;
//
//    private Dialog mDialog;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        initPage();
//        setClickListener();
//    }
//
//    private void initPage()
//    {
//        anim = (Button) findViewById(R.id.anim);
//        color = (Button) findViewById(R.id.color);
//        icon = (Button) findViewById(R.id.icon);
//    }
//
//    private void setClickListener()
//    {
//        anim.setOnClickListener(this);
//        color.setOnClickListener(this);
//        icon.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View view)
//    {
//        switch (view.getId())
//        {
//            case R.id.anim:
//                showRoundProcessDialog(this, R.layout.loading_process_dialog_anim);
//                break;
//            case R.id.color:
//                showRoundProcessDialog(this, R.layout.loading_process_dialog_color);
//                break;
//            case R.id.icon:
//                showRoundProcessDialog(this, R.layout.loading_process_dialog_icon);
//                break;
//            default:
//                break;
//        }
//    }
//
//    public void showRoundProcessDialog(Context mContext, int layout)
//    {
//        OnKeyListener keyListener = new OnKeyListener()
//        {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
//            {
//                if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_SEARCH)
//                {
//                    return true;
//                }
//                return false;
//            }
//        };
//
//        mDialog = new AlertDialog.Builder(mContext).create();
//        mDialog.setOnKeyListener(keyListener);
//        mDialog.show();
//        // 注意此处要放在show之后 否则会报异常
//        mDialog.setContentView(layout);
//    }


}