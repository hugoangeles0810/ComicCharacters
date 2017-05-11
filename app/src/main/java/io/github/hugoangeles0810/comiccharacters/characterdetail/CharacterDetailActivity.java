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

package io.github.hugoangeles0810.comiccharacters.characterdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import io.github.hugoangeles0810.comiccharacters.Injection;
import io.github.hugoangeles0810.comiccharacters.R;
import io.github.hugoangeles0810.comiccharacters.characters.domain.model.Character;
import io.github.hugoangeles0810.comiccharacters.util.ImageLoader;

public class CharacterDetailActivity extends AppCompatActivity
                implements CharacterDetailContract.View {

    public static final String CHARACTER_KEY = "character";

    private Toolbar mToolbar;
    private ImageView mCharacterImageView;

    private Character mCharacter;

    private CharacterDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        initUI();

        mCharacter = (Character) getIntent().getSerializableExtra(CHARACTER_KEY);

        mPresenter = new CharacterDetailPresenter(this, Injection.provideUseCaseHandler());
        mPresenter.onCreate();
    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mCharacterImageView = (ImageView) findViewById(R.id.character_image_view);
    }

    @Override
    public void showCharacterExtra() {
        getSupportActionBar().setTitle(mCharacter.getName());
        ImageLoader.load(this,
                        mCharacter.getImageUrl(),
                        mCharacterImageView);
    }

    @Override
    public Character getCharacterExtra() {
        return mCharacter;
    }
}
