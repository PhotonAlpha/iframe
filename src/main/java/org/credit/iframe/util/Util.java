package org.credit.iframe.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Util {
	public final static String NOTFOUND="{0} not found !";
	public static boolean isEmpty(String str) {
		if(str ==null || str.equalsIgnoreCase("") || str.trim().equals("")) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isEmpty(Long str) {
		if(str ==null || str==0L) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isEmpty(Integer str) {
		if(str ==null ) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isEmpty(List<?> str) {
		if(str ==null || str.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	public static String placeholder(ERROR error,String...strs) {
		return MessageFormat.format(error.displayError(), new Object[]{strs});
	}
	
	@SuppressWarnings("unchecked")
	public static <T>List<T> iteratorToList(Iterable<T> iteratable){
		if(iteratable !=null) {
			Iterator<?> ites = iteratable.iterator();
			List<T> list = new ArrayList<T>();
			while(ites.hasNext()) {
				list.add((T) ites.next());
			}
			if(list.isEmpty())return null;
			return list;
		}
		return null;
	}
	/**
	 * 
	 * copy a object to other object
	 */
	public static <T>Object copyEntity(T origion,T target){
		Object obj = null;
		try {
			if(target instanceof Class) {
				obj = ((Class) target).newInstance();
			}else {
				if(origion ==null||target==null) return target;
				else obj= target;
			}
			Field[] origFileds = origion.getClass().getDeclaredFields();
			Field[] targetFileds = obj.getClass().getDeclaredFields();
			Set<String> attributes=new HashSet<String>();
			for(Field file : targetFileds) {
				attributes.add(file.getName());
			}
			for(Field file: origFileds) {
				file.setAccessible(true);
				Object value = file.get(origion);
				String fileName=file.getName();
				if(attributes.contains(fileName)) {
					Field tarFiled = obj.getClass().getDeclaredField(fileName);
					if(tarFiled.getType().equals(file.getType())) {
						tarFiled.setAccessible(true);
						tarFiled.set(obj, value);
					}
				}
			}
			return obj;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return obj;
	}
	 
	 public static Set<String> getAttributes(Class clazz){
		 Set<String> sets =new HashSet<String>();
		 Field[] fields = clazz.getDeclaredFields();
		 for(Field field :fields) {
			 sets.add(field.getName());
		 }
		 return sets;
	 }
	 @SuppressWarnings("unchecked")
	 public static Object JsonToObject(String str,Class clazz) {
		 ObjectMapper mapper=new ObjectMapper();
		 try {
			return mapper.readValue(str, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return null;
	 }
	 public static Map<String,String> JsonToMap(String str) {
		 ObjectMapper mapper=new ObjectMapper();
		 try {
			 return mapper.readValue(str, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return null;
	 }
	 public static List<?> JsonToArray(String str,Class clazz) {
		 ObjectMapper mapper=new ObjectMapper();
		 try {
			ArrayNode arraynode = mapper.readValue(str, ArrayNode.class);
			Iterator<JsonNode> ites = arraynode.iterator();
//			Object obj= clazz.newInstance();
			List<Object> list=new ArrayList<Object>();
			while(ites.hasNext()) {
				JsonNode node = ites.next();
				Object target = mapper.treeToValue(node, clazz);
				list.add(target);
			}
			return list;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return null;
	 }
	 
	 
	 public static String objectToJson(Object obj) {
		 ObjectMapper mapper=new ObjectMapper();
		 String res="";
		try {
			res = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		 return res;
	 }
}
