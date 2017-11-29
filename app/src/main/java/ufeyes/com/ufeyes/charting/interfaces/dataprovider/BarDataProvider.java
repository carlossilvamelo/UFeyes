package ufeyes.com.ufeyes.charting.interfaces.dataprovider;

import ufeyes.com.ufeyes.charting.data.BarData;

public interface BarDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BarData getBarData();
    boolean isDrawBarShadowEnabled();
    boolean isDrawValueAboveBarEnabled();
    boolean isHighlightFullBarEnabled();
}
