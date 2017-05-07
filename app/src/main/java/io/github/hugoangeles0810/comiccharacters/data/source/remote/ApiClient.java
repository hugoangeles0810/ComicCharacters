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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import io.github.hugoangeles0810.comiccharacters.characters.domain.model.Character;
import java.util.List;
import java.util.concurrent.TimeUnit;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;


public class ApiClient {

  private static ServicesApi servicesApiInterface;

  public static ServicesApi getInstance() {

    if (servicesApiInterface == null) {
      buildInstance("http://gateway.marvel.com");
    }
    return servicesApiInterface;
  }

  public static ServicesApi getInstance(String baseUrl) {

    if (servicesApiInterface == null) {
      buildInstance(baseUrl);
    }
    return servicesApiInterface;
  }

  public static void destroyInstance() {
    servicesApiInterface = null;
  }

  private static void buildInstance(String baseUrl) {
    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(baseUrl)
        .setClient(new OkClient(getClient()))
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .setConverter(new GsonConverter(getGson()))
        .build();

    servicesApiInterface = restAdapter.create(ServicesApi.class);
  }

  private static OkHttpClient getClient() {
    OkHttpClient client = new OkHttpClient();
    client.setConnectTimeout(20, TimeUnit.SECONDS);
    client.setReadTimeout(20, TimeUnit.SECONDS);
    return client;
  }

  private static Gson getGson() {
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(new TypeToken<List<Character>>() { } .getType(), new CharactersDesearilizer())
        .create();
    return gson;
  }

  public interface ServicesApi {
    @Headers("Content-Type: application/json")
    @GET("/v1/public/characters?apikey=f8baf9a586b20573c4f2704530ac26cb&hash=2103b6bbb9425e559f25b8260997eafe&ts=1")
    List<Character> loadHeroes();

    @Headers("Content-Type: application/json")
    @GET("/v1/public/characters?apikey=f8baf9a586b20573c4f2704530ac26cb&hash=2103b6bbb9425e559f25b8260997eafe&ts=1")
    List<Character> loadHeroes(@Query("offset") int offset,
                               @Query("limit") int limit);

    @Headers("Content-Type: application/json")
    @GET("/v1/public/characters?apikey=f8baf9a586b20573c4f2704530ac26cb&hash=2103b6bbb9425e559f25b8260997eafe&ts=1")
    List<Character> loadHeroes(@Query("nameStartsWith") String term,
                               @Query("offset") int offset,
                               @Query("limit") int limit);
  }
}

