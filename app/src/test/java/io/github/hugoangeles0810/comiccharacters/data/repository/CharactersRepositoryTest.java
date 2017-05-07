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

import io.github.hugoangeles0810.comiccharacters.data.source.CharactersLocalDataSource;
import io.github.hugoangeles0810.comiccharacters.data.source.CharactersRemoteDataSource;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by hugo on 30/03/17.
 */

public class CharactersRepositoryTest {

    private static List CHARACTERS = Arrays.asList();

    @Mock
    private CharactersRemoteDataSource mCharsRemoteDataSource;

    @Mock
    private CharactersLocalDataSource mCharsLocalDataSource;

    @Mock
    private CharactersRepository.LoadCharactersCallback mLoadCharactersCallback;

    @Captor
    private ArgumentCaptor<CharactersLocalDataSource.LoadCharactersCallback> mLoadCharsLocalCallbackCaptor;

    @Captor
    private ArgumentCaptor<CharactersRemoteDataSource.LoadCharactersCallback> mLoadCharsRemoteCallbackCaptor;

    private CharactersRepository mCharactersRepository;

    @Before
    public void setupCharactersRepository() {
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mCharactersRepository = CharactersRepositoryImpl.getInstance(mCharsRemoteDataSource,
                                                                    mCharsLocalDataSource);
    }

    @After
    public void destroyRepositoryInstance() {
        CharactersRepositoryImpl.destroyInstance();
    }

    @Test
    public void shouldRequestsAllTasksFromRemoteDataSourceWhenCallGetCharacters() {
        // When characters are requested from the tasks repository
        mCharactersRepository.getCharacters(mLoadCharactersCallback);

        // Then tasks are loaded from the local data source
        verify(mCharsRemoteDataSource).getCharacters(any(CharactersRemoteDataSource.LoadCharactersCallback.class));
    }

    @Test
    public void shouldRepositorySavesAfterRemoteCall() {
        // When characters are requested from remote and response characters
        mCharactersRepository.getCharacters(mLoadCharactersCallback);
        verify(mCharsRemoteDataSource).getCharacters(mLoadCharsRemoteCallbackCaptor.capture());
        mLoadCharsRemoteCallbackCaptor.getValue().onCharactersLoaded(CHARACTERS);

        // Then characters are saved in local data source
        verify(mCharsLocalDataSource).saveOrUpdate(eq(CHARACTERS));
    }

}
