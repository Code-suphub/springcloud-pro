package com.li.Anntation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//@Aspect
//@Component
public class CheckAspect {
    @Pointcut("@annotation(NeedCheck)") // 定义切点，表示任何带有@NeedAuth注解的方法
    public void authPointcut() {
    }

    @Before("authPointcut()") // 在authPointcut()定义的切点之前执行
    public void doAuthCheck(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        // 现在你可以从session中获取token或其他信息
        String token = (String) session.getAttribute("token");
        // 在这里实现身份校验逻辑
        // 例如，检查Session, Token, 或者调用外部认证服务等
        System.out.println("执行身份校验...");

        // 模拟校验失败
        // throw new RuntimeException("身份校验失败");
    }
}
