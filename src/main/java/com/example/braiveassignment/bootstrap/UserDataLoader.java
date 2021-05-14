package com.example.braiveassignment.bootstrap;

import com.example.braiveassignment.Model.Authority;
import com.example.braiveassignment.Model.User;
import com.example.braiveassignment.repositories.AuthorityRepository;
import com.example.braiveassignment.repositories.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
@Builder
@Slf4j
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {

        if(authorityRepository.count()==0)
        {
            loadSecurityData();
        }

    }

    private void loadSecurityData() {
        Authority admin=authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
        Authority user=authorityRepository.save(Authority.builder().role("ROLE_USER").build());

        userRepository.save(User.builder().username("Admin").password(passwordEncoder.encode("adminPass")).authority(admin).build());
        userRepository.save(User.builder().username("User").password(passwordEncoder.encode("userPass")).authority(user).build());

    log.debug("Users loaded "+userRepository.count());
    }
}
