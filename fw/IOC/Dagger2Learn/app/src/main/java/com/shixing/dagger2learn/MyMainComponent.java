package com.shixing.dagger2learn;

import dagger.Component;

/**
 * Created by shixing on 2018/8/18.
 */
//表示该interface是一个连接器
//一个Component的所有Modules中，一种类型（如String）只能由一个函数返回！！！。
@Component(modules = {MyMainModuler.class, PoetryModule.class})
public interface MyMainComponent {

    /**
     *
     * @param activity 有 有@Inject注解的成员变量 的对象
     *
     */
    void injectaaa(MainActivity activity);
}
