package com.udacity.sandwichclub.utils;

import android.text.TextUtils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            JSONObject jsonRootObject = new JSONObject(json);

            JSONObject name = jsonRootObject.optJSONObject(KEY_NAME);

            String mainName = name.optString(KEY_MAIN_NAME);

            JSONArray alsoKnownAs = name.optJSONArray(KEY_ALSO_KNOWN_AS);

            ArrayList<String> alsoKnowAsArrayList = new ArrayList<>();

            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnowAsArrayList.add(alsoKnownAs.optString(i));
            }

            String placeOfOrigin = jsonRootObject.optString(KEY_PLACE_OF_ORIGIN);

            String description = jsonRootObject.optString(KEY_DESCRIPTION);

            String image = jsonRootObject.optString(KEY_IMAGE);

            JSONArray ingredients = jsonRootObject.optJSONArray(KEY_INGREDIENTS);

            ArrayList<String> ingredientsArrayList = new ArrayList<>();

            for (int j = 0; j < ingredients.length(); j++) {
                ingredientsArrayList.add(ingredients.optString(j));
            }

            return new Sandwich(mainName, alsoKnowAsArrayList, placeOfOrigin, description, image, ingredientsArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
