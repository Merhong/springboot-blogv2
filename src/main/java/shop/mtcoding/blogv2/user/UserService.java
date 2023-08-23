package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.vo.MyPath;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

// 핵심로직 처리, 트랜잭션 관리, 예외 처리
@Service
public class UserService {

    // DI
    @Autowired
    private UserRepository userRepository;

    // 쓰기(Insert, Update, Delete)할 때 붙여준다.
    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {

        UUID uuid = UUID.randomUUID(); // Random한 Hash값을 만들어줌
        String fileName = uuid + "_" + joinDTO.getPic().getOriginalFilename(); // 앞에 오면 확장자가 없어진다 주의하자.
        System.out.println("fileName :" + fileName);
        // java.nio 사용
        // 프로젝트 실행 파일변경 -> "blogv2-1.0.jar" 실행파일이 생성되는 경로 기준으로 images 폴더가 필요하다.
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
        try {
            Files.write(filePath, joinDTO.getPic().getBytes());
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

        // Builder를 사용하여 joinDTO로 들어온 value로 User 객체를 생성
        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .picUrl(fileName)
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

        UUID uuid = UUID.randomUUID(); // Random한 Hash값을 만들어줌
        String fileName = uuid + "_" + updateDTO.getPic().getOriginalFilename(); // 앞에 오면 확장자가 없어진다 주의하자.
        System.out.println("fileName :" + fileName);
        // java.nio 사용
        // 프로젝트 실행 파일변경 -> "blogv2-1.0.jar" 실행파일이 생성되는 경로 기준으로 images 폴더가 필요하다.
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
        try {
            Files.write(filePath, updateDTO.getPic().getBytes());
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
        // 1. 조회(영속화)
        // id로 유저를 찾는다.
        User user = userRepository.findById(id).get();
        // 2. 변경
        // DB에서 찾은 유저의 비밀번호를 updateDTO로 들어온 비밀번호로 변경, 썸네일도 마찬가지
        user.setPassword(updateDTO.getPassword());
        user.setPicUrl(fileName);
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
