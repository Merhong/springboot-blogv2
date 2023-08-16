package shop.mtcoding.blogv2.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import shop.mtcoding.blogv2.user.User;
import shop.mtcoding.blogv2.user.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@DataJpaTest // 모든 Repository와 EntityManager가 메모리에 로딩된다!!
public class BoardRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private BoardRepository boardRepository;

    // EAGER로 설정하고 테스트하자. 아니면 에러뜸
    @Test
    public void findAll_paging_test() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        Page<Board> boardPG =  boardRepository.findAll(pageable);
        ObjectMapper om = new ObjectMapper();

        // ObjectMapper는 boradPG 객체의 getter를 호출하면서 json을 만든다.
        String json = om.writeValueAsString(boardPG); // 자바 객체를 JSON으로 변환
        System.out.println(json);
    }

    @Test
    public void mfindAll_test() {
        boardRepository.mfindAll();
    }

    @Test
    public void findAll_test() {
        // 행 5개 -> 객체 5개
        // Eager는 User의 모든 것을 땡겨오고 Lazy는 아래와 같이 PK만 땡겨온다.
        // 각행 : Board(id=1, title=제목1, content=내용1, created_at=날짜, User(id=1))
        System.out.println("조회 직전");
        List<Board> boardList = boardRepository.findAll();
        System.out.println("조회 후: Lazy");
        System.out.println(boardList.get(0).getId()); // Board0 (id=1)
        System.out.println(boardList.get(0).getUser().getId()); // User(id=1)

        // Lazy Loading(지연로딩)
        // 영속화된 객체에 null값을 찾으려고 하면, 조회가 일어난다.
        // 연관된 객체에 null을 참조하려고 하면 조회가 일어난다.
        // FetchType.EAGER이면 바로 ssar이 나온다.
        System.out.println(boardList.get(0).getUser().getUsername()); // null이 뜨니 조회를 해줌!!!
    }

    @Test
    public void update_test() {
        User user = userRepository.findById(1).get();
        // User user = User.builder().id(1).password("5678").build();
        user.setPassword("5678");
        userRepository.save(user);
        em.flush();
    } // rollback

    @Test
    public void save_test() {
        User user = User.builder()
                .username("love")
                .password("1234")
                .email("love@nate.com")
                .build();
        userRepository.save(user);
    } // rollback

    @Test
    public void mSave_test() {
        userRepository.mSave("love", "1234", "love@nate.com");
    } // rollback

    @Test
    public void findByUsername_test() {
        User user = userRepository.findByUsername("hello");
        System.out.println("테스트 : " + user.getEmail());
    } // rollback

    @Test
    public void findById_test() {
        Optional<User> userOP = userRepository.findById(3);

        if (userOP.isPresent()) {
            User user = userOP.get();
            System.out.println(user.getEmail());
        } else {
            System.out.println("해당 id를 찾을 수 없습니다");
        }
    }

    @Test
    public void mFindById_test() {
        User user = userRepository.mFindById(3);
        if (user == null) {
            System.out.println("해당 id를 찾을 수 없습니다");
        } else {
            System.out.println(user.getEmail());
        }

    }

    @Test
    public void optional_test() {
        User user = User.builder().id(1).username("ssar").build();

        Optional<User> userOP = Optional.of(user);

        if (userOP.isPresent()) {
            User u = userOP.get();
            System.out.println("user가 null이 아닙니다");
        } else {
            System.out.println("user가 null이에요");
        }
    }

}
