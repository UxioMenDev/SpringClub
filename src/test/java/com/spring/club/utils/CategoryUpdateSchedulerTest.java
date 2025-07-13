package com.spring.club.utils;

import com.spring.club.entities.Player;
import com.spring.club.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

class CategoryUpdateSchedulerTest {

    @Test
    void testUpdateCategories() {
        PlayerService playerService = Mockito.mock(PlayerService.class);
        Player player = Mockito.mock(Player.class);

        Mockito.when(playerService.findAll()).thenReturn(Collections.singletonList(player));

        CategoryUpdateScheduler scheduler = new CategoryUpdateScheduler(playerService);
        scheduler.updateCategories();

        Mockito.verify(playerService).findAll();
        Mockito.verify(player).calculateCategory();
        Mockito.verify(playerService).update(player);
    }
}