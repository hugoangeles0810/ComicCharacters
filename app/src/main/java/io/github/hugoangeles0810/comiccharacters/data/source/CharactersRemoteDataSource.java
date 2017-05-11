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

package io.github.hugoangeles0810.comiccharacters.data.source;

import android.support.annotation.NonNull;
import io.github.hugoangeles0810.comiccharacters.characters.domain.model.Character;
import java.util.List;

/**
 * Main entry point for accesing remote characters data
 */
public interface CharactersRemoteDataSource {

  interface LoadCharactersCallback {

    void onCharactersLoaded(List<Character> characters);

    void onDataNotAvailable();
  }

  void getCharacters(@NonNull LoadCharactersCallback callback);

  void getCharacters(String term, int offset, int limit, @NonNull LoadCharactersCallback callback);

}