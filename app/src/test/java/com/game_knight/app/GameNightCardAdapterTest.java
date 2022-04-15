package com.game_knight.app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.game_knight.app.Utils.DateUtils;

import org.junit.Test;

import java.time.LocalDateTime;

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