package shop.mtcoding.blogv2.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.reply.ReplyRepository;
import shop.mtcoding.blogv2.reply.ReplyService;
import shop.mtcoding.blogv2.user.User;

import java.util.Optional;

/**
 * 서비스의 역할
 * 1. 비지니스 로직 처리(핵심 로직)
 * 2. 트랜잭션 관리
 * 3. 예외처리 (2단계)
 * 4. DTO 변환 (여기선 안함.)
 */
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    // Update, Create, Insert는 @Transacional을 붙여줘야한다.
    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, int sessionUserId) {
        Board board = Board.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .user(User.builder().id(sessionUserId).build())
                .build();
        boardRepository.save(board);
    }

    public Page<Board> 게시글목록보기(Integer page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return boardRepository.findAll(pageable);
    }

    public Page<Board> 게시글검색하기(String keyword, Integer page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
        return boardRepository.findByKeyword(keyword, pageable);
    }

    public Board 상세보기(Integer id) {
        // board만 가져오면 된다!!
        // TODO 예외처리 해줘야 함
        return boardRepository.mFindByIdJoinRepliesInUser(id).get();
    }

    @Transactional
    public void 글삭제하기(Integer id) {
        // boardId를 조회해서 DB에서 삭제 쿼리 전송
        // TODO 글쓴이가 맞는지 Check
        // TODO 없는 boardId가 들어왔을때의 처리
        try {
            // write는 예외처리 다잡기
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException(id + "는 찾을 수 없습니다.");
        }
    }

    // Update, Create, Insert는 @Transacional을 붙여줘야한다.
    @Transactional
    public void 글수정하기(int boardId, BoardRequest.UpdateDTO updateDTO) throws RuntimeException {
        Optional<Board> boardOP = boardRepository.findById(boardId);
        // boardOP가 존재하면 boardOP의 내용을 가져와서 내용을 수정한다.
        if(boardOP.isPresent()) {
            Board board = boardOP.get();
            board.setTitle(updateDTO.getTitle());
            board.setContent(updateDTO.getContent());
        } else {
            throw new MyException(boardId + "는 찾을 수 없습니다.");
        }
        // boardRepository 사용
        // boardRepository.update(updateDTO.getTitle(), updateDTO.getContent(), boardId);
    } // flush(더티체킹)

}
