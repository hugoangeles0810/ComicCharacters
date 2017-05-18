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

package io.github.hugoangeles0810.comiccharacters.characterdetail.domain.usecase;

import android.support.annotation.NonNull;

import java.util.List;

import io.github.hugoangeles0810.comiccharacters.UseCase;
import io.github.hugoangeles0810.comiccharacters.characterdetail.domain.model.Comic;
import io.github.hugoangeles0810.comiccharacters.data.repository.ComicsRepository;

public class GetComics extends UseCase<GetComics.RequestValues, GetComics.ResponseValue> {

    private final ComicsRepository mComicRepository;

    public GetComics(@NonNull ComicsRepository comicRepository) {
        this.mComicRepository = comicRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        mComicRepository.getComics(requestValues.getCharacterId(), new ComicsRepository.LoadComicsCallback() {
            @Override
            public void onComicsLoaded(List<Comic> comics) {
                ResponseValue response = new ResponseValue(comics);
                getUseCaseCallback().onSuccess(response);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final Long mCharacterId;

        public RequestValues(@NonNull Long characterId) {
            mCharacterId = characterId;
        }

        public Long getCharacterId() {
            return mCharacterId;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Comic> mComics;

        public ResponseValue(@NonNull List<Comic> comics) {
            this.mComics = comics;
        }

        public List<Comic> getComics() {
            return mComics;
        }
    }
}
