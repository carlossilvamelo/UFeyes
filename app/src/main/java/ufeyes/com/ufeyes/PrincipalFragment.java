package ufeyes.com.ufeyes;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import ufeyes.com.ufeyes.domain.Ocorrencia;
import ufeyes.com.ufeyes.serviceLayer.NotificationCreator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrincipalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrincipalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrincipalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView tvLatitude;
    private TextView tvLongitude;
    private Location location;
    private LocationManager locationManager;


    private double latitude;
    private double longitude;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PrincipalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrincipalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrincipalFragment newInstance(String param1, String param2) {
        PrincipalFragment fragment = new PrincipalFragment();
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



        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        ImageButton botaoDenunciar = (ImageButton) view.findViewById(R.id.button_denuncia);

        botaoDenunciar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView texto = (TextView) getActivity().findViewById(R.id.texto);
                texto.setText("FRAGMENT PRINCIPAL");
                pegarOrientacao();

                /// Teste de envio de notificação
                NotificationCreator notecreate = new NotificationCreator(getContext());
                Intent resultIntent = new Intent(getContext(), MainActivity.class);
                notecreate.sendNotification(getContext(), resultIntent, "Testando", "Texto da notificação",
                        001);

                // Teste de atualização
                if(getActivity() instanceof  MainActivity){
                    MainActivity mainAct = (MainActivity) getActivity();
                    MenuItem notifMenu = mainAct.getMenu().findItem(R.id.action_notification);
                    notifMenu.setIcon(R.drawable.notification_received);
                }
               // Ocorrencia
               // showConfirmation(getContext());
/////

                LayoutInflater li = getActivity().getLayoutInflater();
                View view = li.inflate(R.layout.confirmation_layout,null);

                view.findViewById(R.id.btAssalto).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view){

                    }

                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmação de denúncia");
                builder.setView(view);
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });


        return view;
    }


    public void showConfirmation(Context context){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void botao(){

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void pegarOrientacao() {

        tvLatitude = (TextView) getActivity().findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) getActivity().findViewById(R.id.tvLongitude);


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //permição em tempode xecução
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);

        } else {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location == null)
                tvLongitude.setText("location null");
        }
        if (location != null) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            tvLatitude.setText("Latitude: " + latitude);
            tvLongitude.setText("Longitude: " + longitude);
        }


    }


}
