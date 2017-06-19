package com.fatchao.indexview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class IndexView extends View {
    private Paint mPaint;
    private int currentNumber;

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndexView);
        currentNumber = typedArray.getInt(R.styleable.IndexView_currentNumber, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int edgeWidth = (getWidth() - 12) / 3;//中间的间隔是每个6像素
        mPaint = new Paint();
        mPaint.setStrokeWidth((float) 3.0);
        mPaint.setStyle(Paint.Style.FILL);
        //绘制步骤一
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(edgeWidth, 0);
        path.lineTo(edgeWidth + getHeight() / 2, getHeight() / 2);
        path.lineTo(edgeWidth, getHeight());
        path.lineTo(0, getHeight());
        path.lineTo(0, 0);
        mPaint.setColor(Color.BLUE);
        canvas.drawPath(path, mPaint);
        //绘制步骤二
        path.moveTo(edgeWidth + 6, 0);                       //起始点
        path.lineTo(edgeWidth * 2 + 6, 0);
        path.lineTo(edgeWidth * 2 + getHeight() / 2 + 6, getHeight() / 2); //连线到下一点
        path.lineTo(edgeWidth * 2 + 6, getHeight());                      //连线到下一点
        path.lineTo(edgeWidth + 6, getHeight());                      //连线到下一点
        path.lineTo(edgeWidth + 6 + getHeight() / 2, getHeight() / 2);
        path.lineTo(edgeWidth + 6, 0);
        if (1 <= currentNumber)
            mPaint.setColor(Color.BLUE);
        else
            mPaint.setColor(Color.GRAY);
        canvas.drawPath(path, mPaint);

        //绘制步骤三
        path.reset();
        path.moveTo(edgeWidth * 2 + 12, 0);                       //起始点
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth(), 0); //连线到下一点
        path.lineTo(getWidth(), getHeight());                      //连线到下一点
        path.lineTo(getWidth() - edgeWidth, getHeight());                      //连线到下一点
        path.lineTo(getWidth() - edgeWidth + getHeight() / 2, getHeight() / 2);
        path.lineTo(getWidth() - edgeWidth, 0);
        if (currentNumber == 2)
            mPaint.setColor(Color.BLUE);
        else
            mPaint.setColor(Color.GRAY);
        canvas.drawPath(path, mPaint);                   //绘制任意多边形

        //绘制文字
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(34);
        String text = new String("验证手机");
        mPaint.measureText(text);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        int x = 0;
        int textHeight = (int) (fontMetrics.bottom - fontMetrics.top);
        int baseY = (int) (textHeight / 2 + getHeight() / 2 - fontMetrics.bottom);
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    text = "验证手机";
                    x = edgeWidth / 2;
                    break;
                case 1:
                    text = "重设密码";
                    x = edgeWidth + 6 + getHeight() / 2 + edgeWidth / 2;
                    break;
                case 2:
                    x = (edgeWidth + 6) * 2 + getHeight() / 2 + (edgeWidth - getHeight() / 2) / 2;
                    text = "设置成功";
                    break;
            }
            if (i > currentNumber) {
                mPaint.setColor(Color.BLACK);
            } else {
                mPaint.setColor(Color.WHITE);
            }
            canvas.drawText(text, x, baseY, mPaint);
        }
    }
}
