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

package io.github.hugoangeles0810.comiccharacters.data.repository;

import java.util.List;

import io.github.hugoangeles0810.comiccharacters.characterdetail.domain.model.Comic;
import io.github.hugoangeles0810.comiccharacters.data.source.ComicsRemoteDataSource;

public class ComicsRepositoryImpl implements ComicsRepository {

    private static ComicsRepository INSTANCE = null;

    private final ComicsRemoteDataSource mComicsDataSource;

    private ComicsRepositoryImpl(ComicsRemoteDataSource comicsDataSource) {
        this.mComicsDataSource = comicsDataSource;
    }

    public static ComicsRepository getInstance(ComicsRemoteDataSource comicsRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ComicsRepositoryImpl(comicsRemoteDataSource);
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getComics(Long characterId, final LoadComicsCallback callback) {
        mComicsDataSource.getComics(characterId, new ComicsRemoteDataSource.LoadComicsCallback() {
            @Override
            public void onComicsLoaded(List<Comic> comics) {
                callback.onComicsLoaded(comics);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }
}
