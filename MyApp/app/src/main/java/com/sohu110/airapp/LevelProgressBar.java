package com.sohu110.airapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 对平均速度的动态展示；上方带对速度的评级，如小车、火箭等；可以进度条为整体参数 也可以以上传或者下载速度为传入参数
 * 
 * @author hezexi
 * 
 */
public class LevelProgressBar extends View {

	private Paint paint;
	private Drawable progressBg;
	private Drawable progressDrawable;
	private Drawable netSpeedBg;
	private Drawable netSpeedIcon;

	private int progress = 0;
	private int toProgress = 0;

	public static final int SLEEP_TIME = 10;

	private int smallpicWidth = 1;

	private static final int BG_IMG_LEFT_SPACE = 30;
	private static final int BG_IMG_BOTTOM_SPACE = 6;
	private static final int PROGRESS_INC = 1;
	private int progressbar_padding_left = 0;
	private int progressbar_padding_bottom = 0;

	private int progressHeight;
	private int textRectWidth;

	private Rect[] textRectArray;

	private String[] strArray = new String[] { "100Kb/s", "200Kb/s", "500Kb/s",
			"1Mb/s", "2Mb/s", "100Mb/s" };

	private float descent;
	private float textHeight;
	private int textColor = 0XFFFFFFFF;
	private float textSize = 22;

	public LevelProgressBar(Context context) {
		super(context);
		init(context);
	}

	public LevelProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LevelProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(0XFF000000);

		if (getWidth() <= 480) {
			textSize = 16;
		}

		paint.setTextSize(textSize);
		FontMetrics fm = paint.getFontMetrics();
		descent = fm.descent;
		textHeight = (int) Math.floor(fm.descent - fm.ascent);

