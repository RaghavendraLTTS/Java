package com.ltts.usermanagement.UMservice;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ltts.usermanagement.UMentity.Session;
import com.ltts.usermanagement.UMentity.User;
import com.ltts.usermanagement.UMexception.InvalidEmailException;
import com.ltts.usermanagement.UMexception.InvalidPasswordException;
import com.ltts.usermanagement.UMexception.InvalidRoleException;
import com.ltts.usermanagement.UMrepo.SessionRepository;
import com.ltts.usermanagement.UMrepo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private static final String NOKIA_EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@nokia\\.com$";
    private static final Pattern NOKIA_EMAIL_PATTERN = Pattern.compile(NOKIA_EMAIL_REGEX);

//    public User signup(String username, String password, String role) {
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setRole(role);
//        return userRepository.save(user);
//    }
    
    

    public User signup(String username, String password, String role) {
        validateEmail(username);
        validatePassword(password);
        validateRole(role);

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }

//    public String login(String username, String password) {
//        User user = userRepository.findByUsername(username);
//        System.out.println("login user :"+user.getUsername());
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//            Session session = new Session();
//            session.setUser(user);
//            session.setLoginTime(LocalDateTime.now());
//            sessionRepository.save(session);
//            return "Login successfull Role :"+ user.getRole();
//        }
//        return "Invalid credentials";
//    }
    
    public String login(String username, String password) {
        // Check if username is empty or null
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty";
        }

        // Check if password is empty or null
        if (password == null || password.trim().isEmpty()) {
            return "Password cannot be empty";
        }

        User user = userRepository.findByUsername(username);
        
        // Check if user is found
        if (user == null) {
            return "Invalid credentials"; // or "User not found"
        }

        System.out.println("login user :" + user.getUsername());

        // Check if the provided password matches the stored password
        if (passwordEncoder.matches(password, user.getPassword())) {
            Session session = new Session();
            session.setUser(user);
            session.setLoginTime(LocalDateTime.now());
            sessionRepository.save(session);
            return "Login successful. Role: " + user.getRole();
        }

        // If password does not match
        return "Invalid credentials";
    }

    public String logout(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Session activeSession = sessionRepository.findFirstByUserAndLogoutTimeIsNull(user);
            if (activeSession != null) {
                activeSession.setLogoutTime(LocalDateTime.now());
                sessionRepository.save(activeSession);
                return "Logout successful";
            }
        }
        return "User not logged in or no active sessions found";
    }

    public long getLoggedInDuration(String username) {
        User user = userRepository.findByUsername(username);
        System.out.println("User : "+user.getUsername());
        if (user != null) {
            Session session = sessionRepository.findFirstByUserAndLogoutTimeIsNull(user);
            if (session != null) {
                return java.time.Duration.between(session.getLoginTime(), LocalDateTime.now()).toMinutes();
            }
        }
        return 0;
    }
    
    
    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new InvalidEmailException("Email cannot be empty");
        }
        if (!NOKIA_EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException("Email must end with @nokia.com");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new InvalidPasswordException("Password cannot be empty");
        }
        // Add more password validations if necessary, e.g., length, complexity
    }
    
    private void validateRole(String role) {
    	if(role == null || role.isEmpty()) {
    		throw new InvalidRoleException("role cannot be empty");
    	}
    }
    
}