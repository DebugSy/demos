//package com.shi.java.JarScan;
//
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
//import org.springframework.core.type.filter.AnnotationTypeFilter;
//
//import java.util.Set;
//
///**
// * Created by DebugSy on 2017/8/9.
// */
//public class JarScanService {
//
//	public List<FuncDesc> scan(String basePackage) throws Exception {
//		List<FuncDesc> result = new ArrayList<FuncDesc>();
//		ClassPathScanningCandidateComponentProvider scanningProvider = new ClassPathScanningCandidateComponentProvider(false);
////		scanningProvider.addIncludeFilter(new AnnotationTypeFilter(UDF.class));//筛选
//		Set<BeanDefinition> beans = scanningProvider.findCandidateComponents(basePackage);
//		for (BeanDefinition bean : beans) {
//			String className = bean.getBeanClassName();
//			Class clazz = Class.forName(className);
//			UDF udf = (UDF) clazz.getAnnotation(UDF.class);
//
//			FuncDesc func = new FuncDesc();
//			func.name_$eq(udf.name());
//			func.funcType_$eq(udf.fnType());
//			func.returnType_$eq(udf.returnType());
//			func.className_$eq(className);
//
//			result.add(func);
//		}
//		return result;
//	}
//
//	public static void main(String[] args) throws Exception {
//		JarScanService service = new JarScanService();
//		List<FuncDesc> funcs = service.scan("com.datapps");
//		for (FuncDesc func : funcs) {
//			System.out.println(func);
//		}
//	}
//
//}
