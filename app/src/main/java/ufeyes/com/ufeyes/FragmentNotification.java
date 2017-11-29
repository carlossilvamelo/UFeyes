package ufeyes.com.ufeyes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import ufeyes.com.ufeyes.utils.ArrayAdapterOccorrence;
import ufeyes.com.ufeyes.utils.NotificationOcurrenceAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentNotification.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentNotification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNotification extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lvMinhasNotif;

    private OnFragmentInteractionListener mListener;

    public FragmentNotification() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNotification.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNotification newInstance(String param1, String param2) {
        FragmentNotification fragment = new FragmentNotification();
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



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        //mock
        Localization localization = new Localization();
        localization.setTimeStamp("timestamp");
        localization.setLatitude(5.323423);
        localization.setLongitude(5.323423);
        localization.setIdLocalizacao("id");

        User user = new User();
        user.setIdUser("userId");

        //objetos mock
        Assalt assalt = new Assalt();
        assalt.setId("timestamp");
        assalt.setUsuario(user);
        assalt.setLocalizacao(localization);

        CarBreakIn carBreakIn = new CarBreakIn();
        carBreakIn.setId("timestamp");
        carBreakIn.setUsuario(user);
        carBreakIn.setLocalizacao(localization);

        Vandalism vandalism = new Vandalism();
        vandalism.setId("timestamp");
        vandalism.setUsuario(user);
        vandalism.setLocalizacao(localization);


        final ArrayList<Ocorrencia> ocorrenciaList = new ArrayList<Ocorrencia>();
        ocorrenciaList.add(assalt);
        ocorrenciaList.add(carBreakIn);
        ocorrenciaList.add(assalt);
        ocorrenciaList.add(vandalism);
        ocorrenciaList.add(assalt);
        ocorrenciaList.add(assalt);

        lvMinhasNotif = (ListView) view.findViewById(R.id.lst_items);
        NotificationOcurrenceAdapter adapter = new NotificationOcurrenceAdapter(ocorrenciaList, getActivity());
        lvMinhasNotif.setAdapter(adapter);
        lvMinhasNotif.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EditarOcorrenciaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("occorrence",ocorrenciaList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


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
        void onFragmentInteraction(Uri uri);
    }
}
