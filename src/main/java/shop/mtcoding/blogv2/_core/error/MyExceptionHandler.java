package shop.mtcoding.blogv2._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;

// Exception이 터지면 모두 여기로 모인다. 어디서 터지든지 상관없이 모두 모임!!!
@RestControllerAdvice // 데이터를 응답함 이게 없으면 @ResponseBody를 각각 붙여줘야 함.
public class MyExceptionHandler {

    @ExceptionHandler(MyException.class)
    public String error(MyException e) {
        return Script.back(e.getMessage());
        // return "Exception Handler에서 처리된거임@@@@";
    }

    @ExceptionHandler(MyApiException.class)
    public ApiUtil<String> error(MyApiException e) {
        return new ApiUtil<String>(false, e.getMessage());
    }
}
