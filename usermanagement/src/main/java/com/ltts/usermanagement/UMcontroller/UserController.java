package com.ltts.usermanagement.UMcontroller;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.ltts.usermanagement.UMentity.DashboardTimeStamp;
import com.ltts.usermanagement.UMentity.Session;
import com.ltts.usermanagement.UMentity.User;
import com.ltts.usermanagement.UMentity.dts;
import com.ltts.usermanagement.UMmodel.SessionResponse;
import com.ltts.usermanagement.UMmodel.dtsRequest;
//import com.ltts.usermanagement.UMrepo.DashboardTimeStampRepository;
import com.ltts.usermanagement.UMrepo.DtsRepository;
import com.ltts.usermanagement.UMrepo.SessionRepository2;
import com.ltts.usermanagement.UMrepo.UserRepository;
import com.ltts.usermanagement.UMservice.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private SessionRepository2 sessRepo;
    
//    @Autowired
//    private DashboardTimeStampRepository dashboardTimeStampRepository;
    
    @Autowired
    private DtsRepository dtsRepo;

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return userService.signup(user.getUsername(), user.getPassword(), user.getRole());
    }

//    @PostMapping("/login")
//    public String login(@RequestBody User user) {
//        return userService.login(user.getUsername(), user.getPassword());
//    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // Check if username or password is missing
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }

        String result = userService.login(username, password);
        
        if (result.equals("Invalid credentials")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
        
        return ResponseEntity.ok(result);
    }


    @PostMapping("/logout")
    public String logout(@RequestBody User user) {
        return userService.logout(user.getUsername());
    }

    @PostMapping("/loggedin-duration")
    public long getLoggedInDuration(@RequestBody User user) {
        return userService.getLoggedInDuration(user.getUsername());
    }
    
    @GetMapping("/sessions")
    public List<SessionResponse> getAllSessionsByUsername(@RequestParam("username") String username) {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            List<Session> sessions = sessRepo.findByUserId(user.getId());
            return sessions.stream()
                    .map(this::convertSessionToResponse)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
    
    private SessionResponse convertSessionToResponse(Session session) {
        SessionResponse response = new SessionResponse();
        response.setId(session.getId());
        response.setUserId(session.getUser().getId());
        response.setLoginTime(session.getLoginTime());
        response.setLogoutTime(session.getLogoutTime());
        response.setDuration(session.getDuration() / 60); // convert seconds to minutes
        return response;
    }
    

//    @GetMapping("/entry")
//    public String enterDashboard() {
//        DashboardTimeStamp dashboardTimeStamp = new DashboardTimeStamp();
//        dashboardTimeStamp.setEntryTime(LocalDateTime.now());
//        dashboardTimeStampRepository.save(dashboardTimeStamp);
//        return "Welcome to the dashboard!";
//    }
//
//    @PostMapping("/exit")
//    public ResponseEntity<String> exitDashboard(@RequestBody String viewTime) {
//        DashboardTimeStamp dashboardTimeStamp = dashboardTimeStampRepository.findTopByExitTimeIsNullOrderByEntryTimeDesc();
//        if (dashboardTimeStamp!= null) {
//            dashboardTimeStamp.setExitTime(LocalDateTime.now());
//            dashboardTimeStamp.setViewTime(viewTime);
//            dashboardTimeStampRepository.save(dashboardTimeStamp);
//            return ResponseEntity.ok("Exited dashboard successfully");
//        } else {
//            return ResponseEntity.badRequest().body("No active session found");
//        }
//    }
    
    
    @GetMapping("/dtsentry")
    public String enterDts() {
        try {
            dts dashboardTimeStamp = new dts();
            dashboardTimeStamp.setEntryTime(LocalDateTime.now());
            dtsRepo.save(dashboardTimeStamp);
            return "Welcome to the dashboard!";
        } catch (Exception e) {
            log.error("Error entering dashboard: {}", e.getMessage());
            return "Error entering dashboard";
        }
    }

    @PostMapping("/dtsexit")
    public ResponseEntity<String> exitDts(@RequestBody dtsRequest req) {
        try {
            List<dts> dashboardTimeStamps = dtsRepo.findTopByExitTimeIsNullOrderByEntryTimeDesc();
            if (!dashboardTimeStamps.isEmpty()) {
                dts dashboardTimeStamp = dashboardTimeStamps.get(0); // Take the first result
                dashboardTimeStamp.setExitTime(LocalDateTime.now());
                dashboardTimeStamp.setViewTime(req.getViewTime());
                dashboardTimeStamp.setUsername(req.getUserName());
                dtsRepo.save(dashboardTimeStamp);
                return ResponseEntity.ok("Exited dashboard successfully");
            } else {
                return ResponseEntity.badRequest().body("No active session found");
            }
        } catch (Exception e) {
            log.error("Error exiting dashboard: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error exiting dashboard");
        }
    }
    
    @GetMapping("/getAlldtsData")
    public List<dts> getAllDts() {
        return dtsRepo.findAll();
    }
    
}