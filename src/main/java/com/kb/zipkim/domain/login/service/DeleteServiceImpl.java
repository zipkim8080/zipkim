package com.kb.zipkim.domain.login.service;

import com.kb.zipkim.domain.login.entity.RefreshEntity;
import com.kb.zipkim.domain.login.mapper.DeleteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class DeleteServiceImpl implements DeleteService {

    private final DeleteMapper deleteMapper;

    @Override
    @Scheduled(cron = "0 0/35 * * * ?")
    public void deleteToken() {
        List<RefreshEntity> expiredTokens = deleteMapper.selectToken();

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        for (RefreshEntity token : expiredTokens) {
            LocalDateTime tokenExpiration = LocalDateTime.parse(token.getExpiration(), formatter);

            if (tokenExpiration.isBefore(currentTime)) {
                deleteMapper.deleteToken(token.getId());
                System.out.println("삭제했으니 가서 확인해봐");
            }
        }
    }
}
