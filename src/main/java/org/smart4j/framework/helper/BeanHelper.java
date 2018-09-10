package org.smart4j.framework.helper;


import org.smart4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 需要获取所有被Smart框架管理Bean类
 * 需要调用ClassHelper类的getBeanClassSet,然后循环调用ReflectionUtil类
 * newInstance方法，然后实例化对象，最后将创建的对象放入一个静态Map<Class<?>,Object>中
 * 这样我们能随时获取Map,获取所对应的value
 *
 * BeanHelper助手类
 * @author StrangeLuo
 */
public final class BeanHelper {

    /**
     * 定义Bean映射
     */
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> beanClass :beanClassSet){
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
    }

    /**
     *获取Bean映射
     */
    public static  Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class:"+cls);
        }
        return (T) BEAN_MAP.get(cls);
    }


}
