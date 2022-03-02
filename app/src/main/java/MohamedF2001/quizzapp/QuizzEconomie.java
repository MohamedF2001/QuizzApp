package MohamedF2001.quizzapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import MohamedF2001.quizzapp.model.Question;
import MohamedF2001.quizzapp.model.QuestionBank;

public class QuizzEconomie extends AppCompatActivity implements View.OnClickListener {
    public static final String RESULT_SCORE = "RESULT_SCORE";

    private static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    private static final String BUNDLE_STATE_QUESTION_COUNT = "BUNDLE_STATE_QUESTION_COUNT";
    private static final String BUNDLE_STATE_QUESTION_BANK = "BUNDLE_STATE_QUESTION_BANK";

    private static final int INITIAL_QUESTION_COUNT = 5;

    private TextView mTextViewQuestion;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;

    private int mScore;
    private int mRemainingQuestionCount;
    private QuestionBank mQuestionBank;

    private boolean mEnableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_economie);

        mEnableTouchEvents = true;

        mTextViewQuestion = findViewById(R.id.game_activity_textview_question);
        mAnswerButton1 = findViewById(R.id.game_activity_button_1);
        mAnswerButton2 = findViewById(R.id.game_activity_button_2);
        mAnswerButton3 = findViewById(R.id.game_activity_button_3);
        mAnswerButton4 = findViewById(R.id.game_activity_button_4);

        // Use the same listener for the four buttons.
        // The view id value will be used to distinguish the button triggered
        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION_COUNT);
            mQuestionBank = (QuestionBank) savedInstanceState.getSerializable(BUNDLE_STATE_QUESTION_BANK);
        } else {
            mScore = 0;
            mRemainingQuestionCount = INITIAL_QUESTION_COUNT;
            mQuestionBank = generateQuestionBank();
        }

        displayQuestion(mQuestionBank.getCurrentQuestion());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION_COUNT, mRemainingQuestionCount);
        outState.putSerializable(BUNDLE_STATE_QUESTION_BANK, mQuestionBank);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        int index;

        if (v == mAnswerButton1) {
            index = 0;
        } else if (v == mAnswerButton2) {
            index = 1;
        } else if (v == mAnswerButton3) {
            index = 2;
        } else if (v == mAnswerButton4) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

        if (index == mQuestionBank.getCurrentQuestion().getAnswerIndex()) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;

                mRemainingQuestionCount--;

                if (mRemainingQuestionCount <= 0) {
                    endGame();
                } else {
                    displayQuestion(mQuestionBank.getNextQuestion());
                }
            }
        }, 2_000);
    }

    private void displayQuestion(final Question question) {
        // Set the text for the question text view and the four buttons
        mTextViewQuestion.setText(question.getQuestion());
        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));
    }

    private void endGame() {
        // No question left, end the game
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Bien joué!")
                .setMessage("Votre score est " + mScore)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(RESULT_SCORE, mScore);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private QuestionBank generateQuestionBank() {
        Question question1 = new Question(
                "D'où vient une voiture avec les lettres CH ?",
                Arrays.asList(
                        "la Suisse",
                        "la Chine",
                        "la Turquie",
                        "la France"
                ),
                0
        );

        Question question2 = new Question(
                "Quelle est la monnaie nationale de l'Albanie ?\t",
                Arrays.asList(
                        "YEN",
                        "$",
                        "FCFA",
                        "Lek albanais"
                ),
                3
        );

        Question question3 = new Question(
                "Où a été construit le premier métro ?",
                Arrays.asList(
                        "A Pékin",
                        "A Bruxelles",
                        "À Londres",
                        "A Paris"
                ),
                2
        );

        Question question4 = new Question(
                "Quelle est la monnaie officielle au Népal ?",
                Arrays.asList(
                        "Livre",
                        "Roupie",
                        "Dollars",
                        "Yen"
                ),
                1
        );

        Question question5 = new Question(
                "Quelle marque de voiture japonaise a existé de 1932 à 1983 ?",
                Arrays.asList(
                        "Nexus",
                        "Alpha Romoéo",
                        "Volsfvagen",
                        "Datsun"
                ),
                3
        );
        Question question6 = new Question(
                "Quelle est la plus grande compagnie pétrolière aux États-Unis ?",
                Arrays.asList(
                        "Tesla",
                        "Dangote",
                        "IBM",
                        "Exxon Mobil"
                ),
                3
        );
        Question question7 = new Question(
                "Quelle entreprise a été créée en 1962 par Sam Walton et est l'un des plus grands employeurs au monde ?",
                Arrays.asList(
                        "Watch",
                        "Crypto",
                        "Walstreet",
                        "Walmart"
                ),
                3
        );
        Question question8 = new Question(
                "Quelle famille italienne est à la tête de FIAT ?\t\n",
                Arrays.asList(
                        "Smith",
                        "Morgan",
                        "Agnelli",
                        "Angelotelli"
                ),
                2
        );
        Question question9 = new Question(
                "Quelle était la monnaie de la Grèce avant l'introduction de l'EURO ?",
                Arrays.asList(
                        "Yen",
                        "Drachme",
                        "Dachme",
                        "Damme"
                ),
                1
        );
        Question question10 = new Question(
                "Dans quel pays est basée Varig Airlines ?",
                Arrays.asList(
                        "Brésil",
                        "Canada",
                        "Belgique",
                        "Russie"
                ),
                0
        );
        Question question11 = new Question(
                "Quelle est la monnaie nationale du Laos ?",
                Arrays.asList(
                        "Kip du Laos",
                        "Kip du Las",
                        "Kip du Lalasca",
                        "Kip"
                ),
                0
        );
        Question question12 = new Question(
                "Quel est le composant principal du verre ?",
                Arrays.asList(
                        "Bamboun",
                        "Cristaux",
                        "Verre",
                        "Sable"
                ),
                3
        );
        Question question13 = new Question(
                "Quel pays est le plus grand producteur d'huile d'olive ?",
                Arrays.asList(
                        "France",
                        "Brésil",
                        "Espagne",
                        "Russie"
                ),
                2
        );
        Question question14 = new Question(
                "Quelle est la compagnie aérienne nationale de l'Australie ?",
                Arrays.asList(
                        "Air Australie",
                        "Qantas",
                        "WeGo",
                        "Turkie Airlines"
                ),
                1
        );
        Question question15 = new Question(
                "Quel animal préhistorique indique également un grand pétrolier ?",
                Arrays.asList(
                        "Mammouth",
                        "Koala",
                        "Lion",
                        "Dynosaure"
                ),
                0
        );
        Question question16 = new Question(
                "Quelle était la monnaie de l'Italie ?",
                Arrays.asList(
                        "Dollars",
                        "Euros",
                        "Lira",
                        "Livre"
                ),
                2
        );
        Question question17 = new Question(
                "Quelle devise est à la maison à la Bourse de Johannesburg ?",
                Arrays.asList(
                        "Rand ouest-africain",
                        "Rand ",
                        "Rand Européen",
                        "Rand sud-africain"
                ),
                3
        );
        Question question18 = new Question(
                "Quel pays est à l'origine des marques automobiles Daewoo et Kia ?",
                Arrays.asList(
                        "Japon",
                        "Corée du Nord",
                        "Corée du Sud",
                        "Chine"
                ),
                2
        );
        Question question19 = new Question(
                " De combien de chiffres un numéro IBAN est-il composé ?",
                Arrays.asList(
                        "Douze chiffres",
                        "Quatorze chiffres",
                        "Quinze chiffres",
                        "Dix huit chiffres"
                ),
                1
        );
        Question question20 = new Question(
                "Quel est le plus grand immeuble de bureaux au monde ?",
                Arrays.asList(
                        "Pentagone",
                        "Apple",
                        "Maison Blanche",
                        "Hexagone"
                ),
                0
        );
        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5,
                question6, question7, question8, question9, question10, question11, question12,
                question13, question14, question15, question16, question17, question18, question19,
                question20));
    }
}