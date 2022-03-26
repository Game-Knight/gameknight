package com.cs_356.app.Utils;

import android.app.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.StandardCopyOption;

import DataAccess.DataGeneration.InMemoryDB;
import Exceptions.DataAccessException;

public class DirectDBAccess {

    private static DirectDBAccess dbAccessInstance = null;

    public final InMemoryDB db;

    private DirectDBAccess(Activity activity) throws DataAccessException {
        try {
            for (String filename : activity.getAssets().list("")) {
                InputStream is = activity.getAssets().open(filename);
                File targetFile = new File(filename);

                java.nio.file.Files.copy(is, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                is.close();
            }

            this.db = InMemoryDB.getInstance(true);
        }
        catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    public static DirectDBAccess getInstance(Activity activity) throws DataAccessException {
        if (dbAccessInstance == null) {
            dbAccessInstance = new DirectDBAccess(activity);
        }

        return dbAccessInstance;
    }
}
