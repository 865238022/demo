package com.resource;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface ClassReader {
	Set<ClassInfo> getClass(String packageName, boolean recursive);//ͨ��ȫ�����ķ���ʵ��
	
	Set<ClassInfo> getClass(String packageName, Class<?> parent, boolean recursive);
	
	Set<ClassInfo> getClassByAnnotation(String packageName, Class<? extends Annotation> annotation, boolean recursive);
	
	Set<ClassInfo> getClassByAnnotation(String packageName, Class<?> parent, Class<? extends Annotation> annotation, boolean recursive);
}
