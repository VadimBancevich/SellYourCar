package com.vb.utils.mappers;

import com.vb.api.dto.UserDto;
import com.vb.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserMapper extends AEntityDtoMapper<User, UserDto> {

    @Autowired
    private ModelMapper modelMapper;

    public UserMapper() {
        super(User.class, UserDto.class);
    }

    @PostConstruct
    private void setupMap() {
        modelMapper.createTypeMap(User.class, UserDto.class)
                .addMappings(mapping -> mapping.skip(UserDto::setPassword))
                .setPostConverter(toDtoConverter());
    }

}
