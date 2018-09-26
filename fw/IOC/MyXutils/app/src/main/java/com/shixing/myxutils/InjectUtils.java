package com.shixing.myxutils;

import android.content.Context;
import android.view.View;

import com.shixing.myxutils.annotation.ContentView;
import com.shixing.myxutils.annotation.OnClick;
import com.shixing.myxutils.annotation.ViewInject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//第三方模块
public class InjectUtils {

    public static void injectView(Context context) {
        Class<?> clazz = context.getClass();
        Field[] fields = clazz.getFields();
        for(Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                field.setAccessible(true);
                try {
                    Method findViewByIdMethod = clazz.getMethod("findViewById", int.class);
                    Object targetView = findViewByIdMethod.invoke(context, viewInject.value());
                    field.set(context, targetView);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void injectLayout(Context context) {
        int layoutId = 0;
        Class<?> clazz = context.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            layoutId = contentView.value();
            try {
                Method setContentViewMethod = clazz.getMethod("setContentView", int.class);
                setContentViewMethod.invoke(context, layoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    public static void injectClickView(final Context context) {
        Class<?> clazz = context.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                method.setAccessible(true);
                int [] ids = onClick.value();
                for (int id : ids) {
                    try {
                        Method findViewByIdMethod = clazz.getMethod("findViewById", int.class);
                        final View view = (View) findViewByIdMethod.invoke(context, id);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    method.invoke(context, view);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
