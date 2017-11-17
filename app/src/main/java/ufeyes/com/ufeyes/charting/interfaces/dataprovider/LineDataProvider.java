package ufeyes.com.ufeyes.charting.interfaces.dataprovider;

import ufeyes.com.ufeyes.charting.components.YAxis;
import ufeyes.com.ufeyes.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
