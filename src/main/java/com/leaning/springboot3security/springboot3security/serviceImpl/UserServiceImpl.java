package com.leaning.springboot3security.springboot3security.serviceImpl;

import com.leaning.springboot3security.springboot3security.dto.UserDTO;
import com.leaning.springboot3security.springboot3security.entity.UserInfo;
import com.leaning.springboot3security.springboot3security.repository.UserInfoRepository;
import com.leaning.springboot3security.springboot3security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addUser(UserDTO userDTO) {
        log.info("Inside addUser with request {}", userDTO);
        try{
            if(isUserExist(userDTO.getEmail())) {
                return "User already exists";
            }
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userDTO, userInfo);
            userInfo.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userInfo.setCreateDate(new Date());
            userInfo.setModiDate(new Date());
            userInfoRepository.saveAndFlush(userInfo);
            return "User Added Successfully";
        }catch (Exception e) {
            log.error("Exception occurred in addUser is ", e);
            return "Failed to Add User";
        }
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        log.info("Inside updateUser with request {}", userDTO);
        try{
            UserInfo user = userInfoRepository.findByEmailAndIsActive(userDTO.getEmail(),true);
            if(user==null) {
                return "User not found";
            }
            if(userDTO.getName()!=null)
                user.setName(userDTO.getName());
            if(userDTO.getEmail()!=null)
                user.setEmail(userDTO.getEmail());
            if(userDTO.getMobileNo()!=null)
                user.setMobileNo(userDTO.getMobileNo());
            if(userDTO.getRole()!=null)
                user.setRole(userDTO.getRole());
            if(userDTO.getPassword()!=null)
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userInfoRepository.saveAndFlush(user);
            return "User Updated Successfully";
        }catch(Exception e){
            log.error("Exception occurred in updateUser is ", e);
            return "Failed to Update User";
        }
    }

    @Override
    public String deleteUser(String email) {
        log.info("inside deleteUser with request {}", email);
        try {
            UserInfo user = userInfoRepository.findByEmailAndIsActive(email,true);
            if(user==null) {
                return "User not found";
            }
            user.setIsActive(false);
            userInfoRepository.saveAndFlush(user);
            return "User Deleted Successfully";
        }catch (Exception e) {
            log.error("Exception occurred while deleting {} is ", email, e);
            return "Failed to Delete User";
        }
    }

    @Override
    public Boolean isUserExist(String email) {
        UserInfo user = userInfoRepository.findByEmailAndIsActive(email,true);
        return user != null;
    }
}
