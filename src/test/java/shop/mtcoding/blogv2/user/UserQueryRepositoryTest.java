package shop.mtcoding.blogv2.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.Optional;

@Import(UserQueryRepository.class)
@DataJpaTest // JpaRepository만 메모리에 올려준다.
public class UserQueryRepositoryTest {

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private EntityManager em;

    // persist(영속화)
    @Test
    public void save_test() {
        User user = User.builder()
                .username("love")
                .password("1234")
                .email("love@nate.com")
                .build();
        userQueryRepository.save(user);
    }
    
    // 1차 캐시
    @Test
    public void findById_test() {
        // Persistence Context, PC는 비어 있다.
        System.out.println("1. PC가 비어있음!!!");
        userQueryRepository.findById(1); // select 쿼리가 발동됨
        System.out.println("2. PC는 user1의 객체가 영속화 되어 있다.");
        em.clear(); // 영속성 컨텍스트 내의 모든 엔터티 및 관리되는 상태를 초기화하는 역할
        userQueryRepository.findById(1); // clear시 다시 select 아니면 캐싱되어 발동하지 않는다.
        System.out.println("====================================");
    }

    @Test
    public void update_test() {
        // update 알고리즘
        // 1. 업데이트 할 객체를 영속화
        User user = userQueryRepository.findById(1); // select 발동
        // 2. 객체 상태 변경
        user.setEmail("ssarmango@nate.com"); // update 발동
        // 3. em.flush() or @Transactional 종료
        em.flush();
    } // rollback

}
