package MohamedF2001.quizzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizzCategory extends AppCompatActivity {
    private Button Eco,Art,Bio,Film,Music,Histo,Litte,Aliment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_category);

        Eco = findViewById(R.id.economie);
        Art = findViewById(R.id.arts);
        Bio = findViewById(R.id.biologie);
        Film = findViewById(R.id.film);
        Music = findViewById(R.id.music);
        Histo = findViewById(R.id.histoire);
        Litte = findViewById(R.id.litterature);
        Aliment = findViewById(R.id.aliments);

        Eco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent eco = new Intent(getApplicationContext(), QuizzEconomie.class);
                startActivity(eco);
            }
        });
        Art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent art = new Intent(getApplicationContext(), QuizzArt.class);
                startActivity(art);
            }
        });
        Bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bio = new Intent(getApplicationContext(), QuizzBiologie.class);
                startActivity(bio);
            }
        });
        Film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent film = new Intent(getApplicationContext(), QuizzFilm.class);
                startActivity(film);
            }
        });
        Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent music = new Intent(getApplicationContext(), QuizzMusic.class);
                startActivity(music);
            }
        });
        Histo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent histoire = new Intent(getApplicationContext(), QuizzHistoire.class);
                startActivity(histoire);
            }
        });
        Litte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent litterature = new Intent(getApplicationContext(), QuizzLitterature.class);
                startActivity(litterature);
            }
        });
        Aliment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aliments = new Intent(getApplicationContext(), QuizzAliment.class);
                startActivity(aliments);
            }
        });
    }
}