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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import io.github.hugoangeles0810.marvelcharacters.characters.domain.model.Character;
import io.github.hugoangeles0810.marvelcharacters.data.source.CharactersLocalDataSource;
import io.github.hugoangeles0810.marvelcharacters.data.source.local.MarvelCharactersPersistentContract.CharacterEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by hugo on 31/03/17.
 */

public class CharactersLocalDataSourceImpl
    implements io.github.hugoangeles0810.marvelcharacters.data.source.CharactersLocalDataSource {

    private static CharactersLocalDataSourceImpl INSTANCE;

    private MarvelCharactersDbHelper mDbHelper;

    // Prevent direct instantiation.
    private CharactersLocalDataSourceImpl(@NonNull Context context) {
        mDbHelper = new MarvelCharactersDbHelper(context);
    }

    public static CharactersLocalDataSourceImpl getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CharactersLocalDataSourceImpl(context);
        }

        return INSTANCE;
    }

    @Override
    public void getCharacters(@NonNull LoadCharactersCallback callback) {
        List<Character> characters = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                CharacterEntry._ID,
                CharacterEntry.COLUMN_NAME_NAME,
                CharacterEntry.COLUMN_NAME_DESCRIPTION,
                CharacterEntry.COLUMN_NAME_IMAGE_URL
        };

        Cursor c = db.query(
                CharacterEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Long id = c.getLong(c.getColumnIndexOrThrow(CharacterEntry._ID));
                String name = c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_NAME));
                String description = c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_DESCRIPTION));
                String imageUrl = c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_IMAGE_URL));
                Character character = new Character(id, name, description, imageUrl);
                characters.add(character);
            }
        }

        if (c != null) {
            c.close();
        }

        db.close();

        if (characters.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onCharactersLoaded(characters);
        }
    }

    @Override
    public void getCharacters(int offset, int limit, @NonNull LoadCharactersCallback callback) {
        List<Character> characters = new ArrayList<>();

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                CharacterEntry._ID,
                CharacterEntry.COLUMN_NAME_NAME,
                CharacterEntry.COLUMN_NAME_DESCRIPTION,
                CharacterEntry.COLUMN_NAME_IMAGE_URL
        };

        String limitQuery = String.format(Locale.getDefault(), "%d, %d", offset, limit);

        Cursor c = db.query(
                CharacterEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                limitQuery);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                Long id = c.getLong(c.getColumnIndexOrThrow(CharacterEntry._ID));
                String name = c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_NAME));
                String description = c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_DESCRIPTION));
                String imageUrl = c.getString(c.getColumnIndexOrThrow(CharacterEntry.COLUMN_NAME_IMAGE_URL));
                Character character = new Character(id, name, description, imageUrl);
                characters.add(character);
            }
        }

        if (c != null) {
            c.close();
        }

        db.close();

        if (characters.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onCharactersLoaded(characters);
        }
    }

    @Override
    public void saveCharacter(@NonNull Character character) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CharacterEntry._ID, character.getId());
        cv.put(CharacterEntry.COLUMN_NAME_NAME, character.getName());
        cv.put(CharacterEntry.COLUMN_NAME_DESCRIPTION, character.getDescription());
        cv.put(CharacterEntry.COLUMN_NAME_IMAGE_URL, character.getImageUrl());

        db.insert(CharacterEntry.TABLE_NAME, null, cv);

        db.close();
    }

    @Override
    public void saveOrUpdate(@NonNull Character character) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CharacterEntry._ID, character.getId());
        cv.put(CharacterEntry.COLUMN_NAME_NAME, character.getName());
        cv.put(CharacterEntry.COLUMN_NAME_DESCRIPTION, character.getDescription());
        cv.put(CharacterEntry.COLUMN_NAME_IMAGE_URL, character.getImageUrl());

        db.replace(CharacterEntry.TABLE_NAME, null, cv);

        db.close();
    }

    @Override public void saveOrUpdate(@NonNull List<Character> characters) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        for (Character character : characters) {
            ContentValues cv = new ContentValues();
            cv.put(CharacterEntry._ID, character.getId());
            cv.put(CharacterEntry.COLUMN_NAME_NAME, character.getName());
            cv.put(CharacterEntry.COLUMN_NAME_DESCRIPTION, character.getDescription());
            cv.put(CharacterEntry.COLUMN_NAME_IMAGE_URL, character.getImageUrl());

            db.replace(CharacterEntry.TABLE_NAME, null, cv);
        }

        db.close();
    }

    @Override
    public void deleteAllCharacters() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(CharacterEntry.TABLE_NAME, null, null);

        db.close();
    }
}
