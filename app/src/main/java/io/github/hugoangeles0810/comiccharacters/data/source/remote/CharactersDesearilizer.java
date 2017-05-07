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
import io.github.hugoangeles0810.comiccharacters.characters.domain.model.Character;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class CharactersDesearilizer implements JsonDeserializer<List<Character>> {

  public static final String TAG = CharactersDesearilizer.class.getSimpleName();

  @Override
  public List<Character> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
      JsonParseException {
    List<Character> characters = new ArrayList<>();

    JsonArray results = json.getAsJsonObject().get("data").getAsJsonObject()
                                            .get("results").getAsJsonArray();

    for (JsonElement elemHero : results) {
      JsonObject objectCharacter = elemHero.getAsJsonObject();
      Character character = new Character(
          objectCharacter.get("id").getAsLong(),
          objectCharacter.get("name").getAsString(),
          objectCharacter.get("description").getAsString(),
          buildImageUrl(objectCharacter));
      characters.add(character);
    }
    return characters;
  }

  private String buildImageUrl(JsonObject objectCharacter) {
    String imageUrl = "";
    if (objectCharacter.has("thumbnail")) {
      JsonObject thumbnail = objectCharacter.get("thumbnail").getAsJsonObject();
      imageUrl = thumbnail.get("path").getAsString() + "." + thumbnail.get("extension").getAsString();
      if (imageUrl.contains("image_not_available")) {
        imageUrl = null;
      }
    }
    return imageUrl;
  }

}
