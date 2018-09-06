//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.smart4j.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropsUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    public PropsUtil() {
    }

    public static Properties loadProps(String propsPath) {
        Properties props = new Properties();
        InputStream is = null;

        try {
            if (StringUtil.isEmpty(propsPath)) {
                throw new IllegalArgumentException();
            }

            String suffix = ".properties";
            if (propsPath.lastIndexOf(suffix) == -1) {
                propsPath = propsPath + suffix;
            }

            is = ClassUtil.getClassLoader().getResourceAsStream(propsPath);
            if (is != null) {
                props.load(is);
            }
        } catch (Exception var11) {
            logger.error("加载属性文件出错！", var11);
            throw new RuntimeException(var11);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException var10) {
                logger.error("释放资源出错！", var10);
            }

        }

        return props;
    }

    public static Map<String, String> loadPropsToMap(String propsPath) {
        Map<String, String> map = new HashMap();
        Properties props = loadProps(propsPath);
        Iterator i$ = props.stringPropertyNames().iterator();

        while(i$.hasNext()) {
            String key = (String)i$.next();
            map.put(key, props.getProperty(key));
        }

        return map;
    }

    public static String getString(Properties props, String key) {
        String value = "";
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }

        return value;
    }
}
