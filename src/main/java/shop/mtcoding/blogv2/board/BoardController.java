package shop.mtcoding.blogv2.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blogv2._core.util.Script;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;


    // 게시글 수정
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO updateDTO) {
        // where 데이터, body, session 값
        // boardId로 해당 게시글을 찾는다.
        // Board board = boardService.상세보기(id);
        // 해당 게시글의 글을 수정한다.
        boardService.글수정하기(id, updateDTO);
        // 수정한뒤 홈페이지로 리디렉트
        return "redirect:/board/" + id;
    }

    // 게시글 수정화면
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, Model model) {
        // boardId로 게시글 상세보기
        Board board = boardService.상세보기(id);
        // view로 뿌려주기 위해 request에 board를 담는다.
        // request.setAttribute("board", board);
        model.addAttribute("board", board); // request에 담는것과 동일하다.
        // 다시 상세보기 화면으로 리턴
        return "board/updateForm";
    }

    // 게시글 삭제
    @PostMapping("/board/{id}/delete")
    public @ResponseBody String delete(@PathVariable Integer id) {
        // 인증검사

        // 핵심 로직

        boardService.글삭제하기(id);
        // 삭제후 홈페이지로 리디렉트
        return Script.href("/");


    }

    // 게시글 상세보기
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        // TODO 댓글할때 수정, 엔티티보다 DTO를 만들어서 뿌려주는게 좋다.
        Board board = boardService.상세보기(id);
        // view에서 사용하기위해 매핑
        model.addAttribute("board", board);
        // 상세보기 화면 리턴
        return "board/detail";
    }

    // 양방향 매핑, 무한참조 테스트용 메서드
    @GetMapping("test/board/{id}")
    public @ResponseBody Optional<Board> detail(@PathVariable Integer id) {
        Optional<Board> board = boardRepository.mFindByIdJoinRepliesInUser(id);
        return board;
    }

    // localhost:8080?page=1&keyword=바나나
    // index(), 홈페이지
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") Integer page,
                        @RequestParam(defaultValue = "") String keyword,
                        HttpServletRequest request) {

        // 핵심로직
        Page<Board> boardPG = boardService.게시글목록보기(page);

        // 페이징
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
    // TODO 2. 인증 체크
    // TODO 3. 유효성 검사
    // 4. 핵심로직 호출(서비스)
    // 5. view 또는 data 응답
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) {
        boardService.글쓰기(saveDTO, 1);
        return "redirect:/";
    }

}
