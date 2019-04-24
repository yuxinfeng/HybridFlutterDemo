package com.hybirdflutter.model;


import com.hybirdflutter.ApiException;

import io.reactivex.functions.Function;

/**
 * author：yuxinfeng on 2019-04-24 17:58
 * email：yuxinfeng@corp.netease.com
 */
public class SubjectResultFuc<T> implements Function<SubjectResult<T>, T> {

    @Override
    public T apply(SubjectResult<T> tSubjectResult) throws Exception {
        if (tSubjectResult.getCount() == 0) {
            throw new ApiException(100);
        }
        return tSubjectResult.getSubjects();
    }
}
