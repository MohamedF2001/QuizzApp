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

public class QuizzBiologie extends AppCompatActivity implements View.OnClickListener {

    public static final String RESULT_SCORE = "RESULT_SCORE";

    private static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    private static final String BUNDLE_STATE_QUESTION_COUNT = "BUNDLE_STATE_QUESTION_COUNT";
    private static final String BUNDLE_STATE_QUESTION_BANK = "BUNDLE_STATE_QUESTION_BANK";

    private static final int INITIAL_QUESTION_COUNT = 5;

    private TextView mTextViewQuestion;
    private Button mAnswerButton9;
    private Button mAnswerButton10;
    private Button mAnswerButton11;
    private Button mAnswerButton12;

    private int mScore;
    private int mRemainingQuestionCount;
    private QuestionBank mQuestionBank;

    private boolean mEnableTouchEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_biologie);

        mEnableTouchEvents = true;

        mTextViewQuestion = findViewById(R.id.game_activity_textview_question);
        mAnswerButton9 = findViewById(R.id.game_activity_button_9);
        mAnswerButton10 = findViewById(R.id.game_activity_button_10);
        mAnswerButton11 = findViewById(R.id.game_activity_button_11);
        mAnswerButton12 = findViewById(R.id.game_activity_button_12);

        // Use the same listener for the four buttons.
        // The view id value will be used to distinguish the button triggered
        mAnswerButton9.setOnClickListener( this);
        mAnswerButton10.setOnClickListener(this);
        mAnswerButton11.setOnClickListener(this);
        mAnswerButton12.setOnClickListener(this);

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

    public void onClick(View v) {
        int index;

        if (v == mAnswerButton9) {
            index = 0;
        } else if (v == mAnswerButton10) {
            index = 1;
        } else if (v == mAnswerButton11) {
            index = 2;
        } else if (v == mAnswerButton12) {
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
        mAnswerButton9.setText(question.getChoiceList().get(0));
        mAnswerButton10.setText(question.getChoiceList().get(1));
        mAnswerButton11.setText(question.getChoiceList().get(2));
        mAnswerButton12.setText(question.getChoiceList().get(3));
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
                "Quelle fleur est le symbole du soleil et le symbole du Japon ?",
                Arrays.asList(
                        "Tournesol",
                        "Chrysanthème",
                        "Sépales",
                        "Pétales de rose"
                ),
                1
        );

        Question question2 = new Question(
                "Quel animal a trois cœurs ?",
                Arrays.asList(
                        "Baleine",
                        "Requin",
                        "Méduse",
                        "Poulpe"
                ),
                3
        );

        Question question3 = new Question(
                "De quelle couleur est le sang de mollusque ?",
                Arrays.asList(
                        "Incolore",
                        "Noir",
                        "Bleu",
                        "Rouge"
                ),
                2
        );

        Question question4 = new Question(
                "Quel est le plus petit oiseau ?",
                Arrays.asList(
                        "Calibri",
                        "Colibri",
                        "Colibrie",
                        "Kolibri"
                ),
                1
        );

        Question question5 = new Question(
                "Quel est l'animal terrestre le plus rapide ?",
                Arrays.asList(
                        "Lièvre",
                        "Lion",
                        "Léopard",
                        "Guépard"
                ),
                3
        );
        Question question6 = new Question(
                "Quel est le fil naturel le plus fin ?",
                Arrays.asList(
                        "Corde",
                        "Fil simple",
                        "Fil de cheveux",
                        "Toile d'araignée"
                ),
                3
        );
        Question question7 = new Question(
                "Quelles pattes de girafe sont les plus longues ?",
                Arrays.asList(
                        "Toutes égales",
                        "De derrière",
                        "De dos",
                        "De face"
                ),
                3
        );
        Question question8 = new Question(
                "Quel animal se lave les dents après avoir mangé ? (Il se lave la bouche).",
                Arrays.asList(
                        "Lion",
                        "Eléphant",
                        "Tigre",
                        "Lièvre"
                ),
                2
        );
        Question question9 = new Question(
                "Quels oiseaux ont des écailles sur leurs ailes ?",
                Arrays.asList(
                        "Ours",
                        "Pingouins",
                        "Corbeaux",
                        "Dauphins"
                ),
                1
        );
        Question question10 = new Question(
                "Quel animal a de la graisse de couleur verte ?",
                Arrays.asList(
                        "Crocodile",
                        "Grenouille",
                        "Caméléon",
                        "Criquet"
                ),
                0
        );
        Question question11 = new Question(
                "Quelle est la plus grande araignée du monde ?",
                Arrays.asList(
                        "Goliath birdeater (araignée).",
                        "Araignéé tout court",
                        "Goliath bird (araignée).",
                        "David birdeater (araignée)."
                ),
                0
        );
        Question question12 = new Question(
                "Le plus grand serpent sur Terre.",
                Arrays.asList(
                        "Serpent simple",
                        "Vipère",
                        "Boa",
                        "Anaconda (jusqu'à 9 mètres)."
                ),
                3
        );
        Question question13 = new Question(
                "Quel requin a les yeux distants de 2 m ?",
                Arrays.asList(
                        "Requin",
                        "Requin-marie",
                        "Requin-marteau",
                        "Requin-pointu"
                ),
                2
        );
        Question question14 = new Question(
                "Le plus grand arbre du monde.",
                Arrays.asList(
                        "Séquo",
                        "Séquoia",
                        "Séquia",
                        "Séquoi"
                ),
                1
        );
        Question question15 = new Question(
                "Les arbres les plus hauts du monde.",
                Arrays.asList(
                        "Eucalyptus (jusqu'à 187 m).",
                        "Eucal (jusqu'à 187 m).",
                        "Séquoia (jusqu'à 187 m).",
                        "Baobab (jusqu'à 187 m)."
                ),
                0
        );
        Question question16 = new Question(
                "Quel est le plus grand lézard du monde ?",
                Arrays.asList(
                        "Dragon de Tokyo",
                        "Dragon de Kamodo",
                        "Dragon de Komodo",
                        "Dragon"
                ),
                2
        );
        Question question17 = new Question(
                "Quel est le plus grand prédateur du monde ?",
                Arrays.asList(
                        "Ours",
                        "Pingouins",
                        "Ours bipolaire",
                        "Ours polaire"
                ),
                3
        );
        Question question18 = new Question(
                "Quel est le plus grand papillon du monde ?",
                Arrays.asList(
                        "Sorcier blanc",
                        "Sorcière rouge",
                        "Sorcière blanche",
                        "Sorcier bleu"
                ),
                2
        );
        Question question19 = new Question(
                "L'oiseau le plus rare sur Terre.",
                Arrays.asList(
                        "Condor de Géorgonie",
                        "Condor de Californie",
                        "Condor de Bruxelles",
                        "Colombe"
                ),
                1
        );
        Question question20 = new Question(
                "Le plus grand mammifère terrestre du monde.",
                Arrays.asList(
                        "Éléphant d'Afrique",
                        "Éléphant d'Asie",
                        "Éléphant d'Océanie",
                        "Éléphant d'Europe"
                ),
                0
        );
        Question question21 = new Question(
                "Le plus grand singe anthropoïde.",
                Arrays.asList(
                        "Gorille",
                        "Chimpanzés",
                        "Gorille d'Océanie",
                        "Gorille d'Europe"
                ),
                0
        );
        Question question22 = new Question(
                "Les plus gros animaux de notre planète.",
                Arrays.asList(
                        "Baleine ",
                        "Baleine rouge",
                        "Baleine bleue",
                        "Requin"
                ),
                2
        );
        Question question23 = new Question(
                "Où un chameau stocke-t-il son eau ?",
                Arrays.asList(
                        "Dans son estomac",
                        "Dans son intestin",
                        "Dans sa poche",
                        "Dans son sang"
                ),
                3
        );
        Question question24 = new Question(
                "Les escargots ont-ils des dents ?",
                Arrays.asList(
                        "Oui, un escargot a plusieurs rangées de dents",
                        "Oui, un escargot a 35 rangées de dents",
                        "Oui, un escargot a 135 rangées de dents",
                        "Non, un escargot n'a pas de rangées de dents"
                ),
                2
        );
        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5,
                question6, question7, question8, question9, question10, question11, question12,
                question13, question14, question15, question16, question17, question18, question19,
                question20, question21, question22, question23, question24));
    }
}