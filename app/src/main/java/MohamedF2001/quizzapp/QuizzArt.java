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

public class QuizzArt extends AppCompatActivity implements View.OnClickListener {

    public static final String RESULT_SCORE = "RESULT_SCORE";

    private static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    private static final String BUNDLE_STATE_QUESTION_COUNT = "BUNDLE_STATE_QUESTION_COUNT";
    private static final String BUNDLE_STATE_QUESTION_BANK = "BUNDLE_STATE_QUESTION_BANK";

    private static final int INITIAL_QUESTION_COUNT = 5;

    private TextView mTextViewQuestion;
    private Button mAnswerButton5;
    private Button mAnswerButton6;
    private Button mAnswerButton7;
    private Button mAnswerButton8;

    private int mScore;
    private int mRemainingQuestionCount;
    private QuestionBank mQuestionBank;

    private boolean mEnableTouchEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_art);

        mEnableTouchEvents = true;

        mTextViewQuestion = findViewById(R.id.game_activity_textview_question);
        mAnswerButton5 = findViewById(R.id.game_activity_button_5);
        mAnswerButton6 = findViewById(R.id.game_activity_button_6);
        mAnswerButton7 = findViewById(R.id.game_activity_button_7);
        mAnswerButton8 = findViewById(R.id.game_activity_button_8);

        // Use the same listener for the four buttons.
        // The view id value will be used to distinguish the button triggered
        mAnswerButton5.setOnClickListener( this);
        mAnswerButton6.setOnClickListener( this);
        mAnswerButton7.setOnClickListener( this);
        mAnswerButton8.setOnClickListener( this);

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

        if (v == mAnswerButton5) {
            index = 0;
        } else if (v == mAnswerButton6) {
            index = 1;
        } else if (v == mAnswerButton7) {
            index = 2;
        } else if (v == mAnswerButton8) {
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
        mAnswerButton5.setText(question.getChoiceList().get(0));
        mAnswerButton6.setText(question.getChoiceList().get(1));
        mAnswerButton7.setText(question.getChoiceList().get(2));
        mAnswerButton8.setText(question.getChoiceList().get(3));
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
                "De quelle couleur sont souvent les coupoles des églises en Russie ?",
                Arrays.asList(
                        "Couleur or",
                        "Couleur bleu",
                        "Couleur blanche",
                        "Couleur noir"
                ),
                0
        );

        Question question2 = new Question(
                "Dans quelle ville espagnole le musée Joan Miro a-t-il ouvert en 1975 ?",
                Arrays.asList(
                        "Bordeaux",
                        "Lille",
                        "Madrid",
                        "Barcelone"
                ),
                3
        );

        Question question3 = new Question(
                "Quel artiste italien a peint la Naissance de Vénus ?",
                Arrays.asList(
                        "Carlos",
                        "Ancelotti",
                        "Botticelli",
                        "Bittocelli"
                ),
                2
        );

        Question question4 = new Question(
                "Qui était l'auteur original de Dracula ?",
                Arrays.asList(
                        "Bram Soccer",
                        "Bram Stoker",
                        "Adam Stoker",
                        "Dram Stoker"
                ),
                1
        );

        Question question5 = new Question(
                "Dans quelle ville se trouve la célèbre fontaine du Manneken Pis ?",
                Arrays.asList(
                        "Tokyo",
                        "Pékin",
                        "New York",
                        "Bruxelles"
                ),
                3
        );
        Question question6 = new Question(
                "Qui est l'inventeur de la photographie ?",
                Arrays.asList(
                        "Draguerre",
                        "Daguer",
                        "Doguerre",
                        "Daguerre"
                ),
                3
        );
        Question question7 = new Question(
                "Quel artiste a peint Le Cri ?",
                Arrays.asList(
                        "Edvar Moon",
                        "Edard Muncho",
                        "Edgard Munch",
                        "Edvard Munch"
                ),
                3
        );
        Question question8 = new Question(
                "Quel est le premier livre de l'Ancien Testament ?",
                Arrays.asList(
                        "Smith",
                        "Livre",
                        "Genèse",
                        "Génève"
                ),
                2
        );
        Question question9 = new Question(
                "Qui a peint la Joconde ?",
                Arrays.asList(
                        "Calipso",
                        "Léonard de Vinci",
                        "Léonard de Vinc",
                        "Damme"
                ),
                1
        );
        Question question10 = new Question(
                "Dans quelle ville anglaise Adolf Hitler a-t-il étudié l'art ?",
                Arrays.asList(
                        "Liverpool",
                        "Cotonou",
                        "Lyon",
                        "Bondy"
                ),
                0
        );
        Question question11 = new Question(
                "Quel célèbre ingénieur français a conçu deux ponts pour la ville de Porto ?",
                Arrays.asList(
                        "Gustave Eiffel",
                        "Tour Eiffel",
                        "Gustave Fel",
                        "Gustavo Eiffel"
                ),
                0
        );
        Question question12 = new Question(
                "Dans quelle ville peut-on voir le David de Michel-Ange ?",
                Arrays.asList(
                        "Fior",
                        "Flore",
                        "Floriane",
                        "Florence"
                ),
                3
        );
        Question question13 = new Question(
                "Qu'appelle-t-on le chandelier juif à signification religieuse particulière ?",
                Arrays.asList(
                        "Moriah",
                        "Marie",
                        "Ménorah",
                        "Mimi"
                ),
                2
        );
        Question question14 = new Question(
                "Qu'est-ce que la Kabbale ?",
                Arrays.asList(
                        "Un système de croyances mystiques chrétiennes",
                        "Un système de croyances mystiques juives",
                        "Un système de croyances religieuses",
                        "Un système de croyances mythiques"
                ),
                1
        );
        Question question15 = new Question(
                "Qui a peint le plafond de la Chapelle Sixtine ?",
                Arrays.asList(
                        "Michel-Ange",
                        "Michel-Angel",
                        "Patrice-Ange",
                        "Miche-Ange"
                ),
                0
        );
        Question question16 = new Question(
                "Dans quel pays est né le célèbre peintre El Greco ?",
                Arrays.asList(
                        "Canada",
                        "Bruxelles",
                        "Grèce",
                        "France"
                ),
                2
        );
        Question question17 = new Question(
                "Dans quelle ville est enterré le compositeur Frédéric Chopin ?",
                Arrays.asList(
                        "Madrid",
                        "Barcelone",
                        "Lyon",
                        "Paris"
                ),
                3
        );
        Question question18 = new Question(
                "Qui a peint le célèbre tableau Guernica ?",
                Arrays.asList(
                        "Michel-Ange",
                        "Léonard de Vinci",
                        "Picasso",
                        "Piscou"
                ),
                2
        );
        Question question19 = new Question(
                "Dans quelle ville vivaient Roméo et Julia ?",
                Arrays.asList(
                        "New York",
                        "Vérone",
                        "Paris",
                        "Tunis"
                ),
                1
        );
        Question question20 = new Question(
                "Qui a peint la Joconde ?",
                Arrays.asList(
                        "Léonard de Vinci",
                        "Picasso",
                        "Jean",
                        "Albert Einstein"
                ),
                0
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5,
                question6, question7, question8, question9, question10, question11, question12,
                question13, question14, question15, question16, question17, question18, question19,
                question20));
    }
}