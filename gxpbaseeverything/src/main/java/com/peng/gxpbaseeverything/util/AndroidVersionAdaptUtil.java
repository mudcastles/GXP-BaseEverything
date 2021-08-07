package com.peng.gxpbaseeverything.util;

import android.os.Build;
import android.os.Environment;

/**
 * 版本适配工具
 */
public class AndroidVersionAdaptUtil {
    /**
     * 判断是否需要适配分区存储
     *
     * @return 是否使用传统存储方式。true:传统存储方式；false:分区存储
     */
    public static boolean isLegacyExternalStorage() {
        // 使用Environment.isExternalStorageLegacy()来检查APP的运行模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                !Environment.isExternalStorageLegacy()) {
            return false;
        }
        return true;
    }
}
