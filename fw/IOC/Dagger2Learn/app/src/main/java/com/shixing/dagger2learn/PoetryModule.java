package com.shixing.dagger2learn;

import dagger.Module;
import dagger.Provides;

@Module
public class PoetryModule {

    // 这个方法需要一个String参数，在Dagger2注入中，这些参数也是注入形式的，也就是
    // 要有其他对方提供参数poems的生成，不然会造成编译出错
    @Provides
    public Poetry providePoetry(String poems){
        return new Poetry(poems);
    }

    // 这里提供了一个生成String的方法，在这个Module里生成Poetry实例时，会查找到这里
    // 可以为上面提供String类型的参数
    @Provides
//    public String providePoemsaaaaa(){
    public String provideaaaaa(){
        return "只有意志坚强的人，才能到达彼岸";
    }
    //一种类型在同一个Component中只能由一个函数返回
//    @Provides
//    public String providea222aaaa(){
//        return "只有意志坚强的人，才能到达彼岸222222";
//    }

}
