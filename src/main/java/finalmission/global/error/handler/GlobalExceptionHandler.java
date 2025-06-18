package finalmission.global.error.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.global.error.dto.ErrorMessageResponse;
import finalmission.global.error.exception.ExternalApiClientException;
import finalmission.global.error.exception.ExternalApiServerException;
import finalmission.global.error.exception.WarningException;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_LEVEL_WARN = "WARN";
    private static final String LOG_LEVEL_ERROR = "ERROR";

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(WarningException.class)
    public ResponseEntity<ErrorMessageResponse> handleWarningException(
            WarningException ex,
            HttpServletRequest request
    ) {
        String traceId = logErrorDetails(ex, request, LOG_LEVEL_WARN);
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorMessageResponse(traceId, ex.getMessage()));
    }

    @ExceptionHandler(ExternalApiClientException.class)
    public ResponseEntity<ErrorMessageResponse> handleExternalApiClientException(
            ExternalApiClientException ex,
            HttpServletRequest request
    ) {
        String traceId = logErrorDetails(ex, request, LOG_LEVEL_WARN);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageResponse(traceId, ex.getMessage()));
    }

    @ExceptionHandler(ExternalApiServerException.class)
    public ResponseEntity<ErrorMessageResponse> handleExternalApiServerException(
            ExternalApiServerException ex,
            HttpServletRequest request
    ) {
        String traceId = logErrorDetails(ex, request, LOG_LEVEL_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessageResponse(traceId, ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessageResponse> handleException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        String traceId = logErrorDetails(ex, request, LOG_LEVEL_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessageResponse(traceId, ex.getMessage()));
    }

    /**
     * 실제 로깅 로직 모든 에러 핸들러에서 공통으로 호출하여 로그를 남깁니다.
     *
     * @param ex       발생한 예외 객체
     * @param request  HttpServletRequest 객체
     * @param logLevel 로깅할 레벨 (WARN 또는 ERROR)
     */
    private String logErrorDetails(Exception ex, HttpServletRequest request, String logLevel) {
        Map<String, Object> logData = new LinkedHashMap<>();
        // 에러 헤더
        logData.put("level", logLevel);
        logData.put("timestamp", Instant.now().toString());
        logData.put("traceId", MDC.get("traceId"));

        // 예외 정보
        logData.put("exceptionType", ex.getClass().getName());
        logData.put("errorMessage", ex.getMessage());

        // 요청 정보
        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        String remoteAddr = request.getRemoteAddr();

        logData.put("requestUri", requestUri);
        logData.put("httpMethod", method);
        if (queryString != null && !queryString.isEmpty()) {
            logData.put("queryString", queryString);
        }
        // 추후 리버스 프록시 환경으로 변경시 remoteAddr를 X-Forwarded-For 헤더로 대체할 수 있음
        logData.put("remoteAddress", remoteAddr);

        // 요청 헤더
        Map<String, String> headers = new LinkedHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        logData.put("requestHeaders", headers);

        // 요청 페이로드 (ContentCachingRequestWrapper를 통해 캐싱된 데이터 사용)
        String requestBody = "";
        if (request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper wrappedRequest = (ContentCachingRequestWrapper) request;
            byte[] buf = wrappedRequest.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    String characterEncoding = wrappedRequest.getCharacterEncoding();
                    if (characterEncoding == null) {
                        characterEncoding = StandardCharsets.UTF_8.name(); // 기본값 설정
                    }
                    requestBody = new String(buf, characterEncoding);
                } catch (Exception e) {
                    requestBody = "";
                }
            }
        }
        if (!requestBody.isEmpty()) {
            logData.put("requestPayload", requestBody);
        }

        // 최종 JSON 로깅
        try {
            String jsonLog = objectMapper.writeValueAsString(logData);
            if (LOG_LEVEL_WARN.equalsIgnoreCase(logLevel)) {
                log.warn(jsonLog);
            } else if (LOG_LEVEL_ERROR.equalsIgnoreCase(logLevel)) {
                log.error(jsonLog);
            }
        } catch (Exception jsonEx) {
            // JSON 변환 자체에 실패하면 일반 로그로 대체
            log.error("Failed to convert log data to JSON: {}", jsonEx.getMessage());
            log.error("Fallback log (Level: {}, Message: {})", logLevel, ex.getMessage(), ex);
        }
        return MDC.get("traceId");
    }
}
