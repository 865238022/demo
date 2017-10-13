package com.classPath;

/**
 * bean工厂
 * @author 竹
 *
 */
public interface BeanFactory {
	/**
	 * 根据name得到bean
	 * @param name
	 * @return
	 */
	Object getBean(String name);
	
	/**
	 * 根据类型得到bean
	 * @param clazz
	 * @return
	 */
	Object getBean(Class<?> clazz);
}
