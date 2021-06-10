package it.nicolalopatriello.thesis.core.interceptors;


import it.nicolalopatriello.thesis.core.security.ThesisSecurityContext;
import it.nicolalopatriello.thesis.core.security.JwtUser;
import it.nicolalopatriello.thesis.core.service.JwtTokenServiceExt;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static it.nicolalopatriello.thesis.common.utils.ThesisConstant.AUTHORIZATION;


@Component
@Log4j
public class HttpRequestInterceptor extends HandlerInterceptorAdapter {

    @Value("${server.servlet.context-path}")
    private String apiPrefix;

    @Autowired
    private JwtTokenServiceExt jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        log.debug("[ RequestPreHandle ]" + req.getRequestURI());
        String token = req.getHeader(AUTHORIZATION);
        if (token != null) {
            try {
                JwtUser opt = jwtTokenUtil.fromUserToken(token);
                ThesisSecurityContext.set(opt);
            } catch (Exception e) {
                log.info(e.getClass().getCanonicalName() + ": " + e.getMessage());
                if (log.isDebugEnabled())
                    log.debug(e.getMessage(), e);
                return false;
            }
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        ThesisSecurityContext.clear();
    }
}
