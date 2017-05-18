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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.github.hugoangeles0810.comiccharacters.R;
import io.github.hugoangeles0810.comiccharacters.characterdetail.domain.model.Comic;
import io.github.hugoangeles0810.comiccharacters.util.ImageLoader;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder> {

    private List<Comic> mData;

    public ComicsAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.comic_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comic comic = mData.get(position);
        holder.bind(comic);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Comic> comics) {
        mData.clear();
        mData.addAll(comics);
        notifyDataSetChanged();
    }

    public List<Comic> getData() {
        return mData;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewCover;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewCover = (ImageView) itemView.findViewById(R.id.comic_image_view);
        }

        public void bind(Comic comic) {
            ImageLoader.load(imageViewCover.getContext(),
                            comic.getImageUrl(),
                            R.drawable.hero_placeholder,
                            imageViewCover);
        }
    }
}
