package com.classPath;

/**
 * bean����
 * @author ��
 *
 */
public interface BeanFactory {
	/**
	 * ����name�õ�bean
	 * @param name
	 * @return
	 */
	Object getBean(String name);
	
	/**
	 * �������͵õ�bean
	 * @param clazz
	 * @return
	 */
	Object getBean(Class<?> clazz);
}
