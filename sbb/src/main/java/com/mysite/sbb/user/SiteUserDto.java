package com.mysite.sbb.user;

import lombok.Data;

@Data
public class SiteUserDto {
  private Long id;
  private String username;
  private String password;
  private String email;
}
