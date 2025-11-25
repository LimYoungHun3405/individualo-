package com.newsfeed.cider.common.exception;

import com.newsfeed.cider.common.enums.ValidCode;
import com.newsfeed.cider.common.model.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Service에서 일어난 인증 예외 처리
     * @param e ServiceException (ExceptionCode(HttpStatus, message))
     * @return ErrorResponse (HttpStatus, message)
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<String>> serviceException(CustomException e) {

        CommonResponse<String> response = new CommonResponse<>(e.getExceptionCode().getStatus(), e.getMessage());

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * 유저의 입력에 대한 검증 수행해서 잘못된 값이 들어왔을 때 예외 처리
     * @param e MethodArgumentNotValidException Valid
     * @return ErrorResponse (HttpStatus, message)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<HashMap<String, String>>> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        HashMap<String, String> message = getErrorMessage(e);

        CommonResponse<HashMap<String, String>> response = new CommonResponse<>(HttpStatus.BAD_REQUEST, message);

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Bean Validation은 에러를 2개 이상 가지고 있어서 map 형식으로 만들어 String 변환
     * @param e MethodArgumentNotValidException (valid exception)
     * @return 에러를 모은 map을 String으로 만들어 반환
     */
    private HashMap<String, String> getErrorMessage(MethodArgumentNotValidException e) {

        //1. Valid로 검증한 모든 에러들
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        HashMap<String, String> map = new HashMap<>();

        //2. 에러들 각각 map에 (key : 필드, value : message) 담기
        allErrors.forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = ValidCode.getMessage(field, ((FieldError) error).getCode());

            map.put(field, message);
                }
        );

        return map;
    }
}
