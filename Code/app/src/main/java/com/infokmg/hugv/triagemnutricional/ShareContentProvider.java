package com.infokmg.hugv.triagemnutricional;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * This is just a share content provider,
 * IT IS IRRELEVANT TO THE EXAMPLE BY ITSELF
 */
public class ShareContentProvider extends ContentProvider {


    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        File cacheDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "HUGV");
        File privateFile = new File(cacheDir, uri.getLastPathSegment());
        return ParcelFileDescriptor.open(privateFile, ParcelFileDescriptor.MODE_READ_ONLY);
    }

    @Override
    public boolean onCreate() {
        // no create
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // no query
        return null;
    }

    @Override
    public String getType(Uri uri) {
        // no get type
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // no insert
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // no delete
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // no update
        return 0;
    }

}
