package com.example.atul_.eatit;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.atul_.eatit.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_desciption;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);


        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);

        food_desciption = (TextView) findViewById(R.id.food_description);
        food_price = (TextView) findViewById(R.id.food_price);
        food_name = (TextView) findViewById(R.id.food_name);
        food_image = (ImageView) findViewById(R.id.img_food);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
       // collapsingToolbarLayout = setExpandedTitleAppearance(R.style.ExpandedAppbar);
        //collapsingToolbarLayout = setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if (getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty()) {
            getDetailFood(foodId);
        }
     }

        private void getDetailFood(String foodId){
           foods.child(foodId).addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   Food food=dataSnapshot.getValue(Food.class);


                   Picasso.with(getBaseContext()).load(food.getName());
                   food_price.setText(food.getPrice());
                   food_name.setText(food.getName());
                   food_desciption.setText(food.getDescripton());

               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
    }
}
