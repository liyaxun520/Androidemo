package com.decard.piechart;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class ColorUtils {

    /**
     * 根据Ui图得到的颜色集合
     *
     * @param size
     * @return
     */
    public static List<Integer> getColor(int size) {
        List<Integer> allColors = new ArrayList<>();
        allColors.add(Color.parseColor("#FE679C"));
        allColors.add(Color.parseColor("#A973C0"));
        allColors.add(Color.parseColor("#29A7E2"));
        allColors.add(Color.parseColor("#4CD1B0"));
        allColors.add(Color.parseColor("#96E054"));
        allColors.add(Color.parseColor("#FFFA58"));
        allColors.add(Color.parseColor("#FFBD49"));
        if (size > allColors.size()) {
            return null;
        }
        return allColors.subList(0, size);
    }

    public static Integer getDefaultColor() {
        return Color.parseColor("#EBEAE9");
    }
}