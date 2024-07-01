package com.rosenzest.base.util;

public class SerialNoUtil {
    private static int serialNo;

    public static int getSerialNo() {
        if (serialNo == Integer.MAX_VALUE) {
            serialNo = 0;
        } else {
            serialNo++;
        }
        return serialNo;
    }
}
