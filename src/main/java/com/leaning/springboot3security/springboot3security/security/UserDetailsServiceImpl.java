package com.leaning.springboot3security.springboot3security.security;

import com.leaning.springboot3security.springboot3security.entity.UserInfo;
import com.leaning.springboot3security.springboot3security.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user = userInfoRepository.findByEmailAndIsActive(email, true);
        if(user!=null) {
            return new UserDetailsImpl(user);
        } else {
            throw new UsernameNotFoundException("UserName not found for this email : " + email);
        }
    }


}
