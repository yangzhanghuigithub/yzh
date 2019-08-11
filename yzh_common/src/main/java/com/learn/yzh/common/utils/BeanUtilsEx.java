package com.learn.yzh.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
/**
 * Created by wl on 2017/11/16.
 */
public class BeanUtilsEx {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static WebApplicationContext wac;

    /**
     * 将源列表的对象，转换成新的对象，并封装到一个List里面，返回
     * 主要用于dto转换场景，clazz 类必须有get、set方法
     * @param source
     * @param clazz
     * @return List
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static <E> List<E> beanCopyToList(List source, Class<E> clazz) throws Exception
    {
        if (source == null || clazz == null)
        {
            return null;
        }
        List<E> returnList = new ArrayList<E>();

        for (Object obj : (List<?>) source)
        {
            // 创建一个object
            E m = clazz.newInstance();
            beanCopy(obj, m);
            returnList.add(m);
        }

        return returnList;

    }

    /**
     * 以target为主，两个bean的结构可以不一样，也可以拷贝List对象，
     * @param source
     * @param target
     */
    @SuppressWarnings(
            { "unchecked", "rawtypes" })
    public static void beanCopy(Object source, Object target) throws Exception
    {
        if (source == null || target == null)
        {
            return;
        }
        // 遍历 target 属性，然后去source 里面找

        BeanInfo beanInfoTarget = null;
        try
        {
            beanInfoTarget = Introspector.getBeanInfo(target.getClass());
        }
        catch (IntrospectionException e)
        {
            e.printStackTrace();
            return;
        }
        PropertyDescriptor[] propertieDescritors = beanInfoTarget.getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : propertieDescritors)
        {
            // 目标对象
            Method writeMethod = propertyDescriptor.getWriteMethod();
            Method getMethod = propertyDescriptor.getReadMethod();
            if (writeMethod == null || getMethod == null)
            {
                continue;
            }
            Object objValue = getObjectByPropertyName(source, propertyDescriptor.getName());

            if (objValue == null)
            {
                //				writeMethod.invoke(target, new Object[]
                //				{ objValue });
                continue;
            }

            // 如果目标是一个List 对象
            // 对objValue 进行处理，如果是List 则继续进行复制，以target的为主
            if (objValue instanceof List<?>)
            {
                List toReturnList = new ArrayList();

                for (Object obj : (List<?>) objValue)
                {
                    //					System.out.println(propertyDescriptor.getReadMethod().getReturnType().getName() + ">>>>>>>>>>>>>>>>>" + obj + " >>>>>" + MiiKeeClassUtils.getGenericClass(propertyDescriptor.getReadMethod().getReturnType(), 0).getName());
                    // 创建一个object
                    Object m = (Object) Class.forName(getFieldGenricClass(target, propertyDescriptor.getName()).getName()).newInstance();

                    // 拷贝属性
                    beanCopy(obj, m);

                    // 添加到List
                    toReturnList.add(m);
                }

                writeMethod.invoke(target, new Object[]
                        { toReturnList });

            }
            else
            {

                if (getMethod.getReturnType().getName().startsWith("java"))
                {
                    writeMethod.invoke(target, new Object[]
                            { objValue });
                }
                else
                {

                    // 有属性且不是枚举，则进行递归拷贝
                    if (isHasChildProperty(getMethod.getReturnType()) && !getMethod.getReturnType().isEnum())
                    {
                        Object newObject = Class.forName(getMethod.getReturnType().getName()).newInstance();

                        beanCopy(objValue, newObject);
                    }
                    else
                    {
                        writeMethod.invoke(target, new Object[]
                                { objValue });
                    }

                }

            }

        }

    }

    public static void printObject(Object object)
    {
        if (object == null)
        {
            System.out.println("要打印的对象为空");
            return;
        }

        System.out.println(">>>>>>>>>>>>>>>>" + object.getClass().getName() + "@" + Integer.toHexString(object.hashCode()) + ">>>>>>>>>>>>>>");
        try
        {
            printObject(object, "");
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("<<<<<<<<<<<<<" + object.getClass().getName() + "@" + Integer.toHexString(object.hashCode()) + "<<<<<<<<<<");
    }

    @SuppressWarnings("unchecked")
    private static void printObject(Object object, String tabString) throws Exception
    {
        if (object == null)
        {
            return;
        }
        // 如果是List对象

        if (object.getClass().getName().startsWith("java.lang"))
        {
            System.out.println(tabString + ":" + object.toString());
        }
        else
        {
            if (object instanceof Map<?, ?> || object instanceof List<?> || (isHasChildProperty(object.getClass()) && !object.getClass().isEnum()))
            {

            }
            else
            {
                System.out.println(tabString + ":" + object.toString());
                return;
            }

        }

        if (object instanceof List<?>)
        {

            for (Object obj : (List<?>) object)
            {
                printObject(obj, tabString);
            }

            return;
        }

        // 如果是Map对象
        if (object instanceof Map<?, ?>)
        {
            // 遍历map
            for (String key : ((Map<String, Object>) object).keySet())
            {
                //				System.out.println("当前：：：：" + key);

                Object mapObject = ((Map<String, Object>) object).get(key);

                String nexTabString = tabString;
                if (StringUtils.isNotBlank(nexTabString))
                {
                    nexTabString += ".";
                }

                if (mapObject == null)
                {
                    System.out.println(nexTabString + key + ":null");
                    continue;
                }

                //				System.out.println("当前：：：：" + mapObject.getClass().getName());
                // 如果目标是一个List 对象
                // 对objValue 进行处理，如果是List 则继续进行复制，以target的为主
                if (mapObject.getClass().getName().startsWith("java.lang"))
                {
                    System.out.println(nexTabString + key + ":" + mapObject.toString());
                }
                else
                {

                    nexTabString += key;

                    if (mapObject instanceof Map<?, ?> || mapObject instanceof List<?> || (isHasChildProperty(mapObject.getClass()) && !mapObject.getClass().isEnum()))
                    {

                        printObject(mapObject, nexTabString);
                    }
                    else
                    {
                        System.out.println(nexTabString + ":" + mapObject.toString());
                    }
                }

            }

            return;

        }

        BeanInfo beanInfoTarget = null;
        try
        {
            beanInfoTarget = Introspector.getBeanInfo(object.getClass());
        }
        catch (IntrospectionException e)
        {
            e.printStackTrace();
            return;
        }
        PropertyDescriptor[] propertieDescritors = beanInfoTarget.getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : propertieDescritors)
        {
            // 目标对象
            Method writeMethod = propertyDescriptor.getWriteMethod();
            Method getMethod = propertyDescriptor.getReadMethod();
            if (writeMethod == null || getMethod == null)
            {
                continue;
            }
            Object objValue = getObjectByPropertyName(object, propertyDescriptor.getName());
            String nexTabString = tabString;
            if (StringUtils.isNotBlank(nexTabString))
            {
                nexTabString += ".";
            }
            if (objValue == null)
            {
                System.out.println(nexTabString + propertyDescriptor.getName() + ":null");
                continue;
            }

            // 如果目标是一个List 对象
            {

                // 有属性且不是枚举，则进行递归拷贝
                //				if (getMethod.getReturnType().getName().startsWith("java.lang"))
                //				{
                //					System.out.println(tabString + propertyDescriptor.getName() + ":" + objValue.toString());
                //				}
                //				else
                {

                    nexTabString += propertyDescriptor.getName();

                    if (objValue instanceof Map<?, ?> || objValue instanceof List<?> || (isHasChildProperty(objValue.getClass()) && !objValue.getClass().isEnum()))
                    {
                        printObject(objValue, nexTabString);
                    }
                    else
                    {
                        System.out.println(nexTabString + ":" + objValue.toString());
                    }
                }
            }

        }

    }

    public static boolean isHasChildProperty(Class<?> clazz)
    {
        if (clazz.getName().startsWith("java.lang"))
        {
            return false;
        }

        BeanInfo beanInfoTarget = null;
        try
        {
            beanInfoTarget = Introspector.getBeanInfo(clazz);
        }
        catch (IntrospectionException e)
        {
            e.printStackTrace();
            return false;
        }
        PropertyDescriptor[] propertieDescritors = beanInfoTarget.getPropertyDescriptors();

        if (propertieDescritors != null && propertieDescritors.length > 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 获取List泛型类的泛型
     * @param object
     * @param propertyName
     * @return
     */
    private static Class<?> getFieldGenricClass(Object object, String propertyName)
    {
        Field[] fs = object.getClass().getDeclaredFields();
        for (Field field : fs)
        {
            if (field.getName().equals(propertyName))
            {

                Type type = field.getGenericType();
                if (type == null)
                {
                    return null;
                }
                if (type instanceof ParameterizedType)
                {
                    ParameterizedType pt = (ParameterizedType) type;
                    Class<?> genericClass = (Class<?>) pt.getActualTypeArguments()[0];
                    return genericClass;
                }
            }
        }
        return null;
    }

    public static Object getObjectByPropertyName(Object object, String propertyName)
    {
        if (object == null)
        {
            return null;
        }

        // 没有，需要构建对象
        BeanInfo beanInfo = null;
        try
        {
            beanInfo = Introspector.getBeanInfo(object.getClass());
        }
        catch (IntrospectionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (beanInfo == null)
        {
            return null;
        }

        Map<String, Method> methodMap = new HashMap<String, Method>();
        PropertyDescriptor[] propertieDescritors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertieDescritors)
        {
            if (propertyDescriptor.getName().equals(propertyName))
            {
                Method readMethod = propertyDescriptor.getReadMethod();
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers()))
                {
                    readMethod.setAccessible(true);
                }
                methodMap.put(propertyDescriptor.getName(), readMethod);
                try
                {
                    return readMethod.invoke(object, new Object[0]);
                }
                catch (IllegalAccessException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IllegalArgumentException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (InvocationTargetException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

        return null;

    }

    /**
     * 将数据转成 map ，支持 Entity，Map，List
     * @param fieldList
     * @param dataGrid
     * @return
     */
    public static List<Map<String, Object>> getDtoToMap(String[] fieldList, List<?> list)
    {

        // 进行处理，将object，找出需要返回的数据；
        List<Map<String, Object>> dtoMapList = new ArrayList<Map<String, Object>>();

        if (list != null)
        {
            for (Object object : list)
            {
                Map<String, Object> map = new HashMap<String, Object>();
                Object idObject;
                try
                {
                    idObject = getObjectByName("id", object,fieldList);
                    map.put("id", idObject);
                }
                catch (Exception e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                for (String fieldName : fieldList)
                {
                    try
                    {

                        Object valueObject = getObjectByName(fieldName, object,fieldList);

                        if (valueObject != null)
                        {
                            //System.out.println(string + ":" + valueObject.toString());
                            String[] namesStrings = fieldName.split("[.]");
                            Map<String, Object> tempMap = getObjectMap(fieldName, map);
                            if (tempMap != null)
                            {
                                tempMap.put(namesStrings[namesStrings.length - 1], valueObject);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                dtoMapList.add(map);
            }
        }

        return dtoMapList;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getObjectMap(String nameString, Map<String, Object> parentMap)
    {
        if (StringUtils.isBlank(nameString))
        {
            return parentMap;
        }

        String[] names = nameString.split("[.]");

        if (names.length <= 1)
        {
            return parentMap;
        }
        else
        {
            // 大于1
            Map<String, Object> map = null;
            if (parentMap.get(names[0]) != null && parentMap.get(names[0]) instanceof Map<?, ?>)
            {
                map = (Map<String, Object>) (parentMap.get(names[0]));
            }
            else
            {
                map = new HashMap<String, Object>();
                parentMap.put(names[0], map);
            }

            return getObjectMap(nameString.substring(nameString.indexOf(".") + 1), (Map<String, Object>) (parentMap.get(names[0])));

        }

    }

    private static Map<String, Map<String, Method>> classMethodMap = new HashMap<String, Map<String, Method>>();

    private static Method getMethodFromMap(Object object, String propertyName)
    {
        if (object == null)
        {
            return null;
        }
        if (classMethodMap.get(object.getClass().getName()) == null)
        {
            // 没有，需要构建对象
            BeanInfo beanInfo = null;
            try
            {
                beanInfo = Introspector.getBeanInfo(object.getClass());
            }
            catch (IntrospectionException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (beanInfo == null)
            {
                return null;
            }

            Map<String, Method> methodMap = new HashMap<String, Method>();
            PropertyDescriptor[] propertieDescritors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertieDescritors)
            {

                Method readMethod = propertyDescriptor.getReadMethod();
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers()))
                {
                    readMethod.setAccessible(true);
                }
                methodMap.put(propertyDescriptor.getName(), readMethod);
            }
            classMethodMap.put(object.getClass().getName(), methodMap);
        }

        if (classMethodMap.get(object.getClass().getName()) != null)
        {
            return classMethodMap.get(object.getClass().getName()).get(propertyName);
        }

        return null;

    }

    /**
     * 通过名称获取某个对象的属性值
     * @param name
     * @param object
     * @return
     * @throws Exception
     */
    public static Object getObjectByName(String name, Object object) throws Exception
    {
        return getObjectByName(name, object, null);
    }

    /**
     * 通过名字获取某个对象的属性值，支持通过FieldList进行过滤，用于Dto对象，节省在网络上传输的时间
     * @param name
     * @param object
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Object getObjectByName(String name, Object object,String [] fieldList) throws Exception
    {
        if (StringUtils.isBlank(name) || object == null)
        {
            return null;
        }

        // 如果object是一个map对象
        if (object instanceof Map<?, ?>)
        {
            return ((Map<String, Object>) object).get(name);
        }


        // 所有的属性在这里

        String[] names = name.split("[.]");
        // 找到 names[0]这个object；
        Object toReturnObject = null;

        Method readMethod = getMethodFromMap(object, names[0]);

        if (readMethod == null)
        {
            return null;
        }

        toReturnObject = readMethod.invoke(object, new Object[0]);


        if (names.length <= 1)
        {
            //  2015年4月4日，新增，支持treeGrid的数据Dto自动化，只支持children属性，换别的名称无效
            if(name.equalsIgnoreCase("children"))
            {
                if (toReturnObject instanceof List<?>&&fieldList!=null&&fieldList.length>0)
                {
                    if(toReturnObject!=null&&((List<?>)toReturnObject).size()>0 )
                        toReturnObject = getDtoToMap(fieldList, (List<?>)toReturnObject);
                }
            }
            else {
                // 是别的类型的list，就全部返回
            }

            return toReturnObject;
        }
        else
        {
            if (toReturnObject instanceof List<?>)
            {
                List<Object> toReturnList = new ArrayList<Object>();
                for (Object obj : (List<?>) toReturnObject)
                {
                    toReturnList.add(getObjectByName(name.substring(name.indexOf(".") + 1), obj,fieldList));
                }

                return toReturnList;
                //
                //				Map<String, Object> childMap = new HashMap<String, Object>();
                //				childMap.put(names[1], toReturnList);
                //				return childMap;

            }
            else
            {
                // 以map形式返回
                Object childObject = getObjectByName(name.substring(name.indexOf(".") + 1), toReturnObject,fieldList);

                return childObject;

            }

        }

    }

    /**
     *
     * 本方法适用于两个Object 结构一样
     *
     * 说明：将source中的属性值赋给target中的属性，如果为null或空字符串，不再copy
     * 前提：相关属性是public，且定义了相关的getter，setter方法（内省）
     * 应用：spring的数据绑定配合dao持久化时，如果页面传递为空，再调用attachdirty方法会把数据库里的值全部置null
     *      而且spring的beanUtils类会把所有属性一股脑拷贝，不管是不是空或null，于是乎，这个伟大的方法就诞生了~O(∩_∩)O~
     * @author shown
     * @param source
     * @param target
     */
    public static void copyIgnoreNulls(Object source, Object target)
    {
        BeanInfo beanInfo = null;
        try
        {
            beanInfo = Introspector.getBeanInfo(source.getClass());
        }
        catch (IntrospectionException e)
        {
            e.printStackTrace();
            return;
        }
        PropertyDescriptor[] propertieDescritors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertieDescritors)
        {

            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();

            if (writeMethod != null && readMethod != null)
            {
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers()))
                    readMethod.setAccessible(true);
                Object value = null;
                try
                {
                    value = readMethod.invoke(source, new Object[0]);
                }
                catch (Exception e)
                {
                    //logger.debug("------------>getter方法读取失败");
                    continue;
                }
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers()))
                    writeMethod.setAccessible(true);
                if (value != null)
                {
                    //	System.out.println("------------>当前非空属性：" + propertyDescriptor.getName()+value.toString());
                    // 判断value 是否是一个IdEntity对象，如果是，则需要从数据库取出新的值然后再进行更新；

                    // 判断value是否有属性，如果目标是没有
                    //					if (!value.getClass().getName().startsWith("java"))
                    if (isHasChildProperty(value.getClass()) && !value.getClass().isEnum())
                    {
                        try
                        {
                            Object childObject = readMethod.invoke(target, new Object[0]);
                            if (childObject == null)
                            {
                                writeMethod.invoke(target, new Object[]
                                        { value });
                            }
                            else
                            {
                                copyIgnoreNulls(value, childObject);
                            }

                        }
                        catch (Exception e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    else
                    {

                        try
                        {
                            writeMethod.invoke(target, new Object[]
                                    { value });
                            //						logger.debug("------------>属性拷贝：" + value);
                        }
                        catch (Exception e)
                        {
                            ///	logger.debug("------------>setter方法写入失败");
                            e.printStackTrace();
                            continue;
                        }
                    }

                }
                else
                {
                    //logger.debug("------------>属性为空，不再拷贝");
                }
            }
        }
    }

    /**
     * 从spring 里面按 名字 获取Bean
     * @param name
     * @return
     */
    public static Object getBeanFromSpring(String name)
    {
        if (wac == null)
        {
            wac = ContextLoader.getCurrentWebApplicationContext();

        }

        if (wac != null)
        {
            // 测试容器里面到底有多少个Bean，目前测试有113个
//			System.out.println("SPRING 容器：" + wac.toString());
//			System.out.println("==============开始================");
//			int i=1;
//			for (String name2 : wac.getBeanDefinitionNames())
//			{
//				System.out.println((i++)+":[ioc]" + name2 + "    " + wac.getBean(name2));
//			}
//			System.out.println("==============结束================");
            Object objectBean = wac.getBean(name);
            return objectBean;
        }
        else
        {
           // logger.info("spring容器还没有加载好，不能查找Bean");
        }
        return null;

    }
}
