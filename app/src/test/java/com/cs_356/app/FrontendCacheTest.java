package com.cs_356.app;

import static org.junit.Assert.*;

import com.cs_356.app.Cache.FrontendCache;

import org.junit.Test;

import java.util.List;

import Entities.GameNight;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FrontendCacheTest {
    @Test
    public void initGameNightCache_works() {
        // Plz don't throw any errors
        List<GameNight> gameNightList = FrontendCache.getGameNightsForAuthenticatedUser();
        assertNotNull(gameNightList);
        assertFalse(gameNightList.isEmpty());
    }

    @Test
    public void xInYChance_works() {
        /* I this doesn't really *test* anything per se, but this was just helpful to see if the
         * values kind of aligned. (Ex: the 1/4 chance test may only return 1 true out of 10,
         * but that's just randomness I guess
         */
        int x = 1;
        int y = 1;
        System.out.printf("%x in %x chance\n", x, y);
        for (int i = 0; i < 10; i++) {
            System.out.println(FrontendCache.xInYChance(x, y));
        }

        y = 2;
        System.out.printf("\n%x in %x chance\n", x, y);
        for (int i = 0; i < 10; i++) {
            System.out.println(FrontendCache.xInYChance(x, y));
        }

        x = 3;
        y = 4;
        System.out.printf("\n%x in %x chance\n", x, y);
        for (int i = 0; i < 10; i++) {
            System.out.println(FrontendCache.xInYChance(x, y));
        }

        x = 1;
        System.out.printf("\n%x in %x chance\n", x, y);
        for (int i = 0; i < 10; i++) {
            System.out.println(FrontendCache.xInYChance(x, y));
        }
    }
}