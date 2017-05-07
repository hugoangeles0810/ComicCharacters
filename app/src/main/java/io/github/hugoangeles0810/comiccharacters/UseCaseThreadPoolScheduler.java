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

package io.github.hugoangeles0810.comiccharacters;

import android.os.Handler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by hugo on 04/04/17.
 */

public class UseCaseThreadPoolScheduler implements UseCaseScheduler {

  private final Handler mHandler = new Handler();

  public static final int POOL_SIZE = 2;

  public static final int MAX_POOL_SIZE = 4;

  public static final int TIMEOUT = 30;

  ThreadPoolExecutor mThreadPoolExecutor;

  public UseCaseThreadPoolScheduler() {
    mThreadPoolExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT,
        TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(POOL_SIZE));
  }

  @Override public void execute(Runnable runnable) {
    mThreadPoolExecutor.execute(runnable);
  }

  @Override public <V extends UseCase.ResponseValue> void notifyResponse(final V response,
      final UseCase.UseCaseCallback<V> useCaseCallback) {
      mHandler.post(new Runnable() {
        @Override public void run() {
          useCaseCallback.onSuccess(response);
        }
      });
  }

  @Override public <V extends UseCase.ResponseValue> void onError(
      final UseCase.UseCaseCallback<V> useCaseCallback) {
      mHandler.post(new Runnable() {
        @Override public void run() {
          useCaseCallback.onError();
        }
      });
  }
}
