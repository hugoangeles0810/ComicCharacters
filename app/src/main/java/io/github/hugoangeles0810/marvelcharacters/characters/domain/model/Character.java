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

package io.github.hugoangeles0810.marvelcharacters.characters.domain.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Inmutable model class for a Character
 */
public class Character {

    @NonNull
    private final Long mId;

    @NonNull
    private final String mName;

    @Nullable
    private final String mDescription;

    @NonNull
    private String mImageUrl;

    public Character(@NonNull Long id, @NonNull String name,
                     @Nullable String description, @NonNull String imageUrl) {
        mId = id;
        mName = name;
        mDescription = description;
        mImageUrl = imageUrl;
    }

    public Character(@NonNull Long id, @NonNull String name, @NonNull String imageUrl) {
        this(id, name, "", imageUrl);
    }

    public Character(@NonNull Long id, @NonNull String name) {
        this(id, name, "");
    }

    public Character(@NonNull String name) {
        this(System.currentTimeMillis(), name);
    }

    @NonNull
    public Long getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @NonNull
    public String getImageUrl() {
        return mImageUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Character) {
            return mId.equals(((Character) obj).getId());
        }

        return super.equals(obj);
    }

    @Override public int hashCode() {
        return super.hashCode();
    }
}
