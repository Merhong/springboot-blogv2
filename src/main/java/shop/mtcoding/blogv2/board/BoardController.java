package shop.mtcoding.blogv2.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // localhost:8080?page=1&keyword=바나나
    // index(), 홈페이지
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {
        Page<Board> boardPG = boardService.게시글목록보기(page);
        request.setAttribute("boardPG", boardPG);
        request.setAttribute("prevPage", boardPG.getNumber() - 1);
        request.setAttribute("nextPage", boardPG.getNumber() + 1);

        // 위의 방법 대신 DTO에 담아서 request에 DTO만 연결시키는게 관리하기 좋다.
        return "index";
    }

    // saveForm() 글쓰기 화면
    @GetMapping("/board/saveForm")
    public String saveForm() {

        return "board/saveForm";
    }

    /* save() 저장 메서드, 글쓰기 완료 버튼에 매핑 */
    // 1. 데이터 받기 (O)
    // 2. 인증 체크 TODO
    // 3. 유효성 검사 TODO
    // 4. 핵심로직 호출(서비스)
    // 5. view 또는 data 응답
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) {
        boardService.글쓰기(saveDTO, 1);
        return "redirect:/";
    }

}