		progressBg = this.getResources().getDrawable(
				R.drawable.progressbar_null);
		progressDrawable = this.getResources().getDrawable(
				R.drawable.progressbar_full);
		netSpeedBg = this.getResources().getDrawable(R.drawable.netspeed_level);
		netSpeedIcon = this.getResources().getDrawable(
				R.drawable.netspeed_level_cars);
		textRectArray = new Rect[6];
		for (int i = 0; i < 6; i++) {
			textRectArray[i] = new Rect();
		}
	}

	/***
	 * 根据需要的宽度裁剪的进度条生成BitMap 宽度必须大于0
	 * 
	 * @param drawable
	 * @param picwidth
	 * @return
	 */
	private Bitmap drawable2progress(Drawable drawable, int picwidth, int height) {
		Bitmap bigpic;
		Bitmap smallpic;
		bigpic = Bitmap
				.createBitmap(
						getWidth() - progressbar_padding_left * 2,
						height,
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bigpic);
		canvas.drawColor(Color.TRANSPARENT);
		drawable.draw(canvas);
		if (picwidth >= getWidth())
			return bigpic;
		smallpic = Bitmap.createBitmap(bigpic, 0, 0, picwidth, height);
		return smallpic;

	}

	/***
	 * 生成速度级别背景BitMap
	 * 
	 * @param drawable
	 * @param fromX
	 * @return
	 */
	private Bitmap drawable2SpeedBg(Drawable drawable) {
		Bitmap bigpic;
		bigpic = Bitmap
				.createBitmap(
						drawable.getMinimumWidth(),
						drawable.getMinimumHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bigpic);
		canvas.drawColor(Color.TRANSPARENT);
		drawable.draw(canvas);
		return bigpic;

	}

	/***
	 * 根据progress裁剪获取车辆图片
	 * 
	 * @param drawable
	 * @return
	 */
	private Bitmap drawable2SpeedCar(Drawable drawable) {
		Bitmap bigpic;
		Bitmap smallpic;
		bigpic = Bitmap
				.createBitmap(
						drawable.getMinimumWidth(),
						drawable.getMinimumHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bigpic);
		canvas.drawColor(Color.TRANSPARENT);
		drawable.draw(canvas);
		int level = 0;
		if (progress == 0) {
			level = 0;
		} else if (progress < 16) {
			level = 2;
		} else if (progress < 32) {
			level = 3;
		} else if (progress < 48) {
			level = 4;
		} else if (progress < 64) {
			level = 5;
		} else if (progress < 80) {
			level = 6;
		} else if (progress < 90) {
			level = 7;
		} else {
			level = 8;
		}
		int cutX = 0;
		if (level > 0)
			cutX = drawable.getMinimumHeight() * level;
		if (cutX + drawable.getMinimumHeight() > drawable.getMinimumWidth()) {
			cutX = drawable.getMinimumWidth() - drawable.getMinimumHeight();
		}
		smallpic = Bitmap.createBitmap(bigpic, cutX, 0,
				drawable.getMinimumHeight(), drawable.getMinimumHeight());
		return smallpic;

	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint smallPicPaint = new Paint();
		progressHeight = (int) ((float) getHeight() / 7 * 5);
		if (progressDrawable != null && netSpeedBg != null
				&& progressBg != null) {
			progressHeight = progressBg.getIntrinsicHeight();
			// 居中
			float spaceUpDown = getHeight() - progressHeight - 2 * textHeight;
			if (spaceUpDown > 0) {
				progressHeight += spaceUpDown / 2;
			}
			progressBg.setBounds(new Rect(0, 0, getWidth(), progressHeight));
			progressBg.draw(canvas);

			int progress_height = progressDrawable.getMinimumHeight();
			int netSpeed_width = netSpeedBg.getMinimumWidth();
			int netSpeed_height = netSpeedBg.getMinimumHeight();
			progressbar_padding_left = BG_IMG_LEFT_SPACE * progress_height / 15;
			progressbar_padding_bottom = BG_IMG_BOTTOM_SPACE * progress_height
					/ 15;

			progressDrawable.setBounds(0, 0, getWidth()
					- progressbar_padding_left * 2, progress_height);
			netSpeedBg.setBounds(0, 0, netSpeed_width, netSpeed_height);
			netSpeedIcon.setBounds(0, 0, netSpeedIcon.getMinimumWidth(),
					netSpeedIcon.getMinimumHeight());

			smallpicWidth = (int) ((getWidth() - progressbar_padding_left * 2)
					* (float) progress / 100);

			int speedFromX = smallpicWidth - netSpeed_width / 2;

			if (smallpicWidth > 0) {
				Bitmap proBitmap = drawable2progress(progressDrawable,
						smallpicWidth, progressHeight);
				Bitmap speedBitmap = drawable2SpeedBg(netSpeedBg);
				Bitmap speedCarBitmap = drawable2SpeedCar(netSpeedIcon);
				canvas.drawBitmap(speedBitmap, progressbar_padding_left
						+ speedFromX, progressHeight
						- progressbar_padding_bottom - progress_height
						- netSpeed_height, smallPicPaint);
				canvas.drawBitmap(proBitmap, progressbar_padding_left,
						progressHeight - progressbar_padding_bottom
								- progress_height, smallPicPaint);
				canvas.drawBitmap(speedCarBitmap, progressbar_padding_left
						+ speedFromX + 19 * progress_height / 15,
						progressHeight - progressbar_padding_bottom
								- progress_height - netSpeed_height,
						smallPicPaint);
			}
		}

		textRectWidth = getWidth() / 6;
		float textTop = 0;
		float textLeft = 0;
		try {
			for (int i = 0; i < 6; i++) {
				textRectArray[i].left = textRectWidth * i;
				textRectArray[i].top = progressHeight;
				textRectArray[i].right = textRectWidth * (i + 1);
				textRectArray[i].bottom = getHeight();
				paint.setColor(textColor);
				textTop = textRectArray[i].bottom
						- (textRectArray[i].height() - textHeight) / 2
						- descent;
				textTop = progressHeight + textHeight;
				textLeft = textRectArray[i].left
						+ (textRectArray[i].width() - paint
								.measureText(strArray[i])) / 2;
//				canvas.drawText(strArray[i], textLeft, textTop, paint);//暂时不显示下面的字
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		bufferChange();
	}

	/***
	 * 缓冲进度的变化
	 */
	private void bufferChange() {
		if (progress != toProgress) {
			if (progress < toProgress) {
				progress += PROGRESS_INC;
				if (progress > toProgress)
					progress = toProgress;
			} else {
				// progress -= PROGRESS_INC;
				// if (progress < toProgress)
				progress = toProgress;
			}
			postInvalidate();
		}
	}

	public int getProgress() {
		return progress;
	}

	/***
	 * 下载速度格式输入，单位kbps
	 */
	public void setDownSpeed(int speed, String[] strArray) {
		int temp = 0;
		this.strArray = strArray;
		if (speed >= 10240) {
			temp = 90 + (speed - 10240) * 10 / 92160;
		} else if (speed >= 2048) {
			temp = 80 + (speed - 2048) * 10 / 8192;
		} else if (speed > 1024) {
			temp = 64 + (speed - 1024) * 16 / 1024;
		} else if (speed > 500) {
			temp = 48 + (speed - 500) * 16 / 524;
		} else if (speed > 200) {
			temp = 32 + (speed - 200) * 16 / 300;
		} else {
			temp = speed * 32 / 200;
		}
		setProgress(temp);
	}

	/***
	 * 上传速度格式输入，单位kbps
	 */
	public void setUpSpeed(int speed, String[] strArray) {
		int temp = 0;
		this.strArray = strArray;
		if (speed >= 800) {
			temp = 80 + (speed - 800) * 20 / 101600;
		} else if (speed > 200) {
			temp = 64 + (speed - 200) * 16 / 600;
		} else if (speed > 100) {
			temp = 48 + (speed - 100) * 16 / 100;
		} else if (speed > 50) {
			temp = 32 + (speed - 50) * 16 / 50;
		} else if (speed > 10) {
			temp = 16 + (speed - 10) * 16 / 40;
		} else {
			temp = speed * 16 / 10;
		}
		setProgress(temp);
	}

	public void setProgress(int currentProgress) {
		if (currentProgress < 0) {
			toProgress = 0;
		} else if (currentProgress < 100) {
			toProgress = currentProgress;
		} else
			toProgress = 100;

		postInvalidate();
	}

}
