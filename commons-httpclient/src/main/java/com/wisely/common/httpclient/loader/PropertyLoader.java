package com.wisely.common.httpclient.loader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;

import com.wisely.common.httpclient.annotation.PropertyConfigiration;
import com.wisely.common.httpclient.annotation.Value;
import com.wisely.common.httpclient.utils.PropertiesUtils;

/**
 * ClassName: PropertyLoader  
 * (属性加载器)
 * @author zhangtian  
 * @version
 */
public class PropertyLoader {
	public static Object loadProperty(Class<?> clazz) {
		try {
			Object object = clazz.newInstance() ;
			if(clazz.isAnnotationPresent(PropertyConfigiration.class)) {
				// 获取类注解
				PropertyConfigiration propertyConfigiration = clazz.getAnnotation(PropertyConfigiration.class) ;
				String location = propertyConfigiration.location() ;
				if(location.equals("")) {
					throw new RuntimeException("location is blank!") ;
				} else {
					Properties properties = PropertiesUtils.loadProperties(location) ;
					if(properties == null)
						return null ;
						
					Field[] fields = clazz.getDeclaredFields() ;
					for(Field field : fields) {
						if(field.isAnnotationPresent(Value.class)){
							Value value = field.getAnnotation(Value.class) ;
							field.setAccessible(true);
							String v = value.value() ;
							if(v.startsWith("${") && v.endsWith("}")) {
								BeanUtils.setProperty(object, field.getName(), properties.getProperty(v.substring(2, v.length()-1)));
							} else {
								throw new RuntimeException("fomate is wrong!") ;
							}
						}
					}
				}
				return object ;
			}
		} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
