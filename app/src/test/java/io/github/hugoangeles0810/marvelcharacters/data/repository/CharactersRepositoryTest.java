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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.github.hugoangeles0810.marvelcharacters.data.source.CharactersDataSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by hugo on 30/03/17.
 */

public class CharactersRepositoryTest {

    private static List CHARACTERS = Arrays.asList();

    @Mock
    private CharactersDataSource mCharsRemoteDataSource;

    @Mock
    private CharactersDataSource mCharsLocalDataSource;

    @Mock
    private CharactersRepository.LoadCharactersCallback mLoadCharactersCallback;

    @Captor
    private ArgumentCaptor<CharactersDataSource.LoadCharactersCallback> mLoadCharsCallbackCaptor;

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
    public void shouldRequestsAllTasksFromLocalDataSourceWhenCallGetCharacters() {
        // When tasks are requested from the tasks repository
        mCharactersRepository.getCharacters(mLoadCharactersCallback);

        // Then tasks are loaded from the local data source
        verify(mCharsLocalDataSource).getCharacters(any(CharactersDataSource.LoadCharactersCallback.class));
    }

    @Test
    public void shouldRepositorySavesAfterFirstRemoteCall() {
        // When two calls are issued to the characters repository
        twoCharactersLoadCallsToRepository(mLoadCharactersCallback);

        // Then characters were only requested once from remote data source
        verify(mCharsRemoteDataSource).getCharacters(any(CharactersDataSource.LoadCharactersCallback.class));
    }

    private void twoCharactersLoadCallsToRepository(
        CharactersRepository.LoadCharactersCallback mLoadCharactersCallback) {
        // When characters are requested from repository
        mCharactersRepository.getCharacters(mLoadCharactersCallback); // First call to API

        // Capture the callback
        verify(mCharsLocalDataSource).getCharacters(mLoadCharsCallbackCaptor.capture());

        // Local data source doesn't have data yet
        mLoadCharsCallbackCaptor.getValue().onDataNotAvailable();

        // Verify the remote data source is queried
        verify(mCharsRemoteDataSource).getCharacters(mLoadCharsCallbackCaptor.capture());

        // Trigger callback so characters are cached
        mLoadCharsCallbackCaptor.getValue().onCharactersLoaded(CHARACTERS);

        mCharactersRepository.getCharacters(mLoadCharactersCallback); // Second call to API
    }

}
