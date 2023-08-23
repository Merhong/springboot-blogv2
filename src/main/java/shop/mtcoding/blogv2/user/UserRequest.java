package shop.mtcoding.blogv2.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

// User에 관련된 DTO를 모아두는 클래스
public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
        private MultipartFile pic; // Key값을 사용하는 mustache와 맞춰야함!!!
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
        private MultipartFile pic; // Key값을 사용하는 mustache와 맞춰야함!!!
    }
}
