package com.learn.yzh.common.utils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 将Object对象转化成json字符串
 * John.liu
 * 2014-4-1
 */
public class JsonObject {
	private StringBuffer sb;
	private Map<Class, List<String>> excludeMap;
	
	public JsonObject(Object obj, Map<Class, List<String>> typeExcludesFields){
		excludeMap = typeExcludesFields;
		sb = new StringBuffer();
		try {
			handleObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public JsonObject(Object obj){
		sb = new StringBuffer();
		try {
			handleObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleObject(Object obj) throws Exception{
		if(obj == null) {
			sb.append("\"\"");
			return;
		}
		Class clazz = obj.getClass();
		String javaName = clazz.getName();
		if(javaName.startsWith("java.")){
			if(clazz.getSuperclass() == null){
				sb.append("\"").append(obj!=null?obj.toString():"").append("\"");
			}else{
				String superName = clazz.getSuperclass().getName();
				if(superName.startsWith("java.util.")&&superName.endsWith("List")){
					handleList((List)obj);
				}else if(superName.startsWith("java.util.")&&superName.endsWith("Map")){
					handleMap((Map)obj);
				}else{
					sb.append("\"").append(obj!=null?obj.toString():"").append("\"");
				}
			}
		}else{
			sb.append("{");
			Field[] fields = clazz.getDeclaredFields();
			for(int i=0;i<fields.length; i++){
				Field f = fields[i];
				if(excludeMap != null && excludeMap.get(clazz)!=null && excludeMap.get(clazz).contains(f.getName())) continue;
				f.setAccessible(true);
				Object val = f.get(obj);
				sb.append("\"").append(f.getName()).append("\":");
				handleObject(val);
//				sb.append("\"");
				
				if(i < fields.length-1) sb.append(",");
			}
			//删除�?��的�?�?
			if(sb.lastIndexOf(",") == sb.length()-1) sb.deleteCharAt(sb.length()-1);
			sb.append("}");
		}
	}
	
	private void handleList(List list){
		sb.append("[");
		Iterator it = list.iterator();
		try {
			while(it.hasNext()){
				Object obj = it.next();
				handleObject(obj);
				if(it.hasNext()) sb.append(",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sb.append("]");
	}
	
	private void handleMap(Map map){
		sb.append("{");
		Iterator it = map.keySet().iterator();
		try {
			while(it.hasNext()){
				Object key = it.next();
				Object val = map.get(key);
				sb.append("\"");
				if(key != null) sb.append(key.toString());
				sb.append("\":");
				handleObject(val);
				if(it.hasNext()) sb.append(",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("}");
	}
	
	public String toString(){
		return sb.toString();
	}
	
	public static void main(String[] args) {
//		Reservation entity = new Reservation();
//		entity.setClubber("test");
//		entity.setCreate_time("2014-02-10 09:00:00");
//		entity.setId(1);
//		entity.setProduct("ceshi");
//		DataModel dm = new DataModel(Common.STATUS_SUCCESS_CODE, "test message", entity);
//		Map<Class, String[]> fieldExcludes = new HashMap<Class, String[]>(0);
//		fieldExcludes.put(Reservation.class, new String[]{"clubber","id","create_time","states"});
//		Map<Class, List<String>> fieldExcludesMap = new HashMap<Class, List<String>>(fieldExcludes.size());
//		Iterator<Class> it = fieldExcludes.keySet().iterator();
//		while(it.hasNext()){
//			Class key = it.next();
//			String[] arr = fieldExcludes.get(key);
//			List<String> list = new ArrayList<String>(arr.length);
//			for(String s : arr){
//				list.add(s);
//			}
//			fieldExcludesMap.put(key, list);
//		}
//		JsonObject jo = new JsonObject(dm, fieldExcludesMap);
//		System.out.println(jo.toString());
	}
}
