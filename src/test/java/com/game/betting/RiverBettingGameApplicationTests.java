package com.game.betting;

import com.game.betting.controller.PlayerController;
import com.game.betting.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RiverBettingGameApplicationTests {

    @Autowired
    private PlayerController playerController;

    @Autowired
    private PlayerService playerService;

    @Test
    void contextLoads() {
        assertThat(playerController).isNotNull();
        assertThat(playerService).isNotNull();
    }
}
