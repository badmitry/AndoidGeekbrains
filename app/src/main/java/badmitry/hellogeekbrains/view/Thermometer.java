package badmitry.hellogeekbrains.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import badmitry.hellogeekbrains.SingletonForSaveState;

public class Thermometer extends View {
    private final int thermometerColor = Color.GRAY;
    private int colorDegree = Color.RED;
    private RectF thermometerRectangle = new RectF();
    private Rect degreeRectangle = new Rect();
    private Rect cutCircleRect = new Rect();
    private int radius = 0;
    private int width = 0;
    private int height = 0;
    private double level = 2;
    private int padding = 5;
    private int round = 5;
    private Paint thermometerPaint;
    private Paint degreePaint;
    private Paint circleInside;
    private Paint circleOutside;
    private Paint cutCircleRectPaint;
    SingletonForSaveState singletonForSaveState;

    public Thermometer(Context context) {
        super(context);
        singletonForSaveState = SingletonForSaveState.getInstance();
        init();
    }

    public Thermometer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        singletonForSaveState = SingletonForSaveState.getInstance();
        init();
    }

    public Thermometer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        singletonForSaveState = SingletonForSaveState.getInstance();
        init();
    }

    public Thermometer(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        singletonForSaveState = SingletonForSaveState.getInstance();
        init();
    }

    private void init() {
        thermometerPaint = new Paint();
        thermometerPaint.setColor(thermometerColor);
        thermometerPaint.setStyle(Paint.Style.FILL);
        degreePaint = new Paint();
        if (singletonForSaveState.isCity()) {
            level = Double.parseDouble(singletonForSaveState.getArrayList().get(0)[0].replace(",", "."));
            Log.d("TAG", singletonForSaveState.getArrayList().get(0)[0]);
            if (level > 0) {
                colorDegree = Color.RED;
            } else {
                colorDegree = Color.BLUE;
            }
        }
        degreePaint.setColor(colorDegree);
        degreePaint.setStyle(Paint.Style.FILL);
        circleOutside = new Paint();
        circleOutside.setColor(thermometerColor);
        circleOutside.setStyle(Paint.Style.FILL);
        circleInside = new Paint();
        circleInside.setColor(colorDegree);
        circleInside.setStyle(Paint.Style.FILL);
        cutCircleRectPaint = new Paint();
        cutCircleRectPaint.setColor(thermometerColor);
        cutCircleRectPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        padding = h / 20;
        round = padding;
        width = w - getPaddingLeft() - getPaddingRight();
        height = h - getPaddingTop() - getPaddingBottom();
        radius = (width - (2 * padding)) / 2;
        thermometerRectangle.set(padding, padding, width - padding, height - padding - (radius - padding));
        cutCircleRect.set(2 * padding, 2 *padding, width - 2 * padding, height - 2 * padding - (radius - padding));
        degreeRectangle.set(2 * padding, (int) ((height - padding - radius) * ((40 - level) / (double) 40)),
                width - 2 * padding, height - padding - radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(thermometerRectangle, round, round, thermometerPaint);
        float center = ((float) width) / 2;
        canvas.drawCircle(center, height - padding - radius, radius, circleOutside);
        canvas.drawCircle(center, height - padding - radius, radius - padding, circleInside);
        canvas.drawRect(cutCircleRect, cutCircleRectPaint);
        canvas.drawRect(degreeRectangle, degreePaint);
    }
}
