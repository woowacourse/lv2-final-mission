package finalmission.global.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        response.setHeader(START_TIME, String.valueOf(startTime));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        long startTime = Long.parseLong(response.getHeader("startTime"));
        long processedTime = System.currentTimeMillis() - startTime;

        String uri = request.getRequestURI();
        int status = response.getStatus();
        String ip = request.getRemoteAddr();

        log.info("[요청 결과] URI: {}, 상태: {}, IP: {}, 소요 시간: {}ms", uri, status, ip, processedTime);
    }
}
