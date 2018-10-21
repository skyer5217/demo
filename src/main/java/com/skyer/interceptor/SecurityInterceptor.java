package com.skyer.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.skyer.contants.AppConst;
import com.skyer.enumerate.ResultEnum;
import com.skyer.util.ResultInfo;
import com.skyer.vo.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 安全验证
 *
 * @author SKYER
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        resp.setContentType("text/plain;charset=UTF-8");
        String servletPath = req.getServletPath();
        // 放行的请求
        if (servletPath.startsWith("/login") || servletPath.startsWith("/doLogin")) {
            return true;
        }

        // 获取当前登录用户
        User user = (User) req.getSession().getAttribute(AppConst.CURRENT_USER);

        // 没有登录状态下访问系统主页面，都跳转到登录页，不提示任何信息
        if (servletPath.startsWith("/")) {
            if (user == null) {
                resp.sendRedirect(getDomain(req) + "/login");
                return false;
            }
        }

        // 未登录或会话超时
        if (user == null) {
            String requestType = req.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(requestType)) { // ajax请求
                ResultInfo<Object> resultInfo = new ResultInfo<>();
                resultInfo.setCode(ResultEnum.SESSION_TIMEOUT.getCode());
                resultInfo.setData(ResultEnum.SESSION_TIMEOUT.getValue());
                resp.getWriter().write(JSONObject.toJSONString(resultInfo));
                return false;
            }
            String param = URLEncoder.encode(ResultEnum.SESSION_TIMEOUT.getValue(), "UTF-8");
            resp.sendRedirect(getDomain(req) + "/login?errorMsg=" + param);
            return false;
        }

        // 检查是否被其他人挤出去
        ServletContext application = req.getServletContext();
        @SuppressWarnings("unchecked")
        Map<String, String> loginedMap = (Map<String, String>) application.getAttribute(AppConst.LOGINEDUSERS);
        String loginedUserSessionId = loginedMap.get(user.getUserName());
        String mySessionId = req.getSession().getId();

        if (!mySessionId.equals(loginedUserSessionId)) {
            String requestType = req.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(requestType)) { // ajax请求
                ResultInfo<Object> resultInfo = new ResultInfo<>();
                resultInfo.setCode(ResultEnum.OTHER_LOGINED.getCode());
                resultInfo.setData(ResultEnum.OTHER_LOGINED.getValue());
                resp.getWriter().write(JSONObject.toJSONString(resultInfo));
                return false;
            }
            String param = URLEncoder.encode(ResultEnum.OTHER_LOGINED.getValue(), "UTF-8");
            resp.sendRedirect(getDomain(req) + "/login?errorMsg=" + param);
            return false;
        }
        return true;
    }

    /**
     * 获得域名
     */
    private String getDomain(HttpServletRequest request) {
        String path = request.getContextPath();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    }

}