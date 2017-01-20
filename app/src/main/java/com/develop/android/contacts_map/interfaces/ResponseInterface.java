package com.develop.android.contacts_map.interfaces;

import org.json.JSONArray;

/**
 * Created by sakshiagarwal on 10/11/16.
 */

public interface ResponseInterface {
    void onSuccess(JSONArray jsonArray);

    void onFailure(String message);

}
