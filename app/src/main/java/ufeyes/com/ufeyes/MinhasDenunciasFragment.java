package ufeyes.com.ufeyes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Localization;
import ufeyes.com.ufeyes.domain.Ocorrencia;
import ufeyes.com.ufeyes.domain.User;
import ufeyes.com.ufeyes.domain.Vandalism;
import ufeyes.com.ufeyes.serviceLayer.Listeners.IRequestOcorrenceListener;
import ufeyes.com.ufeyes.serviceLayer.QueryRequestServiceAssalt;
import ufeyes.com.ufeyes.serviceLayer.QueryRequestServiceCarBreakIn;
import ufeyes.com.ufeyes.serviceLayer.QueryRequestServiceVandalism;
import ufeyes.com.ufeyes.utils.ArrayAdapterOccorrence;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MinhasDenunciasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MinhasDenunciasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MinhasDenunciasFragment extends Fragment implements IRequestOcorrenceListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static List<Assalt> listAssalt;
    private static List<Vandalism> listVandalism;
    private static List<CarBreakIn> listCarBreakIn;

    private static int barrier = 0;
    private static ListView lvMinhasOcorrencias = null;

    private OnFragmentInteractionListener mListener;

    public MinhasDenunciasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MinhasDenunciasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MinhasDenunciasFragment newInstance(String param1, String param2) {
        MinhasDenunciasFragment fragment = new MinhasDenunciasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        QueryRequestServiceAssalt queryRequestServiceAssalt = new QueryRequestServiceAssalt(new MinhasDenunciasFragment());
        QueryRequestServiceVandalism queryRequestServiceVandalism =
                new QueryRequestServiceVandalism(new MinhasDenunciasFragment());
        QueryRequestServiceCarBreakIn queryRequestServiceCarBreakIn =
                new QueryRequestServiceCarBreakIn(new MinhasDenunciasFragment());
        queryRequestServiceVandalism.getAllVandalism();
        queryRequestServiceAssalt.getAllAssalt();
        queryRequestServiceCarBreakIn.getAllCarBreakIn();


    }

    private static Activity act;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_minhas_denuncias, container, false);
        lvMinhasOcorrencias = (ListView) view.findViewById(R.id.lista);
        act = getActivity();


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
    public void resultListenerVandalism(List<Vandalism> vandalism) {
        listVandalism = vandalism;
        barrier++;
    }

    @Override
    public void resultListenerAssalt(List<Assalt> assalt) {
        listAssalt = assalt;
        barrier++;
    }

    @Override
    public void resultListenerCarBreakIn(List<CarBreakIn> carBreakIn) {
        listCarBreakIn = carBreakIn;
        barrier++;
        attListViewOccurrence(act);
    }

    private void attListViewOccurrence(Activity act) {
        while (barrier != 3) {

        }
        barrier = 0;
        //setar lista
        final ArrayList<Ocorrencia> ocorrenciaList = new ArrayList<Ocorrencia>();
        User user = new User();
        user.setIdUser("123");
        user.setCondition(1);

        Localization l = new Localization();
        l.setIdLocalizacao("adasd");
        l.setLatitude(5.44);
        l.setLongitude(4.13);
        l.setTimeStamp("123123");
        Assalt assalt = new Assalt();

        assalt.setUsuario(user);
        assalt.setLocalizacao(l);
        assalt.setId("asdasd");
        ocorrenciaList.add(assalt);
        //ocorrenciaList.add(new Assalt());
        // ocorrenciaList.addAll(listAssalt);
       //  ocorrenciaList.addAll(listCarBreakIn);
        for (Vandalism v: listVandalism) {
           if(v.getLocalizacao() != null)
                ocorrenciaList.add(v);
        }
        for (Assalt a: listAssalt) {
            if(a.getLocalizacao() != null)
                ocorrenciaList.add(a);
        }
        for (CarBreakIn c: listCarBreakIn) {
            if(c.getLocalizacao() != null)
                ocorrenciaList.add(c);
        }

        ArrayAdapterOccorrence adapter = new ArrayAdapterOccorrence(ocorrenciaList, act);
        lvMinhasOcorrencias.setAdapter(adapter);
      /*  lvMinhasOcorrencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EditarOcorrenciaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("occorrence",ocorrenciaList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/

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
}
