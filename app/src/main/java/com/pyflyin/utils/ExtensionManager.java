package com.pyflyin.utils;

import android.util.Log;
import com.pyflyin.R;

import java.io.File;
import java.io.FileInputStream;

public class ExtensionManager {

    public static String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            return fileName.substring(index + 1);
        }

        return null;
    }

    public static int getIcon(String fileName) {
        Language language = getLanguage(getExtension(fileName));

        switch (language) {  
            case PYTHON:
                return R.drawable.vector_python;
            case TEXT:
                return R.drawable.vector_txt;
            case NONE:
                return R.drawable.vector_file;
            default:
                return R.drawable.vector_file;
        }
    }

    public static Language getLanguage(String extension) {
        if (extension == null) return Language.NONE;

        String normalized = extension.toLowerCase();
        if (normalized.equals("py")) {
            return Language.PYTHON;
        } else if (normalized.equals("txt") || normalized.equals("text")) {
            return Language.TEXT;
        } else {
            return Language.NONE;
        }
    }

    public static boolean isBinaryFile(File f) {
        int result = 0;
        try {
            FileInputStream in = new FileInputStream(f);
            int size = in.available();
            if (size > 1024) size = 1024;
            byte[] data = new byte[size];
            result = in.read(data);
            in.close();

            int ascii = 0;
            int other = 0;

            for (byte b : data) {
                if (b < 0x09) return true;

                if (b == 0x09 || b == 0x0A || b == 0x0C || b == 0x0D) ascii++;
                else if (b >= 0x20 && b <= 0x7E) ascii++;
                else other++;
            }

            return other != 0 && 100 * other / (ascii + other) > 95;

        } catch (Exception e) {
            Log.e("pyflyin", e.getMessage() + String.valueOf(result));
        }

        return true;
    }

    public enum Language {
        PYTHON, TEXT, NONE
    }
}
