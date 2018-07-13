package com.shixing.myhttp.service;

import com.shixing.myhttp.service.convert.IConvert;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by shixing on 2018/7/11.
 */

public class WrapperResponse extends MyAbstractResponse<String> {
    private MyAbstractResponse mMyResponse;

    private List<IConvert> mConvert;

    public WrapperResponse(MyAbstractResponse myResponse, List<IConvert> converts) {
        this.mMyResponse = myResponse;
        this.mConvert = converts;
    }
    @Override
    public void success(MyRequest request, String data) {
        for (IConvert convert : mConvert) {
            if (convert.isCanParse(request.getContentType())) {

                try {
                    Object object = convert.parse(data, getType());
                    mMyResponse.success(request, object);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

        }


    }


    public Type getType() {
        Type type = mMyResponse.getClass().getGenericSuperclass();
        Type[] paramType = ((ParameterizedType) type).getActualTypeArguments();
        return paramType[0]; //得到泛型T的实际类型
    }

    @Override
    public void fail(int errorCode, String errorMsg) {

    }
}
