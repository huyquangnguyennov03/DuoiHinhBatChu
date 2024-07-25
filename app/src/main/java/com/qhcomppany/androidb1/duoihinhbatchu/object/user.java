package com.qhcomppany.androidb1.duoihinhbatchu.object;

import android.content.Context;
import android.content.SharedPreferences;

public class user {
    private static final String PREF_NAME = "user_data";  // Tên cho SharedPreferences
    private static final String KEY_NAME = "name";
    private static final String KEY_POINT = "point";
    private static final String KEY_POSITION = "position";  // Thêm trường lưu vị trí

    public String name;
    public int point;
    public int position;  // Thêm trường vị trí

    public void saveTT(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.putInt(KEY_POINT, point);
        editor.putInt(KEY_POSITION, position);  // Lưu vị trí
        editor.apply();
    }

    public void getTT(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_NAME, "");
        point = sharedPreferences.getInt(KEY_POINT, 0);  // Giả sử điểm mặc định là 0
        position = sharedPreferences.getInt(KEY_POSITION, 0);  // Lấy vị trí từ SharedPreferences
    }
}
