package com.example.navigationapp_backend.service;

import com.example.navigationapp_backend.dto.UserBaseDto;
import com.example.navigationapp_backend.dto.UserDto;
import com.example.navigationapp_backend.entity.User;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class UserMapper {

    @Inject
    EncryptionService encryptionService;

    public UserBaseDto userToUserBaseDto(User user) {
        if ( user == null ) {
            return null;
        }
        UserBaseDto userBaseDto = new UserBaseDto();
        userBaseDto.setAisId( user.getAisId() );
        return userBaseDto;
    }

    public User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }
        Map<String,String> map = encryptionService.encryptPassword(userDto.getPassword());
        User user = new User();
        user.setAisId(userDto.getAisId());
        user.setEncryptedPassword(map.get("hashedPassword"));
        user.setSalt(map.get("salt"));
        if(userDto.getTimeTable()!=null){
            user.setTimeTable(userDto.getTimeTable());
        }
        return user;
    }
}
