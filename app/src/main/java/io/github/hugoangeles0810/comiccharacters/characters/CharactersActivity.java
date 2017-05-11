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

package io.github.hugoangeles0810.comiccharacters.characters;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import io.github.hugoangeles0810.comiccharacters.Injection;
import io.github.hugoangeles0810.comiccharacters.R;
import io.github.hugoangeles0810.comiccharacters.characters.domain.model.Character;
import io.github.hugoangeles0810.comiccharacters.navigation.Navigator;

public class CharactersActivity extends AppCompatActivity
                        implements CharactersContract.View {

  private static final String LOG_TAG =  CharactersActivity.class.getSimpleName();
  private static final String CHARACTERS_KEY = "characters";
  private static final String TERM_KEY = "term";

  private Toolbar mToolbar;
  private ProgressBar mProgressBar;
  private SearchView mSearchView;

  private String mTerm;
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

    mTerm = "";
    mPresenter = new CharactersPresenter(Injection.provideUseCaseHandler(),
                                    this, Injection.provideGetCharacters(getApplicationContext()));

    setupRecycler();

    restoreOrLoadCharacters(savedInstanceState);
  }


  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(CHARACTERS_KEY, (ArrayList<Character>) mAdapter.getCharacters());
    outState.putString(TERM_KEY, mTerm);
  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_characters, menu);
      setupSearchView(menu);
      return super.onCreateOptionsMenu(menu);
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
    mAdapter.setOnItemClickListener(new CharactersAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(Character character) {
        Navigator.navigateToCharactersDetail(getApplicationContext(), character);
      }
    });
    recyclerView.setAdapter(mAdapter);
    recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
      @Override
      public void onLoadMore(int offset) {
        mPresenter.loadMoreCharacters(mTerm, offset);
      }
    });
  }

  private void setupSearchView(Menu menu) {
    Log.d(LOG_TAG, "setupSearchView");
    MenuItem searchItem =  menu.findItem(R.id.action_search);
    mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
    mSearchView.setQueryHint(getString(R.string.hint_characters_search));
    mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        mTerm = query;
        mPresenter.loadCharacters(mTerm);
        mSearchView.clearFocus();
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });

    mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
      @Override
      public boolean onClose() {
        if (!mTerm.isEmpty()) {
          mTerm = "";
          mPresenter.loadCharacters(mTerm);
        }
        mSearchView.clearFocus();
        mSearchView.onActionViewCollapsed();
        return true;
      }
    });

    if (!TextUtils.isEmpty(mTerm)) {
      mSearchView.onActionViewExpanded();
      mSearchView.setQuery(mTerm, false);
      mSearchView.clearFocus();
    }

  }

  private void restoreOrLoadCharacters(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      List<Character> characters = (List<Character>) savedInstanceState.getSerializable(CHARACTERS_KEY);
      mAdapter.setCharacters(characters);
      mTerm = savedInstanceState.getString(TERM_KEY);
    } else {
      mPresenter.loadCharacters(mTerm);
    }
  }

}
