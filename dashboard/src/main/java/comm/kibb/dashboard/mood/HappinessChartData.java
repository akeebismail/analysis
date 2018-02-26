package comm.kibb.dashboard.mood;

import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static java.time.LocalTime.now;

public class HappinessChartData implements MessageListener<TweetMood> {

    private final XYChart.Series<String ,Double> dataSeries = XYChart.Series<>();
    private final Map<Integer, Integer> minuteToDataPosition = new HashMap<>();

    public HappinessChartData(){

        // get minute value for right now
        int nowMinute = LocalDateTime.now().getMinute();

        // create an empty bar for every minute for the next ten minutes
        IntStream.range(nowMinute, nowMinute +10).forEach(this::initialiseBarToZero);
    }
    @Override
    public void onMessage(TweetMood message){
        if (message.isHappy()){
            int x = now().getMinute();

            Integer dataIndex = minuteToDataPosition.get(x);
            XYChart.Data<String, Double> barForNow = dataSeries.getData().get(dataIndex);
            barForNow.setXValue(barForNow.getXValue() +1);
        }
    }

    public XYChart.Series<String, Double> getDataSeries() {
        return dataSeries;
    }


    private void initialiseBarToZero(int minute) {
        dataSeries.getData().add(new XYChart.Data<>(String.valueOf(minute), 0.0));
        minuteToDataPosition.put(minute, dataSeries.getData().size() - 1);
    }

}
