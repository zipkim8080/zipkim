package com.kb.zipkim.domain.login.mapper;

import com.kb.zipkim.domain.login.entity.RefreshEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeleteMapper {

    public List<RefreshEntity> selectToken();

    public void deleteToken(Long id);
}
