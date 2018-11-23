package com.infokmg.hugv.triagemnutricional.layout.expansive;

import android.animation.TimeInterpolator;

/**
 * Created by Klinsman on 02/05/2017.
 */

public class ItemModel {
    public final String title;
    public final String description;
    public final int colorId1;
    public final int colorId2;
    public final TimeInterpolator interpolator;

    public ItemModel(String title, String description, int colorId1, int colorId2, TimeInterpolator interpolator) {
        this.title = title;
        this.description = description;
        this.colorId1 = colorId1;
        this.colorId2 = colorId2;
        this.interpolator = interpolator;
    }
}
