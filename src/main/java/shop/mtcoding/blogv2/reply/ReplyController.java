package shop.mtcoding.blogv2.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2.user.User;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {

    // DI
    @Autowired
    private ReplyService replyService;

    @Autowired
    private HttpSession httpSession;

    // 댓글 삭제
    @DeleteMapping("/api/reply/{id}/delete")
    public @ResponseBody ApiUtil<String> delete(@PathVariable Integer id) {
        // 1. 인증체크
        User sessionUser = (User) httpSession.getAttribute("sessionUser");
        if (sessionUser == null) {
            // return new ApiUtil<String>(false, "인증실패"); // 이렇게 해도 되는데 서비스쪽에서 오류나면 복잡하고 일관성이 없음
            throw new MyApiException("인증되지 않았습니다.");
        }
        // 2. 핵심로직
        replyService.댓글삭제(id, sessionUser.getId());
        // 3. 응답
        return new ApiUtil<String>(true, "댓글삭제 완료");
    }

    // AJAX 통신으로 댓글 등록
    @PostMapping("/api/reply/save")
    public @ResponseBody ApiUtil<String> save(@RequestBody ReplyRequest.SaveDTO saveDTO) {
        // 인증체크
        User sessionUser = (User) httpSession.getAttribute("sessionUser");
        if (sessionUser == null) {
            // return new ApiUtil<String>(false, "인증실패"); // 이렇게 해도 되는데 서비스쪽에서 오류나면 복잡하고 일관성이 없음
            throw new MyApiException("인증되지 않았습니다.");
        }
        replyService.댓글쓰기(saveDTO, sessionUser.getId());
        return new ApiUtil<String>(true, "댓글쓰기 성공");
    }
}
