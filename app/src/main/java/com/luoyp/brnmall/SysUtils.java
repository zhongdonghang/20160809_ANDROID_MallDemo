package com.luoyp.brnmall;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Created by lyp3314@gmail.com on 16/5/17.
 */
public class SysUtils {
    public static String formatDouble(double d) {
        NumberFormat nf = NumberFormat.getNumberInstance();


        // 保留两位小数
        nf.setMaximumFractionDigits(2);


        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);


        return nf.format(d);
    }
}
