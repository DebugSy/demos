package com.java.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy {

    interface IHello{
        String sayHello(String hello);
    }

    static class Hello implements IHello{

        @Override
        public String sayHello(String hello) {
            System.out.println("hello world");
            return hello;
        }
    }

    //1.DynamicProxyTest 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
    static class DynamicProxyTest implements InvocationHandler{

        Object originObj;

        Object bind(Object originObj){
            this.originObj = originObj;
            //2. 通过 Proxy 为包括 Interface 接口在内的一组接口动态创建代理类的类对象
            return Proxy.newProxyInstance(originObj.getClass().getClassLoader(),
                    originObj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Welcome");
            Object result = method.invoke(originObj, args);
            return result;
        }
    }

    public static void main(String[] args){
        //设置这个值，在程序运行完成后，可以生成代理类
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        IHello hello = (IHello) new DynamicProxyTest().bind(new Hello());
        String result = hello.sayHello("good night");
        System.out.println("result ===> " + result);
    }

}
