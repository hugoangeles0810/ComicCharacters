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

package io.github.hugoangeles0810.comiccharacters;

import android.content.Context;
import android.support.annotation.NonNull;

import io.github.hugoangeles0810.comiccharacters.characterdetail.domain.usecase.GetComics;
import io.github.hugoangeles0810.comiccharacters.characters.domain.usecase.GetCharacters;
import io.github.hugoangeles0810.comiccharacters.data.repository.CharactersRepository;
import io.github.hugoangeles0810.comiccharacters.data.repository.CharactersRepositoryImpl;
import io.github.hugoangeles0810.comiccharacters.data.repository.ComicsRepository;
import io.github.hugoangeles0810.comiccharacters.data.repository.ComicsRepositoryImpl;
import io.github.hugoangeles0810.comiccharacters.data.source.local.CharactersLocalDataSourceImpl;
import io.github.hugoangeles0810.comiccharacters.data.source.remote.ApiClient;
import io.github.hugoangeles0810.comiccharacters.data.source.remote.CharactersRemoteDataSourceImpl;
import io.github.hugoangeles0810.comiccharacters.data.source.remote.ComicsRemoteDataSourceImpl;

public class Injection {

  public static UseCaseHandler provideUseCaseHandler() {
    return UseCaseHandler.getInstance();
  }

  public static CharactersRepository provideCharactersRepository(@NonNull Context context) {
    return CharactersRepositoryImpl.getInstance(
        CharactersRemoteDataSourceImpl.getInstance(ApiClient.getInstance()),
        CharactersLocalDataSourceImpl.getInstance(context));
  }

  public static ComicsRepository provideComicsRepository() {
    return ComicsRepositoryImpl.getInstance(
            ComicsRemoteDataSourceImpl.getInstance(ApiClient.getInstance()));
  }

  public static GetCharacters provideGetCharacters(@NonNull Context context) {
    return new GetCharacters(provideCharactersRepository(context));
  }

  public static GetComics provideGetComics() {
    return new GetComics(provideComicsRepository());
  }

}
