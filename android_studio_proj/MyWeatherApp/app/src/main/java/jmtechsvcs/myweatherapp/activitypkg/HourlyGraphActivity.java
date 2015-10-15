package jmtechsvcs.myweatherapp.activitypkg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import jmtechsvcs.myweatherapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

//https://github.com/jjoe64/GraphView
//https://github.com/jjoe64/GraphView-Demos
public class HourlyGraphActivity extends AppCompatActivity
{
    private static final String LOGTAG = "HourlyGraphActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_graph);

        Log.d(LOGTAG,"inside the graph view activity");

        GraphView graph = (GraphView)findViewById(R.id.graphViewId);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 3),
                new DataPoint(1, 3),
                new DataPoint(2, 6),
                new DataPoint(3, 2),
                new DataPoint(4, 5)
        });
        graph.addSeries(series2);

        // legend
        series.setTitle("foo");
        series2.setTitle("bar");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
    }
}
