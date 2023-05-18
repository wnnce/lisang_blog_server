package com.zeroxn.blog.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * @author ran
 * @date
 * 项目工具类
 */
@Slf4j
public class BaseUtil {
    /**
     * 生成随机6位验证码
     * @return 返回生成的验证码
     */
    public static String randomCode(){
        Integer code = new Random().nextInt(100000, 999999);
        return String.valueOf(code);
    }

    /**
     * 检查 status 是否符合要求
     * @param status 传入到参数
     * @return 参数为0或者1返回true
     */
    public static boolean checkStatus(Integer status){
        return status == 0 || status == 1;
    }

    /**
     * 通过反射检查指定类中指定字段是否为空 不能判断父类中的字段
     * @param obj 指定类
     * @param fieldNames 指定字段名称 可以是多个
     * @return 有字段为空即返回true
     */
    public static boolean checkObjectFieldIsNull(Object obj, String... fieldNames) {
        Class objClass = obj.getClass();
        for (String filedName : fieldNames){
            try{
                Field objFiled = objClass.getDeclaredField(filedName);
                objFiled.setAccessible(true);
                if (objFiled.get(obj) == null || checkObjectIsEmpty(objFiled)){
                    return true;
                }
            }catch (NoSuchFieldException ns){
                log.warn(ns.getMessage() + "\t" + filedName + "字段不存在");
                //如果获取不到 可以通过getSuperClass来判断是否有父类 再通过父类进行判断 懒得写了 项目两个接口入参有父类
                // 直接使用 = null 判断了
            }catch (IllegalAccessException il){
                log.warn(il.getMessage() + "获取" + objClass.getName() + "类中" + filedName + "字段不存在的参数出错");
            }
        }
        return false;
    }

    /**
     * 检查String类的参数是否位empty
     * @param field 字段
     * @return 返回true或false
     */
    private static boolean checkObjectIsEmpty(Object field){
        if (field.getClass().equals(String.class)){
            return field.toString().length() == 0;
        }
        return false;
    }
}
