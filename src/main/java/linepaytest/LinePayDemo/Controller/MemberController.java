package linepaytest.LinePayDemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;

import linepaytest.LinePayDemo.Dao.MemberDao;
import linepaytest.LinePayDemo.Model.Member;
import linepaytest.LinePayDemo.Security.MyJwtUtil;


@RestController
public class MemberController {
    
    @Autowired
    private MemberDao memberDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyJwtUtil myJwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 註冊會員帳號
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Member member) {
        
        // 將密碼加密
        String hashedPassword = passwordEncoder.encode(member.getPassword());
        // 將加密後的密碼設定回 member 物件
        member.setPassword(hashedPassword);

        // 檢查是否有缺少必要欄位
        if  (member.getMemberName().isBlank() || member.getMemberName() == null || 
            member.getEmail().isBlank() || member.getEmail() == null || 
            member.getPassword().isBlank() || member.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Missing required fields"));
        }
        
        // 檢查是否已經註冊過
        Integer memberIdCheck = memberDao.getMemberIdByEmail(member.getEmail());
        if(memberIdCheck != null) {
            System.out.println("Email already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email already exists"));
        }

        // 會員未被注冊，新增註冊會員
        else {
            Integer memberId = memberDao.register(member);
            System.out.println("Member ID: " + memberId);
            return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
        }
    }

    // 登入會員帳號
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member member){
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword()));

            System.out.println("authhentication: " + authentication.getPrincipal());
            System.out.println("Member email: " + member.getEmail());
            String token = myJwtUtil.generateToken(member.getEmail());

            return ResponseEntity.ok(Map.of("token", token));
        }
        catch (Exception e) {
            // System.out.println("Invalid credentials" + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }

    // 取得會員資料
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String autHeader) {
        // 檢查是否有 token
        if (autHeader == null || !autHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid token"));
        }

        // 檢查 token 是否有效
        String token = autHeader.substring(7);
        if (!myJwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid token"));
        }

        // 取得 token 中的 email
        String email = myJwtUtil.getEmailFromToken(token);
        Member member = memberDao.getMemberByEmail(email);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Member not found"));
        }

        return ResponseEntity.ok(Map.of("memberName", member.getMemberName(), "email", member.getEmail()));
    }
}
