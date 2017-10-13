package com.resource;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class AbstractClassReader  implements ClassReader{
	
	public Set<ClassInfo> getClass(String packageName) {
		return this.getClassByAnnotation(packageName, null, null, true);
	}
	
	@Override
	public Set<ClassInfo> getClass(String packageName, boolean recursive) {
		return this.getClassByAnnotation(packageName, null, null, recursive);
	}

	/**
	 * Ĭ��ʵ�����ļ���ʽ�Ķ�ȡ
	 */
	@Override
	public Set<ClassInfo> getClass(String packageName, Class<?> parent, boolean recursive) {
        return this.getClassByAnnotation(packageName, parent,  null, recursive);
	}
	
	/**
	 * ����������ȡclass
	 * @param packageName
	 * @param packagePath
	 * @param parent
	 * @param annotation
	 * @param recursive
	 * @return
	 */
	private Set<ClassInfo> findClassByPackage(final String packageName, final String packagePath, 
			final Class<?> parent,  final Class<? extends Annotation> annotation, 
			final boolean recursive, Set<ClassInfo> classes) throws ClassNotFoundException {
		
		// ��ȡ�˰���Ŀ¼ ����һ��File
        File dir = new File(packagePath);
        // ��������ڻ��� Ҳ����Ŀ¼��ֱ�ӷ���
        if ((!dir.exists()) || (!dir.isDirectory())) {
//        	LOGGER.warn("The package [{}] not found.", packageName);
        }
        // ������� �ͻ�ȡ���µ������ļ� ����Ŀ¼
        File[] dirfiles = accept(dir, recursive);
        // ѭ�������ļ�
        if(null != dirfiles && dirfiles.length > 0){
        	for (File file : dirfiles) {
                // �����Ŀ¼ �����ɨ��
                if (file.isDirectory()) {
                	findClassByPackage(packageName + "." + file.getName(), file.getAbsolutePath(), parent, annotation, recursive, classes);
                } else {
                    // �����java���ļ� ȥ�������.class ֻ��������
                    String className = file.getName().substring(0, file.getName().length() - 6);
//                    Class<?> clazz = classLoader.defineClassByName(packageName + '.' + className);
                    	Class<?> clazz = Class.forName(packageName + '.' + className);
					if(null != parent && null != annotation){
						if(null != clazz.getSuperclass() && clazz.getSuperclass().equals(parent) && 
								null != clazz.getAnnotation(annotation)){
							classes.add(new ClassInfo(clazz));
						}
						continue;
					}
					if(null != parent){
						if(null != clazz.getSuperclass() && clazz.getSuperclass().equals(parent)){
							classes.add(new ClassInfo(clazz));
						} else {
							if(null != clazz.getInterfaces() && clazz.getInterfaces().length > 0 && clazz.getInterfaces()[0].equals(parent)){
								classes.add(new ClassInfo(clazz));
							}
						}
						continue;
					}
					if(null != annotation){
						if(null != clazz.getAnnotation(annotation)){
							classes.add(new ClassInfo(clazz));
						}
						continue;
					}
					classes.add(new ClassInfo(clazz));
                }
            }
        }
        return classes;
    }
	
	/**
	 * �����ļ�����
	 * @param file
	 * @param recursive
	 * @return
	 */
	private File[] accept(File file, final boolean recursive){
		File[] dirfiles = file.listFiles(new FileFilter() {
            // �Զ�����˹��� �������ѭ��(������Ŀ¼) ��������.class��β���ļ�(����õ�java���ļ�)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
		return dirfiles;
	}
	
	@Override
	public Set<ClassInfo> getClassByAnnotation(String packageName, Class<? extends Annotation> annotation, boolean recursive) {
		return this.getClassByAnnotation(packageName, null, annotation, recursive);
	}
	
	@Override
	public Set<ClassInfo> getClassByAnnotation(String packageName, Class<?> parent, Class<? extends Annotation> annotation, boolean recursive) {
//		Assert.notBlank(packageName);
		Set<ClassInfo> classes = new HashSet<>();
        // ��ȡ�������� �������滻
        String packageDirName = packageName.replace('.', '/');
        // ����һ��ö�ٵļ��� ������ѭ�����������Ŀ¼�µ�URL
        Enumeration<URL> dirs;
        try {
            dirs = this.getClass().getClassLoader().getResources(packageDirName);
            // ѭ��������ȥ
            while (dirs.hasMoreElements()) {
                // ��ȡ��һ��Ԫ��
                URL url = dirs.nextElement();
		    	try {
					// ��ȡ��������·��
		    		String filePath = new URI(url.getFile()).getPath();
					Set<ClassInfo> subClasses = findClassByPackage(packageName, filePath, parent, annotation, recursive, classes);
					if(subClasses.size() > 0){
						classes.addAll(subClasses);
					}
				} catch (URISyntaxException e) {
				}  
            }
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
//			throw new ClassReaderException(e);
		}
        return classes;
	}

}
