package org.smart4j.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * 依赖注入助手类
 */
public final class IocHelper {

    /**
     * 实例化IocHelper，进行初始化
     */
    static {
        //获取所有的Bean类与Bean实例之间的映射关系
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty((Collection<?>) beanMap)){
            //遍历Bean Map
            for(Map.Entry<Class<?>,Object> beanEntry:beanMap.entrySet()){
                //从beanMap中获取Bean类与Bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取Bean类定义的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if(ArrayUtils.isNotEmpty(beanFields)){
                    //遍历Bean Field
                    for(Field field:beanFields){
                        //判断当前Bean Filed是否带有Inject注解
                        if(field.isAnnotationPresent(Inject.class)){
                            Class<?> beanFiledClass = field.getType();
                            Object beanFieldInstance = beanMap.get(beanFiledClass);
                            if(beanFieldInstance!=null){
                                //通过反射初始化BeanField
                                ReflectionUtil.setField(beanInstance,field,beanFieldInstance);
                            }
                        }
                    }
                }

            }
        }
    }



}
