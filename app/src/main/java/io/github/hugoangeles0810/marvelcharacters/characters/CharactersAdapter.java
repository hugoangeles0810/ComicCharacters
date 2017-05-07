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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import io.github.hugoangeles0810.marvelcharacters.R;
import io.github.hugoangeles0810.marvelcharacters.characters.domain.model.Character;
import io.github.hugoangeles0810.marvelcharacters.util.ImageLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugo on 04/04/17.
 */

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {

  private List<Character> mData;
  private LayoutInflater mInflater;

  public CharactersAdapter(@NonNull Context context) {
    mInflater = LayoutInflater.from(context);
    mData = new ArrayList<>();
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = mInflater.inflate(R.layout.character_item, parent, false);
    return new ViewHolder(itemView);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Character character = mData.get(position);
    holder.textViewName.setText(character.getName());
    if (character.getDescription() != null && !character.getDescription().trim().isEmpty()) {
      holder.textViewDescription.setVisibility(View.VISIBLE);
      holder.textViewDescription.setText(character.getDescription());
    } else {
      holder.textViewDescription.setVisibility(View.GONE);
    }

    ImageLoader.load(
          holder.imageViewPhoto.getContext(),
          character.getImageUrl(),
          R.drawable.hero_placeholder,
          holder.imageViewPhoto);
  }

  @Override public int getItemCount() {
    return mData.size();
  }

  public void setCharacters(@NonNull List<Character> characters) {
    mData.clear();
    for (Character character : characters) {
      mData.add(character);
    }
    notifyDataSetChanged();
  }

  public void addCharacters(@NonNull List<Character> characters) {
      mData.addAll(characters);
      notifyDataSetChanged();
  }

  public List<Character> getCharacters() {
    return mData;
  }

  static final class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageViewPhoto;
    TextView  textViewName;
    TextView  textViewDescription;

    public ViewHolder(View itemView) {
      super(itemView);
      imageViewPhoto = (ImageView) itemView.findViewById(R.id.character_imageview_image);
      textViewName = (TextView) itemView.findViewById(R.id.character_textview_name);
      textViewDescription = (TextView) itemView.findViewById(R.id.character_textview_description);
    }
  }

}
