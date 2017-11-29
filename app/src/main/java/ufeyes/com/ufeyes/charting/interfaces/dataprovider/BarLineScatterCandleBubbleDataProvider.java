package ufeyes.com.ufeyes.charting.interfaces.dataprovider;

import ufeyes.com.ufeyes.charting.components.YAxis.AxisDependency;
import ufeyes.com.ufeyes.charting.data.BarLineScatterCandleBubbleData;
import ufeyes.com.ufeyes.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
