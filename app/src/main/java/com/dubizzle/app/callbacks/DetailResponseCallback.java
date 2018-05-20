package com.dubizzle.app.callbacks;

import com.dubizzle.app.domain.model.DetailedAsset;

public interface DetailResponseCallback {

    void onSuccess(DetailedAsset asset);

    void onFail(Throwable t);
}
