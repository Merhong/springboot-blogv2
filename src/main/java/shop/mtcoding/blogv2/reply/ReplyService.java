package shop.mtcoding.blogv2.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.user.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    public void 댓글삭제하기(Integer id) {
        // replyId를 조회해서 DB에서 삭제 쿼리 전송
        // TODO 글쓴이가 맞는지 체크
        // TODO 없는 replyId 들어왔을때의 처리

        try {
            replyRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("댓글서비스 " + id + " 없음!!!");
        }
    }

    // Update, Create, Insert
    @Transactional
    public void 댓글등록하기(ReplyRequest.SaveDTO saveDTO, int sessionUserId) {
        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(Board.builder().id(saveDTO.getBoardId()).build()) // boardId
                .user(User.builder().id(sessionUserId).build())   // userId
                .build();

        replyRepository.save(reply);
    }

    @Transactional
    public void 댓글쓰기(ReplyRequest.SaveDTO saveDTO, Integer sessionId) {

        // 1. boardId가 존재하는지 유무
        Board board = Board.builder().id(saveDTO.getBoardId()).build();
        User user = User.builder().id(sessionId).build();

        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(board)
                .user(user)
                .build();

        // 영속화
        replyRepository.save(reply);
    }

    @Transactional
    public void 댓글삭제(Integer id, Integer sessionUserId) {
        // 권한체크
        Optional<Reply> replyOP = replyRepository.findById(id);

        if (replyOP.isEmpty()) {
            throw new MyApiException("삭제할 댓글이 없습니다.");
        }

        Reply reply = replyOP.get();
        if (reply.getUser().getId() != sessionUserId) {
            throw new MyApiException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        replyRepository.deleteById(id);
    }
}
