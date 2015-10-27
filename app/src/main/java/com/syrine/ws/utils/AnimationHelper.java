package com.syrine.ws.utils;

import android.animation.ObjectAnimator;
import android.widget.ImageView;

public class AnimationHelper {
    private static final int FADE_IN_DURATION = 500;

    public static void FadeInAnimation(final ImageView imageView) {
        ObjectAnimator fade = ObjectAnimator.ofFloat(imageView, "alpha", 0.0f, 1.0f);
        fade.setDuration(FADE_IN_DURATION);
        fade.start();
    }
}
