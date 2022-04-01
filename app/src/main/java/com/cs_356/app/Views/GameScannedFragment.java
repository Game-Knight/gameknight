package com.cs_356.app.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cs_356.app.Cache.FrontendCache;
import com.cs_356.app.R;
import com.cs_356.app.Utils.ActivityUtils;
import com.cs_356.app.Utils.Constants;
import com.cs_356.app.Utils.Image.PicassoTransformations;
import com.cs_356.app.databinding.FragmentGameScannedBinding;
import com.cs_356.app.databinding.GameCardBinding;
import com.google.android.gms.vision.CameraSource;
import com.squareup.picasso.Picasso;

import Entities.BoardGame;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class GameScannedFragment extends Fragment {

    private FragmentGameScannedBinding binding;
    private GameCardBinding gameCardBinding;
    private CameraSource cameraSource;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentGameScannedBinding.inflate(inflater, container, false);
        gameCardBinding = GameCardBinding.inflate(inflater, binding.resultLinearLayout, true);
        initializeCamera();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BoardGame boardGameRetrieved = null;
        try {
            assert getArguments() != null;
            String barcodeRetrieved = getArguments().getString(Constants.BARCODE_KEY);
            boardGameRetrieved = FrontendCache.getGameMatchingUPC(barcodeRetrieved);
        }
        catch (Exception ex) {
            System.out.println("There was no barcode retrieved!");
        }

        if (boardGameRetrieved == null) {
            binding.resultTitle.setText(R.string.no_game_found);

            Picasso.get().load(Constants.NO_RESULTS_IMG_URL)
                    .transform(new PicassoTransformations.SCALE_300_MAX())
                    .into(gameCardBinding.cardImg);
            Picasso.get().load(Constants.NO_RESULTS_IMG_URL)
                    .transform(new PicassoTransformations.CROP_SQUARE())
                    .transform(new PicassoTransformations.SCALE_300_MAX())
                    .transform(new BlurTransformation(gameCardBinding.cardBg.getContext(),30))
                    .into(gameCardBinding.cardBg);

            binding.addGameButton.setText(R.string.add_game_manually_button);
            binding.addGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requireActivity().startActivity(
                            new Intent(requireActivity(), AddGameManuallyActivity.class)
                    );
                    requireActivity().finish();
                }
            });

            binding.retryButton.setVisibility(View.VISIBLE);
            binding.retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment.findNavController(GameScannedFragment.this)
                            .navigate(R.id.action_GameScannedFragment_to_BarcodeScannerFragment);
                }
            });
        }
        else {
            final BoardGame nonNullBoardGame = boardGameRetrieved;
            Picasso.get().load(nonNullBoardGame.getImageUrl())
                    .transform(new PicassoTransformations.SCALE_300_MAX())
                    .into(gameCardBinding.cardImg);
            Picasso.get().load(nonNullBoardGame.getImageUrl())
                    .transform(new PicassoTransformations.CROP_SQUARE())
                    .transform(new PicassoTransformations.SCALE_300_MAX())
                    .transform(new BlurTransformation(gameCardBinding.cardBg.getContext(),30))
                    .into(gameCardBinding.cardBg);

            binding.addGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FrontendCache.addGameOwnershipForAuthUser(nonNullBoardGame);
                    requireActivity().startActivity(
                            new Intent(requireActivity(), GameLibraryActivity.class)
                    );
                    requireActivity().finish();
                }
            });
        }

        binding.gameScannedBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GameScannedFragment.this)
                        .navigate(R.id.action_GameScannedFragment_to_BarcodeScannerFragment);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeCamera();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cameraSource.release();
        binding = null;
    }

    private void initializeCamera() {
        cameraSource = ActivityUtils.initializeCamera(
                requireActivity(),
                binding.gameScannedSurfaceView,
                false,
                null
        );
    }
}