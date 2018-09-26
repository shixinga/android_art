package com.shixing;

import javax.lang.model.type.TypeMirror;

/**
 * Created by shixing on 2018/8/17.
 */

public class FieldViewBinding {
    /**
     * 3 variable that represent the info below
     *  @BindView(R.id.textview)
     *  TextView textviewaaa
     */
    private String name;//  textviewaaa
    private TypeMirror type ;//--->TextView
    private int resId;//--->R.id.textview

    public FieldViewBinding(String name, TypeMirror type, int resId) {
        this.name = name;
        this.type = type;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return type;
    }

    public int getResId() {
        return resId;
    }
}
