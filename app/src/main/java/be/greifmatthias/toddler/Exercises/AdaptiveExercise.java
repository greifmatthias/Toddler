package be.greifmatthias.toddler.Exercises;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import be.greifmatthias.toddler.Activities.ExerciseActivity;
import be.greifmatthias.toddler.Models.Group;
import be.greifmatthias.toddler.R;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class AdaptiveExercise extends Exercise {
    private Group.Condition condition;

    public AdaptiveExercise(String word, Group.Condition condition) {
        super(word);

        this._type = "Adaptive";
        this.condition = condition;
    }
    @Override
    public int getIcon() {
        switch (this.condition){
            case A:
                if(this._haspassed){
                    return R.drawable.ic_round_sentiment_satisfied;
                }

                return R.drawable.ic_round_sentiment_dissatisfied;
            default:
                if(this._haspassed){
                    return R.drawable.ic_round_done;
                }

                return R.drawable.ic_round_close;
        }
    }

    @Override
    public String getName() {
        String name = "Veranderende oefening: ";
        switch (condition){
            case A:
                name += "Gevoel";
                break;
            case B:
                name += "Zoemen";
                break;
            case C:
                name += "Lettergrepen";
                break;
        }
        return name;
    }

    @Override
    public String getKaatje() {
        switch (condition){
            case A:
                return this._word + ", wat is dat een mooi woord. Vind jij " + this.getWord() + " ook zo een mooi woord of toch niet?";
            case B:
                return "";
            case C:
                return "";
        }

        return "";
    }

    private String getWord(){
        List<String> wordparts = Arrays.asList(this._word.split(" "));

        if(wordparts.size() > 1){
            return wordparts.get(wordparts.size() - 1);
        }

        return this._word;
    }

    public WordPart getWordpart(){
        List<String> parts = new ArrayList<>();
        List<Integer> voices = new ArrayList<>();
        switch (this._word){
            case "De duikbril":
                parts.add("Duik");
                parts.add("bril");
                voices.add(R.raw.adaptive_c_duikbril_1);
                voices.add(R.raw.adaptive_c_duikbril_2);
                return new WordPart(parts, voices);
            case "Het klimtouw":
                parts.add("Klim");
                parts.add("touw");
                voices.add(R.raw.adaptive_c_klimtouw_1);
                voices.add(R.raw.adaptive_c_klimtouw_2);
                return new WordPart(parts, voices);
            case "Het kroos":
                parts.add("Kroos");
                voices.add(R.raw.adaptive_c_duikbril_1);
                return new WordPart(parts, voices);
            case "Het riet":
                parts.add("Riet");
                voices.add(R.raw.adaptive_c_duikbril_1);
                return new WordPart(parts, voices);
            case "De val":
                parts.add("Val");
                voices.add(R.raw.adaptive_c_duikbril_1);
                return new WordPart(parts, voices);
            case "Het kompas":
                parts.add("Kom");
                parts.add("pas");
                voices.add(R.raw.adaptive_c_kompas_1);
                voices.add(R.raw.adaptive_c_kompas_2);
                return new WordPart(parts, voices);
            case "Steil":
                parts.add("Steil");
                voices.add(R.raw.adaptive_c_duikbril_1);
                return new WordPart(parts, voices);
            case "De zwaan":
                parts.add("Zwaan");
                voices.add(R.raw.adaptive_c_duikbril_1);
                return new WordPart(parts, voices);
            case "Het kamp":
                parts.add("Kamp");
                voices.add(R.raw.adaptive_c_duikbril_1);
                return new WordPart(parts, voices);
            case "De zaklamp":
                parts.add("Zak");
                parts.add("lamp");
                voices.add(R.raw.adaptive_c_zaklamp_1);
                voices.add(R.raw.adaptive_c_zaklamp_2);
                return new WordPart(parts, voices);
        }

        return null;
    }

    public class WordPart {
        private List<Integer> _voices;
        private List<String> _parts;

        public WordPart(List<String> parts, List<Integer> voices){
            this._voices = voices;
            this._parts = parts;
        }

        public List<Integer> getVoices(){
            return this._voices;
        }

        public List<String> getParts(){
            return this._parts;
        }
    }

    @Override
    public Fragment getFragment(ExerciseActivity activity) {
        return new AdaptiveFragment(activity, this);
    }

    public static class AdaptiveFragment extends Fragment {
        private ExerciseActivity _activity;
        private AdaptiveExercise _exercise;

//        C
        private MediaPlayer player;
        private int currentaudiopos = 0;
        private GifDrawable gdRabbit;

        public AdaptiveFragment() {
            // Required empty public constructor
        }

        @SuppressLint("ValidFragment")
        public AdaptiveFragment(ExerciseActivity activity, AdaptiveExercise exercise) {
            this._activity = activity;
            this._exercise = exercise;

            this._activity.setKaatje(exercise.getKaatje());
            this._activity.setFullScreen(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View view = inflater.inflate(getLayout(), container, false);

            setupLayout(view);

            return view;
        }

        private int getLayout() {
            switch (this._exercise.condition) {
                case B:
                    return R.layout.fragment_adaptive_exercise_b;
                case C:
                    return R.layout.fragment_adaptive_exercise_c;
                default:
                    return R.layout.fragment_adaptive_exercise_a;
            }
        }

        private void setupLayout(View view){
            switch (this._exercise.condition) {
                case B:
//                    B
                    this._activity.goNext();
                    break;
                case C:
//                    C
//                    Get word information
                    final WordPart word = this._exercise.getWordpart();

//                    Add parts
                    TextView tvWordparts = view.findViewById(R.id.tvWordparts);
                    for (int i = 0; i < word.getParts().size() - 1; i++) {
                        tvWordparts.setText(tvWordparts.getText().toString() + word.getParts().get(i) + " - ");
                    }
//                    Add last part
                    tvWordparts.setText(tvWordparts.getText().toString() + word.getParts().get(word.getParts().size() - 1));

//                    Setup rabbit
                    try {
                        gdRabbit = new GifDrawable(getResources(), R.drawable.rabbit);
                        gdRabbit.stop();
                        final GifImageView givRabbit = view.findViewById(R.id.givRabbit);
                        givRabbit.setImageDrawable(gdRabbit);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    Set play click
                    view.findViewById(R.id.fabReplay).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            playC(word);
                        }
                    });

//                    Set next click
                    view.findViewById(R.id.fabNext).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setresult(true);
                        }
                    });

                    break;
                default:
