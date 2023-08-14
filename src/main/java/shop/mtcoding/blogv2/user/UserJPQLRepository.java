package shop.mtcoding.blogv2.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJPQLRepository extends JpaRepository<User, Integer> {

    // @Query : executeQuery() 발동
    // from에 엔티티명(클래스이름, 대문자 체크) '별칭'을 줘서 사용한다.
    @Query(value = "select u from User u where u.id = :id")
    Optional<User> mFindById(@Param("id") Integer id);

    // @Query : executeQuery() 발동
    @Query(value = "select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);

    @Modifying // executeUpdate() 발동시킴
    // JPQL은 insert, delete, update 지원을 하지 않는다. 이들은 nativeQuery = true를 써줘야 함.
    @Query(value = "insert into user_tb(created_at, email, password, username) values(now(), :email, :password, :username)", nativeQuery = true)
    void mSave(@Param("username") String username, @Param("password") String password, @Param("email") String email);
}
