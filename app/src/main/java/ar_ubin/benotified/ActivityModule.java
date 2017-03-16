/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar_ubin.benotified;

import android.app.Activity;

import com.google.gson.Gson;

import ar_ubin.benotified.utils.ActivityScope;
import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule
{
  private final Activity activity;

  public ActivityModule( Activity activity) {
    this.activity = activity;
  }

  @Provides
  @ActivityScope
  Activity activity() {
    return this.activity;
  }

  @Provides
  @ActivityScope
  Gson provideGson() {
    return new Gson();
  }
}
