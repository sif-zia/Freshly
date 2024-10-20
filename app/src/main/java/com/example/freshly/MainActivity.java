package com.example.freshly;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.freshly.room.database.FreshlyDB;
import com.example.freshly.room.entity.Category;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new retreiveCategories().execute();
    }

    class insertCategories extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPostExecute(Boolean res) {
            super.onPostExecute(res);

            final String TAG = "Freshly.Insert.Res";

            if(res)
                Log.d(TAG, "Insertion Successfully");
            else
                Log.d(TAG, "Insertion Failed");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            FreshlyDB db = FreshlyDB.getInstance(MainActivity.this);

            long res = db.categoryDao().insert(new Category("Fruits"));
            if(res  == -1)
                return false;

            res = db.categoryDao().insert(new Category("Vegetables"));
            if(res == -1)
                return false;

            res = db.categoryDao().insert(new Category("Dry Fruits"));
            if(res == -1)
                return false;

            return true;
        }
    }

    class retreiveCategories extends AsyncTask<Void, Void, List<Category>> {
        @Override
        protected void onPostExecute(List<Category> categories) {
            super.onPostExecute(categories);

            final String TAG = "Freshly.Retrieve.Res";

            if(categories.size() == 0)
                Log.d(TAG, "Retrieve Failed");
            else{
                for(Category category: categories) {
                    Log.d(TAG, category.name);
                }
            }
        }

        @Override
        protected List<Category> doInBackground(Void... voids) {
            FreshlyDB db = FreshlyDB.getInstance(MainActivity.this);

            return db.categoryDao().getAllCategories();
        }
    }
}