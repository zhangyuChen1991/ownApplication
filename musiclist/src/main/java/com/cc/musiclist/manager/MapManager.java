package com.cc.musiclist.manager;

import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangyu on 2016-06-28 16:56.
 */
public enum MapManager {

    INSTANCE;
    private static final String TAG = "MapManager";
    private HashMap<String, Integer> map = new HashMap<>();

    public void init(List<File> list) {

        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                map.put(list.get(i).getName(), i);
            }
        }
    }

    public int getFilePosition(File file) {
        if (null == file)
            return 0;
        return map.get(file.getName()) == null ? 0 : map.get(file.getName());
    }
}
