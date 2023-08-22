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

    @Autowired
    private ReplyService replyService;

    @Autowired
    private HttpSession httpSession;

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

    // // 댓글 삭제
    // @PostMapping("/reply/{id}/delete")
    // public String delete(@PathVariable Integer id, Integer boardId) {
    //     replyService.댓글삭제하기(id);
    //     return "redirect:/board/" + boardId;
    // }

    // /* save() 저장 메서드, 글쓰기 완료 버튼에 매핑 */
    // // 1. 데이터 받기 (O)
    // // TODO 2. 인증 체크
    // // TODO 3. 유효성 검사
    // // 4. 핵심로직 호출(서비스)
    // // 5. view 또는 data 응답
    // // 댓글 등록
    // @PostMapping("/reply/save")
    // public String save1(ReplyRequest.SaveDTO saveDTO) {
    //     replyService.댓글등록하기(saveDTO, 1);
    //     return "redirect:/board/" + saveDTO.getBoardId();
    // }

    // AJAX 통신으로 댓글 등록
    @PostMapping("/api/reply/save")
    public @ResponseBody ApiUtil<String> save(@RequestBody ReplyRequest.SaveDTO saveDTO) {
        // System.out.println("boardId :" + saveDTO.getBoardId());
        // System.out.println("comment :" + saveDTO.getComment());
        // 인증체크
        User sessionUser = (User) httpSession.getAttribute("sessionUser");
        if (sessionUser == null) {
            // return new ApiUtil<String>(false, "인증실패"); // 이렇게 해도 되는데 서비스쪽에서 오류나면 복잡하고 일관성이 없음
            throw new MyApiException("인증되지 않았습니다.");
        }
        // replyService.댓글쓰기(saveDTO, sessionUser.getId());
        return new ApiUtil<String>(true, "댓글쓰기 성공");
    }
}
