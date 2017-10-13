package com.classPath;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.annotation.*;
import com.exception.NoSuchBeanException;

import java.util.Set;

public abstract class AbstractClassPathContextApplication implements BeanFactory{
	protected static Map<String,Object> IOC = new HashMap<>(16);//IOC容器
	protected static List<String> objects = new ArrayList<>();
	
	@Override
	public Object getBean(String name) {
		return IOC.get(name);
	}

	@Override
	public Object getBean(Class<?> clazz) {
		return null;
	}
	
	protected  void annotationReflect(String name){
		Object bean = null;
		try {
			//还没有为字段实例化
			bean = IOC.get(name);
            //获取其全部的字段描述    
            Field[] fields = bean.getClass().getDeclaredFields();  
            for(Field field : fields){
                if(field!=null && field.isAnnotationPresent(Autowired.class)) {
                	String fieldName = field.getName();
                	//得到该字段上的注解类型
                	Autowired resource = field.getAnnotation(Autowired.class);
                	Qualifier qualifier = field.getAnnotation(Qualifier.class);
                	boolean flag = resource.required();
                	String value = qualifier.value();
                	
                	field.setAccessible(true);
                	if(flag) {
        				if(null != value) {
        					Object obj = IOC.get(value);
            				if(obj==null) {
            					throw new NoSuchBeanException(" 没有发现这样一个bean" +fieldName);
            				}
            				field.set(bean, obj);
        				}else {
        					Object obj = IOC.get(fieldName);
            				if(obj==null) {
            					throw new NoSuchBeanException(" 没有发现这样一个bean" +fieldName);
            				}
            				field.set(bean, obj);
        				}
        			}   
                }    
            }    
        } catch (Exception e) {    
        	e.printStackTrace();
        }
	}
	
	/**
	 * 反射实例化对象  这里要优化  有可能出现问题
	 * @param name
	 * @param clazz
	 */
	protected static  void reflect(String name,Map<String,String> map) throws Exception{
		Object obj = IOC.get(name);
		
		Set<Entry<String, String>> set = map.entrySet();
		set.forEach(key -> {
			String k =key.getKey();
			String V = key.getValue();
				Field field = null;	
					try {
					field = obj.getClass().getDeclaredField(k);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				
			field.setAccessible(true);
			Class<?> clz = field.getType();
		
			if(clz.getName().contains("String")) {
				try {
					field.set(obj, V);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(clz.getName().contains("int")){
				int value = Integer.parseInt(V);
				try {
					field.set(obj, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				try {
					field.set(obj, IOC.get(k));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	});
}
	
}
