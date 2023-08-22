package shop.mtcoding.blogv2._core.error.ex;

// JSON 요청 예외처리
public class MyApiException extends RuntimeException {
    public MyApiException(String message) {
        super(message);
    }
}