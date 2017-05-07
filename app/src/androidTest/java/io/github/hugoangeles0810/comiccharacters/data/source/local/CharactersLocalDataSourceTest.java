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

package io.github.hugoangeles0810.comiccharacters.data.source.local;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.github.hugoangeles0810.comiccharacters.characters.domain.model.Character;
import io.github.hugoangeles0810.comiccharacters.data.source.CharactersLocalDataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Integration test for the {@link CharactersLocalDataSource}, which uses the {@link MarvelCharactersDbHelper}.
 */
@RunWith(AndroidJUnit4.class)
public class CharactersLocalDataSourceTest {

    private static final String NAME = "name";

    private static final String NAME1 = "name1";

    private static final String NAME2 = "name2";

    private CharactersLocalDataSourceImpl mLocalDataSource;

    @Before
    public void setUp() {
        mLocalDataSource = CharactersLocalDataSourceImpl.getInstance(
                InstrumentationRegistry.getTargetContext());
    }

    @After
    public void cleanUp() {
        mLocalDataSource.deleteAllCharacters();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void shouldRetrieveCharacterWhenSaveCharacter() {
        // Given a new character
        final Character character = new Character(NAME);

        // When saved into persistent repository
        mLocalDataSource.saveCharacter(character);

        // Then the character can be retrieved from the persistent repository
        mLocalDataSource.getCharacters(new CharactersLocalDataSource.LoadCharactersCallback() {
            @Override
            public void onCharactersLoaded(List<Character> characters) {
                assertTrue(characters.contains(character));
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });
    }

    @Test
    public void shouldRetrieveCharactersWithOffsetAndLimit() {
        final int insertionsQuantity = 10;
        int offset = 5;
        int limit = 5;
        final int expectedSize = 5;
        String term = "";
        insertRandomCharacters(insertionsQuantity);

        mLocalDataSource.getCharacters(term, offset, limit, new CharactersLocalDataSource.LoadCharactersCallback() {
            @Override
            public void onCharactersLoaded(List<Character> characters) {
                assertEquals(characters.size(), expectedSize);
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });
    }

    private void insertRandomCharacters(int quantity) {
        for (long i = 1; i <= quantity; i++) {
            Character character = new Character(i, "Name " + i, "Description " + i, "image " + i);
            mLocalDataSource.saveCharacter(character);
        }
    }

}
