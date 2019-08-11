package com.learn.yzh.common.redis;

import com.google.common.base.Function;
import com.google.common.base.Functions;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 主要用来 string转成其它类型，其它类型转成string
 * string的collection转成其它类型的collection
 *
 * @author zhaohe
 */
public class TypeConvert {

    public static final TypeConvert DEFAULT = new TypeConvert();

    public TypeConvert() {
    }

    //把要存储的对象转成字符串
    public <T> String toString(T value) {
        if (value == null) return null;
        Class<?> clazz = value.getClass();
        String result = "";
        if (value instanceof Number) {
            //是基本的数字类型，直接toString
            result = value.toString();

        } else if (clazz.equals(Date.class)) {
            //日期类型存getTime()
            Date date = (Date) value;
            result = "" + date.getTime();

        } else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            //布尔型转成0, 1
            Boolean bool = (Boolean) value;
            result = bool ? "1" : "0";

        } else if (clazz.equals(Character.class) || clazz.equals(String.class)) {
            //Char 和 String直接转
            result = value.toString();

        } else {
            //其它所有类型转成JSON
//            result = JsonUtils.toSimpleJson(value);
            result = JSON.toJSONString(value);
        }

        return result;
    }

    //把字符串转成对象  对要支持的对象增加代码
    @SuppressWarnings("unchecked")
    public <T> T toType(String value, Class<T> clazz) {
        T t = null;
        if (clazz.equals(Double.class) || clazz.equals(double.class)) {
            Double val = 0D;
            if (value != null) {
                try {
                    val = Double.parseDouble(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            t = (T) val;

        } else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
            Float val = 0F;
            if (value != null) {
                try {
                    val = Float.parseFloat(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            t = (T) val;

        } else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
            Long val = 0L;
            if (value != null) {
                try {
                    val = Long.parseLong(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            t = (T) val;

        } else if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            Integer val = 0;
            if (value != null) {
                try {
                    val = Integer.parseInt(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            t = (T) val;

        } else if (clazz.equals(Short.class) || clazz.equals(short.class)) {
            Short val = 0;
            if (value != null) {
                try {
                    val = Short.parseShort(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            t = (T) val;

        } else if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
            Byte val = 0;
            if (value != null) {
                try {
                    val = Byte.parseByte(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            t = (T) val;

        } else if (clazz.equals(Character.class) || clazz.equals(char.class)) {
            Character val = new Character(' ');
            if (value != null) {
                val = value.length() > 0 ? value.charAt(0) : new Character(' ');
            }
            t = (T) val;

        } else if (clazz.equals(String.class)) {
            String val = "";
            if (value != null) {
                val = value;
            }
            t = (T) val;

        } else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            Boolean val = false;
            if (value != null) {
                val = value.equals("1");
            }
            t = (T) val;

        } else if (clazz.equals(Date.class)) {
            Date dateVal = new Date(0L);
            if (value != null) {
                try {
                    Long time = Long.parseLong(value);
                    dateVal = new Date(time);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            t = (T) dateVal;

        } else {
            //实现其它类型 认为是JSON
            if (value != null) {
//				t = clazz.cast(value);
//                t = JsonUtils.fromSimpleJson(value, clazz);
                t = JSON.parseObject(value, clazz);
            }
        }
        return t;
    }

    public <T> Set<T> toSet(Set<String> set, Class<T> clazz) {
        if (set == null) return Collections.emptySet();
        Set<T> result = new LinkedHashSet<T>(set.size());
        for (String value : set) {
            T t = toType(value, clazz);
            result.add(t);
        }
        return result;
    }

    public <T> List<T> toList(List<String> list, Class<T> clazz) {
        if (list == null) return Collections.emptyList();
        List<T> result = new ArrayList<T>(list.size());
        for (String value : list) {
            T t = toType(value, clazz);
            result.add(t);
        }
        return result;
    }

    public <K, V, L, W> Map<L, W> transformMap(Map<K, V> map, Function<K, L> keyFunction, Function<V, W> valueFunction) {
        Map<L, W> transformedMap = new HashMap();

        for (Map.Entry<K, V> entry : map.entrySet()) {
            transformedMap.put(
                    keyFunction.apply(entry.getKey()),
                    valueFunction.apply(entry.getValue()));
        }

        return transformedMap;
    }

    public <K, V, L> Map<L, V> transformKeys(Map<K, V> map, Function<K, L> keyFunction) {
        return transformMap(map, keyFunction, Functions.<V>identity());
    }

}
