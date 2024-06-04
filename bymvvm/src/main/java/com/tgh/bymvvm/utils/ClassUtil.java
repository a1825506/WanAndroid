package com.tgh.bymvvm.utils;

import com.tgh.bymvvm.base.NoViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.lifecycle.AndroidViewModel;

public class ClassUtil {

    //获取泛型ViewModel的class对象
    public static <T> Class<T> getViewModel(Object obj){
        Class<?> currentClass = obj.getClass();
        Class<T> tClass = getGenericClass(currentClass, AndroidViewModel.class);
        if(tClass == null || tClass == AndroidViewModel.class || tClass == NoViewModel.class){
            return null;
        }
        return tClass;
    }

    private static <T> Class<T> getGenericClass(Class<?> currentClass, Class<?> filterClass) {
        Type type = currentClass.getGenericSuperclass();
        if(type == null ||!(type instanceof ParameterizedType)) return null;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        for (Type t : types){
            Class<T> tClass = (Class<T>) t;
            if(filterClass.isAssignableFrom(tClass)){
                return tClass;
            }
        }
        return null;
    }
}
