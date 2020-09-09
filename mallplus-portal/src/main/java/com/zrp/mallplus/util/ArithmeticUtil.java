package com.zrp.mallplus.util;

import org.apache.commons.lang3.StringUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ArithmeticUtil {

    private final static String ZERO = "0";

    /**
     * 功能描述:
     * <小数相加，参数为空当0处理>
     *
     * @param str1 1
     * @param str2 2
     * @return java.lang.String
     * @author zhoulipu
     * @date 2019/8/27 19:58
     */
    public static String additionStr(String str1, String str2) {
        try {
            BigDecimal bd1 = new BigDecimal(StringUtils.isBlank(str1) ? "0" : str1);
            BigDecimal bd2 = new BigDecimal(StringUtils.isBlank(str2) ? "0" : str2);
            return bd1.add(bd2).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 功能描述:
     * <小数相减，参数为空当0处理>
     *
     * @param str1 1
     * @param str2 2
     * @return java.lang.String
     * @author zhoulipu
     * @date 2019/7/8 16:26
     */
    public static String subtractionStr(String str1, String str2) {
        try {
            BigDecimal bd1 = new BigDecimal(StringUtils.isBlank(str1) ? "0" : str1);
            BigDecimal bd2 = new BigDecimal(StringUtils.isBlank(str2) ? "0" : str2);
            return bd1.subtract(bd2).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 功能描述:
     * <乘法保留小数位数，参数为空不运算>
     *
     * @param str1 1
     * @param str2 2
     * @param num  3
     * @return java.lang.String
     * @author zhoulipu
     * @date 2019/7/8 16:27
     */
    public static String multiplicationStr(String str1, String str2, int num) {
        if (StringUtils.isBlank(str1) || StringUtils.isBlank(str2)) {
            return "";
        }
        try {
            BigDecimal v1 = new BigDecimal(str1);
            BigDecimal v2 = new BigDecimal(str2);
            return v1.multiply(v2).setScale(num, RoundingMode.HALF_UP).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


//    public static void main(String[] args) {
//        System.out.println( multiplicationStr("1","2",2));
//    }

    /**
     * 功能描述:
     * <除法保留小数位数，参数为空不运算>
     *
     * @param str1 1
     * @param str2 2
     * @param num  3
     * @return java.lang.String
     * @author zhoulipu
     * @date 2019/7/8 16:27
     */
    public static String divisionStr(String str1, String str2, int num) {
        // 去除后置0
        str2 = new BigDecimal(str2).stripTrailingZeros().toPlainString();
        if (StringUtils.isBlank(str1) || StringUtils.isBlank(str2) || ZERO.equals(str2)) {
            return "";
        }
        try {
            BigDecimal v1 = new BigDecimal(str1);
            BigDecimal v2 = new BigDecimal(str2);
            return v1.divide(v2, num, RoundingMode.HALF_UP).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
