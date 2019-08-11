package com.learn.yzh.common.utils.secure;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by cuicheng on 2018/3/9.
 */
@Component
public class CommonProperty {

    private final Properties prop;

    public CommonProperty() {
        prop = new Properties();
        init();
    }

    private void init() {
//        if (prop.isEmpty()) {
//            // load
//            String fileName = "common.properties";
//            try {
//                File file = ResourceUtils.getFile("classpath:" + fileName);
//                prop.load(new FileInputStream(file));
//            } catch (IOException e) {
//                prop.clear();
//                throw new NullPointerException( fileName + " file not found! please check file!");
//            }
//        }
    }

    public Properties getProp() {
        return prop;
    }

    public Object getProperty(String key) {
        return getProperty(key, Object.class);
    }

    public String getPropertyForStr(String key) {
        return getProperty(key, String.class);
    }

    public Integer getPropertyForInt(String key) {
        return getProperty(key, Integer.class);
    }

    public <T> T getProperty(String key, Class<T> type) {
        if (key == null || "".equals(key.trim())) {
            return null;
        }
        return prop.isEmpty() ? null : (T) prop.getProperty(key);
    }
}
