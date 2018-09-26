package com.shixing.myjsonparser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by shixing on 2018/8/14.
 */

public class MyFastJson {

    public static final int JSON_ARRAY=1;

    public static  final int JSON_OBJECT=2;

    public static final int JSON_ERRO=3;


    /**
     * =======================下面的代码都是将json字符串转为对象相关========================
     * 暴露API  给调用层调用
     * @param json
     * @param clazz
     * @return
     */
    public  static Object pareseObject(String json,Class clazz)
    {
        Object object=null;
        Class<?> jsonClass=null;
        //JSONArray 类型
        if(json.charAt(0)=='[')
        {
            try {
                object=toList(json,clazz);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(json.charAt(0)=='{')
        {
            try {
                JSONObject jsonObject=new  JSONObject(json);
                //反射得到最外层的model   作为返回值返回  一定要有空的构造方法 User
                object=clazz.newInstance();
                /*
                得到的最外层的key集合

                 */
                Iterator<?> iterator=jsonObject.keys();
                //遍历集合
                while (iterator.hasNext())
                {
                    String key= (String) iterator.next();
                    Object fieldValue=null;
                    //得到当前clazz类型的所有成员变量
                    List<Field>  fields=getAllFields(clazz,null);
                    for (Field field:fields)
                    {
                        //将key和成员变量进行匹配
                        if(field.getName().equalsIgnoreCase(key))
                        {
                            field.setAccessible(true);
                            //得到 key所对应的值   值 可以基本类型  类类型
                            fieldValue=getFieldValue(field,jsonObject,key);
                            if(fieldValue!=null)
                            {
                                field.set(object,fieldValue);
                            }
                            field.setAccessible(false);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    /**
     * 得到当前的value值
     * @param field
     * @param jsonObject
     * @param key
     * @return
     */
    private static Object getFieldValue(Field field, JSONObject jsonObject, String key) throws JSONException {
        Object fieldValue=null;
        //得到当前成员变量类型
        Class<?> fieldClass=field.getType();
        if(fieldClass.getSimpleName().toString().equals("int")
                ||fieldClass.getSimpleName().toString().equals("Integer"))
        {
            fieldValue=jsonObject.getInt(key);
        }else  if(fieldClass.getSimpleName().toString().equals("String"))
        {
            fieldValue=jsonObject.getString(key);
        }else  if(fieldClass.getSimpleName().toString().equals("double"))
        {
            fieldValue=jsonObject.getDouble(key);
        }else  if(fieldClass.getSimpleName().toString().equals("boolean")
                )
        {
            fieldValue=jsonObject.getBoolean(key);
        }else  if(fieldClass.getSimpleName().toString().equals("long")
                )
        {
            fieldValue=jsonObject.getLong(key);
        }else
        {
            //判断集合类型 和对象类型 jsonValue 代表完整的json字符串  里面一层
            String jsonValue=jsonObject.getString(key);
            switch (getJSONType(jsonValue))
            {
                case JSON_ARRAY:
                    //List<User>
                    Type fieldType=field.getGenericType();
                    if(fieldType instanceof ParameterizedType)
                    {
                        ParameterizedType parameterizedType= (ParameterizedType) fieldType;
                        //List 当前类 所实现的泛型  User
                        Type[]  fieldArgType=parameterizedType.getActualTypeArguments();
                        for(Type type:fieldArgType)
                        {
                            //fieldArgClass  代表着User.class
                            Class<?> fieldArgClass= (Class<?>) type;
                            fieldValue=toList(jsonValue,fieldArgClass);
                        }
                    }
                    break;
                case JSON_OBJECT:
                    //剥下来的json字符串   fieldClass 成员变量类型
                    fieldValue=pareseObject(jsonValue,fieldClass);
                    break;
                case JSON_ERRO:

                    break;
            }

        }
        return fieldValue;
    }

    /**
     * 获取当前json字符串的类型
     *
     * @param jsonValue
     * @return
     */
    private static int getJSONType(String jsonValue) {
        char firstChar=jsonValue.charAt(0);
        if(firstChar=='{')
        {
            return JSON_OBJECT;
        }else if(firstChar=='[')
        {
            return JSON_ARRAY;
        }else
        {
            return JSON_ERRO;
        }
    }

    /**
     * 解析JsonArray数组
     * @param json
     * @param clazz
     * @return
     */
    private static Object toList(String json, Class clazz) throws JSONException {
        List<Object> list=null;
        JSONArray jsonArray=new JSONArray(json);
        list=new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++)
        {
            //拿到JSON字符串
            String jsonValue=jsonArray.getJSONObject(i).toString();
            switch (getJSONType(jsonValue))
            {
                case JSON_ARRAY:
                    //外层JSONArray 嵌套里面JSONArray
                    List<?> infoList=(List<?>) toList(jsonValue,clazz);
                    list.add(infoList);
                    break;
                case JSON_OBJECT:
                    list.add(pareseObject(jsonValue,clazz));
                    break;
                case JSON_ERRO:

                    break;


            }
        }
        return list;
    }


    /**
     * =======================下面的代码都是将对象转为json字符串相关========================
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        StringBuffer sb = new StringBuffer();
        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            sb.append("[");
            for(int i = 0; i < list.size(); ++i) {
                addObjectToJson(sb, list.get(i));
                if (i < list.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
        } else {
            addObjectToJson(sb, obj);
        }
        return sb.toString();
    }

    /**
     * 解析单独的JSONObject类型
     * 递归准备
     * @param jsonBuffer
     * @param o
     */
    private static void addObjectToJson(StringBuffer jsonBuffer, Object o) {
        jsonBuffer.append("{");

        List<Field> fields=new ArrayList<>();
        getAllFields(o.getClass(),fields);
        for(int i=0;i<fields.size();i++)
        {
            //代表getMethod方法
            Method method=null;
            Field field=fields.get(i);
            //代表成员变量的值  张三
            Object  fieldValue=null;

            String fieldName=field.getName();
            //fieldName  成员名称 name   a 97 A 65   getName
            String methodName="get"+((char)(fieldName.charAt(0)-32)+fieldName.substring(1));

            try {
                //拿到Method对象
                method=o.getClass().getMethod(methodName);
            } catch (NoSuchMethodException e) {
                if(!fieldName.startsWith("is"))
                {
                    methodName="is"+((char)(fieldName.charAt(0)-32)+fieldName.substring(1));
                    //拿到Method对象
                }
                try {
                    method=o.getClass().getMethod(methodName);
                } catch (NoSuchMethodException e1) {
                    replaceChar(i,fields,jsonBuffer);
                    continue;
                }
            }
            //拿到了成员变量对应的get方法
            if(method!=null)
            {
                try {
                    //Integer 对象类型
                    fieldValue =method.invoke(o);
                } catch (Exception e) {
                    replaceChar(i,fields,jsonBuffer);
                    continue;
                }
            }
            if(fieldValue!=null)
            {
                jsonBuffer.append("\"");
                jsonBuffer.append(fieldName);
                jsonBuffer.append("\":");
                if(fieldValue instanceof Integer
                        || fieldValue  instanceof  Double||
                        fieldValue instanceof Long||
                        fieldValue instanceof Boolean)
                {
                    jsonBuffer.append(fieldValue.toString());
                }else if(fieldValue instanceof String)
                {
                    jsonBuffer.append("\"");
                    jsonBuffer.append(fieldValue.toString());
                    jsonBuffer.append("\"");
                }else if(fieldValue instanceof  List<?>)
                {
                    addListToBuffer(jsonBuffer,fieldValue);
                }else if(fieldValue instanceof Map){
                    /**
                     * Map String String
                     * String Object
                     */
                }else
                {
                    addObjectToJson(jsonBuffer,fieldValue);
                }
                jsonBuffer.append(",");
            }
            /**
             * 1 要
             * 2
             * 替换最后的逗号
             */
            if(i==fields.size()-1&&jsonBuffer.charAt(jsonBuffer.length()-1)==',')
            {
                //删除最后一个逗号
                jsonBuffer.deleteCharAt(jsonBuffer.length()-1);
            }
        }
        jsonBuffer.append("}");
    }
    public static  void replaceChar(int i,List<Field> fields,StringBuffer jsonBuffer)
    {
        if(i==fields.size()-1&&jsonBuffer.charAt(jsonBuffer.length()-1)==',')
        {
            //删除最后一个逗号
            jsonBuffer.deleteCharAt(jsonBuffer.length()-1);
        }
    }

    /**
     * 解析集合类型数据
     * @param jsonBuffer
     * @param fieldValue
     * neng  1
     * 不能 2
     */
    private static void addListToBuffer(StringBuffer jsonBuffer, Object fieldValue) {
        //前面已经判断了fieldValue的类型
        List<?> list= (List<?>) fieldValue;
        jsonBuffer.append("[");
        for (int i=0;i<list.size();i++)
        {
            //遍历集合中的每一个元素
            addObjectToJson(jsonBuffer,list.get(i));
            if(i<list.size()-1)
            {
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("]");
    }

    /**
     * 获取当前Class  所有的成员变量 Field
     * 父类的Class  成员变量
     *  Object 类型
     *  final 修饰的成员变量
     *  会 1
     *  不会 2
     *   递归方法
     * @param aClass
     * @param fields
     */
    private static List<Field> getAllFields(Class<?> aClass, List<Field> fields) {
        if(fields==null)
        {
            fields=new ArrayList<>();
        }
        //排除Object类型
        if(aClass.getSuperclass()!=null)
        {
            //拿到当前Class的所有成员变量的Field
            Field[] fieldsSelf=aClass.getDeclaredFields();
            for(Field field:fieldsSelf)
            {
                //排除final修饰的成员变量
                if(!Modifier.isFinal(field.getModifiers()))
                {
                    fields.add(field);
                }
            }
            getAllFields(aClass.getSuperclass(),fields);
        }
        return fields;
    }
}
