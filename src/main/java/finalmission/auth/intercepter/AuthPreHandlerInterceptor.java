package finalmission.auth.intercepter;

import finalmission.auth.exception.AuthenticationException;
import finalmission.auth.exception.AuthorizationException;
import finalmission.auth.jwt.JwtCookieResolver;
import finalmission.auth.jwt.JwtTokenProvider;
import finalmission.auth.user.MemberInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthPreHandlerInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            String token = JwtCookieResolver.getTokenFromCookie(request);
            MemberInfo memberInfo = jwtTokenProvider.parseTokenToMemberInfo(token);
            if (memberInfo == null || !memberInfo.isCoach()) {
                throw new AuthorizationException("권한이 없는 사용자입니다.");
            }
            return true;
        } catch (AuthenticationException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        } catch (AuthorizationException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return false;
        }
    }
}
