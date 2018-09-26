package com.shixing.dagger2learn;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shixing on 2018/8/18.
 */
@Module
public class MyMainModuler {

    @Provides
    public Gson provideGsona() {
        return new Gson();
    }

}
