package me.croshaw.yop.utils;

import com.google.common.primitives.Booleans;

import java.util.ArrayList;

public class ConvertUtils {

    public static int getIntMask(ArrayList<Boolean> values) {
        return getIntMask(Booleans.toArray(values));
    }

    public static int getIntMask(boolean[] values) {
        int bits = 0;
        for (int i = 0; i < values.length; i++)
            if (values[i])
                bits |= 1 << i;
        return bits;
    }
    public static boolean[] getBooleans(int intMask) {
        boolean[] booleans = new boolean[30];
        for (int i = 0; i < booleans.length; i++)
            if ((intMask & 1 << i) != 0)
                booleans[i] = true;
        return booleans;
    }
}
