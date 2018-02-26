package comm.kibb.dashboard.mood;

import java.util.Set;

import static comm.kibb.dashboard.mood.Mood.HAPPY;
import static comm.kibb.dashboard.mood.Mood.SAD;

public class TweetMood {

    private final Set<Mood> moods;

    public TweetMood(Set<Mood> moods){
        this.moods = moods;
    }

    public boolean isHappy(){
        return moods.contains(HAPPY);
    }

    public boolean isSad(){
        return  moods.contains(SAD);
    }

    public boolean isConfused(){
        return isHappy() && isSad();
    }

}
