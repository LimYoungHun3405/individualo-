package com.newsfeed.cider.common.config;

import com.newsfeed.cider.common.enums.ExceptionCode;
import com.newsfeed.cider.common.exception.CustomException;
import com.newsfeed.cider.common.model.CommonResponse;
import com.newsfeed.cider.common.model.SessionUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1. 세션 검사가 필요없는 경로 확인 false면 세션 검사 필요
        boolean requiredSession = requiredSession(request);

        //2. 세션 검사가 필요한 경우
        if (requiredSession) {
            //3. 세션 조회
            HttpSession session = request.getSession(false);

            if (session != null) {

                //4. 세션에서 loginUser 속성을 꺼냄
                SessionUser loginUser = (SessionUser) session.getAttribute("loginUser");

                if (loginUser != null) {

                    //TEST용 Log id와 이메일 찍어보기 ** 지우셔도 좋습니다 **
                    log.info("CustomFilter doFilterInternal login  id : {} email {}", loginUser.getUserId(), loginUser.getEmail());

                    //다음 요청으로 넘어갈 수 있게 사용하는 메서드
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            makeResponse(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 요청 uri에서 세션이 필요한지 확인하는 기능
     * @param request HttpServletRequest 요청
     * @return 필요하면 true, 필요없으면 false
     */
    private boolean requiredSession(HttpServletRequest request) {

        //요청 URI
        String uri = request.getRequestURI();
        String method = request.getMethod();

        //세션 확인이 필요없는 곳
        if (uri.startsWith("/signup") || uri.startsWith("/login") ||
                ("GET".equals(method))) return false;

        return true;
    }

    /**
     * 세션이 없어서 응답을 작성
     * @param response 응답
     * @throws IOException 응답 작성에 대한 예외
     */
    private void makeResponse(HttpServletResponse response) throws IOException {

        //세션이 없거나 loginUser가 없으면 응답을 만들어서 줘야함
        CustomException exception = new CustomException(ExceptionCode.NOT_LOGGED_IN);
        CommonResponse<String> result = new CommonResponse<>(exception.getExceptionCode().getStatus(), exception.getMessage());

        //응답 설정 : HttpStatus, ContentType 및 인코딩
        response.setStatus(result.getCode());
        response.setContentType("application/json; charset=UTF-8");

        //object -> json으로 직렬화
        String json = objectMapper.writeValueAsString(result);

        //응답 작성 및 바로 버퍼에서 반환
        response.getWriter().write(json);
        response.flushBuffer();
    }
}