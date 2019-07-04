package com.damo.popularscreenguard.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import com.damo.popularscreenguard.BuildConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DateTimeView extends View {


    private Paint paint;
    private Context mContext;
    private String[] days,hours,minutes,seconds;
    private float mScale,secondDelta;
    private int dayIndex,hourIndex,minusIndex,secondIndex;
    private Paint mTextPaint,mTextPaint2;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix,matrix=new Matrix();
    private int mViewWidth=20;
    private String mName="万";
    private StringBuilder stringBuilder;
    private float f,f2;


    public DateTimeView(Context context) {
        this(context, null);
    }

    public DateTimeView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint();//初始化时分秒的画笔
        mContext = context;
        initData();
        init();
    }

    private void initData() {
        this.mScale = 0.5f;
        this.secondDelta = 0.0f;
        this.dayIndex = 0;
        this.days = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十", "三十一"};
        this.hourIndex = 0;
        this.hours = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
        this.minusIndex = 0;
        this.minutes = new String[]{"一分", "二分", "三分", "四分", "五分", "六分", "七分", "八分", "九分", "十分", "十一分", "十二分", "十三分", "十四分", "十五分", "十六分", "十七分", "十八分", "十九分", "二十分", "二十一分", "二十二分", "二十三分", "二十四分", "二十五分", "二十六分", "二十七分", "二十八分", "二十九分", "三十分", "三十一分", "三十二分", "三十三分", "三十四分", "三十五分", "三十六分", "三十七分", "三十八分", "三十九分", "四十分", "四十一分", "四十二分", "四十三分", "四十四分", "四十五分", "四十六分", "四十七分", "四十八分", "四十九分", "五十分", "五十一分", "五十二分", "五十三分", "五十四分", "五十五分", "五十六分", "五十七分", "五十八分", "五十九分", BuildConfig.FLAVOR};
        this.secondIndex = 0;
        this.seconds = new String[]{"一秒", "二秒", "三秒", "四秒", "五秒", "六秒", "七秒", "八秒", "九秒", "十秒", "十一秒", "十二秒", "十三秒", "十四秒", "十五秒", "十六秒", "十七秒", "十八秒", "十九秒", "二十秒", "二十一秒", "二十二秒", "二十三秒", "二十四秒", "二十五秒", "二十六秒", "二十七秒", "二十八秒", "二十九秒", "三十秒", "三十一秒", "三十二秒", "三十三秒", "三十四秒", "三十五秒", "三十六秒", "三十七秒", "三十八秒", "三十九秒", "四十秒", "四十一秒", "四十二秒", "四十三秒", "四十四秒", "四十五秒", "四十六秒", "四十七秒", "四十八秒", "四十九秒", "五十秒", "五十一秒", "五十二秒", "五十三秒", "五十四秒", "五十五秒", "五十六秒", "五十七秒", "五十八秒", "五十九秒", BuildConfig.FLAVOR};

    }

    private void init() {
        this.paint.setAntiAlias(true);//为时分秒的画笔设置参数
        this.paint.setColor(Color.WHITE);
        this.paint.setTextSize(50.0f);
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Calendar instance = Calendar.getInstance();
                DateTimeView.this.dayIndex = instance.get(Calendar.DAY_OF_MONTH) - 1;
                DateTimeView.this.hourIndex = instance.get(Calendar.HOUR) - 1;
                DateTimeView.this.minusIndex = instance.get(Calendar.MINUTE) - 1;
                DateTimeView.this.secondIndex = instance.get(Calendar.SECOND) - 1;
                DateTimeView.this.secondDelta = ((((float) (System.currentTimeMillis() % 1000)) * 1.0f) / 1000.0f) * 3.0f;
                if (DateTimeView.this.secondDelta > 0.6f) {
                    DateTimeView.this.secondDelta = 1.0f;
                }
                DateTimeView.this.postInvalidate();
            }
        }, 0, 16, TimeUnit.MILLISECONDS);

        mTextPaint = new Paint();//画中间的文字
        mTextPaint.setColor(Color.WHITE);//设置画笔的颜色
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);//描边加填充
        mTextPaint.setStrokeWidth(3);//设置画笔的宽度
        mTextPaint.setAntiAlias(true);//防锯齿，设置为true时,会损失一定的性能,使用时视情况而定
        mTextPaint.setStrokeCap(Paint.Cap.ROUND);//圆形  笔帽,就是画笔画出来的线的两端的样式
        mTextPaint.setStrokeJoin(Paint.Join.ROUND);//圆角   两线相交样式
        mTextPaint.setLetterSpacing(12);//设置字符间间距
        mTextPaint.setTextSize(dpToPx(70));//设置字符大小
        mTextPaint.setTextAlign(Paint.Align.CENTER);//居中  文本对齐方式
