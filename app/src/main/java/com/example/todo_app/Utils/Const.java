package com.example.todo_app.Utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Const {
    public static final String EDIT_TAG = "EDIT";
    public static final String CLICK = "CLICK";

    public static String DEFAULT_PASSWORD = "1234";
    public static boolean isSet = true;

    public static int NOTIFICATION_ID = 1;
    public static String CHANNEL_ID = "CHANNEL_1";
    public static String CHANNEL_STATIC = "STATIC";


    public static final String ALGO = "AES";
    public static final String MD5 = "MD5";
    public static final String SHA_256 = "SHA-256";

    public static final String ENCRYPT_ALGO = "AES/CBC/PKCS5Padding";
    public static final int IV_LENGTH_BYTE = 16;
    public static final int AES_KEY_BIT = 256;

    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static final String TAG = "ENCRYPTED_TAG";


}
