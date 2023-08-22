package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blogv2._core.error.ex.MyException;

// 핵심로직 처리, 트랜잭션 관리, 예외 처리
@Service
public class UserService {

    // DI
    @Autowired
    private UserRepository userRepository;

    // 쓰기(Insert, Update, Delete)할 때 붙여준다.
    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        // Builder를 사용하여 joinDTO로 들어온 value로 User 객체를 생성
        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .build();
        // 생성된 user 객체를 저장
        userRepository.save(user); // 영속화, em.persist
    }

    public User 로그인(UserRequest.LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        // 1. 유저네임 검증
        if (user == null) {
            throw new MyException("유저네임이 없습니다.");
        }
        // 2. 패스워드 검증
        // 패스워드 비교시 같지 않으면 null, 같으면 통과
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new MyException("패스워드가 틀렸습니다.");
        }
        // 3. 로그인 성공
        return user;
    }

    public User 회원정보보기(Integer id) {
        // Jpa 기본 메서드 findById
        // id를 통해 user를 찾아서 리턴한다.
        return userRepository.findById(id).get();
    }

    @Transactional
    public User 회원수정(UserRequest.UpdateDTO updateDTO, Integer id) {
        // 1. 조회(영속화)
        // id로 유저를 찾는다.
        User user = userRepository.findById(id).get();
        // 2. 변경
        // DB에서 찾은 유저의 비밀번호를 updateDTO로 들어온 비밀번호로 변경
        user.setPassword(updateDTO.getPassword());
        // 변경된 유저 정보를 리턴한다.
        return user;
        // 3. 트랜잭션 종료시 자동 flush @Transactional
    }

    public User 유저네임중복체크(String username) {
        // 1. 조회 = 영속화
        // username이 같은게 있는지 조회
        User user = userRepository.findByUsername(username);

        return user;
    }
}
