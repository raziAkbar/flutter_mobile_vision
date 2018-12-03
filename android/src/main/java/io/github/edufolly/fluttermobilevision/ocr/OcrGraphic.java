/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.edufolly.fluttermobilevision.ocr;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;


import com.google.android.gms.common.util.Function;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.github.edufolly.fluttermobilevision.ui.GraphicOverlay;

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class OcrGraphic extends GraphicOverlay.Graphic {

    private static final int TEXT_COLOR = Color.WHITE;
    private static final Paint rectPaint = new Paint();
    private static final Paint textPaint = new Paint();
    private boolean breaks = false;



    static {
        rectPaint.setColor(TEXT_COLOR);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(4.0f);

        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(50.0f);
    }

    private boolean showText;
    private volatile TextBlock textBlock;
    private OcrCaptureActivity activity;
    private String pattern;

    OcrGraphic(GraphicOverlay overlay, boolean showText, OcrCaptureActivity activity, String pattern) {
        super(overlay);

        this.showText = showText;
        this.activity = activity;
        this.pattern = pattern;
        postInvalidate();
    }

    public void updateItem(TextBlock textBlock) {
        this.textBlock = textBlock;
        postInvalidate();
    }

    public TextBlock getTextBlock() {
        return textBlock;
    }

    /**
     * @return RectF that represents the graphic's bounding box.
     */
    public RectF getBoundingBox() {
        TextBlock textBlock = this.textBlock;
        if (textBlock == null) {
            return null;
        }
        RectF rect = new RectF(textBlock.getBoundingBox());
        rect = translateRect(rect);
        return rect;
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        TextBlock text = textBlock;
        if (text == null) {
            return;
        }
if(breaks == true) {return;}
        // Draws the bounding box around the TextBlock.
        RectF rect = new RectF(text.getBoundingBox());
        rect = translateRect(rect);
//        canvas.drawRect(rect, rectPaint);

        // Break the text into multiple lines and draw each one according to its own bounding box.
        List<? extends Text> textComponents = text.getComponents();
        for (Text currentText : textComponents) {
            float left = translateX(currentText.getBoundingBox().left);
            float bottom = translateY(currentText.getBoundingBox().bottom);


//                if(currentText.getValue().matches("^T\\\\d{6}(C||c)$")) {
//                    android.util.Log.d("razi", currentText.getValue());
//                }0O
//                09/08/18 20: 33
//                ^[$]\d{2}.\d{2}$
                //  ^[$]\d{2}.\d{2}$
            android.util.Log.d("razi", pattern);
if(pattern.matches("1")){

        android.util.Log.d("razi", pattern);
        android.util.Log.d("razi", "in 1");
//        android.util.Log.d("razi", currentText.getValue());
        if (currentText.getValue().matches("^T\\d{6}(C||c)$")) {
//                                        "^XXXXXXXXXX\\d{4}$" \d{2}/\d{2}/\d{2} [0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2} ^T\d{6}(C||c)$ DATE 09/08/18
            android.util.Log.d("razi", "number plate");
//            android.util.Log.d("razi", currentText.getValue());
            activity.lists.add(new MyTextBlock(text));
            canvas.drawText("Car Number found", left, bottom, textPaint);
            activity.textDetected(activity.lists);
            breaks = true;
            break;
        }

}else if(pattern.matches("2")) {

//        android.util.Log.d("razi", pattern);
//        android.util.Log.d("razi", "in 2");
//        android.util.Log.d("razi", currentText.getValue());
    if (currentText.getValue().matches("^[$]\\d{2}.\\d{2}$") && activity.next == true) {
//                                        "^XXXXXXXXXX\\d{4}$" \d{2}/\d{2}/\d{2} [0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2} ^T\d{6}(C||c)$ DATE 09/08/18
        android.util.Log.d("razi", "first");
//        android.util.Log.d("razi", currentText.getValue());
//                        lists.add(text);
//                        activity.textDetected(text);
                        activity.next = false;
        //
//                       breaks = true;
//                        break;
        activity.lists.add(new MyTextBlock(text));

        canvas.drawText("Gas Cost found", left, bottom, textPaint);


//                        activity.textDetected(text);
//                        breaks = true;
//                        break;
    }
    if (currentText.getValue().matches("^A(u||U)th\\s\\d{6}$")) {
//                                        "^XXXXXXXXXX\\d{4}$" \d{2}/\d{2}/\d{2} [0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2} ^T\d{6}(C||c)$ DATE 09/08/18
        android.util.Log.d("razi", "second");
//        android.util.Log.d("razi", currentText.getValue());
        activity.lists.add(new MyTextBlock(text));
        canvas.drawText("Auth Number found", left, bottom, textPaint);
if( activity.next == false) {
    activity.textDetected(activity.lists);
    breaks = true;
    break;
}

    }


                //canvas.drawText(currentText.getValue(), left, bottom, textPaint);
            }
        }
    }
}
