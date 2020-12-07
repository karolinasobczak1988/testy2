package com.realitymine.uiautomatortest.reallifeuitester.util;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LocaleHelper {
    // List based on From: https://stackoverflow.com/questions/3217492/list-of-language-codes-in-yaml-or-json/28902032#28902032 and our supported set of locales
    static String sLanguagesJson = "[" +
        "{\"code\":\"en\",\"name\":\"English\",\"nativeName\":\"English\", \"regionName\":\"United Kingdom\"}," +
        "{\"code\":\"pl\",\"name\":\"Polish\",\"nativeName\":\"Polski\"}," +
        "{\"code\":\"ko\",\"name\":\"Korean\",\"nativeName\":\"한국어\"}," +
        "{\"code\":\"fr\",\"name\":\"French\",\"nativeName\":\"Français\", \"regionName\":\"France\"}," +
        "{\"code\":\"fr-CA\",\"name\":\"French Canadian\",\"nativeName\":\"Français\", \"regionName\":\"Canada\"}," +
        "{\"code\":\"da\",\"name\":\"Danish\",\"nativeName\":\"Dansk\"}," +
        "{\"code\":\"nl\",\"name\":\"Dutch\",\"nativeName\":\"Nederlands\"}," +
        "{\"code\":\"pt\",\"name\":\"Portuguese\",\"nativeName\":\"Português\"}," +
        "{\"code\":\"pt-BR\",\"name\":\"Portuguese Brazilian\",\"nativeName\":\"Português\", \"regionName\":\"Brasil\"}," +
        "{\"code\":\"it\",\"name\":\"Italian\",\"nativeName\":\"Italiano\"}," +
        "{\"code\":\"es\",\"name\":\"Spanish\",\"nativeName\":\"Español\"}," +
        "{\"code\":\"es-MX\",\"name\":\"Spanish Mexican\",\"nativeName\":\"Español\", \"regionName\":\"México\"}," +
        "{\"code\":\"id\",\"name\":\"Indonesian\",\"nativeName\":\"Indonesia\"}," +
        "{\"code\":\"ms\",\"name\":\"Malay\",\"nativeName\":\"Bahasa Melayu\"}," +
        "{\"code\":\"ja\",\"name\":\"Japanese\",\"nativeName\":\"日本語\"}," +
        "{\"code\":\"ru\",\"name\":\"Russian\",\"nativeName\":\"Русский\"}," +
        "{\"code\":\"hi\",\"name\":\"Hindi\",\"nativeName\":\"हिन्दी\"}," +
        "{\"code\":\"zh-cn\",\"name\":\"Chinese Simplified\",\"nativeName\":\"简体中文\"}," +
        "{\"code\":\"zh-tw\",\"name\":\"Chinese Traditional\",\"nativeName\":\"繁體中文\"}," +
        "{\"code\":\"th\",\"name\":\"Thai\",\"nativeName\":\"ไทย\"}," +
        "{\"code\":\"tl\",\"name\":\"Tagalog\",\"nativeName\":\"Wikang Tagalog\"}," +
        "{\"code\":\"ar\",\"name\":\"Arabic\",\"nativeName\":\"العربية\"}," +
        "{\"code\":\"tr\",\"name\":\"Turkish\",\"nativeName\":\"Türkçe\"}," +
        "{\"code\":\"sv\",\"name\":\"Swedish\",\"nativeName\":\"Svenska\"}," +
        "{\"code\":\"de\",\"name\":\"German\",\"nativeName\":\"Deutsch\"}," +
        "{\"code\":\"nb\",\"name\":\"Norwegian Bokmål\",\"nativeName\":\"Norsk bokmål\"}," +
        "{\"code\":\"sw\",\"name\":\"Swahili\",\"nativeName\":\"Kiswahili\"}," +
        "{\"code\":\"so\",\"name\":\"Somali\",\"nativeName\":\"Soomaali\"}," +
        "{\"code\":\"vi\",\"name\":\"Vietnamese\",\"nativeName\":\"Tiếng Việt\"}," +
        "{\"code\":\"my\",\"name\":\"Burmese\",\"nativeName\":\"ဗမာစာ\"}" +
    "]";
    private static JSONArray sLanguages = null;

    public static String getNativeLangFullNameFromCode(String langCode) {
        JSONObject lang = getLangFromCode(langCode);
        if (lang == null) {
            return null;
        }

        try {
            String langName = lang.getString("nativeName");
            if (lang.opt("regionName") != null) {
                langName += " (" + lang.getString("regionName") + ")";
            }

            return langName;

        } catch (JSONException e) {
            return null;
        }
    }

    public static String getNativeLangNameFromCode(String langCode) {
        JSONObject lang = getLangFromCode(langCode);
        if (lang == null) {
            return null;
        }

        try {
            return lang.getString("nativeName");

        } catch (JSONException e) {
            return null;
        }
    }

    // Returns the region, or null if one is not set
    public static String getRegionFromCode(String langCode) {
        JSONObject lang = getLangFromCode(langCode);
        if (lang == null) {
            return null;
        }

        return lang.optString("regionName", null);
    }

    // Returns the language JSON obj or null if not found
    private static JSONObject getLangFromCode(String languageCode) {
        try {
            if (sLanguages == null) {
                sLanguages = new JSONArray(sLanguagesJson);
            }

            for (int i = 0;i < sLanguages.length();i++) {
                JSONObject lang = sLanguages.getJSONObject(i);
                if (lang.getString("code").equals(languageCode)) {
                    return lang;
                }
            }

            // We didn't find
            return null;

        } catch (JSONException e) {
            throw new InternalError("Failed to parse languages JSON");
        }
    }

}