//                    A
                    ((ImageView)view.findViewById(R.id.ivImage)).setImageResource(ExerciseGroup.getHdImage(this._exercise._word));

//                    Set onclick listeners
                    view.findViewById(R.id.rlRate_yes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setresult(true);
                        }
                    });

                    view.findViewById(R.id.rlRate_not).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setresult(false);
                        }
                    });
                    break;
            }
        }

//        Set result for exercises
        private void setresult(boolean passed){
//            Update results
            this._exercise._hasscore = true;
            this._exercise._haspassed = passed;

//            Continue flow
            this._activity.goNext();
        }

        private void playC(final WordPart word){
//            Play audio
            currentaudiopos = 0;
            gdRabbit.setLoopCount(1);

            player = MediaPlayer.create(getContext(), word.getVoices().get(currentaudiopos));

            final MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    currentaudiopos++;
                    gdRabbit.stop();
                    gdRabbit.seekToFrame(0);

                    if(currentaudiopos < word.getVoices().size()) {
                        player = MediaPlayer.create(getContext(), word.getVoices().get(currentaudiopos));
                        player.setOnCompletionListener(this);

                        new Timer().schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        player.start();
                                        gdRabbit.start();
                                    }
                                }, 1000);
                    }
                }
            };
            player.setOnCompletionListener(listener);
            player.start();
            gdRabbit.seekToFrame(0);
            gdRabbit.start();
        }
    }
}