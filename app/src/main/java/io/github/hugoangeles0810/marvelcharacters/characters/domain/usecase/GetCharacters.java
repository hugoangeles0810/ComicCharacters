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

package io.github.hugoangeles0810.marvelcharacters.characters.domain.usecase;

import android.support.annotation.NonNull;
import io.github.hugoangeles0810.marvelcharacters.UseCase;
import io.github.hugoangeles0810.marvelcharacters.data.repository.CharactersRepository;
import io.github.hugoangeles0810.marvelcharacters.characters.domain.model.Character;
import java.util.List;

/**
 * Created by hugo on 04/04/17.
 */

public class GetCharacters extends UseCase<GetCharacters.RequestValues, GetCharacters.ResponseValue> {

  private CharactersRepository mCharactersRepository;

  public GetCharacters(@NonNull  CharactersRepository charactersRepository) {
    mCharactersRepository = charactersRepository;
  }

  @Override
  protected void executeUseCase(GetCharacters.RequestValues requestValues) {
    mCharactersRepository.getCharacters(requestValues.getOffset(), requestValues.getLimit(),
            new CharactersRepository.LoadCharactersCallback() {
      @Override public void onCharactersLoaded(List<Character> characters) {
        ResponseValue response = new ResponseValue(characters);
        getUseCaseCallback().onSuccess(response);
      }

      @Override public void onDataNotAvailable() {
        getUseCaseCallback().onError();
      }
    });
  }

  public static final class RequestValues implements UseCase.RequestValues {
    private final int mOffset;
    private final int mLimit;

    public RequestValues(int offset, int limit) {
      mOffset = offset;
      mLimit = limit;
    }

    public int getOffset() {
      return mOffset;
    }

    public int getLimit() {
      return mLimit;
    }
  }

  public static final class ResponseValue implements UseCase.ResponseValue {

    private final List<Character> mCharacters;

    public ResponseValue(@NonNull List<Character> characters) {
      mCharacters = characters;
    }

    public List<Character> getCharacters() {
      return mCharacters;
    }
  }
}
