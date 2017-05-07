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

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import io.github.hugoangeles0810.marvelcharacters.Injection;
import io.github.hugoangeles0810.marvelcharacters.R;
import io.github.hugoangeles0810.marvelcharacters.characters.domain.model.Character;

public class CharactersActivity extends AppCompatActivity
                        implements CharactersContract.View {

  private static final String LOG_TAG =  CharactersActivity.class.getSimpleName();
  private static final String CHARACTERS_KEY = "characters";

  private Toolbar mToolbar;
  private ProgressBar mProgressBar;

  private CharactersAdapter mAdapter;

  private CharactersPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_characters);

    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setTitle(getString(R.string.app_name));
    setSupportActionBar(mToolbar);

    mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

    mPresenter = new CharactersPresenter(Injection.provideUseCaseHandler(),
                                    this, Injection.provideGetCharacters(getApplicationContext()));

    setupRecycler();

    restoreOrLoadCharacters(savedInstanceState);
  }

  private void setupRecycler() {
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.characters_recycler_view);
    RecyclerView.LayoutManager layoutManager;

    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      layoutManager = new LinearLayoutManager(this);
    } else {
      layoutManager = new GridLayoutManager(this, 2);
    }

    recyclerView.setLayoutManager(layoutManager);
    mAdapter = new CharactersAdapter(this);
    recyclerView.setAdapter(mAdapter);
    recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
      @Override
      public void onLoadMore(int offset) {
        mPresenter.loadMoreCharacters(offset);
      }
    });
  }

  private void restoreOrLoadCharacters(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
        List<Character> characters = (List<Character>) savedInstanceState.getSerializable(CHARACTERS_KEY);
        mAdapter.setCharacters(characters);
    } else {
      mPresenter.loadCharacters();
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(CHARACTERS_KEY, (ArrayList<Character>) mAdapter.getCharacters());
  }

  @Override
  public void showLoading() {
    mProgressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    mProgressBar.setVisibility(View.INVISIBLE);
  }

  @Override
  public void showCharacters(List<Character> characters) {
    mAdapter.setCharacters(characters);
  }

  @Override
  public void addCharacters(List<Character> characters) {
    mAdapter.addCharacters(characters);
  }
}
