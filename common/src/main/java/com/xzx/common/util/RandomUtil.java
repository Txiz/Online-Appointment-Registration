package com.xzx.common.util;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 随机数工具
 * 作者: xzx
 * 创建时间: 2021-05-08-15-54
 **/
public class RandomUtil {

    private static final Random random = new Random();
    private static final DecimalFormat four_format = new DecimalFormat("0000");
    private static final DecimalFormat six_format = new DecimalFormat("000000");

    public static String getFourBitRandom() {
        return four_format.format(random.nextInt(10000));
    }

    public static String getSixBitRandom() {
        return six_format.format(random.nextInt(1000000));
    }
}
