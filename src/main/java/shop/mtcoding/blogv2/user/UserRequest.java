package shop.mtcoding.blogv2.user;

import lombok.Getter;
import lombok.Setter;

// User에 관련된 DTO를 모아두는 클래스
public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private String password;
    }
}
