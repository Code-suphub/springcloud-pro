package com.li.aop.aspect;

import com.li.aop.anotation.Check;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component
@Aspect
public class AuthAspect {
    @Pointcut("@annotation(com.li.aop.anotation.Check)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        // 现在你可以从session中获取token或其他信息
        String token = (String) session.getAttribute("token");
        // 在这里实现身份校验逻辑
        // 例如，检查Session, Token, 或者调用外部认证服务等
        System.out.println("执行身份校验...");

        // 模拟校验失败
        // throw new RuntimeException("身份校验失败");

        // 获取参数
        Object[] args = pjp.getArgs();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        // 参数列表
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            // 判断参数上是否修饰了注解
            if (parameter.isAnnotationPresent(Check.class)) {
                // 获取注解进而得到注解上的参数值
                Annotation annotation = parameter.getAnnotation(Check.class);
                // 实际文件大小
                long rSize = 0L;
                // 实际文件后缀
                String suffix = null;
            }
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return "error";
    }
}
