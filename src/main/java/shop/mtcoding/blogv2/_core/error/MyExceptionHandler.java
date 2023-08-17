package shop.mtcoding.blogv2._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.blogv2._core.util.Script;


@RestControllerAdvice // Exception이 터지면 모두 여기로 모인다. 어디서 터지든지 상관없이 모두 모임!!!
public class MyExceptionHandler {
    // 어떤 Exception이 터지면 여기로 모임
    @ExceptionHandler(RuntimeException.class)
    public String error(RuntimeException e) {

        return Script.back(e.getMessage());
        // return "Exception Handler에서 처리된거임@@@@";
    }
}
