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

package io.github.hugoangeles0810.comiccharacters.data.source.remote;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.github.hugoangeles0810.comiccharacters.characterdetail.domain.model.Comic;

public class ComicsDeserializer implements JsonDeserializer<List<Comic>> {

    @Override
    public List<Comic> deserialize(JsonElement json,
                                   Type typeOfT,
                                   JsonDeserializationContext context) throws JsonParseException {
        List<Comic> comics = new ArrayList<>();


        JsonArray results = json.getAsJsonObject()
                                .get("data").getAsJsonObject()
                                .get("results").getAsJsonArray();

        for (JsonElement elemComic : results) {
            JsonObject objectComic = elemComic.getAsJsonObject();
            Long id = objectComic.get("id").getAsLong();
            String description = "";

            if (!objectComic.get("description").isJsonNull()) {
                description = objectComic.get("description").getAsString();
            }

            Comic comic = new Comic(
                    objectComic.get("id").getAsLong(),
                    description,
                    buildImageUrl(objectComic)
            );

            comics.add(comic);
        }

        return comics;
    }

    private String buildImageUrl(JsonObject objectComic) {
        String imageUrl = "";
        if (objectComic.has("thumbnail")) {
            JsonObject thumbnail = objectComic.get("thumbnail").getAsJsonObject();
            imageUrl = thumbnail.get("path").getAsString() + "." + thumbnail.get("extension").getAsString();
            if (imageUrl.contains("image_not_available")) {
                imageUrl = null;
            }
        }
        return imageUrl;
    }

}
