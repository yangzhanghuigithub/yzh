package com.learn.yzh.common.spring;

import com.learn.yzh.common.utils.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by hello on 2016/12/8.
 */
public class SpringGainContextUtils {
    public static   <T> T getBean(String beanName) {
        if (StringUtils.isEmpty(beanName)) {
            return null;
        }
        char c = beanName.charAt(0);
        char c1 = beanName.charAt(1);
        if (c >= 'A' && c <= 'Z' && !(c1 >= 'A' && c1 <= 'Z')) {
            beanName = String.valueOf((char) (c + 32)) + beanName.substring(1);
        }
        T t = null;
        try {
            t = SpringContextUtils.getBean(beanName);
        } catch (Exception e) {
            t = SpringContextUtils.getBean(beanName + "Impl");
        }
        return t;
    }
}
