package org.credit.iframe.factory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.credit.iframe.entity.Entity;
import org.credit.iframe.entity.Property;
import org.credit.iframe.entity.PropertyType;
import org.credit.iframe.util.FreeMarkerUtil;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class TemplateFactory {
	private static File javaFile = null;  
	public static void main(String[] args) {
		TemplateFactory tf=new TemplateFactory();
//		tf.generateTemplate();
		tf.generateServiceTemplate();
	}
	public void generateTemplate() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("template").getFile());
		Configuration cfg = FreeMarkerUtil.getInstance(file);
		Map<String, Object> root = createDataModel();
		try {
			Template temp = cfg.getTemplate("entity.ftl");
//			ByteArrayOutputStream out=new ByteArrayOutputStream();
//			Writer writer = new OutputStreamWriter(out,"UTF-8");
//			temp.process(root, writer);
//			String result =new String(out.toByteArray(),"UTF-8");
//			System.out.println("----------");
//			System.out.println(result);
			if(javaFile !=null) {
				 Writer javaWriter = new FileWriter(javaFile);
				 temp.process(root, javaWriter);
				 javaWriter.flush();
				 javaWriter.close();
			}
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	public void generateServiceTemplate() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("template").getFile());
		Configuration cfg = FreeMarkerUtil.getInstance(file);
		Map<String, Object> root = createDataModel();
		try {
			Template temp = cfg.getTemplate("service.ftl");
			Template tempImpl = cfg.getTemplate("serviceImpl.ftl");
//			ByteArrayOutputStream out=new ByteArrayOutputStream();
//			Writer writer = new OutputStreamWriter(out,"UTF-8");
//			temp.process(root, writer);
//			String result =new String(out.toByteArray(),"UTF-8");
//			System.out.println("----------");
//			System.out.println(result);
			if(javaFile !=null) {
				 Writer javaWriter = new FileWriter(javaFile);
				 temp.process(root, javaWriter);
				 javaWriter.flush();
				 tempImpl.process(root, javaWriter);
				 javaWriter.flush();
				 javaWriter.close();
			}
		} catch (TemplateNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedTemplateNameException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	/** 
     * 创建数据模型 
     * @return 
     */  
    private static Map<String, Object> createDataModel() {  
        Map<String, Object> root = new HashMap<String, Object>();  
        Entity user = new Entity();  
        user.setJavaPackage("com.study.entity"); // 创建包名  
        user.setServicePackage("com.study.service"); // 创建包名  
        user.setClassName("User");  // 创建类名  
        user.setConstructors(true); // 是否创建构造函数  
        // user.setSuperclass("person");  
          
        List<Property> propertyList = new ArrayList<Property>();  
          
        // 创建实体属性一   
        Property attribute1 = new Property();  
        attribute1.setJavaType("String");  
        attribute1.setPropertyName("name");  
        attribute1.setPropertyType(PropertyType.String);  
          
        // 创建实体属性二  
        Property attribute2 = new Property();  
        attribute2.setJavaType("int");  
        attribute2.setPropertyName("age");  
        attribute2.setPropertyType(PropertyType.Int);  
          
        propertyList.add(attribute1);  
        propertyList.add(attribute2);  
          
        // 将属性集合添加到实体对象中  
        user.setProperties(propertyList);  
          
        // 创建.java类文件  
        File outDirFile = new File("./src-template");  
        if(!outDirFile.exists()){  
            outDirFile.mkdir();  
        }  
          
        javaFile = toJavaFilename(outDirFile, user.getJavaPackage(), user.getClassName());  
         
        root.put("entity", user);  
        return root;  
    }  
    /** 
     * 创建.java文件所在路径 和 返回.java文件File对象 
     * @param outDirFile 生成文件路径 
     * @param javaPackage java包名 
     * @param javaClassName java类名 
     * @return 
     */  
    private static File toJavaFilename(File outDirFile, String javaPackage, String javaClassName) {  
        String packageSubPath = javaPackage.replace('.', '/');  
        File packagePath = new File(outDirFile, packageSubPath);  
        File file = new File(packagePath, javaClassName + ".java");  
        if(!packagePath.exists()){  
            packagePath.mkdirs();  
        }  
        return file;  
    } 
}
