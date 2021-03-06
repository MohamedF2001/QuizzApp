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

public class QuizzAliment extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_quizz_aliment);

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

        builder.setTitle("Bien jou??!")
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
                "De quelle couleur est le vin fran??ais Beaujolais ?",
                Arrays.asList(
                        "couleur rose",
                        "couleur rouge",
                        "couleur bleu",
                        "couleur noir"
                ),
                1
        );
        Question question2 = new Question(
                "Que faire dans les pays arabes pour montrer que la nourriture ??tait d??licieuse ?",
                Arrays.asList(
                        "Rooter",
                        "Broter",
                        "Rootez",
                        "Roter"
                ),
                3
        );
        Question question3 = new Question(
                "Quelles noix sont utilis??es dans le massepain ?",
                Arrays.asList(
                        "Hamandes",
                        "Amande",
                        "Amandes",
                        "Mandes"
                ),
                2
        );

        Question question4 = new Question(
                "?? partir de quel cactus la tequila est-elle fabriqu??e ?",
                Arrays.asList(
                        "Algave",
                        "Agave",
                        "Gave",
                        "Grave"
                ),
                1
        );
        Question question5 = new Question(
                "De quel pays provient le pain pita ?",
                Arrays.asList(
                        "Gr??ces",
                        "Br??sil",
                        "Gr??ce",
                        "Belgique"
                ),
                2
        );
        Question question6 = new Question(
                "Quel pays est ?? l'origine du cocktail Mojito ?",
                Arrays.asList(
                        "Cubaa",
                        "Tokyo",
                        "Cube",
                        "Cuba"
                ),
                3
        );
        Question question7 = new Question(
                "Combien de calories contient un verre d'eau ?",
                Arrays.asList(
                        "Deux calorie",
                        "Cinq calorie",
                        "Dix calorie",
                        "Z??ro calorie"
                ),
                3
        );
        Question question8 = new Question(
                "Quelle est la boisson la plus connue de Gr??ce ?",
                Arrays.asList(
                        "Onzo",
                        "Biere",
                        "Ouzo",
                        "Gouzo"
                ),
                2
        );
        Question question9 = new Question(
                "Qu'appelle-t-on un repas en plein air ?",
                Arrays.asList(
                        "Buffet",
                        "Pique-nique",
                        "Pique-glass",
                        "Pique-nuit"
                ),
                1
        );
        Question question10 = new Question(
                "Quel fromage est traditionnellement utilis?? pour les pizzas ?",
                Arrays.asList(
                        "Mozzarella",
                        "Panini",
                        "Mozzarel",
                        "Fromage"
                ),
                0
        );
        Question question11 = new Question(
                "Quel pays est ?? l'origine de la bi??re Stella ?",
                Arrays.asList(
                        "Belgique",
                        "France",
                        "Belgique",
                        "Canada"
                ),
                0
        );
        Question question12 = new Question(
                "De quel pays vient le c??l??bre Emmental ?",
                Arrays.asList(
                        "la Belgique",
                        "la France",
                        "la Gr??ce",
                        "la Suisse"
                ),
                3
        );
        Question question13 = new Question(
                "Quelle est la bi??re mexicaine la plus connue ?",
                Arrays.asList(
                        "Heineken",
                        "Chapeau",
                        "Couronne",
                        "B??ninoise"
                ),
                2
        );
        Question question14 = new Question(
                "Comment s'appelle le gaz qui fait lever la p??te ?? pain ?",
                Arrays.asList(
                        "Gaz bicarbonique",
                        "Gaz carbonique",
                        "Butane",
                        "Ethane"
                ),
                1
        );
        Question question15 = new Question(
                "De quoi est fait le sak?? japonais ?",
                Arrays.asList(
                        "Riz",
                        "Bl??",
                        "Gari",
                        "Rizi??re"
                ),
                0
        );
        Question question16 = new Question(
                "Qu'est-ce qui est beaucoup mis en bouteille dans la ville fran??aise de Vichy ?",
                Arrays.asList(
                        "Le jus de fruit",
                        "La boisson",
                        "L'eau",
                        "Le vin"
                ),
                2
        );
        Question question17 = new Question(
                "Quelle ville fran??aise est connue pour sa moutarde ?",
                Arrays.asList(
                        "Paris",
                        "Bondy",
                        "Lyon",
                        "Dijon"
                ),
                3
        );
        Question question18 = new Question(
                "Quelle est la formule chimique du sel de table ?\t",
                Arrays.asList(
                        "H2O",
                        "NaOH",
                        "NaCl",
                        "CaCl"
                ),
                2
        );
        Question question19 = new Question(
                "Quelle bi??re est commercialis??e comme la reine des bi??res ?",
                Arrays.asList(
                        "Budwe",
                        "Budweiser",
                        "Budwei",
                        "Bud"
                ),
                1
        );
        Question question20 = new Question(
                "Comment s'appelle la po??le dans laquelle on fait la paella ?",
                Arrays.asList(
                        "Paella (Paella signifie casserole)",
                        "Louche",
                        "Casserole",
                        "Marmite"
                ),
                0
        );
        Question question21 = new Question(
                "Avec quel fruit la bi??re Kriek (une bi??re belge) est-elle aromatis??e ?\t",
                Arrays.asList(
                        "Gateaux",
                        "Cerises",
                        "Raisin",
                        "Fraise"
                ),
                1
        );
        Question question22 = new Question(
                "Quelle est la v??ritable signification du mot grec Pita ?",
                Arrays.asList(
                        "Riz ",
                        "Mais",
                        "Pain",
                        "Lait"
                ),
                2
        );
        Question question23 = new Question(
                "Quel est le mot hongrois pour poivre?",
                Arrays.asList(
                        "Poivron",
                        "Papri",
                        "Poprika",
                        "Paprika"
                ),
                3
        );
        Question question24 = new Question(
                "Comment appelle-t-on une banane en Malaisie ?",
                Arrays.asList(
                        "Banano",
                        "Fisang",
                        "Pisang",
                        "Banana"
                ),
                2
        );
        Question question25 = new Question(
                "Quelle est la bi??re la plus c??l??bre d'Irlande ?",
                Arrays.asList(
                        "Castel beer",
                        "Heineken",
                        "Guinness",
                        "B??ninoise"
                ),
                2
        );
        Question question26 = new Question(
                "Quel genre de nourriture est Manchego d'Espagne ?",
                Arrays.asList(
                        "Du fromage blanc",
                        "De la pizza",
                        "Du jambon",
                        "Du fromage"
                ),
                3
        );
        Question question27 = new Question(
                "De quel pays provient la bi??re Peroni ?",
                Arrays.asList(
                        "France",
                        "Canada",
                        "Br??sil",
                        "Italie"
                ),
                3
        );
        Question question28 = new Question(
                "Quel c??l??bre brasseur d'Amsterdam est d??c??d?? en 2002 ?",
                Arrays.asList(
                        "Castel",
                        "Heineken",
                        "Beer",
                        "Youki"
                ),
                1
        );
        Question question29 = new Question(
                "Quelle vitamine est la seule que vous ne trouverez pas dans un ??uf ?",
                Arrays.asList(
                        "Vitamine E",
                        "Vitamine A",
                        "Vitamine B",
                        "Vitamine C"
                ),
                3
        );
        Question question30 = new Question(
                "Quel est le seul ingr??dient qu'on ne trouve nulle part en dehors de l'Inde ?",
                Arrays.asList(
                        "Oignons",
                        "Poivre",
                        "Coriandre",
                        "Ail"
                ),
                2
        );
        Question question31 = new Question(
                "Quelles noix sont utilis??es dans le massepain ?",
                Arrays.asList(
                        "Armand",
                        "Amant",
                        "Amandes",
                        "Farmandes"
                ),
                2
        );
        Question question32 = new Question(
                "Quel fruit est aussi un animal ?",
                Arrays.asList(
                        "orange",
                        "iwi",
                        "kiwi",
                        "banane"
                ),
                2
        );
        Question question33 = new Question(
                "Quels fuits ont la m??me couleur que son nom",
                Arrays.asList(
                        "Orange et citron vert",
                        "Orange et banane jaune",
                        "Orange et citron jaune",
                        "Orange et papaye vert"
                ),
                0
        );
        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5,
                question6, question7, question8, question9, question10, question11, question12,
                question13, question14, question15, question16, question17, question18, question19,
                question20, question21, question22, question23, question24, question25, question26,
                question27, question28, question29, question30, question31, question32, question33));
    }
}