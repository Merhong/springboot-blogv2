package shop.mtcoding.blogv2._core.error.ex;

// 일반적 요청 예외처리
public class MyException extends RuntimeException {
    public MyException(String message) {
        super(message);
    }
}
