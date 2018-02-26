package comm.kibb.dashboard.mood;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MoodsParser {

    static TweetMood parse(String moodAsCsv){
        Set<Mood> moodSet = Stream.of(moodAsCsv.split(","))
                .filter(s -> !s.isEmpty()).map(Mood::valueOf).collect(Collectors.toSet());
        return new TweetMood(moodSet);
    }
}
