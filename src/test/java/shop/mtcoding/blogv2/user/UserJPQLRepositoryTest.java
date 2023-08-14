package shop.mtcoding.blogv2.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@DataJpaTest // 모든 Repository와 EntityManager가 메모리에 로딩된다!!
public class UserJPQLRepositoryTest {

    @Autowired
    private UserJPQLRepository userJPQLRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findByUsername_test() {
        User user = userJPQLRepository.findByUsername("ssar");
        System.out.println("테스트 : " + user.getEmail());
    } // rollback

    @Test
    public void findById_test() {
        System.out.println("테스트 : findById_Test");
        Optional<User> userOP = userJPQLRepository.mFindById(1);

        if (userOP.isPresent()) {
            User user = userOP.get();
            System.out.println(user.getEmail());
        } else {
            System.out.println("해당 id를 찾을 수 없습니다");
        }
    }
}
