package com.decard.piechart;

public class PieModel {

    public float	startAngle;	// 开始绘制的角度

    public String	eatName;	// 吃的名称

    public float	sweepAngle;	// 扫过的角度

    public int		color;		// 显示的颜色

    public int	   percent;	// 所占百分比

    public boolean	selected;	// true为选中

    public PieModel(int color, int percent) {
        this.color = color;
        this.percent = percent;
    }

    public PieModel(String name,int color, int percent) {
        this.eatName = name;
        this.color = color;
        this.percent = percent;
    }

    public PieModel(String name,int color, int percent, boolean selected) {
        this.eatName = name;
        this.color = color;
        this.percent = percent;
        this.selected = selected;
    }
}
