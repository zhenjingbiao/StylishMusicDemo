package com.example.jingbiaozhen.stylishmusicdemo.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/*
 * Created by jingbiaozhen on 2018/1/31.
 * 画一个字符Drawable
 **/

public class CharacterDrawable extends Drawable
{
    private int mWidth;

    private int mHeight;

    private String mCharacter;

    @ColorInt
    private int mCharacterTextColor;

    private boolean mBackgroundRoundAsCircle;

    @ColorInt
    private int mBackgroundColor;

    @Dimension
    private float mBackgroundRadius;

    @Dimension
    float mCharacterPadding;

    private RectF mBackgroundRect = new RectF();

    private Paint mPaint = new Paint();

    private Path mChipPath = new Path();

    @Override
    public void draw(@NonNull Canvas canvas)
    {
        mWidth = getBounds().right - getBounds().left;
        mHeight = getBounds().bottom - getBounds().top;
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBackgroundColor);
        mBackgroundRect.set(0, 0, mWidth, mHeight);
        if (mBackgroundRoundAsCircle)
        {

            canvas.drawOval(mBackgroundRect, mPaint);
            mChipPath.addOval(mBackgroundRect, Path.Direction.CW);// 表示顺时针方向
        }
        else
        {
            canvas.drawRoundRect(mBackgroundRect, mBackgroundRadius, mBackgroundRadius, mPaint);
            mChipPath.addRoundRect(mBackgroundRect, mBackgroundRadius, mBackgroundRadius, Path.Direction.CW);
        }
        canvas.clipPath(mChipPath);
        mPaint.setColor(mCharacterTextColor);
        mPaint.setTextSize(mHeight - mCharacterPadding * 2);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTypeface(Typeface.DEFAULT);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float textBaseY = mHeight - fontMetrics.bottom / 2 - mCharacterPadding;
        float fontWidth = mPaint.measureText(mCharacter);
        float textBaseX = (mWidth - fontWidth) / 2;
        canvas.drawText(mCharacter, textBaseX, textBaseY, mPaint);

    }

    @Override
    public void setAlpha(int alpha)
    {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter)
    {

    }

    @SuppressLint("WrongConstant")
    @Override
    public int getOpacity()
    {
        return 0;
    }

    public String getCharacter()
    {
        return mCharacter;
    }

    public void setCharacter(String character)
    {
        mCharacter = character;
    }

    public int getCharacterTextColor()
    {
        return mCharacterTextColor;
    }

    public void setCharacterTextColor(int characterTextColor)
    {
        mCharacterTextColor = characterTextColor;
    }

    public boolean isBackgroundRoundAsCircle()
    {
        return mBackgroundRoundAsCircle;
    }

    public void setBackgroundRoundAsCircle(boolean backgroundRoundAsCircle)
    {
        mBackgroundRoundAsCircle = backgroundRoundAsCircle;
    }

    public int getBackgroundColor()
    {
        return mBackgroundColor;
    }

    public void setBackgroundColor(int backgroundColor)
    {
        mBackgroundColor = backgroundColor;
    }

    public float getBackgroundRadius()
    {
        return mBackgroundRadius;
    }

    public void setBackgroundRadius(float backgroundRadius)
    {
        mBackgroundRadius = backgroundRadius;
    }

    public float getCharacterPadding()
    {
        return mCharacterPadding;
    }

    public void setCharacterPadding(float characterPadding)
    {
        mCharacterPadding = characterPadding;
    }

    public static class Builder
    {
        private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#CCCCCC");

        private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#EEEEEE");

        private String character;

        @ColorInt
        private int characterTextColor = DEFAULT_TEXT_COLOR;

        private boolean backgroundRoundAsCircle;

        @ColorInt
        private int backgroundColor = DEFAULT_BACKGROUND_COLOR;

        @Dimension
        private float backgroundRadius;

        @Dimension
        private float mCharacterPadding;

        public Builder applyStyle(int style)
        {
            return this;
        }

        public Builder setCharacter(char character)
        {
            this.character = String.valueOf(character);
            return this;
        }

        public Builder setCharacter(String character)
        {
            this.character = character;
            return this;
        }

        public Builder setCharacterTextColor(@ColorInt int characterTextColor){
            this.characterTextColor=characterTextColor;
            return this;
        }
        public Builder setBackgroundRoundAsCircle(boolean roundAsCircle) {
            this.backgroundRoundAsCircle = roundAsCircle;
            return this;
        }

        public Builder setBackgroundColor(@ColorInt int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setBackgroundRadius(@Dimension float backgroundRadius) {
            this.backgroundRadius = backgroundRadius;
            return this;
        }

        public Builder setCharacterPadding(@Dimension float padding) {
            this.mCharacterPadding = padding;
            return this;
        }

        public CharacterDrawable build(){
            CharacterDrawable drawable=new CharacterDrawable();
            drawable.setCharacter(character);
            drawable.setCharacterTextColor(characterTextColor);
            drawable.setBackgroundRoundAsCircle(backgroundRoundAsCircle);
            drawable.setBackgroundColor(backgroundColor);
            drawable.setBackgroundRadius(backgroundRadius);
            drawable.setCharacterPadding(mCharacterPadding);
            return drawable;
        }



    }

    public static CharacterDrawable create(Context context,char character,boolean roundAsDrawable,int padding){
        return new CharacterDrawable.Builder()
                .setCharacter(character)
                .setBackgroundRoundAsCircle(roundAsDrawable)
                .setCharacterPadding(context.getResources().getDimensionPixelSize(padding))
                .build();
    }

}
