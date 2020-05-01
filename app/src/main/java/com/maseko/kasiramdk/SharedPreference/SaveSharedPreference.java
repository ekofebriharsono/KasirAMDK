package com.maseko.kasiramdk.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.maseko.kasiramdk.SharedPreference.UtilPreferences.ID_USER;
import static com.maseko.kasiramdk.SharedPreference.UtilPreferences.INGAT;
import static com.maseko.kasiramdk.SharedPreference.UtilPreferences.NAMA;
import static com.maseko.kasiramdk.SharedPreference.UtilPreferences.NAMA_LAIN;
import static com.maseko.kasiramdk.SharedPreference.UtilPreferences.NAMA_TOKO;
import static com.maseko.kasiramdk.SharedPreference.UtilPreferences.PASSWORD;
import static com.maseko.kasiramdk.SharedPreference.UtilPreferences.USERNAME;

public class SaveSharedPreference {


    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param id
     */

    public static void setIdUser(Context context, String id) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(ID_USER, id);
        editor.apply();
    }

    public static void setNama(Context context, String nama) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(NAMA, nama);
        editor.apply();
    }

    public static void setUsername(Context context, String username) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(USERNAME, username);
        editor.apply();
    }

    public static void setPassword(Context context, String password) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public static void setNamaToko(Context context, String nama_toko) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(NAMA_TOKO, nama_toko);
        editor.apply();
    }

    public static void setIngat(Context context, boolean ingat) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(INGAT, ingat);
        editor.apply();
    }



    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */

    public static String getIdUser(Context context) {
        return getPreferences(context).getString(ID_USER, null);
    }

    public static String getNama(Context context) {
        return getPreferences(context).getString(NAMA, null);
    }

    public static String getUsername(Context context) {
        return getPreferences(context).getString(USERNAME, null);
    }

    public static String getPassword(Context context) {
        return getPreferences(context).getString(PASSWORD, null);
    }

    public static String getNamaToko(Context context) {
        return getPreferences(context).getString(NAMA_TOKO, null);
    }

    public static boolean getIngat(Context context) {
        return getPreferences(context).getBoolean(INGAT, false);
    }



}
