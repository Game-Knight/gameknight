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
}