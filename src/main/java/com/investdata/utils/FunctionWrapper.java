package com.investdata.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.json.JSONObject;

/**
 * 小功能性的封装。
 * @author hailong
 *
 */
public class FunctionWrapper {
	/**
	 * 将传入的对象 obj 转换为 Json对象。
	 * obj的属性 即为 json的 key，同时通过反射机制调用obj所有属性的get方法，作为json 的val
	 * @param obj
	 * @param jsonObj
	 * @throws Exception
	 */
	public static void convertObj2Json(Object obj, JSONObject jsonObj) throws Exception {
		if (obj ==  null || jsonObj == null) return;
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field : fields) {
			 PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
			   Method getMethod = pd.getReadMethod();//获得get方法
			   jsonObj.put(field.getName(), getMethod.invoke(obj));
		}
	}
}
