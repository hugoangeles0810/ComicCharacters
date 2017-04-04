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

package io.github.hugoangeles0810.marvelcharacters;

/**
 * Created by hugo on 04/04/17.
 */

public class UseCaseHandler {

  private static UseCaseHandler INSTANCE;

  private final UseCaseScheduler mUseCaseScheduler;

  public UseCaseHandler(UseCaseScheduler useCaseScheduler) {
    mUseCaseScheduler = useCaseScheduler;
  }

  public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> void execute(
      final UseCase<T, R> useCase, T values, UseCase.UseCaseCallback<R> callback) {
    useCase.setRequestValues(values);
    useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));

    mUseCaseScheduler.execute(new Runnable() {
      @Override
      public void run() {
        useCase.run();
      }
    });
  }

  private  <V extends UseCase.ResponseValue> void notifyResponse(final V response,
      final UseCase.UseCaseCallback<V> useCaseCallback) {
    mUseCaseScheduler.notifyResponse(response, useCaseCallback);
  }

  private <V extends UseCase.ResponseValue> void notifyError(
      final UseCase.UseCaseCallback<V> useCaseCallback) {
    mUseCaseScheduler.onError(useCaseCallback);
  }

  private static final class UiCallbackWrapper<V extends UseCase.ResponseValue> implements
      UseCase.UseCaseCallback<V> {
    private final UseCase.UseCaseCallback<V> mCallback;
    private final UseCaseHandler mUseCaseHandler;

    public UiCallbackWrapper(UseCase.UseCaseCallback<V> callback,
        UseCaseHandler useCaseHandler) {
      mCallback = callback;
      mUseCaseHandler = useCaseHandler;
    }

    @Override
    public void onSuccess(V response) {
      mUseCaseHandler.notifyResponse(response, mCallback);
    }

    @Override
    public void onError() {
      mUseCaseHandler.notifyError(mCallback);
    }
  }
}
