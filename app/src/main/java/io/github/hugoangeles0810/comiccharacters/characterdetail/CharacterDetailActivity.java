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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.github.hugoangeles0810.comiccharacters.Injection;
import io.github.hugoangeles0810.comiccharacters.R;
import io.github.hugoangeles0810.comiccharacters.characterdetail.domain.model.Comic;
import io.github.hugoangeles0810.comiccharacters.characters.domain.model.Character;
import io.github.hugoangeles0810.comiccharacters.util.ImageLoader;

public class CharacterDetailActivity extends AppCompatActivity
                implements CharacterDetailContract.View {

    private static final String LOG_TAG = CharacterDetailActivity.class.getSimpleName();

    public static final String CHARACTER_KEY = "character";
    public static final String COMICS_KEY = "comics";

    private ImageView mCharacterImageView;
    private TextView mDescriptionTextView;
    private RecyclerView mComicsRecycler;
    private ProgressBar mComicsProgress;
    private TextView mNoComicsTextView;

    private ComicsAdapter mComicsAdapter;

    private Character mCharacter;

    private CharacterDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        initUI();

        mPresenter = new CharacterDetailPresenter(
                this,
                Injection.provideUseCaseHandler(),
                Injection.provideGetComics());

        restoreOrLoadCharacter(savedInstanceState);
    }

    private void initUI() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCharacterImageView = (ImageView) findViewById(R.id.character_image_view);
        mDescriptionTextView = (TextView) findViewById(R.id.character_textview_description);
        mComicsRecycler = (RecyclerView) findViewById(R.id.comics_recycler);
        mComicsProgress = (ProgressBar) findViewById(R.id.comics_progress_bar);
        mNoComicsTextView = (TextView) findViewById(R.id.no_comics_text_view);

        mComicsRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mComicsAdapter = new ComicsAdapter();
        mComicsRecycler.setAdapter(mComicsAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CHARACTER_KEY, mCharacter);
        outState.putSerializable(COMICS_KEY, (ArrayList<Comic>) mComicsAdapter.getData());
    }

    @Override
    public void showCharacterExtra() {
        getSupportActionBar().setTitle(mCharacter.getName());
        if (TextUtils.isEmpty(mCharacter.getDescription())) {
            mDescriptionTextView.setText(R.string.no_description_message);
        } else {
            mDescriptionTextView.setText(mCharacter.getDescription());
        }
        ImageLoader.load(this,
                        mCharacter.getImageUrl(),
                        R.drawable.hero_placeholder,
                        mCharacterImageView);
    }

    @Override
    public Character getCharacterExtra() {
        return mCharacter;
    }

    @Override
    public void showComics(List<Comic> comics) {
        mComicsAdapter.setData(comics);
        mNoComicsTextView.setVisibility(View.GONE);
        mComicsRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void noComicsAvailable() {
        Log.d(LOG_TAG, "noComicsAvailable");
        mNoComicsTextView.setVisibility(View.VISIBLE);
        mComicsRecycler.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showComicProgress() {
        mComicsProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideComicProgress() {
        mComicsProgress.setVisibility(View.GONE);
    }

    private void restoreOrLoadCharacter(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mCharacter = (Character) getIntent().getSerializableExtra(CHARACTER_KEY);
            mPresenter.loadComics();
        } else {
            mCharacter = (Character) savedInstanceState.getSerializable(CHARACTER_KEY);
            List<Comic> comics = (List<Comic>) savedInstanceState.getSerializable(COMICS_KEY);
            showComics(comics);
        }
        mPresenter.onCreate();
    }

}
