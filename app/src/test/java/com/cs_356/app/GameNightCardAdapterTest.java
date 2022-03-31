package com.cs_356.app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.Utils.DateUtils;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Entities.GameNight;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GameNightCardAdapterTest {
    @Test
    public void onBindViewHolder_formatDate() {
        System.out.println(DateUtils.formatDate(LocalDateTime.now()));
    }
}