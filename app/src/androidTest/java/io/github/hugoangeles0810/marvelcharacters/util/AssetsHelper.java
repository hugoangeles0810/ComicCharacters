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

package io.github.hugoangeles0810.marvelcharacters.util;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hugo on 01/04/17.
 */

public class AssetsHelper {

  public static String convertStreamToString(InputStream is) throws Exception {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();
    String line = null;
    while ((line = reader.readLine()) != null) {
      sb.append(line).append("\n");
    }
    reader.close();
    return sb.toString();
  }

  public static String getStringFromFile(Context context, String filePath) throws Exception {
    final InputStream stream = context.getResources().getAssets().open(filePath);
    String ret = convertStreamToString(stream);
    stream.close();
    return ret;
  }

}
