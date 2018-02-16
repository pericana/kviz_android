package hr.math.kviz;

import android.animation.ObjectAnimator;
import android.view.View;

public class Animacije {

    public static void povecaj(View view, float from, float to, int duration) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", from, to);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", from, to);

        scaleX.setDuration(duration);
        scaleY.setDuration(duration);

        scaleX.start();
        scaleY.start();

    }

}
