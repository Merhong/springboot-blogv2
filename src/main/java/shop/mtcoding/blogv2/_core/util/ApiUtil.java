package shop.mtcoding.blogv2._core.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiUtil<T> {
    private boolean success; // insert 성공시 true, 실패시 false
    private T data; // 댓글쓰기 성공, 실패

    public ApiUtil(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
}
