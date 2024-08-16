//package com.ltts.usermanagement.UMvalidation;
//
//import java.util.regex.Pattern;
//
//import com.ltts.usermanagement.UMexception.InvalidEmailException;
//import com.ltts.usermanagement.UMexception.InvalidPasswordException;
//
//public class Validation {
//	
//	private static final String NOKIA_EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@nokia\\.com$";
//    private static final Pattern NOKIA_EMAIL_PATTERN = Pattern.compile(NOKIA_EMAIL_REGEX);
//	
//	public void validateEmail(String email) {
//        if (email == null || email.isEmpty()) {
//            throw new InvalidEmailException("Email cannot be empty");
//        }
//        if (!NOKIA_EMAIL_PATTERN.matcher(email).matches()) {
//            throw new InvalidEmailException("Email must end with @nokia.com");
//        }
//    }
//
//    public void validatePassword(String password) {
//        if (password == null || password.isEmpty()) {
//            throw new InvalidPasswordException("Password cannot be empty");
//        }
//        // Add more password validations if necessary, e.g., length, complexity
//    }
//}
