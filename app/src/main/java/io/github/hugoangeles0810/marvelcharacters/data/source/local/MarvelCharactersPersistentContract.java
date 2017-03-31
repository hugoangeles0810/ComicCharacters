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

package io.github.hugoangeles0810.marvelcharacters.data.source.local;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the characters locally.
 */
public final class MarvelCharactersPersistentContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private MarvelCharactersPersistentContract() {}

    /* Inner class that defines the table contents */
    public static abstract class CharacterEntry implements BaseColumns {
        public static final String TABLE_NAME = "characters";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IMAGE_URL = "image_url";
    }

}
