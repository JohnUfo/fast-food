package fast_food_website.service.impl;


import fast_food_website.entity.Role;
import fast_food_website.entity.User;
import fast_food_website.payload.EmailVerifyDto;
import fast_food_website.payload.RegistrationDto;
import fast_food_website.repository.RoleRepository;
import fast_food_website.repository.UserRepository;
import fast_food_website.security.SecurityUtil;
import fast_food_website.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @SneakyThrows
    @Override
    public void saveUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setFullName(registrationDto.getFullName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setVerificationCode(String.valueOf(new Random().nextInt(999999)).substring(0, 4));
        Role userRole = roleRepository.findByName("USER");
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
        sendEmail(user.getEmail(),user.getVerificationCode());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void userForFrontEnd(Model model) {
        User user = new User();
        model.addAttribute("user", null);
        String email = SecurityUtil.getSessionUser();
        if (email != null) {
            user = findByEmail(email);
        }
        model.addAttribute("user", user);
    }

    @Override
    public void verifyEmail(EmailVerifyDto emailVerifyDto, Model model) {
        User user = userRepository.findByEmail(emailVerifyDto.getEmail());
        if (user != null && user.getVerificationCode().equals(emailVerifyDto.getVerificationCode())) {
            user.setEnabled(true);
            user.setVerificationCode(null);
            userRepository.save(user);
        }
        model.addAttribute("user", user);
    }

    public void sendEmail(String sendingEmail, String emailCode) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);

        helper.setFrom("hvjjj83@gmail.com");
        helper.setTo(sendingEmail);
        helper.setSubject("Verify email");
        helper.setText(emailCode);
        javaMailSender.send(helper.getMimeMessage());
    }
}
