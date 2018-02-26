package comm.kibb.dashboard.mood;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.String.format;
import static java.lang.Integer.valueOf;

public class HappinessController implements Initializable {
    @FXML private NumberAxis yAxis;
    @FXML private BarChart<String,Double> happinessOvertime;

    public void setData(HappinessChartData data){
        happinessOvertime.getData().add(data.getDataSeries());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        yAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return format("%.0f",object);
            }

            @Override
            public Number fromString(String string) {
                return valueOf(string);
            }
        });

    }
}
