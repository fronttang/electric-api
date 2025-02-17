package com.rosenzest.base.util;

import java.io.Serializable;
import java.util.UUID;

/**
 * 生成RCID,生成唯一id，主要作用于新增地推产品中的生成链接使用
 */
public class UniCodeUtil implements Serializable {

    private static final long serialVersionUID = -5628028113003022356L;

    private static final int MAX_SIZE = 40;
    private static final int MIN_SIZE = 14;
    private static final int BASE_SIZE = 2;

    public static void main(String[] args) {
        String rand = rand(14);
        System.out.println(rand);
    }

    /**
     * 默认生成RCID 12位
     *
     * @return
     */
    public static String rand() {
        return rand(16);
    }

    /**
     * 根据传入值生成相应位数随机码
     *
     * @param offset
     *            最大生成40位，最小生成14位 (位权基础系数为4) 4 6 8 1 2 3
     * @return
     */
    public static String rand(int offset) {

        if (offset > MAX_SIZE || offset < MIN_SIZE) {
            return "";
        }
        int div = offset / 10;
        int bitSize = BASE_SIZE + (div << 2 >> 1);
        int baseSize = offset - bitSize;

        String baseID = randomBaseID(baseSize);

        StringBuilder rcid = new StringBuilder(baseID);
        String rv = bitWeight(bitSize);
        char[] ch = rv.toCharArray();
        for (char c : ch) {
            rcid.insert(Character.getNumericValue(c), c);
        }
        return rcid.toString().toUpperCase();
    }

    /**
     * 根据UUID生成随机指定位数位ID
     *
     * @return
     */
    public static String randomBaseID(int baseSize) {
        UUID uid = UUID.randomUUID();
        long idx = uid.getLeastSignificantBits();
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            buff.append(toa(0x1F & idx));
            idx >>>= 5;
        }
        long idxb = uid.getMostSignificantBits();
        buff.append(toa((idxb & 0x1) == 0 ? idx : idx + 0x10));
        idx = (idxb >>> 1);
        for (int i = 0; i < 13; i++) {
            buff.append(toa(0x1F & idx));
            idx >>>= 5;
        }
        return buff.reverse().toString().substring(0, baseSize);
    }

    /**
     * 获取位权数
     *
     * @return
     */
    private static String bitWeight(int bitSize) {
        String str = String.valueOf(Math.random()).substring(2);
        while (str.length() < bitSize || str.indexOf('-') > 0) {
            str = String.valueOf(Math.random()).substring(2);
        }
        return str.substring(0, bitSize);
    }

    /**
     * 生成可见字符
     *
     * @param lidx
     * @return
     */
    private static char toa(long lidx) {
        if (lidx < 10) {
            return (char)(0x30 + lidx);
        }
        lidx += (0x41 - 10);
        int[] skips = {0x49, 0x4f, 0x57, 0x5a};
        for (int ch : skips) {
            if (lidx < ch) {
                break;
            }
            lidx++;
        }
        return (char)lidx;
    }
}
