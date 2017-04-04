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

package io.github.hugoangeles0810.marvelcharacters.data.source.remote;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import io.github.hugoangeles0810.marvelcharacters.domain.model.Character;
import io.github.hugoangeles0810.marvelcharacters.data.source.CharactersRemoteDataSource;
import io.github.hugoangeles0810.marvelcharacters.util.AssetsHelper;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Integration test for the {@link CharactersRemoteDataSource}, which uses the {@link ApiClient}.
 */
@RunWith(AndroidJUnit4.class)
public class CharactersRemoteDataSourceTest {

  private MockWebServer mMockWebServer;

  private CharactersRemoteDataSource mCharactersRemoteDataSource;

  private Context mContext;

  @Before
  public void setUp() {
    mMockWebServer = new MockWebServer();

    try {
      mMockWebServer.start();
    } catch (IOException e) {
      e.printStackTrace();
    }

    mContext = InstrumentationRegistry.getTargetContext();

    HttpUrl baseUrl = mMockWebServer.url("");
    mCharactersRemoteDataSource = new CharactersRemoteDataSourceImpl(
                                          ApiClient.getInstance(baseUrl.toString()));
  }

  @After
  public void tearDown() {
    try {
      ApiClient.destroyInstance();
      mMockWebServer.shutdown();
      mMockWebServer = null;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldCallOnDataNotAvailableWhenEndpointRespondsErrorCode() throws Exception {
    // When make a request for load characters and server is down
    mMockWebServer.enqueue(new MockResponse()
        .setResponseCode(500));

    // Then onDataNotAvailable is called
    mCharactersRemoteDataSource.getCharacters(new CharactersRemoteDataSource.LoadCharactersCallback() {
      @Override public void onCharactersLoaded(List<Character> characters) {
        fail();
      }

      @Override public void onDataNotAvailable() {
        // Do nothing
      }
    });
  }

  @Test
  public void shouldLoadCharactersFromEndpoint() throws Exception {
    // When make a request for load characters and server responds json
    mMockWebServer.enqueue(new MockResponse()
        .setBody(AssetsHelper.getStringFromFile(mContext, "stubs/characters_ok.json")));

    // Then onCharactersLoaded is called with characters
    mCharactersRemoteDataSource.getCharacters(new CharactersRemoteDataSource.LoadCharactersCallback() {
      @Override public void onCharactersLoaded(List<Character> characters) {
        assertNotNull(characters);
        assertTrue(!characters.isEmpty());
      }

      @Override public void onDataNotAvailable() {
        fail();
      }
    });
  }

}
