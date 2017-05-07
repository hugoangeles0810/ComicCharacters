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

package io.github.hugoangeles0810.comiccharacters.characters;

import io.github.hugoangeles0810.comiccharacters.BasePresenter;
import io.github.hugoangeles0810.comiccharacters.BaseView;
import io.github.hugoangeles0810.comiccharacters.characters.domain.model.Character;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public class CharactersContract {

  interface View extends BaseView {

    void showLoading();
    void hideLoading();

    void showCharacters(List<Character> characters);
    void addCharacters(List<Character> characters);

  }

  interface Presenter extends BasePresenter {

    void loadCharacters(String term);
    void loadMoreCharacters(String term, int offset);

  }

}
