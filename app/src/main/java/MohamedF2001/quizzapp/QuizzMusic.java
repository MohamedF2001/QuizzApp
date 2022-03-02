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

public class QuizzMusic extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_quizz_music);

        mEnableTouchEvents = true;

        mTextViewQuestion = findViewById(R.id.game_activity_textview_question);
        mAnswerButton1 = findViewById(R.id.game_activity_button_1);
        mAnswerButton2 = findViewById(R.id.game_activity_button_2);
        mAnswerButton3 = findViewById(R.id.game_activity_button_3);
        mAnswerButton4 = findViewById(R.id.game_activity_button_4);

        // Use the same listener for the four buttons.
        // The view id value will be used to distinguish the button triggered
        mAnswerButton1.setOnClickListener( this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener( this);
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
                "Quel est le deuxième prénom d'Elvis Presley ?",
                Arrays.asList(
                        "Raaron",
                        "Aaron",
                        "Gaaron",
                        "Faaroon"
                ),
                1
        );

        Question question2 = new Question(
                "Qui est le chanteur de The Counting Crows ?",
                Arrays.asList(
                        "Adam Hunt",
                        "Ada Duritz",
                        "Adam Dur",
                        "Adam Duritz"
                ),
                3
        );

        Question question3 = new Question(
                "Qui était la reine de la soul ?",
                Arrays.asList(
                        "Artemis Franklin",
                        "Athena Franklin",
                        "Aretha Franklin",
                        "Arethate Franklin"
                ),
                2
        );

        Question question4 = new Question(
                "Quelle est la voix masculine la plus basse ?",
                Arrays.asList(
                        "Voix de haute",
                        "Voix de basse",
                        "Voix légère",
                        "Voix courte"
                ),
                1
        );
        Question question5 = new Question(
                "Quel groupe célèbre était autrefois connu sous le nom de The Quarrymen ?",
                Arrays.asList(
                        "Les Eatles",
                        "Les Beats",
                        "Les Beatles",
                        "Les Eagles"
                ),
                2
        );
        Question question6 = new Question(
                "Comment s'appelait le chanteur d'AC/DC décédé en 1980 ?",
                Arrays.asList(
                        "Ben Scott",
                        "Bon Scotty",
                        "Bony Scott",
                        "Bon Scott"
                ),
                3
        );
        Question question7 = new Question(
                "Quel groupe a interprété la chanson Dear God?",
                Arrays.asList(
                        "Le groupe ATC",
                        "Le groupe XTA",
                        "Le groupe XTO",
                        "Le groupe XTC"
                ),
                3
        );
        Question question8 = new Question(
                "Quel est le nom complet de Madonna",
                Arrays.asList(
                        "Madonne Louise Ciccone",
                        "Madon Louisa Ciccone",
                        "Madonna Louise Ciccone",
                        "Momo Louise Ciccone"
                ),
                2
        );
        Question question9 = new Question(
                "Comment s'appelle le chanteur irlandais qui a remporté deux fois le concours Eurovision de la chanson ?",
                Arrays.asList(
                        "John Logan",
                        "Johnny Logan",
                        "Johnny McGreen",
                        "Morgan Freeman"
                ),
                1
        );
        Question question10 = new Question(
                "Comment s'appelle l'invention bruyante de Louis Glass en 1890 ?",
                Arrays.asList(
                        "Juke-box",
                        "Junk-box",
                        "Juke-boxe",
                        "Duke-box"
                ),
                0
        );
        Question question11 = new Question(
                "Qui est le leader de The Prodigy ?",
                Arrays.asList(
                        "Keith Flint",
                        "Keith Flynt",
                        "Keineth Flint",
                        "Ken Flynt"
                ),
                0
        );
        Question question12 = new Question(
                "Qui est le batteur de Metallica ?",
                Arrays.asList(
                        "Robert Radford",
                        "Tony Ulrich",
                        "Fars Ulrich",
                        "Lars Ulrich"
                ),
                3
        );
        Question question13 = new Question(
                "Quel groupe a eu un tube avec la Macarena ?",
                Arrays.asList(
                        "Los Palma",
                        "Dos Del Río",
                        "Los Del Río",
                        "Los Dal Río"
                ),
                2
        );
        Question question14 = new Question(
                "Qui a remporté le plus de Grammy Awards dans les années 80 ?",
                Arrays.asList(
                        "Michael Jordan",
                        "Michael Jackson",
                        "Johnny halliday",
                        "Justin Bieber"
                ),
                1
        );


        Question question15 = new Question(
                "Combien de cordes a une mandoline ?",
                Arrays.asList(
                        "Huit cordes",
                        "Neuf cordes",
                        "Six cordes",
                        "Deux cordes"
                ),
                0
        );
        Question question16 = new Question(
                "Quel est le plus grand succès de Bing Crosby ?",
                Arrays.asList(
                        "Noël noir",
                        "Noël rouge",
                        "Noël blanc",
                        "Noël gris"
                ),
                2
        );
        Question question17 = new Question(
                "Quel est le nom de famille de la chanteuse Rihanna ?",
                Arrays.asList(
                        "Swift",
                        "Smith",
                        "Durang",
                        "Fenty"
                ),
                3
        );
        Question question18 = new Question(
                "Quel groupe de pop britannique a eu un hit appelé Angel Eyes dans les années 70 ?",
                Arrays.asList(
                        "Musique Rock",
                        "Musique Chest",
                        "Musique Roxy",
                        "Musique Pop"
                ),
                2
        );
        Question question19 = new Question(
                "Combien de lignes une portée musicale (ou une portée musicale) a-t-elle ?",
                Arrays.asList(
                        "Dix lignes",
                        "Cinq lignes",
                        "Deux lignes",
                        "Huit lignes"
                ),
                1
        );
        Question question20 = new Question(
                "Quelle chanteuse a été surnommée la Voix de l'Europe après avoir remporté trois fois le Concours Eurovision de la chanson ?",
                Arrays.asList(
                        "Johnny Logan",
                        "Johnny Hallyday",
                        "John Logan",
                        "Johnanthan Logan"
                ),
                0
        );
        Question question21 = new Question(
                "Qui a composé la musique du western spaghetti The Good, The Bad and The Ugly avec Clint Eastwood ?",
                Arrays.asList(
                        "Ennio Cane",
                        "Ennio Morricone",
                        "Ennio Morri",
                        "Jessie Morricone"
                ),
                1
        );
        Question question22 = new Question(
                "Quelle chanteuse pop a épousé Debbie Rowe ?",
                Arrays.asList(
                        "Antares Salieri. ",
                        "Michael Jack",
                        "Michael Jackson",
                        "Tonio Salieri."
                ),
                2
        );
        Question question23 = new Question(
                "Quel chanteur britannique a fait un carton en 2006 avec Rehab ?",
                Arrays.asList(
                        "Elena Gomez",
                        "Taylor Swift",
                        "Amy Andersson",
                        "Amy Winehouse"
                ),
                3
        );
        Question question24 = new Question(
                "Quelle était la nationalité de Mozart ?",
                Arrays.asList(
                        "Américaine",
                        "Française",
                        "Autrichien",
                        "Colombienne"
                ),
                2
        );
        Question question25 = new Question(
                "Combien de cordes possède un violon ?",
                Arrays.asList(
                        "Deux cordes",
                        "Cinq cordes",
                        "Quatre cordes",
                        "Huit cordes"
                ),
                2
        );
        Question question26 = new Question(
                "Quel a été le premier single de Madonna dans le top 10 ?",
                Arrays.asList(
                        "Tranquille",
                        "Repos",
                        "Congés",
                        "Vacances"
                ),
                3
        );
        Question question27 = new Question(
                "Quel groupe a eu un succès numéro 1 avec \"Barbie Girl\" ?",
                Arrays.asList(
                        "Aquax",
                        "Repos",
                        "Nouss",
                        "Aqua"
                ),
                3
        );
        Question question28 = new Question(
                "Dans quelle ville John Lennon a-t-il été tué ?",
                Arrays.asList(
                        "Kiev",
                        "New York",
                        "Paris",
                        "Los Angeles"
                ),
                1
        );
        Question question29 = new Question(
                "\tQuel groupe de rock a eu un tube avec The Final Countdown ?",
                Arrays.asList(
                        "L'Europe \uF0D7",
                        "L'Amérique \uF0D7",
                        "L'Asie \uF0D7",
                        "L'Afrique \uF0D7"
                ),
                3
        );
        Question question30 = new Question(
                "De quel pays vient Céline Dion ?",
                Arrays.asList(
                        "Espagne",
                        "Belgique",
                        "Canada",
                        "Suisse"
                ),
                2
        );
        Question question31 = new Question(
                "Qui était le manager d'Elvis Presley ?",
                Arrays.asList(
                        "Colonel Parker",
                        "Kolonel Park",
                        "Kolonel Parker",
                        "Parker"
                ),
                2
        );
        Question question32 = new Question(
                "Qui est l'artiste de la chanson fleur imparfaite",
                Arrays.asList(
                        "Quando rando",
                        "Quanto rondo",
                        "Quando rondo",
                        "Quarto rondo"
                ),
                2
        );
        Question question33 = new Question(
                "Qu'est-ce que nba youngboy représente",
                Arrays.asList(
                        "Youngboy ne s'est plus jamais cassé",
                        "Youngboy s'est cassé",
                        "Youngboy s'en vas",
                        "Youngboy ne sort plus jamais cassé"
                ),
                0
        );
        Question question34 = new Question(
                "Qui est Tina Snow ?",
                Arrays.asList(
                        "Regan toi étalon",
                        "Megane toi étalon",
                        "Megan toi étalon",
                        "Megan moi étalon"
                ),
                2
        );
        Question question35 = new Question(
                "Qui est le Beatle le plus âgé ?",
                Arrays.asList(
                        "Ringo Star",
                        "Ringo Starty",
                        "Rocky Star",
                        "Ringo Palm"
                ),
                0
        );
        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5,
                question6, question7, question8, question9, question10, question11, question12,
                question13, question14, question15, question16, question17, question18, question19,
                question20, question21, question22, question23, question24, question25, question26,
                question27, question28, question29, question30, question31, question32, question33,
                question34, question35));
    }
}