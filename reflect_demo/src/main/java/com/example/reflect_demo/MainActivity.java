package com.example.reflect_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView tvShowStaff;
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShowStaff = ((TextView) findViewById(R.id.tvShowStaff));
        reflect();
    }

    private void reflect() {
        //（1）方法一：
        Staff staff = new Staff();
        Class<? extends Staff> aClass = staff.getClass();
        Log.d(TAG, "reflect: "+aClass.getSimpleName());
        //（2）方法二：
        Class aClass1 = Staff.class;
        Log.d(TAG, "reflect: "+aClass1.getSimpleName()+"     "+aClass1);
        //  方法3：Class.forName()
        try {
            Class<?> aClass2 = Class.forName("com.example.reflect_demo.Staff");
            int modifiers = aClass2.getModifiers();
            String s = Modifier.toString(modifiers);
            Log.d(TAG, "reflect: 修饰符    "+s);
            Field[] fields = aClass2.getFields();
            String s1 = Arrays.toString(fields);
            Log.d(TAG, "reflect: 可用字段  "+s1);
            Constructor<?>[] constructors = aClass2.getConstructors();
            Log.d(TAG, "reflect: 构造方法  "+Arrays.toString(constructors));
            Method declaredMethod = aClass2.getDeclaredMethod("work");
            Log.d(TAG, "reflect: 声明方法   "+declaredMethod.getName());
            Staff staff1 = (Staff) aClass2.newInstance();
            staff1.setAge(20);
            staff1.setName("ls");
            staff1.setDepart("mobile development");
            Object invoke = declaredMethod.invoke(staff1);
            Log.d(TAG, "reflect: 反射方法已执行  ");

            Object o = aClass1.newInstance();
            Object invoke1 = declaredMethod.invoke(o);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
