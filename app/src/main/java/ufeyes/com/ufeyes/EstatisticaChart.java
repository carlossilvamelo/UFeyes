package ufeyes.com.ufeyes;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ufeyes.com.ufeyes.charting.animation.Easing;
import ufeyes.com.ufeyes.charting.charts.PieChart;
import ufeyes.com.ufeyes.charting.components.Legend;
import ufeyes.com.ufeyes.charting.data.PieData;
import ufeyes.com.ufeyes.charting.data.PieDataSet;
import ufeyes.com.ufeyes.charting.data.PieEntry;
import ufeyes.com.ufeyes.charting.formatter.PercentFormatter;
import ufeyes.com.ufeyes.charting.utils.ColorTemplate;
import ufeyes.com.ufeyes.charting.utils.MPPointF;
import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Vandalism;
import ufeyes.com.ufeyes.serviceLayer.Listeners.IRequestOcorrenceListener;
import ufeyes.com.ufeyes.serviceLayer.QueryRequestServiceAssalt;
import ufeyes.com.ufeyes.serviceLayer.QueryRequestServiceCarBreakIn;
import ufeyes.com.ufeyes.serviceLayer.QueryRequestServiceVandalism;
import ufeyes.com.ufeyes.utils.TimestampManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EstatisticaChart.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EstatisticaChart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstatisticaChart extends Fragment implements IRequestOcorrenceListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static PieChart mChart;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static TextView porcentAssalt;
    private static TextView porcentCarBreakIn;
    private static TextView porcentVandalism;
    private static TextView tvCarBreakInPeriod;
    private static TextView tvVandalismPeriod;
    private static TextView tvAssaltPeriod;
    private View fragmentView;
    private static float totalOccorrences, numVandalism, numCarBreakIn, numAssalt;
    private static int barrier = 0;
    private static boolean end = true;
    private static List<Assalt> listAssalt;
    private static List<Vandalism> listVandalism;
    private static List<CarBreakIn> listCarBreakIn;

    private OnFragmentInteractionListener mListener;

    public EstatisticaChart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstatisticaChart.
     */
    // TODO: Rename and change types and number of parameters
    public static EstatisticaChart newInstance(String param1, String param2) {
        EstatisticaChart fragment = new EstatisticaChart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QueryRequestServiceAssalt queryRequestServiceAssalt = new QueryRequestServiceAssalt();
        QueryRequestServiceVandalism queryRequestServiceVandalism = new QueryRequestServiceVandalism();
        QueryRequestServiceCarBreakIn queryRequestServiceCarBreakIn = new QueryRequestServiceCarBreakIn();
        queryRequestServiceVandalism.getAllVandalism();
        queryRequestServiceAssalt.getAllAssalt();
        queryRequestServiceCarBreakIn.getAllCarBreakIn();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estatistica_chart, container, false);
        mChart = (PieChart) view.findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        // mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText(generateCenterSpannableText());//texto do centro

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);//backgrownd do circulo

        mChart.setTransparentCircleColor(Color.GRAY);//borda do circulo
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(40f);
        mChart.setTransparentCircleRadius(42f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // add a selection listener
        //  mChart.setOnChartValueSelectedListener(getActivity().getApplicationContext());

        //tempo da animação inicial
        mChart.animateY(2000, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);


        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(50f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextSize(15f);

        // labels do grafico
        mChart.setEntryLabelColor(Color.BLACK);
        //  mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(15f);




        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void attPercentOccurrences(){
        DecimalFormat df = new DecimalFormat("0.00");
        while(barrier != 3){

        }
        barrier =0;
        setData(3, 100);

    }




    @Override
    public void resultListenerVandalism(List<Vandalism> vandalism) {
        listVandalism = vandalism;
        numVandalism =  listVandalism.size();
        Vandalism v = vandalism.get(0);

        Log.i("objVandalism",v.getUsuario().getIdUser()+" ");
        Log.i("objVandalism",v.getLocalizacao().getLatitude()+" ");
        Log.i("objAssalt",v.getThugList().size()+" ");
        barrier++;
    }

    @Override
    public void resultListenerAssalt(List<Assalt> assalt) {
        listAssalt = assalt;
        numAssalt = listAssalt.size();
        Log.i("objAssalt",assalt.get(0).getUsuario().getIdUser()+" ");
        Log.i("objAssalt",assalt.get(0).getLocalizacao().getLatitude()+" ");
        Log.i("objAssalt",assalt.get(0).getThugList().size()+" ");
        barrier++;
    }

    @Override
    public void resultListenerCarBreakIn(List<CarBreakIn> carBreakIn) {
        listCarBreakIn = carBreakIn;
        numCarBreakIn = listCarBreakIn.size();
        barrier++;
        attPercentOccurrences();
        Log.i("objCarBreakIn",carBreakIn.get(0).getUsuario().getIdUser()+" ");
        Log.i("objCarBreakIn",carBreakIn.get(0).getLocalizacao().getLatitude()+" ");
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        //    for (int i = 0; i < count ; i++) {
       Log.i("resultado",""+numAssalt+" "+numCarBreakIn+" "+numVandalism);

        entries.add(new PieEntry((float) numAssalt,
                "Assalto",
                null));

        entries.add(new PieEntry((float)numVandalism,
                "Vandalismo",
                null));

        entries.add(new PieEntry((float)numCarBreakIn,
                "Arrombamento de carro",
                null));
        //   }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(18f);
        data.setValueTextColor(Color.BLACK);
      //  data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {
        String value = "Estatística \nda \nviolência";
        SpannableString s = new SpannableString(value);
        s.setSpan(new RelativeSizeSpan(2f), 0,value.length(), 0);
        return s;
    }


}
