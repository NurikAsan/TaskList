package com.nurikov.tasklist.web.mapper;

import com.nurikov.tasklist.domain.user.User;
import com.nurikov.tasklist.web.dto.user.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDTO>{

}
