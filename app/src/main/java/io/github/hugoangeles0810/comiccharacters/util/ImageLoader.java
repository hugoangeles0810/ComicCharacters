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

package io.github.hugoangeles0810.comiccharacters.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class ImageLoader {

  public static void load(@NonNull  Context context,
                          @NonNull String url,
                          @NonNull ImageView imageView) {
    Glide.with(context)
          .load(url)
          .into(imageView);
  }

  public static void load(@NonNull Context context, String url,
                          @DrawableRes  int placeholder,
                          @NonNull ImageView imageView) {
    Glide.with(context)
          .load(url)
          .placeholder(placeholder)
          .into(imageView);
  }

}
