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

public class QuizzFilm extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_quizz_film);

        mEnableTouchEvents = true;

        mTextViewQuestion = findViewById(R.id.game_activity_textview_question);
        mAnswerButton1 = findViewById(R.id.game_activity_button_1);
        mAnswerButton2 = findViewById(R.id.game_activity_button_2);
        mAnswerButton3 = findViewById(R.id.game_activity_button_3);
        mAnswerButton4 = findViewById(R.id.game_activity_button_4);

        // Use the same listener for the four buttons.
        // The view id value will be used to distinguish the button triggered
        mAnswerButton1.setOnClickListener( this);
        mAnswerButton2.setOnClickListener( this);
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
                "Combien d'oscars le film Titanic a-t-il obtenu ?",
                Arrays.asList(
                        "Douze",
                        "Onze",
                        "Dix",
                        "Cinq"
                ),
                1
        );

        Question question2 = new Question(
                "Combien de films Tomb Raider ont été réalisés ?",
                Arrays.asList(
                        "Cinq films",
                        "Dix films",
                        "Trois films",
                        "Deux films"
                ),
                3
        );

        Question question3 = new Question(
                "Quelle malformation Marilyn Monroe avait-elle à sa naissance ?",
                Arrays.asList(
                        "Une jammbe",
                        "Sept orteils",
                        "Six orteils",
                        "Deux orteils"
                ),
                2
        );

        Question question4 = new Question(
                "Quel est le numéro de maison des Simpson ?",
                Arrays.asList(
                        "Numéro 74",
                        "Numéro 742",
                        "Numéro 702",
                        "Numéro 42"
                ),
                1
        );
        Question question5 = new Question(
                "Comment s'appelle la prison dans le film The Rock ?",
                Arrays.asList(
                        "Alca",
                        "Alcatra",
                        "Acatraz",
                        "Alcatraz"
                ),
                3
        );
        Question question6 = new Question(
                " Qui est le protagoniste du film Last Action Hero ?",
                Arrays.asList(
                        "Arnold Schwarzeneg",
                        "Farnold Schwarzenegger",
                        "Arnaud Schwarzenegger",
                        "Arnold Schwarzenegger"
                ),
                3
        );
        Question question7 = new Question(
                "Quel est le pseudonyme d'Allen Stewart Koningsberg ?",
                Arrays.asList(
                        "Wood Allen",
                        "Woody All",
                        "Wody Allen",
                        "Woody Allen"
                ),
                3
        );
        Question question8 = new Question(
                "Comment s'appelle le petit dragon du film d'animation Mulan ?",
                Arrays.asList(
                        "Moushu",
                        "Musshu",
                        "Mushu",
                        "Mushuu"
                ),
                2
        );
        Question question9 = new Question(
                "Quel acteur joue Sonny Crockett dans Miami Vice 2006 ?",
                Arrays.asList(
                        "Bruce Willis",
                        "Colin Farel",
                        "Col Farel",
                        "Morgan Freeman"
                ),
                1
        );
        Question question10 = new Question(
                "Qui est le directeur de Reservoir Dogs ?",
                Arrays.asList(
                        "Quentin Tarantino",
                        "Quentin Tarant",
                        "Quantin Tarantino",
                        "Quenti Tarantino"
                ),
                0
        );
        Question question11 = new Question(
                "Qui est le directeur des X-files ?",
                Arrays.asList(
                        "Rob Bowman",
                        "Robby Bowman",
                        "Chadic",
                        "Rob Bow"
                ),
                0
        );
        Question question12 = new Question(
                "Qui était le protagoniste masculin de The Horse Wisperer ?",
                Arrays.asList(
                        "Robert Radford",
                        "Roberto Redford",
                        "Robert Red",
                        "Robert Redford"
                ),
                3
        );
        Question question13 = new Question(
                "Qui a joué le rôle de Peter Pan dans le film Peter Pan ?",
                Arrays.asList(
                        "Daniella Williams",
                        "Serrena Williams",
                        "Robin Williams",
                        "Robino Williams"
                ),
                2
        );
        Question question14 = new Question(
                "Quel réalisateur italien est considéré comme le père du western spaghetti ?",
                Arrays.asList(
                        "Sergio Leon",
                        "Sergio Leone",
                        "Serge Leone",
                        "Segio Leone"
                ),
                1
        );
        Question question15 = new Question(
                "Quel numéro est sur Herbie le Beatle ?",
                Arrays.asList(
                        "Cinquante trois",
                        "Soixante trois",
                        "Trente trois",
                        "Vingt trois"
                ),
                0
        );
        Question question16 = new Question(
                "Donnez le nom de la meilleure parodie de James Bond.",
                Arrays.asList(
                        "Austino Powers",
                        "Augustin Powers",
                        "Austin Powers",
                        "Austin Pow"
                ),
                2
        );
        Question question17 = new Question(
                "Comment s'appelle le commandant chauve de l'Enterprise dans Star Trek ?",
                Arrays.asList(
                        "Capitaine Biceps",
                        "Capitaine Piscard",
                        "Capitaine Piscou",
                        "Capitaine Picard"
                ),
                3
        );
        Question question18 = new Question(
                "Comment s'appelle le film en noir et blanc de Steven Spielberg sur la Seconde Guerre mondiale ?",
                Arrays.asList(
                        "Le livre de Schindler",
                        "La rue de Schindler",
                        "La liste de Schindler",
                        "La couverture de Schindler"
                ),
                2
        );
        Question question19 = new Question(
                "Qui était l'actrice principale de Sister act I en II ?",
                Arrays.asList(
                        "Whoopi Gold",
                        "Whoopi Goldberg",
                        "Whoop Goldberg",
                        "Whopi Goldberg"
                ),
                1
        );
        Question question20 = new Question(
                "Quel est le métier de Popeye ?",
                Arrays.asList(
                        "Marin",
                        "Marine",
                        "Marie",
                        "Maria"
                ),
                0
        );
        Question question21 = new Question(
                "Qui était le réalisateur du film Le Piano ?",
                Arrays.asList(
                        "Jeanne Campion",
                        "Jeanne Camp",
                        "Jeannette Campion",
                        "Jean Campion"
                ),
                0
        );
        Question question22 = new Question(
                "Qui était le grand rival de Mozart dans le film Amadeus ?",
                Arrays.asList(
                        "Antares Salieri. ",
                        "Antoni Salieri.",
                        "Antonio Salieri.",
                        "Tonio Salieri."
                ),
                2
        );
        Question question23 = new Question(
                "Quelle femme française était l'actrice la plus célèbre au monde à la fin du XIXe et au début du XXe siècle ?",
                Arrays.asList(
                        "Sophie Bernhardt",
                        "Serge Bernhardt",
                        "Sarah Bernh",
                        "Sarah Bernhardt"
                ),
                3
        );
        Question question24 = new Question(
                "Qui a joué le rôle principal dans le film Scarface en 1983 ?",
                Arrays.asList(
                        "All Pacino",
                        "Al Pacno",
                        "Al Pacino",
                        "Al Pinocio"
                ),
                2
        );
        Question question25 = new Question(
                "Qui a joué Harry Potter dans Harry Potter",
                Arrays.asList(
                        "Jérémy Radcliffe",
                        "Daniella Radcliffe",
                        "Daniel Radcliffe",
                        "Daniel Radclif"
                ),
                2
        );
        Question question26 = new Question(
                "Qui a joué Che Guevara dans le film \"Evita\" ?",
                Arrays.asList(
                        "Antony Banderas",
                        "Antoinette Banderas",
                        "Antoni Bande",
                        "Antonio Banderas"
                ),
                3
        );
        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5,
                question6, question7, question8, question9, question10, question11, question12,
                question13, question14, question15, question16, question17, question18, question19,
                question20, question21, question22, question23, question24, question25, question26));
    }
}