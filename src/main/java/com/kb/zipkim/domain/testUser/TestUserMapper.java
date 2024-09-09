package com.kb.zipkim.domain.testUser;

import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface TestUserMapper {

    List<TestUser> findAll();
}
