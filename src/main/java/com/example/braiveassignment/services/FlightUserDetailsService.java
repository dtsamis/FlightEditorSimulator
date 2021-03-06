package com.example.braiveassignment.services;

import com.example.braiveassignment.Model.Authority;
import com.example.braiveassignment.Model.User;
import com.example.braiveassignment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
/***
 * This is a custom implementation of user details service
 */
public class FlightUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("getting users from JPA");
        User user=userRepository.findByUsername(username).orElseThrow(()-> {
            return new UsernameNotFoundException("User name: "+username+" not found!");});
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getEnabled(),user.getAccountNonExpired(),user.getCredentialsNonExpired(),user.getAccountNonLocked(),convertToSpringAuthorities(user.getAuthorities()));
        }

    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Authority> authorities) {
        if(authorities!=null && authorities.size()>0)
        {
            return authorities.stream()
                    .map(Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
        else
        {
            return new HashSet<>();
        }


    }
}
