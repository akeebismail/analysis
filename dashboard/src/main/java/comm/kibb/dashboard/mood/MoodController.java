package comm.kibb.dashboard.mood;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class MoodController {
    @FXML private PieChart overallMethod;

    public void setData(MoodChartData data){
        overallMethod.setData(data.getPieChartData());
    }
}
