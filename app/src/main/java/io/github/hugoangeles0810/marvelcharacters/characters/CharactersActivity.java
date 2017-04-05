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

package io.github.hugoangeles0810.marvelcharacters.characters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import io.github.hugoangeles0810.marvelcharacters.Injection;
import io.github.hugoangeles0810.marvelcharacters.R;
import io.github.hugoangeles0810.marvelcharacters.characters.domain.model.Character;
import java.util.List;

public class CharactersActivity extends AppCompatActivity implements CharactersContract.View {

  private Toolbar mToolbar;

  private CharactersAdapter mAdapter;

  private CharactersPresenter mPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_characters);

    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setTitle(getString(R.string.app_name));
    setSupportActionBar(mToolbar);

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.characters_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    mAdapter = new CharactersAdapter(this);
    recyclerView.setAdapter(mAdapter);

    mPresenter = new CharactersPresenter(Injection.provideUseCaseHandler(),
                                    this, Injection.provideGetCharacters(getApplicationContext()));

  }

  @Override protected void onStart() {
    super.onStart();
    mPresenter.start();
  }

  @Override public void setLoadingIndicator(boolean active) {

  }

  @Override public void showCharacters(List<Character> characters) {
    mAdapter.setCharacters(characters);
  }
}
