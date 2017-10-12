package com.shiy.demo.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by DebugSy on 2017/10/12.
 */

public class UumericalUtil {

	public static float FloatTO(float f, int number) {
		BigDecimal b = new BigDecimal(f);
		float f1 = b.setScale(number, BigDecimal.ROUND_HALF_UP).floatValue();
		return f1;
	}

	public static String NumberTO(int number) {
		DecimalFormat dfInt = new DecimalFormat("00");
		String sInt = dfInt.format(number);
		System.out.println(sInt);
		return sInt;
	}

}
