package org.credit.iframe.entity;

import java.util.List;

public class Entity {
	private String servicePackage;
	private String javaPackage;
	private String className;
	private String superclass;
	private String implementsclass;
	private List<Property> properties;
	private boolean constructors;
	public String getJavaPackage() {
		return javaPackage;
	}
	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}
	
	public String getServicePackage() {
		return servicePackage;
	}
	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSuperclass() {
		return superclass;
	}
	public void setSuperclass(String superclass) {
		this.superclass = superclass;
	}
	public String getImplementsclass() {
		return implementsclass;
	}
	public void setImplementsclass(String implementsclass) {
		this.implementsclass = implementsclass;
	}
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	public boolean isConstructors() {
		return constructors;
	}
	public void setConstructors(boolean constructors) {
		this.constructors = constructors;
	}
	
	
}
