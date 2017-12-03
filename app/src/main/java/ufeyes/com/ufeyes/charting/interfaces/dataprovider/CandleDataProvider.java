package ufeyes.com.ufeyes.charting.interfaces.dataprovider;

import ufeyes.com.ufeyes.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
