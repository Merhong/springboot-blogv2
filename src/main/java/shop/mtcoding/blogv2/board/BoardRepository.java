package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blogv2.user.User;

import java.util.Optional;

/**
 * save(), findById(), findAll(), count(), deleteById() 같은
 * 기본적인 CRUD 메서드들이 이미 만들어져 있다. (Update 제외)
 */
// 스프링이 실행될 때, BoardRepository의 구현체가 IoC 컨테이너에 로딩됨.
public interface BoardRepository extends JpaRepository<Board, Integer> {


}
