package com.vb.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends ADto {

    private String name;
    private String email;
    private String password;
    private List<String> roles;

}
