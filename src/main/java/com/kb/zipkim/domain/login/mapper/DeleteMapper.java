package com.kb.zipkim.domain.login.mapper;

import com.kb.zipkim.domain.login.entity.RefreshEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeleteMapper {

    List<RefreshEntity> selectToken();

    void deleteToken(Long id);
}
