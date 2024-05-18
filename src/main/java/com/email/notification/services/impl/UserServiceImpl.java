package com.email.notification.services.impl;

import com.email.notification.dto.RequestDto;
import com.email.notification.dto.UserMapper;
import com.email.notification.entities.EmailDetails;
import com.email.notification.entities.User;
import com.email.notification.repository.UserRepository;
import com.email.notification.services.EmailService;
import com.email.notification.services.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Transactional
    @Override
    public void registerUser(@NotNull RequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            log.warn("User with email {} already exists", requestDto.getEmail());
            throw new RuntimeException("User with email " + requestDto.getEmail() + " already exists");
        }

        String encodedPassword = Base64.getEncoder().encodeToString(requestDto.getPassword().getBytes());
        User user = UserMapper.mapToUser(new User(), requestDto);
        user.setPassword(encodedPassword);
        userRepository.save(user);

        // Prepare HTML email body
        String emailBody = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>Registration Success</title>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; }" +
                ".container { padding: 20px; background-color: #f9f9f9; border: 1px solid #ddd; margin: 20px auto; width: 80%; max-width: 600px; }" +
                ".header { background-color: #4CAF50; color: white; padding: 10px; text-align: center; }" +
                ".content { margin: 20px 0; }" +
                ".footer { text-align: center; color: #777; margin-top: 20px; font-size: 12px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "<div class=\"header\">" +
                "<h1>Registration Successful</h1>" +
                "</div>" +
                "<div class=\"content\">" +
                "<p>Dear " + requestDto.getFirstName() + " " + requestDto.getLastName() + ",</p>" +
                "<p>Thank you for registering with us. Your email ID <strong>" + requestDto.getEmail() + "</strong> has been successfully registered.</p>" +
                "<p>If you have any questions, feel free to contact our support team.</p>" +
                "</div>" +
                "<div class=\"footer\">" +
                "<p>&copy; 2024 Your Company. All rights reserved.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

        // Send HTML email
        String subject = String.format("Welcome %s %s, Your registration is successful", requestDto.getFirstName(), requestDto.getLastName());
        emailService.sendEmail(EmailDetails.builder()
                .recipient(requestDto.getEmail())
                .messageBody(emailBody)
                .subject(subject)
                .build());
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            String encodedPassword = user.getPassword();
            byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
            String decodedPassword = new String(decodedBytes);
            user.setPassword(decodedPassword);
        }
        log.info("All Users: {}", users);
        return users;
    }

}
