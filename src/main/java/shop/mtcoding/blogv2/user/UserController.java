package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.blogv2._core.util.Script;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// 요청받고 (유효성 검사 필요) 응답처리
@Controller
public class UserController {

    // DI
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    // 회원가입 페이지
    // C-V 작동
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // 회원가입 메서드
    // M - V - C 작동
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userService.회원가입(joinDTO);
        return "user/loginForm"; // 응답될 때 persist 초기화(clear)
    }

    // 로그인 페이지
    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // 로그인 메서드
    // @ResponseBody로 데이터 전송
    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO) {
        // 로그인
        // userService의 로그인 메서드 호출하여 로그인
        User sessionUser = userService.로그인(loginDTO);
        // 위의 결과가 null이면 실행
        if (sessionUser == null) {
            // return Script.href("/loginForm","로그인 실패");
            // JavaScript 사용 뒤로가기 및 alert() 실행
            return Script.back("로그인 실패");
        }
        // 로그인 성공시 session에 sessionUser 정보를 담는다.
        session.setAttribute("sessionUser", sessionUser);
        // 로그인 후 홈페이지로 리디렉트
        return Script.href("/");
    }

    // 회원정보 상세보기
    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request) {
        // 세션에 저장된 sessionUser의 사용자 정보를 가져옴
        User sessionUser = (User) session.getAttribute("sessionUser");
        // userService의 회원정보보기 메서드 호출하여 id를 통해 유저의 정보를 조회
        User user = userService.회원정보보기(sessionUser.getId());
        // 조회된 유저의 정보를 request(가방)에 담는다.
        request.setAttribute("user", user);
        // 회원정보 상세보기 화면 리턴
        return "user/updateForm";
    }

    // update는 세션과 동기화 시켜줘야 한다.
    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO) {
        // 1. 회원정보 수정(서비스)
        // 세션에 저장된 sessionUser의 정보를 가져온다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        // userService의 회원수정 메서드 호출 id를 통해 조회
        // 조회한 user의 정보를 변경한 후 다시 user 정보에 담는다.
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        // 2. 세션 동기화
        // 변경된 user 정보를 세션의 sessionUser에 동기화 시킨다.
        session.setAttribute("sessionUser", user);
        // 홈페이지로 리디렉트
        return "redirect:/";
    }


    // 로그아웃
    // 브라우저 GET /logout 요청을 함(request 1)
    // 서버는 / 주소를 응답의 헤더에 담음(Location) 상태코드 302 응답
    // 브라우저는 GET / 로 재요청을 함.(request 2)
    // index 페이지 응답받고 렌더링함.
    @GetMapping("/logout")
    public @ResponseBody String logout() {
        session.invalidate(); // 세션 무효화(삭제)

        return Script.href("/", "로그아웃 했습니다.");
    }
}
