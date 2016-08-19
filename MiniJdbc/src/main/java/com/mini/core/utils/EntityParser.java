package com.mini.core.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.mini.core.EntityInfo;
import com.mini.core.StrongEntity;
import com.mini.core.StrongEntityRowMapper;
import com.mini.core.WeakEntity;
import com.mini.core.WeakEntityRowMapper;
 
public class EntityParser {
    public static void main(String[] args) throws Exception {
        String packageName = "com.mini";
        Set<EntityInfo> results = new HashSet<EntityInfo>();
        getClassName(packageName, true ,results);
        if (results != null) {
            for (EntityInfo result : results) {
                System.out.println(result.getModelClass().getName());
            }
        }
    }
 
    /**
     * 获取某包下所有类
     * @param packageName 包名
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    public static Set<EntityInfo> getClassName(String packageName, boolean isRecursion,Set<EntityInfo> results) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
 
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String protocol = url.getProtocol();
            if (protocol.equals("file")) {
            	getClassNameFromDir(url.getPath(), packageName, isRecursion,results);
            } else if (protocol.equals("jar")) {
                JarFile jarFile = null;
                try{
                    jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                    if(jarFile != null){
                        getClassNameFromJar(jarFile.entries(), packageName, isRecursion);
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }finally{
					try {
						if(jarFile!=null)
							jarFile.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
                 
            }
        } else {
            /*从所有的jar包中查找包名*/
        	getClassNameFromJars(((URLClassLoader)loader).getURLs(), packageName, isRecursion,results);
        }
         
        return results;
    }
 
    /**
     * 从项目文件获取某包下所有类
     * @param filePath 文件路径
     * @param className 类名集合
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    private static Set<EntityInfo> getClassNameFromDir(String filePath, String packageName, boolean isRecursion, Set<EntityInfo> results) {
		File file = new File(filePath);
		File[] files = file.listFiles();
		for (File childFile : files) {
		    if (childFile.isDirectory()) {
		        if (isRecursion) {
		        	results.addAll(getClassNameFromDir(childFile.getPath(), packageName+"."+childFile.getName(), isRecursion , results));
		        }
		    } else {
		        String fileName = childFile.getName();
		        if (fileName.endsWith(".class") && !fileName.contains("$")) {
		        	String entryName = packageName+ "." + fileName.replace(".class", "");
		        	getEntityInfo(entryName,results);
		        }
		    }
		}
        return results;
    }
    
    /**
     * @author sxjun
     * @param entryName
     * @param results
     */
    private static void  getEntityInfo(String entryName, Set<EntityInfo> results){
    	 try {
			Class<?> clazz = Class.forName(entryName);
			 if (clazz != null && !Modifier.isAbstract(clazz.getModifiers())) {
					Annotation[] annotations = clazz.getAnnotations();
					if (annotations != null && annotations.length >= 1) {
						EntityInfo buf = AnnotationUtil.isEntity(annotations,clazz);
						if (buf != null) {
							if(AnnotationUtil.hasColumns(annotations,clazz)){
								if(StrongEntity.class.isInstance(clazz))
									buf.setRowMapper(StrongEntityRowMapper.newInstance(clazz));
								else if(WeakEntity.class.isAssignableFrom(clazz))
									buf.setRowMapper(WeakEntityRowMapper.newInstance(clazz));
							}
							results.add(buf);
						}
					}
			 }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
 
     
    /**
     * @param jarEntries
     * @param packageName
     * @param isRecursion
     * @return
     */
    private static Set<EntityInfo> getClassNameFromJar(Enumeration<JarEntry> jarEntries, String packageName, boolean isRecursion){
        Set<EntityInfo> results = new HashSet<EntityInfo>();
         
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            if(!jarEntry.isDirectory()){
                /*
                 * 这里是为了方便，先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug
                 * (FIXME: 先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug)
                 */
                String entryName = jarEntry.getName().replace("/", ".");
                if (entryName.endsWith(".class") && !entryName.contains("$") && entryName.startsWith(packageName)) {
                    entryName = entryName.replace(".class", "");
                    if(isRecursion){
                    	getEntityInfo(entryName,results);
                    } else if(!entryName.replace(packageName+".", "").contains(".")){
                    	getEntityInfo(entryName,results);
                    }
                }
            }
        }
         
        return results;
    }
     
    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     * @param urls URL集合
     * @param packageName 包路径
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    private static Set<EntityInfo> getClassNameFromJars(URL[] urls, String packageName, boolean isRecursion, Set<EntityInfo> results) {
        for (int i = 0; i < urls.length; i++) {
            String classPath = urls[i].getPath();
             
            //不必搜索classes文件夹
            if (classPath.endsWith("classes/")) {continue;}
 
            JarFile jarFile = null;
            try {
                jarFile = new JarFile(classPath.substring(classPath.indexOf("/")));
            } catch (IOException e) {
                e.printStackTrace();
            }
 
            if (jarFile != null) {
            	results.addAll(getClassNameFromJar(jarFile.entries(), packageName, isRecursion));
            }
        }
         
        return results;
    }
}