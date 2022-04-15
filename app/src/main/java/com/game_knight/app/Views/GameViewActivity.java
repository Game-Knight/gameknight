package com.game_knight.app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.game_knight.app.R;
import com.game_knight.app.Utils.Constants;
import com.game_knight.app.Utils.Image.PicassoTransformations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.apache.commons.text.StringEscapeUtils;

import Entities.BoardGame;
import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * This is the activity that represents viewing a single game.
 * TODO: Add a fragment that displays the game instructions.
 */
public class GameViewActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        BoardGame game = (BoardGame) getIntent().getSerializableExtra(Constants.GAME_KEY);

        ImageView gameImg, bgImg;
        TextView name, numPlayers, playTime, description;
        Button rulesButton;

        name = findViewById(R.id.gameTitle);
        numPlayers = findViewById(R.id.numPlayers);
        playTime = findViewById(R.id.playTime);
        gameImg = findViewById(R.id.gameImg);
        bgImg = findViewById(R.id.bgImg);
        description = findViewById(R.id.description);
        rulesButton = findViewById(R.id.rulesButton);

        Picasso.get().load(game.getImageUrl()).transform(new PicassoTransformations.SCALE_300_MAX()).into(gameImg);
        Picasso.get().load(game.getImageUrl()).transform(new PicassoTransformations.SCALE_1000_MAX()).transform(new BlurTransformation(bgImg.getContext(),75)).into(bgImg);

        name.setText(game.getName());
        numPlayers.setText(game.getMinPlayers() + "–" + game.getMaxPlayers() + " " + getString(R.string.players));
        if (game.getMinPlayingTime() != game.getMaxPlayingTime()) {
            playTime.setText(game.getMinPlayingTime() + "–" + game.getMaxPlayingTime() + " " + getString(R.string.mins));
        } else {
            playTime.setText(game.getMinPlayingTime() + " " + getString(R.string.mins));
        }
        description.setText(StringEscapeUtils.unescapeHtml4(game.getDescription()));

        if (game.getRulesURL() == null || game.getRulesURL().equals("")) {
            rulesButton.setVisibility(View.INVISIBLE);
        }
        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GameRulesActivity.class);
                intent.putExtra("URL", game.getRulesURL());
                startActivity(intent);
            }
        });

        FloatingActionButton backFAB = findViewById(R.id.game_view_back_button);
        backFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        FloatingActionButton closeFullscreenFAB = findViewById(R.id.closeFullscreenFAB);
        View fullscreenShade = findViewById(R.id.fullscreenShade);
        ImageView fullscreenGameImage = findViewById(R.id.fullscreenGameImage);

        Picasso.get().load(game.getImageUrl()).into(fullscreenGameImage);

        gameImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFullscreenFAB.setVisibility(View.VISIBLE);
                fullscreenShade.setVisibility(View.VISIBLE);
                fullscreenGameImage.setVisibility(View.VISIBLE);
            }
        });

        closeFullscreenFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFullscreenFAB.setVisibility(View.GONE);
                fullscreenShade.setVisibility(View.GONE);
                fullscreenGameImage.setVisibility(View.GONE);
            }
        });

    }
}