//        Typeface customFont = Typeface.createFromAsset(mContext.getAssets(), "yzqs.ttf");
        Typeface customFont=Typeface.DEFAULT;
        mTextPaint.setTypeface(customFont); //字体 Typeface.create(familyName, style)//加载自定义字体

        mViewWidth =dpToPx(100);


        //实现中间文字炫彩效果
        mLinearGradient = new LinearGradient(mViewWidth, 0, mViewWidth * 2, 0,
                new int[]{Color.WHITE, Color.GREEN, Color.CYAN, Color.BLUE, Color.RED, Color.GREEN, Color.WHITE}, null, Shader.TileMode.CLAMP);
        mTextPaint.setShader(mLinearGradient);
        mGradientMatrix = new Matrix();

        mTextPaint2 = new Paint();//画顶部的提示语
        mTextPaint2.setAntiAlias(true);//防锯齿，
        mTextPaint2.setColor(Color.WHITE);//设置画笔的颜色
        mTextPaint2.setStyle(Paint.Style.FILL_AND_STROKE);//描边加填充
        mTextPaint2.setTypeface(customFont);
        mTextPaint2.setTextAlign(Paint.Align.CENTER);
    }


    /**
     * 自定义view的测量方法
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //绘画顶部的提示语
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        mTextPaint2.setTextSize(dpToPx(25));//设置字符大小
        canvas.drawText("这玩意贼耗电", canvas.getWidth()/2, dpToPx(40), mTextPaint2);//画提示语
        mTextPaint2.setTextSize(dpToPx(45));//设置字符大小
        canvas.drawText("" + formatter.format(curDate) + "", canvas.getWidth() / 2, dpToPx(40) * 2 + 40, mTextPaint2);//画提示语


        //画姓氏
        canvas.drawText(mName, canvas.getWidth() / 2, getHeight() / 2 + dpToPx(26), mTextPaint);//画姓氏


        //画时分秒

        String[] strArr = this.hours;
        this.mScale = (((float) canvas.getWidth()) / 1080.0f) * 0.5f;
        float length = 360.0f / ((float) strArr.length);
        float f3 = 0.0f - (((float) this.hourIndex) * length);
        int i4 = 0;
        f = f3;
        for (String str222 : strArr) {
            matrix.reset();
            matrix.postTranslate(300.0f, 0.0f);
            matrix.postRotate(f, 0.0f, 0.0f);
            f += length;
            matrix.postTranslate((float) (getWidth() / 2), (float) (getHeight() / 2));
            f2 = this.mScale;
            matrix.postScale(f2, f2, (float) (getWidth() / 2), (float) (getHeight() / 2));
            canvas.setMatrix(matrix);
            this.paint.setColor(i4 == this.hourIndex ? Color.WHITE : -7829368);
            stringBuilder = new StringBuilder();
            stringBuilder.append(str222);
            stringBuilder.append("点");
            canvas.drawText(stringBuilder.toString(), 0.0f, 0.0f, this.paint);
            i4++;
        }
        strArr = this.minutes;
        length = 360.0f / ((float) strArr.length);
        f3 = 0.0f - (((float) this.minusIndex) * length);
        f = f3;
        i4 = 0;
        for (String str2222 : strArr) {
            matrix.reset();
            matrix.postTranslate(500.0f, 0.0f);
            matrix.postRotate(f, 0.0f, 0.0f);
            f += length;
            matrix.postTranslate((float) (getWidth() / 2), (float) (getHeight() / 2));
            f2 = this.mScale;
            matrix.postScale(f2, f2, (float) (getWidth() / 2), (float) (getHeight() / 2));
            canvas.setMatrix(matrix);
            this.paint.setColor(i4 == this.minusIndex ? Color.WHITE : -7829368);
            canvas.drawText(str2222, 0.0f, 0.0f, this.paint);
            i4++;
        }
        strArr = this.seconds;
        float length3 = 360.0f / ((float) strArr.length);
        length = (0.0f - (((float) this.secondIndex) * length3)) - (this.secondDelta * length3);
        float f5 = length;
        int i5 = 0;
        for (String str3 : strArr) {
            matrix.reset();
            matrix.postTranslate(760.0f, 0.0f);
            matrix.postRotate(f5, 0.0f, 0.0f);
            f5 += length3;
            matrix.postTranslate((float) (getWidth() / 2), (float) (getHeight() / 2));
            float f6 = this.mScale;
            matrix.postScale(f6, f6, (float) (getWidth() / 2), (float) (getHeight() / 2));
            canvas.setMatrix(matrix);
            if (this.secondDelta == 1.0f) {
                this.paint.setColor(i5 == this.secondIndex + 1 ? Color.WHITE : -7829368);
            } else {
                this.paint.setColor(i5 == this.secondIndex ? Color.WHITE : -7829368);
            }
            canvas.drawText(str3, 0.0f, 0.0f, this.paint);
            i5++;
        }
        super.onDraw(canvas);
    }

    /**
     * 根据手机分辨率从DP转成PX
     * @param dpValue
     * @return
     */
    public  int dpToPx(float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
