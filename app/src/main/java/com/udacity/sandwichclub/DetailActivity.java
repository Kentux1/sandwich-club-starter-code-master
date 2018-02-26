package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();

        if (alsoKnownAsList.isEmpty()) {
            alsoKnownAs.setText(R.string.no_data);
        } else {
            for (int i = 0; i < alsoKnownAsList.size(); i++) {
                alsoKnownAs.append(alsoKnownAsList.get(i));

                if (i != alsoKnownAsList.size() - 1) {
                    alsoKnownAs.append(", ");
                } else {
                    alsoKnownAs.append(".");
                }
            }
        }

        TextView origin = findViewById(R.id.origin_tv);

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            origin.setText(R.string.no_data);
        } else {
            origin.setText(sandwich.getPlaceOfOrigin());
        }

        TextView description = findViewById(R.id.description_tv);

        if (sandwich.getDescription().isEmpty()) {
            description.setText(R.string.no_data);
        } else {
            description.setText(sandwich.getDescription());
        }

        TextView ingredients = findViewById(R.id.ingredients_tv);

        List<String> ingredientsList = sandwich.getIngredients();

        if (ingredientsList.isEmpty()) {
            ingredients.setText(R.string.no_data);
        } else {
            for (int i = 0; i < ingredientsList.size(); i++) {
                ingredients.append(ingredientsList.get(i));

                if (i != ingredientsList.size() - 1) {
                    ingredients.append(", ");
                } else {
                    ingredients.append(".");
                }
            }
        }
    }
}
