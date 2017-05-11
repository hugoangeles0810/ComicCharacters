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

package io.github.hugoangeles0810.comiccharacters.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import io.github.hugoangeles0810.comiccharacters.characterdetail.CharacterDetailActivity;
import io.github.hugoangeles0810.comiccharacters.characters.domain.model.Character;

public class Navigator {

    public static void navigateToCharactersDetail(Context context, Character character) {
        // TODO: Improve this
        Intent intent = new Intent(context, CharacterDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CharacterDetailActivity.CHARACTER_KEY, character);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
