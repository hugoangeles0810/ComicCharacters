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

package io.github.hugoangeles0810.marvelcharacters.data.source.remote;

import android.support.annotation.NonNull;
import io.github.hugoangeles0810.marvelcharacters.domain.model.Character;
import io.github.hugoangeles0810.marvelcharacters.data.source.CharactersRemoteDataSource;
import java.util.List;
import retrofit.RetrofitError;

/**
 * Created by hugo on 01/04/17.
 */

public class CharactersRemoteDataSourceImpl implements CharactersRemoteDataSource {

  private ApiClient.ServicesApi mServicesApi;

  public CharactersRemoteDataSourceImpl(ApiClient.ServicesApi servicesApi) {
    mServicesApi = servicesApi;
  }

  @Override
  public void getCharacters(@NonNull final LoadCharactersCallback callback) {
    try {
      List<Character> characters = mServicesApi.loadHeroes();
      if (characters != null || !characters.isEmpty()) {
        callback.onCharactersLoaded(characters);
      } else {
        callback.onDataNotAvailable();
      }
      return;
    } catch (RetrofitError e) {
      callback.onDataNotAvailable();
      return;
    }
  }
}
