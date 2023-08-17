package shop.mtcoding.blogv2.board;

import lombok.Getter;
import lombok.Setter;

/* 요청 DTO를 다 모으는 곳 */
public class BoardRequest {
    @Getter
    @Setter
    public static class SaveDTO {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private String title;
        private String content;
    }

}
