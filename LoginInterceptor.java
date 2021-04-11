package edu.XUFE.online_class.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.XUFE.online_class.utils.JWTUtils;
import edu.XUFE.online_class.utils.JsonData;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 定义拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 进入controller之前的方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            String accessToken = request.getHeader("token");
            if (accessToken == null){
                accessToken = request.getParameter("token");
            }
            if (StringUtils.isNotBlank(accessToken)){
                Claims claims = JWTUtils.checkJWT(accessToken);
                if (claims == null){
                    //过期，重新登陆
                    sendJsonMessage(response, JsonData.buildError("登录过期，请重新登录"));
                    return false;
                }
                Integer id = (Integer) claims.get("id");
                String name = (String) claims.get("name");
                request.setAttribute("user_id",id);
                request.setAttribute("name",name);
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
            //登陆失败
            sendJsonMessage(response, JsonData.buildError("登录失败，请重新登录"));
        }
        sendJsonMessage(response, JsonData.buildError("登录失败，请重新登录"));
        return false;
    }

    /**
     * 响应json数据给前端
     * @param response
     * @param obj
     */
    public static void sendJsonMessage(HttpServletResponse response,Object obj){
        try {
            //序列化对象，将object序列化为json数据
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(obj));
            writer.close();
            response.flushBuffer();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
