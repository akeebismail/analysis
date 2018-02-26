package comm.kibb.dashboard.user;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;


public class LeaderBoardController {
    @FXML private TableView<TwitterUser> leaders;

    public void setData(LeaderBoardData data){
        leaders.setItems(data.getItems());
    }
}
