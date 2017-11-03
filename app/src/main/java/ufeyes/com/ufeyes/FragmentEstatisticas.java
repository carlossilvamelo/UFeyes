package ufeyes.com.ufeyes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ufeyes.com.ufeyes.serviceLayer.Listeners.IRequestOcorrenceListener;
import ufeyes.com.ufeyes.serviceLayer.QueryRequestService;
import ufeyes.com.ufeyes.utils.DateTime;
import ufeyes.com.ufeyes.utils.ParseContextElement;
import ufeyes.com.ufeyes.utils.ContextElement;
import ufeyes.com.ufeyes.utils.TimestampManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentEstatisticas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentEstatisticas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEstatisticas extends Fragment implements Observer, IRequestOcorrenceListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Observable observableRequest;
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

    private OnFragmentInteractionListener mListener;

    public FragmentEstatisticas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEstatisticas.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEstatisticas newInstance(String param1, String param2) {
        FragmentEstatisticas fragment = new FragmentEstatisticas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        QueryRequestService queryRequestService = new QueryRequestService();
        queryRequestService.getAllVandalism();
        queryRequestService.getAllAssalt();
        queryRequestService.getAllCarBreakIn();






        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estatisticas, container, false);
        porcentAssalt = (TextView) view.findViewById(R.id.porcentAssalt);
        porcentCarBreakIn = (TextView) view.findViewById(R.id.porcentCarBreakIn);
        porcentVandalism = (TextView) view.findViewById(R.id.porcentVandalism);



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
    private static float totalOccorrences, numVandalism, numCarBreakIn, numAssalt;
    private static ArrayList<ContextElement> listVandalism, listAssalt, listCarBreakIn;
    private static int barrier = 0;

    @Override
    public void resultListenerVandalism(String result) {
        ArrayList<ContextElement> listVandalism = ParseContextElement.getContextResponse(result);
        numVandalism = listVandalism.size();
        barrier++;

    }

    @Override
    public void resultListenerAssalt(String result) {
        ArrayList<ContextElement> listAssalt = ParseContextElement.getContextResponse(result);
        numAssalt = listAssalt.size();
        barrier++;


    }

    @Override
    public void resultListenerCarBreakIn(String result) {
        ArrayList<ContextElement> listCarBreakIn = ParseContextElement.getContextResponse(result);
        numCarBreakIn = listCarBreakIn.size();
        barrier++;

        attPercentOcorrences();
    }

    private void attPeriodsOccorrences(){
        DateTime dateTime;
        int noite =0;
        int manha = 0;
        int tarde = 0;
        String maior="";
       if(listVandalism != null){
           for (ContextElement ce : listVandalism) {
               dateTime = TimestampManager.stringToTimestamp(ce.getId());
               if((dateTime.getHour()>5)&&(dateTime.getHour()<=12)){
                   manha++;
               }
               if((dateTime.getHour()>12)&&(dateTime.getHour()<=18)){
                    tarde++;
               }
               if((dateTime.getHour()>18)&&(dateTime.getHour()<=0)){
                    noite++;
               }
           }

           //ordenando
           if(manha>=tarde && manha>=noite)
               maior = "manhã";

           if(tarde >=manha && tarde>=noite)
               maior = "tarde";

           if(noite >=tarde && noite>=manha)
               maior = "noite";

           tvVandalismPeriod.setText("Período de maior risco: ");
       }

    }
    private void attPercentOcorrences(){
        DecimalFormat df = new DecimalFormat("0.00");
        while(barrier != 3){

        }
        barrier =0;
        totalOccorrences =numAssalt+numCarBreakIn+numVandalism;
        porcentVandalism.setText(df.format((numVandalism/totalOccorrences)*100)+"%");
        porcentCarBreakIn.setText(df.format((numCarBreakIn/totalOccorrences)*100)+"%");
        porcentAssalt.setText(df.format((numAssalt/totalOccorrences)*100)+"%");
        tvVandalismPeriod.setText("Período de maior risco: ");
        attPeriodsOccorrences();
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
    public void construtorObservable(Observable obs) {
        this.observableRequest = obs;
        obs.addObserver(this);
    }
}
