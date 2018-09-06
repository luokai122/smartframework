package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     * 获取当前线程中的ClassLoder
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     * @param className
     * @param isInitialize
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialize){
        Class<?> cls;
        try {
            cls = Class.forName(className,isInitialize,getClassLoader());
        }catch (ClassNotFoundException e){
            LOGGER.error("load class failure",e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取指定包下的所有类
     * 我们需要根据包名并将其转换为文件路径，读取class文件或jar包
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        /*try{


        }catch (Exception e ){

        }*/
        Enumeration<URL> urls = null;
        try {
            urls = getClassLoader().getResources(packageName.replace(".","/"));
        } catch (IOException e) {
            LOGGER.error("get class set failure",e);
            throw new RuntimeException();
        }
        while (urls.hasMoreElements()){
            URL url = urls.nextElement();
            if(url != null ){
                //返回URL的协议
                String protocol = url.getProtocol();
                if(protocol.equals("file")){
                    String packagePath = url.getPath().replaceAll("%20","");
                    addClass(classSet,packagePath,packageName);
                }else if(protocol.equals("jar")){
                    JarURLConnection jarURLConnection = null;
                    try {
                        jarURLConnection = (JarURLConnection) url.openConnection();
                    } catch (IOException e) {
                        LOGGER.error("get class set failure",e);
                        throw new RuntimeException();
                    }
                    JarFile jarFile = null ;
                    if(jarURLConnection !=null){
                        try {
                            jarFile= jarURLConnection.getJarFile();
                        } catch (IOException e) {
                            LOGGER.error("get class set failure",e);
                            throw new RuntimeException();
                        }
                        if(jarFile != null){
                            Enumeration<JarEntry> jarEntries = jarFile.entries();
                            while (jarEntries.hasMoreElements()){
                                JarEntry jarEntry = jarEntries.nextElement();
                                String jarEntryName = jarEntry.getName();
                                if(jarEntryName.endsWith(".class")){
                                    String className = jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).
                                            replaceAll("/",".");
                                    doAddClass(classSet,className);
                                }
                            }
                        }
                    }
                }
            }
        }


        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet,String packagePath,String packageName){
        final File[] files = new File(packagePath).listFiles(
                new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return (file.isFile()&&file.getName().endsWith(".class"))||file.isDirectory();
                    }
                }
        );
        for(File file:files){
            String fileName = file.getName();
            if(file.isFile()){
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                if(StringUtil.isNotEmpty(packageName)){
                    className = packageName + "." + className;
                }
                doAddClass(classSet,className);
            }else {
                String subPackagePath = fileName;
                if(StringUtil.isNotEmpty(packagePath)){
                    subPackagePath = packagePath + "/"+subPackagePath;
                }
                String subPackageName = fileName;
                if(StringUtil.isNotEmpty(subPackageName)){
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
        }

    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className,false);
        classSet.add(cls);
    }
}
