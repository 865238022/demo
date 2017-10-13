package com.classPath;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.annotation.Component;
import com.annotation.Service;
import com.resource.ClassInfo;
import com.resource.ClassPathClassReader;

public class ClassPathContextApplication extends AbstractClassPathContextApplication {
	private ClassPathClassReader c = new ClassPathClassReader();
	
	public ClassPathContextApplication(String location) {
		try {
			Parser(location);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析配置文件
	 * 
	 * @param location
	 * @throws Exception
	 */
	private void Parser(String location) throws Exception {
		String xmlpath = location; // 解析路径
		SAXBuilder builder = new SAXBuilder();// 使用默认的解析器
		Document document = builder.build(xmlpath);
		Element element = document.getRootElement();
		/*
		 * 判断是不是开启了annotation
		 */
		Element annotation = element.getChild("annotation");
		if (annotation != null) {
			String oPackage = annotation.getAttributeValue("package");
			ScanAnnotation(oPackage);
		}
		Map<String,String> map = new HashMap<>();
		Iterator<Element> iter = element.getChildren("bean").iterator();
		while(iter.hasNext()) {
			Element ele = iter.next();
			String name = ele.getAttributeValue("name");
			String clazz = ele.getAttributeValue("class");
			
			Iterator<Element> it = ele.getChildren().iterator();
			while(it.hasNext()) {
				Element e = it.next();
				String key = e.getAttributeValue("key");
				String value = e.getAttributeValue("value");
				map.put(key, value);
			}
			setBean(name,clazz);//
			objects.add(name);
		}
		objects.forEach(name ->{
			try {
				reflect(name,map);
			} catch (Exception e) {
				e.printStackTrace();
			};
		});
	}

	private void ScanAnnotation(String oPackage) {
		Set<ClassInfo> s = c.getClass(oPackage);
		s.forEach(classInfo -> {
			Class<?> claz = classInfo.getClazz();
			if (!claz.isInterface() && !Modifier.isAbstract(claz.getModifiers())) {
				Service service = claz.getAnnotation(Service.class);
				Component component = claz.getAnnotation(Component.class);
				if (null != service || null != component) {
					if (null != service || null != component) {
						//annotation 的反射 com.test.Person  Person
						String name = "";
						if(service!=null && service.value().length()!=0) {
							name = service.value();
						}else if(component!=null && component.value().length()!=0) {
							name = component.value();
						}else {
							name = claz.getSimpleName();
							name = name.substring(0,1).toLowerCase() + name.substring(1);
						}
						//					   com.test.Car《====》wudi
//						System.out.println(claz.getName()+"《====》"+name);
						setBean(name,claz.getName());
						objects.add(name);
					}
				}
			}
		});
		
		objects.forEach(name ->{
			annotationReflect(name);
		});
		
	}
	
	private void setBean(String name,String className) {
		Object bean =null;
		try {
			bean = Class.forName(className).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(null!=IOC.put(name, bean)){
			System.out.println("配置文件找不到这样的bean" + name);
		}
	}

}
