package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserQueryRepository {

    @Autowired
    private EntityManager em;

    public void save(User user) {
        em.persist(user); // 영속화(영속성 컨텍스트)
    }

    public User findById(Integer id) {
        // // 쿼리 작성
        // Query query = em.createQuery("select u from User u where u.id = :id", User.class);
        // // 변수 바인딩
        // query.setParameter("id", id);
        // // 리턴 or 전송
        // return (User) query.getSingleResult();
        // 위와 같이 넣으면 쿼리를 요청하니 무조건 쿼리가 발동함
        return em.find(User.class, id);
    }
}