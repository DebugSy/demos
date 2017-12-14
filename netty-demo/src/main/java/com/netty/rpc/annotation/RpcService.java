package com.netty.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by DebugSy on 2017/12/14.
 */
@Target(ElementType.TYPE)//注解应用在接口上
@Retention(RetentionPolicy.RUNTIME)//JVM将在运行期间保留注释，因此可以通过反射机制读取注解的信息
@Component
public @interface RpcService {

	Class<?> value();

}
