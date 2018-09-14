package org.smart4j.framework;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.IocHelper;
import org.smart4j.framework.util.ClassUtil;

/**
 * 加载相应的Helper类
 * 实际上我们第一次访问类的时候就会加载静态块
 * 这里只是为了让加载更集中一点
 * 才写了HelperLoader类
 * @author StrangeLuo
 */
public class HelperLoader {

    public static void init(){
        Class<?> [] classList = {
                BeanHelper.class,
                ConfigHelper.class,
                ControllerHelper.class,
                IocHelper.class
        };
        for(Class<?> cls :classList){
            ClassUtil.loadClass(cls.getName(),true);
        }


    }

}
