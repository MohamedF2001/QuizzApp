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

public class QuizzLitterature extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_quizz_litterature);

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
                "Quel est l'animal national du Canada ?\t",
                Arrays.asList(
                        "Castor d'Asie du Nord",
                        "Castor d'Amérique du Nord",
                        "Castor d'Amérique de L Est",
                        "Castor d'Amérique du Sud"
                ),
                1
        );

        Question question2 = new Question(
                "Quel est l'animal national de l'Albanie ?",
                Arrays.asList(
                        "Aigle en vert",
                        "Aigle en rouge",
                        "Aigle bleu",
                        "Aigle en or"
                ),
                3
        );

        Question question3 = new Question(
                "Quel chien était autrefois sacré en Chine ?",
                Arrays.asList(
                        "Poilus",
                        "Berger",
                        "Pékinois",
                        "Pékin"
                ),
                2
        );

        Question question4 = new Question(
                "L'urticaire est une maladie de peau autrement connue sous le nom de quoi ?",
                Arrays.asList(
                        "Utilitaire",
                        "Urticaire",
                        "Urticoire",
                        "Vurticaire"
                ),
                1
        );
        Question question5 = new Question(
                "Quel genre d'animal est la plus grande créature vivante sur Terre?",
                Arrays.asList(
                        "Meduse",
                        "Requin",
                        "Baleine",
                        "Grenouille"
                ),
                2
        );
        Question question6 = new Question(
                "Donner un autre nom à l'étude des fossiles ?",
                Arrays.asList(
                        "Fossilotologie",
                        "Léontologie",
                        "Baléontologie",
                        "Paléontologie"
                ),
                3
        );
        Question question7 = new Question(
                "Quel oiseau sait nager mais ne peut pas voler ?",
                Arrays.asList(
                        "pigeon",
                        "ours",
                        "pingouin",
                        "manchot"
                ),
                3
        );
        Question question8 = new Question(
                "Qu'est-ce que les libellules préfèrent manger ?",
                Arrays.asList(
                        "Les larves",
                        "Les criquets",
                        "Les moustiques",
                        "Les fourmis"
                ),
                2
        );
        Question question9 = new Question(
                "Qu'obtient-on quand on croise un âne et un cheval ?",
                Arrays.asList(
                        "Bardote",
                        "Bardot",
                        "Badot",
                        "Gardot"
                ),
                1
        );
        Question question10 = new Question(
                "Quels insectes ne peuvent pas voler, mais peuvent sauter à plus de 30 cm ?",
                Arrays.asList(
                        "Des puces",
                        "Des larves",
                        "Des poux",
                        "Des criquets"
                ),
                0
        );
        Question question11 = new Question(
                "Comment s'appelle le bison d'Europe ?",
                Arrays.asList(
                        "Bison",
                        "Bison d Europe",
                        "Biso",
                        "Dison"
                ),
                0
        );
        Question question12 = new Question(
                "Qu'appelle-t-on un poisson au corps de serpent ?",
                Arrays.asList(
                        "Poisson fumée",
                        "Poisson aiguille",
                        "Chien anguille",
                        "Poisson anguille"
                ),
                3
        );
        Question question13 = new Question(
                "Dans quelle ville se trouve le plus vieux zoo du monde ?",
                Arrays.asList(
                        "Paris",
                        "Viennoix",
                        "Vienne",
                        "Tokyo"
                ),
                2
        );
        Question question14 = new Question(
                "D'après quels animaux les îles Canaries portent-elles le nom ?",
                Arrays.asList(
                        "Chats",
                        "Chiens",
                        "Rats",
                        "Lapins"
                ),
                1
        );
        Question question15 = new Question(
                "Quelle plante contient le drapeau canadien?",
                Arrays.asList(
                        "Érable",
                        "férable",
                        "Érab",
                        "Éra"
                ),
                0
        );
        Question question16 = new Question(
                "Quelle est la nourriture des pingouins ?",
                Arrays.asList(
                        "Platon",
                        "Planton",
                        "Plancton",
                        "Socrate"
                ),
                2
        );
        Question question17 = new Question(
                "Quelle est la plus grande espèce de tigre ?",
                Arrays.asList(
                        "tigre de France",
                        "tigre de Suisse",
                        "tigre d espagne",
                        "tigre de Sibérie"
                ),
                3
        );
        Question question18 = new Question(
                "La piqûre de quel insecte provoque la maladie de Lyme ?",
                Arrays.asList(
                        "Irque du cerf",
                        "Tiquez du cerf",
                        "Tique du cerf",
                        "Cirque du cerf"
                ),
                2
        );
        Question question19 = new Question(
                "Quel mammifère ne sait pas sauter ?",
                Arrays.asList(
                        "hipopotame",
                        "l'éléphant",
                        "le porc",
                        "Rhinocéros"
                ),
                1
        );
        Question question20 = new Question(
                "Quel animal lave sa nourriture avant de manger ?",
                Arrays.asList(
                        "Raton laveur",
                        "Castor",
                        "Rat",
                        "Lion"
                ),
                0
        );
        Question question21 = new Question(
                "Qu'est-ce qu'un régime principal de papillons?",
                Arrays.asList(
                        "Noids",
                        "Nectar",
                        "Riz",
                        "Miel"
                ),
                1
        );
        Question question22 = new Question(
                "Combien de temps vit un papillon ?",
                Arrays.asList(
                        "6 mois",
                        "2 mois",
                        "12 mois",
                        "1 mois"
                ),
                2
        );
        Question question23 = new Question(
                "Quel animal est tacheté et a un long cou ?\t",
                Arrays.asList(
                        "Boa",
                        "Zèbre",
                        "Autruche",
                        "Girafe"
                ),
                3
        );
        Question question24 = new Question(
                "Quelle est la plus petite race de chiens ?",
                Arrays.asList(
                        "Poussins",
                        "Chien",
                        "Chihuahua",
                        "Chiots"
                ),
                2
        );
        Question question25 = new Question(
                "Quel est le plus grand animal terrestre ?",
                Arrays.asList(
                        "Dophin",
                        "rhinocéros",
                        "l'éléphant",
                        "Hipopotame"
                ),
                2
        );
        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5,
                question6, question7, question8, question9, question10, question11, question12,
                question13, question14, question15, question16, question17, question18, question19,
                question20, question21, question22, question23, question24, question25));
    }
}