package org.example.minyeong.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.minyeong.dto.LoginRequest;
import org.example.minyeong.dto.UserRequestDto;
import org.example.minyeong.dto.UserResponseDto;
import org.example.minyeong.entity.UserEntity;
import org.example.minyeong.service.UserService;
import org.example.minyeong.service.SchoolService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController//Json형태로 객체 데이터 반환
@RequiredArgsConstructor //생성자 주입 자동 설정
public class UserController {//UserController 클래스 선언

    //공개| 고정 | 클래스  | 변수 |
    public final UserService userService;//UserService 타입을 담을 변수(userService)
    private final SchoolService schoolService;

    @PostMapping("/user/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userService.signup(userRequestDto));
    }

    @PostMapping("/login")
    public void login(HttpServletRequest servletRequest, @RequestBody LoginRequest request) {

        UserEntity userEntity = userService.findOne(request);

        HttpSession session = servletRequest.getSession();
        session.setAttribute("LOGIN_USER", userEntity.getId());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    // 생성
    @PostMapping("/users")
    // 공개|리턴값-http 본문 함께 전달 | 데이터 저장 메소드명| 클라이언트가 보낸 데이터 Man객체로 변환|
    public ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserRequestDto userRequestDto) {
        //리턴 | 저장 결과를 200ok 상태와 응답으로 반환| manService 의 save 메소드 호출
        return ResponseEntity.ok(userService.signup(userRequestDto));
    }

    @GetMapping("/users")
    public Page<UserResponseDto> getUsers(
            @PageableDefault(size = 10, page = 0, sort = {"createdAt"}, direction = Sort.Direction.DESC)Pageable pageable,
            @RequestParam(required = false) String searchKeyword
            ){
        return userService.getAll(searchKeyword, pageable);
    }

    @GetMapping("test")
    public String test() {
        return "로그인 유저";
    }
//    public ResponseEntity<List<UserResponseDto>> get(){
//        return ResponseEntity.ok(userService.getAll());
//    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> put(@PathVariable Long id, @Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.updateId(id, userRequestDto));
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.deleteId(id);
        return ResponseEntity.ok("삭제 완료");
    }
}

//    //조회
//    @GetMapping("/mens")
//    //공개|메소드| 객체.메소드(매개변수 없음)
//    public ResponseEntity<List<Man>> get(){
//        //반환| 저장결과 200ok로 반환| manService의 전체 목록 가져오기
//        return ResponseEntity.ok(manService.getAll());
//    }
//
//    //단건조회
//    @GetMapping("/mens/{manId}")
//    //응답을 ResponseEntity 반환할건데, id를 가져와서
//    public ResponseEntity<Man> get(@PathVariable Long manId){
//        //id와 일치하는 데이터를 받아와 ok반환
//        return ResponseEntity.ok(manService.getById(manId));
//    }
//
//    //수정
//    @PutMapping("/mens/{manId}")
//    //index 를 사용해 리스트 내에 몇번째에 누구를 수정할지 확인함
//    public ResponseEntity<Man> update(@PathVariable Long manId, @RequestBody Man man) {
//        //manService의 update메소드로 index,man 을 전달
//        return ResponseEntity.ok(manService.update(manId, man));
//    }
//
//    @DeleteMapping("/mens/{manId}")
//    //공개| 반환 타입<문자> | 삭제 메소드(index에서 가져옴)
//    public ResponseEntity<String> delete(@PathVariable Long manId){
//        //manService의 삭제(index) 가져옴
//        manService.delete(manId);
//        return ResponseEntity.ok("삭제 완료");
//    }

