package com.comeon.player.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.comeon.player.service.PlayerService;

@Component
public class LimitEnforcementJob {

    @Autowired
    private PlayerService playerService;

    @Scheduled(fixedRate = 60000)
    public void checkAndLogoutExceededPlayers() {
        playerService.forceLogoutIfLimitExceeded();
    }
}
