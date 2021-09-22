package com.itheima.cglib;

import com.itheima.aspect.MyAspect;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
    public Object createProxy(Object target){
        //创建一个动态类对象
        Enhancer enhancer = new Enhancer();
        //确定需要增强的类，设置其父类
        enhancer.setSuperclass(target.getClass());
        //添加回调函数
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     *
     * proxy CGlib 根据指定父类生成的代理对象
     * method 拦截的方法
     * args 拦截方法的参数数组
     * methodProxy方法的代理对象，用于执行父类方法
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //创建切面类对象
        MyAspect myAspect = new MyAspect();
        //前增强
        myAspect.check_Permissions();
        Object obj = methodProxy.invokeSuper(proxy,args);
        myAspect.log();
        return obj;
    }
}
