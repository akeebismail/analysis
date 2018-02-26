package comm.kibb.dashboard;

import comm.kibb.dashboard.mood.HappinessController;
import comm.kibb.dashboard.mood.MoodController;
import comm.kibb.dashboard.user.LeaderBoardController;
import comm.kibb.dashboard.user.LeaderBoardData;
import javafx.fxml.FXML;

public class DashboardController {
    @FXML private MoodController moodController;
    @FXML private HappinessController happinessController;
    @FXML private LeaderBoardController leaderBoardController;

    public MoodController getMoodController() {
        return moodController;
    }

    public HappinessController getHappinessController() {
        return happinessController;
    }

    public LeaderBoardController getLeaderBoardController() {
        return leaderBoardController;
    }
}
