package com.cylmms;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.setting.Setting;

/**
 * @author EKERTREE
 * @Date 2022/11/01 17:56
 **/
public class EncryptUtils {
    private static final String KEY;

    static {
        KEY = new Setting("encrypt.setting").get("encryptKey");
    }

    public static String encode(String str) {
        return SecureUtil.aes(KEY.getBytes()).encryptHex(str);
    }

    public static String decode(String str) {
        return SecureUtil.aes(KEY.getBytes()).decryptStr(str);
    }
}
