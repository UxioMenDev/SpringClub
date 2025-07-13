package com.spring.club.utils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SeasonUtilTest {

    @Test
    void testGetCurrentSeasonFormat() {
        String season = SeasonUtil.getCurrentSeason();
        assertThat(season).matches("\\d{2}/\\d{2}");

        String[] years = season.split("/");
        int first = Integer.parseInt(years[0]);
        int second = Integer.parseInt(years[1]);
        assertThat(second).isEqualTo(first + 1);
    }
}