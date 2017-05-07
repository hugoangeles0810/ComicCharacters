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

package io.github.hugoangeles0810.marvelcharacters.data.repository;

import android.support.annotation.NonNull;
import io.github.hugoangeles0810.marvelcharacters.characters.domain.model.Character;
import io.github.hugoangeles0810.marvelcharacters.data.source.CharactersLocalDataSource;
import io.github.hugoangeles0810.marvelcharacters.data.source.CharactersRemoteDataSource;
import java.util.List;

/**
 * Created by hugo on 30/03/17.
 */

public class CharactersRepositoryImpl implements CharactersRepository {

    private static  CharactersRepository INSTANCE = null;

    private final CharactersRemoteDataSource mCharsRemoteDataSource;

    private final CharactersLocalDataSource mCharsLocalDataSource;

    private CharactersRepositoryImpl(@NonNull CharactersRemoteDataSource charsRemoteDataSource,
                                    @NonNull CharactersLocalDataSource charsLocalDataSource) {
        mCharsRemoteDataSource = charsRemoteDataSource;
        mCharsLocalDataSource = charsLocalDataSource;
    }

    public static CharactersRepository getInstance(CharactersRemoteDataSource charsRemoteDataSource,
                                                   CharactersLocalDataSource charsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CharactersRepositoryImpl(charsRemoteDataSource, charsLocalDataSource);
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Get characters from remote data source or local data source (SQLite), whichever is
     * available first
     * <p/>
     * Note: {@link LoadCharactersCallback#onDataNotAvailable()} is fired if all data source fail
     * to get data
     */
    @Override
    public void getCharacters(@NonNull final LoadCharactersCallback callback) {
        mCharsRemoteDataSource.getCharacters(new CharactersRemoteDataSource.LoadCharactersCallback() {
            @Override public void onCharactersLoaded(List<Character> characters) {
                callback.onCharactersLoaded(characters);
                mCharsLocalDataSource.saveOrUpdate(characters);
            }

            @Override public void onDataNotAvailable() {
                getCharactersFromLocalDataSource(callback);
            }
        });
    }

    @Override
    public void getCharacters(final int offset, final int limit, @NonNull final LoadCharactersCallback callback) {
        mCharsRemoteDataSource.getCharacters(offset, limit, new CharactersRemoteDataSource.LoadCharactersCallback() {
            @Override public void onCharactersLoaded(List<Character> characters) {
                callback.onCharactersLoaded(characters);
                mCharsLocalDataSource.saveOrUpdate(characters);
            }

            @Override public void onDataNotAvailable() {
                getCharactersFromLocalDataSource(offset, limit, callback);
            }
        });
    }

    private void getCharactersFromLocalDataSource(final LoadCharactersCallback callback) {
        mCharsLocalDataSource.getCharacters(new CharactersLocalDataSource.LoadCharactersCallback() {
            @Override
            public void onCharactersLoaded(List<Character> characters) {
                callback.onCharactersLoaded(characters);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void getCharactersFromLocalDataSource(int offset, int limit, final LoadCharactersCallback callback) {
        mCharsLocalDataSource.getCharacters(offset, limit, new CharactersLocalDataSource.LoadCharactersCallback() {
            @Override
            public void onCharactersLoaded(List<Character> characters) {
                callback.onCharactersLoaded(characters);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

}
