package com.demo.sisyphus.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Sisyphus on 2017/3/22.
 */

public class MyLoader extends View {

    private int waterColor = Color.BLUE; // 水波颜色
    private Paint paint = new Paint(); // 画笔

    private int radius; // 圆球半径
    private int waterLevel; // 水位高度

    private int change; // 水波水平移动位置

    private float[] pointOneY; // 水波1，Y轴坐标
    private float[] pointTwoY; // 水波2，Y轴坐标
    private float[] circleY; // 圆球Y轴坐标
    private float period; // 水波曲线周期
    private float swing; // 水波曲线振幅

    private int textColor; // 字体颜色
    private int textSize; // 字体大小
    private String text; // 文字内容

    public MyLoader(Context context) {
        super(context);
    }

    public MyLoader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyLoader);
        radius = typedArray.getInt(R.styleable.MyLoader_radius, 50);
        waterColor = typedArray.getColor(R.styleable.MyLoader_color, Color.BLUE);
        waterLevel = (2 * radius) - typedArray.getInt(R.styleable.MyLoader_defalt_level, radius/2);
        text = typedArray.getString(R.styleable.MyLoader_text);
        textSize = typedArray.getDimensionPixelSize(R.styleable.MyLoader_text_size, radius/4);
        textColor = typedArray.getColor(R.styleable.MyLoader_text_color, Color.GRAY);
        typedArray.recycle(); // 最后记得把这个对象回收了，避免造成溢出
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(waterColor);

        drawWater(canvas);

        if (text != null) {
            drawText(canvas);
        }
    }

    private void drawWater(Canvas cavas){
        getWaterLine();
        paint.setAlpha(80);
        paint.setAntiAlias(true);
        float temp = 0;

        for (int i = 0; i < 2 * radius; i++){
            if (waterLevel < radius){
                temp = 2*radius-circleY[i];
            }else {
                temp = circleY[i];
            }
            // 画波浪1
            cavas.drawLine(i, pointOneY[i], i, temp, paint);
            // 画波浪2
            paint.setAlpha(90);
            cavas.drawLine(i, pointTwoY[i], i, temp, paint);
        }
    }

    private void drawText(Canvas canvas){
        Paint p = new Paint();
        p.setColor(textColor);
        p.setTextSize(textSize);
        p.setAntiAlias(true);
        // 获取所要画的文本所占像素宽度，下面计算中间位置备用
        float length = p.measureText(text);
        // 将文本绘制在圆球正中间的位置
        canvas.drawText(text, radius - (length / 2), radius+textSize/2, p);
    }

    private void getWaterLine(){

        period = (float) (2 * Math.PI / radius); // 周期
        swing = radius/10; // 振幅
        pointOneY = new float[2 * radius]; // 波浪1，Y坐标
        pointTwoY = new float[2 * radius]; // 波浪2，Y坐标

        circleY = new float[2 * radius]; // 圆球Y坐标
        int x = radius; // 圆球圆心X坐标
        int y = x; // 圆球圆心坐标

        for (int i = 0; i < 2 * radius; i++){
            float oneY = (float) (swing * Math.sin(period * i + change) + waterLevel);
            float twoY = (float) ((swing - 5) * Math.sin(period * i + change + 5) + waterLevel);
            if (waterLevel < radius) {
                circleY[i] = (float) (-Math.sqrt(radius*radius - (i-x)*(i-x)) + y);
                pointOneY[i] = circleY[i] >= oneY ? circleY[i] : oneY;
                pointTwoY[i] = circleY[i] >= twoY ? circleY[i] : twoY;
            }else {
                circleY[i] = (float) (Math.sqrt(radius*radius - (i-x)*(i-x)) + y);
                pointOneY[i] = circleY[i] <= oneY ? circleY[i] : oneY;
                pointTwoY[i] = circleY[i] <= twoY ? circleY[i] : twoY;
            }
        }
    }

    public void setChange(int change) {
        this.change = change;
    }

    public void setWaterLevel(int waterColor){
        this.waterLevel = 2 * this.radius - waterLevel;
    }

    // 开始水波动画
    public void startAnimation(){
        new Thread(){
            int change = 0;
            @Override
            public void run() {
                super.run();
                while (change <= radius){
                    if (change == radius){
                        change = 0;
                    }
                    change += 1;
                    setChange(change);
                    postInvalidate();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
