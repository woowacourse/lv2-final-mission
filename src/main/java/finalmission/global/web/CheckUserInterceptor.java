package finalmission.global.web;

import static finalmission.domain.TokenAuthRole.USER;

import finalmission.dto.AuthenticatedMember;
import finalmission.domain.TokenProcessor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class CheckUserInterceptor implements HandlerInterceptor {

    private final TokenProcessor tokenProcessor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String rawToken = request.getHeader("Authorization");
        AuthenticatedMember authenticatedMember = tokenProcessor.extract(rawToken);
        if (authenticatedMember.tokenAuthRole().hasAccessRole(USER)) {
            return true;
        }
        response.setStatus(403);
        return false;
    }
}
