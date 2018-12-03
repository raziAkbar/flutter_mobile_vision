package io.github.edufolly.fluttermobilevision.ocr;

import android.util.Log;

import com.google.android.gms.common.util.Function;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.text.TextBlock;

import java.util.concurrent.Callable;

import io.github.edufolly.fluttermobilevision.ui.GraphicOverlay;

public class OcrTrackerFactory implements MultiProcessor.Factory<TextBlock> {
    private GraphicOverlay<OcrGraphic> graphicOverlay;
    private boolean showText;
    public OcrCaptureActivity activity;
    private String pattern;

    public OcrTrackerFactory(GraphicOverlay<OcrGraphic> graphicOverlay, boolean showText, OcrCaptureActivity activity, String pattern) {
        this.graphicOverlay = graphicOverlay;
        this.showText = showText;
        this.activity = activity;
        this.pattern = pattern;
    }

    @Override
    public Tracker<TextBlock> create(TextBlock textBlock) {
        OcrGraphic graphic = new OcrGraphic(graphicOverlay, showText,this.activity, pattern);
        try {
            return new OcrGraphicTracker(graphicOverlay, graphic);
        } catch (Exception ex) {
            Log.d("OcrTrackerFactory", ex.getMessage(), ex);
        }
        return null;
    }
}
