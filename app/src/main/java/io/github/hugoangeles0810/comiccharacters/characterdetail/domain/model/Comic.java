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

package io.github.hugoangeles0810.comiccharacters.characterdetail.domain.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Inmutable model class for a Comic
 */
public class Comic implements Serializable {

    @NonNull
    private final Long mId;

    @NonNull
    private final String mName;

    @NonNull
    private String mImageUrl;

    public Comic(@NonNull Long id, @NonNull String name, @NonNull String imageUrl) {
        this.mId = id;
        this.mName = name;
        this.mImageUrl = imageUrl;
    }

    public Comic(@NonNull String name, @NonNull String imageUrl) {
        this(System.currentTimeMillis(), name, imageUrl);
    }

    @NonNull
    public Long getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getImageUrl() {
        return mImageUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Comic) {
            return mId.equals(((Comic) obj).getId());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
