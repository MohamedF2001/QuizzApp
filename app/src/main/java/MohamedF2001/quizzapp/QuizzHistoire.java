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

public class QuizzHistoire extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_quizz_histoire);

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
        mAnswerButton4.setOnClickListener( this);

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
                "Quel comte allemand a inventé le zeppelin ?",
                Arrays.asList(
                        "Comte Dracula",
                        "Comte (Von) Zeppelin",
                        "Comte (Vin) Zappelin",
                        "Comte Marcho"
                ),
                1
        );

        Question question2 = new Question(
                "Qui a été le premier président des États-Unis ?",
                Arrays.asList(
                        "Donald Trump",
                        "Nelson Mandela",
                        "George Cadet",
                        "George Washington"
                ),
                3
        );

        Question question3 = new Question(
                "Qui était le deuxième président des États-Unis ?",
                Arrays.asList(
                        "Phillipe Adams",
                        "Adams Hunt",
                        "Jean Adams",
                        "Jeannette Adams"
                ),
                2
        );

        Question question4 = new Question(
                "Combien de femmes Henri VIII avait-il ?",
                Arrays.asList(
                        "Dix femmes",
                        "Six femmes",
                        "Dix huits femmes",
                        "Deux femmes"
                ),
                1
        );
        Question question5 = new Question(
                "Dans quelle ville le Titanic a-t-il été construit ?",
                Arrays.asList(
                        "Tokyo",
                        "Belfasto",
                        "Belfast",
                        "Los Angeles"
                ),
                2
        );
        Question question6 = new Question(
                "Comment s'appelait la première épouse de Napoléon ?",
                Arrays.asList(
                        "Justine",
                        "Evelyne",
                        "Joceline",
                        "Joséphine"
                ),
                3
        );
        Question question7 = new Question(
                "Sur quelle île est né Napoléon ?",
                Arrays.asList(
                        "La perse",
                        "Goma",
                        "Ile de la réunion",
                        "la Corse"
                ),
                3
        );
        Question question8 = new Question(
                "Combien d'enfants la reine Victoria a-t-elle eu ?",
                Arrays.asList(
                        "Dix enfants",
                        "Huit enfants",
                        "Neuf enfants",
                        "Deux enfants"
                ),
                2
        );
        Question question9 = new Question(
                "Quelle guerre s'est terminée par la signature de l'armistice le 27 juillet 1953 ?",
                Arrays.asList(
                        "La guerre du Japon",
                        "La guerre de Corée",
                        "La guerre de Chine",
                        "La guerre de l'Ukraine"
                ),
                1
        );
        Question question10 = new Question(
                "Quand l'Albanie est-elle devenue un pays indépendant ? En 1713, 1813 ou 1913 ?",
                Arrays.asList(
                        "En l'an 1913",
                        "En l'an 1920",
                        "En l'an 1900",
                        "En l'an 1904"
                ),
                0
        );
        Question question11 = new Question(
                "Xerxès a gouverné un grand empire vers le Ve siècle av. Quel empire ?",
                Arrays.asList(
                        "L'empire perse",
                        "L'empire corse",
                        "L'empire belge",
                        "L'empire persée"
                ),
                0
        );
        Question question12 = new Question(
                "Quel roi de France s'appelait le Roi Soleil ?",
                Arrays.asList(
                        "Francois I",
                        "Benoit XVI",
                        "Louis XIIIV",
                        "Louis XIV"
                ),
                3
        );
        Question question13 = new Question(
                "Quelle était en Angleterre la frontière septentrionale de l'Empire romain ?",
                Arrays.asList(
                        "Le mur d'hadrianna",
                        "La muraille de chine",
                        "Le mur d'hadrian",
                        "Le mur de drian"
                ),
                2
        );
        Question question14 = new Question(
                "Quel criminel de guerre allemand a été pendant 21 ans le seul détenu du complexe de Spandau ?",
                Arrays.asList(
                        "Rodolphe Hessy",
                        "Rodolphe Hess",
                        "Ricardo Hess",
                        "Phillipe Hess"
                ),
                1
        );
        Question question15 = new Question(
                "Qui a découvert le Groenland ?",
                Arrays.asList(
                        "Erik de Rode",
                        "Eric de Rode",
                        "Eric Zemmour",
                        "Ericka de Rode"
                ),
                0
        );
        Question question16 = new Question(
                "Quel rebelle mexicain a été abattu en 1923 et est mort ?",
                Arrays.asList(
                        "Ville Pancho",
                        "la Pancho",
                        "Villa Pancho",
                        "Villa Pancha"
                ),
                2
        );
        Question question17 = new Question(
                "Quel bâtiment d'Athènes a été détruit par un boulet de canon vénitien au XVIIe siècle ?",
                Arrays.asList(
                        "Prométhée",
                        "Poncahontas",
                        "Parthen",
                        "Parthénon"
                ),
                3
        );
        Question question18 = new Question(
                "Comment s'appelait la révolution protestante contre la domination du pape ?",
                Arrays.asList(
                        "Déformation",
                        "Formation",
                        "Réformation",
                        "Malformation"
                ),
                2
        );
        Question question19 = new Question(
                "Qui était le premier roi de Belgique ?",
                Arrays.asList(
                        "François Ier",
                        "Léopold Ier",
                        "Léopold II",
                        "David VII"
                ),
                1
        );
        Question question20 = new Question(
                " Quel était l'ancien nom de New York ?",
                Arrays.asList(
                        "Nouvel Amsterdam",
                        "Nouvel Orléan",
                        "Nouvel Callédonie",
                        "Nouvel Ville"
                ),
                0
        );
        Question question21 = new Question(
                "Quel pays s'appelait autrefois Ceylan ?",
                Arrays.asList(
                        "Sri Lanko",
                        "Sri Lanka",
                        "Sri ",
                        "Crise Lanka"
                ),
                1
        );
        Question question22 = new Question(
                "Quel était le nom latin de Paris à l'époque romaine ?",
                Arrays.asList(
                        "Lucette",
                        "Laetétia",
                        "Lutétia",
                        "Lulette"
                ),
                2
        );
        Question question23 = new Question(
                "Quelle ville a été la capitale de l'Australie de 1901 à 1927 ?",
                Arrays.asList(
                        "Kiev",
                        "Borgonne",
                        "Belbourne",
                        "Melbourne"
                ),
                3
        );
        Question question24 = new Question(
                "Quel pays a envoyé sa marine autour du monde pour combattre les Japonais en 1904 ?",
                Arrays.asList(
                        "France",
                        "Chine",
                        "Russie",
                        "Colombie"
                ),
                2
        );
        Question question25 = new Question(
                "En quelle année la Seconde Guerre mondiale a-t-elle pris fin ?",
                Arrays.asList(
                        "1925",
                        "1955",
                        "1945",
                        "1900"
                ),
                2
        );
        Question question26 = new Question(
                "Quel roi des Français s'appelait \"Saint Louis\" ?",
                Arrays.asList(
                        "Benoit IX",
                        "Patrice IX",
                        "Louis X",
                        "Louis IX"
                ),
                3
        );
        Question question27 = new Question(
                "Comment les romains appelaient-ils l'Ecosse ?\t",
                Arrays.asList(
                        "Aqualédonie",
                        "Colédonie",
                        "Calédoniene",
                        "Calédonie"
                ),
                3
        );
        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5,
                question6, question7, question8, question9, question10, question11, question12,
                question13, question14, question15, question16, question17, question18, question19,
                question20, question21, question22, question23, question24, question25, question26,
                question27));
    }
}