package com.li.Anntation;

import com.alibaba.fastjson.JSONObject;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

//@Component
public class TokenInterceptor implements HandlerInterceptor {
    // 假设现在的token有如下数据
    List<String> tokenList = Arrays.asList("111", "222", "333");

    // 这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 设置返回为json格式，使用UTF-8
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String token = request.getHeader("token");
        PrintWriter out;
        // 对于注解的判断---------------------------------------------------
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(NeedCheck.class) != null || handlerMethod.getBeanType().isAnnotationPresent(NeedCheck.class)) {
            // 如果自己拥有NoNeedToken标注或者所属的class拥有NoNeedToken 就直接放行
            return true;
        }
        // 之后写你的判断逻辑：return true是通过拦截器，可以继续访问controller，return false是不通过
        if (token == null || !tokenList.contains(token)) {
            // 如果失败了返回{state:"false", msg:"token is null or wrong"}
            JSONObject res = new JSONObject();
            res.put("state", "false");
            res.put("msg", "token is null or wrong");
            out = response.getWriter();
            out.append(res.toString());
            return false;
        }
        // 否则返回true 进入controller
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
