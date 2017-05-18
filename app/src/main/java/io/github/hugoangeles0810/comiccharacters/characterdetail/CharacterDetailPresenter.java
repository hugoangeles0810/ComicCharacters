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

package io.github.hugoangeles0810.comiccharacters.characterdetail;

import android.support.annotation.NonNull;

import java.util.List;

import io.github.hugoangeles0810.comiccharacters.UseCase;
import io.github.hugoangeles0810.comiccharacters.UseCaseHandler;
import io.github.hugoangeles0810.comiccharacters.characterdetail.domain.model.Comic;
import io.github.hugoangeles0810.comiccharacters.characterdetail.domain.usecase.GetComics;
import io.github.hugoangeles0810.comiccharacters.characters.domain.model.Character;

public class CharacterDetailPresenter implements CharacterDetailContract.Presenter {

    private final CharacterDetailContract.View mView;
    private final UseCaseHandler mUseCaseHandler;

    private final GetComics mGetComics;

    public CharacterDetailPresenter(@NonNull CharacterDetailContract.View view,
                                    @NonNull UseCaseHandler useCaseHandler,
                                    @NonNull GetComics getComics) {
        mView = view;
        mUseCaseHandler = useCaseHandler;
        mGetComics = getComics;
    }

    @Override
    public void onCreate() {
        mView.showCharacterExtra();
    }

    @Override
    public void loadComics() {
        if (mView.getCharacterExtra() == null) {
            return;
        }

        Character character = mView.getCharacterExtra();
        mView.showComicProgress();
        mUseCaseHandler.execute(mGetComics,
                new GetComics.RequestValues(character.getId()),
                new UseCase.UseCaseCallback<GetComics.ResponseValue>() {
                    @Override
                    public void onSuccess(GetComics.ResponseValue response) {
                        mView.hideComicProgress();
                        List<Comic> comics = response.getComics();

                        if (comics != null && !comics.isEmpty()) {
                            mView.showComics(response.getComics());
                        } else {
                            mView.noComicsAvailable();
                        }
                    }

                    @Override
                    public void onError() {
                        mView.hideComicProgress();
                        mView.noComicsAvailable();
                    }
                });
    }
}
