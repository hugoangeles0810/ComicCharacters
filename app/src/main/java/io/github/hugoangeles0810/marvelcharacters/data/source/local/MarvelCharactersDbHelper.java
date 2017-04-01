/*
 * Copyright 2017 Hugo Angeles
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 *    http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.hugoangeles0810.marvelcharacters.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.github.hugoangeles0810.marvelcharacters.data.source.local.MarvelCharactersPersistentContract.CharacterEntry;

/**
 * Created by hugo on 31/03/17.
 */

public class MarvelCharactersDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "MarvelCharacters.db";


    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CharacterEntry.TABLE_NAME + " ("
                    + CharacterEntry._ID + INT_TYPE + " PRIMARY KEY,"
                    + CharacterEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP
                    + CharacterEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP
                    + CharacterEntry.COLUMN_NAME_IMAGE_URL + TEXT_TYPE
                    + " )";

    public MarvelCharactersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
