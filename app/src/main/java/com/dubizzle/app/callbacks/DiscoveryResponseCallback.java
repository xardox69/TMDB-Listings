package com.dubizzle.app.callbacks;

import com.dubizzle.app.domain.model.DiscoveryResponse;

public interface DiscoveryResponseCallback {

    void onSuccess(DiscoveryResponse response);

    void onFail(Throwable t);




}
