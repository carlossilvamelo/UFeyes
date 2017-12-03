package ufeyes.com.ufeyes.charting.interfaces.dataprovider;

import ufeyes.com.ufeyes.charting.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
