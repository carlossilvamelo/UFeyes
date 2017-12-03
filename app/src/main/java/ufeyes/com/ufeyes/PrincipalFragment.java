package ufeyes.com.ufeyes;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Date;

import ufeyes.com.ufeyes.dataLayer.OccurrenceDAO;
import ufeyes.com.ufeyes.domain.Assalt;
import ufeyes.com.ufeyes.domain.CarBreakIn;
import ufeyes.com.ufeyes.domain.Localization;
import ufeyes.com.ufeyes.domain.Ocorrencia;
import ufeyes.com.ufeyes.domain.User;
import ufeyes.com.ufeyes.domain.UserA;
import ufeyes.com.ufeyes.enumerations.EnumUserCondition;
import ufeyes.com.ufeyes.domain.Vandalism;
import ufeyes.com.ufeyes.serviceLayer.InsertRequestService;
import ufeyes.com.ufeyes.serviceLayer.LocationService;
import ufeyes.com.ufeyes.utils.UsuarioLogado;
import ufeyes.com.ufeyes.utils.TimestampManager;


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

    FloatingActionButton fab, fab1, fab2, fab3;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    View fabBGLayout;
    boolean isFABOpen=false;
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

    private static AlertDialog alert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        ImageButton botaoDenunciar = (ImageButton) view.findViewById(R.id.button_denuncia);

        botaoDenunciar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                pegarOrientacao();

                LayoutInflater li = getActivity().getLayoutInflater();
                View view = li.inflate(R.layout.confirmation_layout, null);



                view.findViewById(R.id.btCancelar).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alert.dismiss();
                    }

                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmação de denúncia");
                builder.setView(view);
                alert = builder.create();
                alert.show();

                return true;
            }
        });

        fabLayout1= (LinearLayout) view.findViewById(R.id.fabLayout1);
        fabLayout2= (LinearLayout) view.findViewById(R.id.fabLayout2);
        fabLayout3= (LinearLayout) view.findViewById(R.id.fabLayout3);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2= (FloatingActionButton) view.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) view.findViewById(R.id.fab3);
        fabBGLayout = view.findViewById(R.id.fabBGLayout);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                pegarOrientacao();

                LayoutInflater li = getActivity().getLayoutInflater();
                View view = li.inflate(R.layout.confirmation_layout, null);

                Button btConfirm = (Button) view.findViewById(R.id.btConfirmar);
                btConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.assalto, 0, 0, 0);
                btConfirm.setText("Confirmar Assalto");
                view.findViewById(R.id.btCancelar).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alert.dismiss();
                    }

                });

                view.findViewById(R.id.btConfirmar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String type = "Assalto";
                        confirmarDenuncia(type);
                        alert.dismiss();
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmação de denúncia");
                builder.setView(view);
                alert = builder.create();
                alert.show();

            }

        });
        fab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pegarOrientacao();

                LayoutInflater li = getActivity().getLayoutInflater();
                View view = li.inflate(R.layout.confirmation_layout, null);

                Button btConfirm = (Button) view.findViewById(R.id.btConfirmar);
                btConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrombamento_carro, 0, 0, 0);
                btConfirm.setText("Confirmar Arrombamento");

                view.findViewById(R.id.btCancelar).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alert.dismiss();
                    }

                });

                view.findViewById(R.id.btConfirmar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String type = "Arrombamento";
                        confirmarDenuncia(type);
                        alert.dismiss();
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmação de denúncia");
                builder.setView(view);
                alert = builder.create();
                alert.show();

            }

        });
        fab3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                pegarOrientacao();

                LayoutInflater li = getActivity().getLayoutInflater();
                View view = li.inflate(R.layout.confirmation_layout, null);

                Button btConfirm = (Button) view.findViewById(R.id.btConfirmar);
                btConfirm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vandalismo, 0, 0, 0);
                btConfirm.setText("Confirmar Vandalismo");

                view.findViewById(R.id.btCancelar).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        alert.dismiss();
                    }

                });

                view.findViewById(R.id.btConfirmar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String type = "Vandalismo";
                        confirmarDenuncia(type);
                        alert.dismiss();
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmação de denúncia");
                builder.setView(view);
                alert = builder.create();
                alert.show();
            }

        });

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });

        return view;
    }

    public void confirmarDenuncia(String type){
        //setando usuário
        User newUser = new User();
        newUser.setCondition(EnumUserCondition.WITNESS.ordinal());
        newUser.setIdUser("ID" + TimestampManager.getTimeStamp());

        //obtendo localização
        LocationService locationService = new LocationService(getActivity());
        locationService.pegarOrientacao();
        Localization newLocalization = new Localization();
        newLocalization.setLatitude(locationService.getLatitude());
        newLocalization.setLongitude(locationService.getLongitude());
        newLocalization.setIdLocalizacao("ID" + TimestampManager.getTimeStamp());

        InsertRequestService insertRequestService = new InsertRequestService();
        switch (type){
            case "Assalto":
                Assalt newAssalt = new Assalt();
                newAssalt.setId(TimestampManager.getTimeStamp());
                newAssalt.setUsuario(newUser);
                newAssalt.setLocalizacao(newLocalization);

                //enviando requisição
                insertRequestService.insertAssaltEntity(newAssalt);
                break;
            case "Arrombamento":
                //setando arrombamento
                CarBreakIn newCarBreakIn = new CarBreakIn();
                newCarBreakIn.setId(new Timestamp(System.currentTimeMillis()).toString());
                newCarBreakIn.setUsuario(newUser);
                newCarBreakIn.setLocalizacao(newLocalization);

                //enviando requisição
                insertRequestService.insertCarBreakInEntity(newCarBreakIn);
                break;
            case "Vandalismo":
                //setando assalto
                Vandalism newVandalism = new Vandalism();
                newVandalism.setId(new Timestamp(System.currentTimeMillis()).toString());
                newVandalism.setUsuario(newUser);
                newVandalism.setLocalizacao(newLocalization);

                //enviando requisição
                insertRequestService.insertVandalismEntity(newVandalism);
                break;
            default:
                break;
        }

        UsuarioLogado user = UsuarioLogado.getInstance("0001", getContext());

        //UserA usuarioLogado = user.getUser();
        UserA usuarioLogado = new UserA("19281","Usuario Anonimo","Nao definido" );
        //cadastra uma nova ocorrencia no bd
        String time  = new Date().toString();
        Log.d("Data da localizacao ", time);
        Log.d("Usuario logado ", usuarioLogado.getNome());

        OccurrenceDAO Odao = new OccurrenceDAO(getContext());
        boolean sucessOccurrenc = Odao.salvar(time, latitude, longitude, type, usuarioLogado.getId(), time);
        if(sucessOccurrenc){
            System.out.println("Ocorrência inserida com sucesso: " + type);
        }

    }

    private void enviarOcorrencia(Ocorrencia oc){
        UsuarioLogado user = UsuarioLogado.getInstance("0001", getContext());

        UserA usuarioLogado = user.getUser();

        //cadastra uma nova ocorrencia no bd
        String time  = new Date().toString();
        Log.d("Data da localizacao ", time);
        Log.d("Usuario logado ", usuarioLogado.getNome());


        OccurrenceDAO Odao = new OccurrenceDAO(getContext());
        boolean sucessOccurrenc = Odao.salvar(time, latitude, longitude, "Vandalismo", usuarioLogado.getId(), time);
        if(sucessOccurrenc){
            System.out.println("Success occurrence vandalism");
        }
    }

    private void showFABMenu(){
        isFABOpen=true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab.animate().rotationBy(-180);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                    fabBGLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void onBackPressed() {
        if(isFABOpen){
            closeFABMenu();
        }else{
            getActivity().onBackPressed();
        }
    }


    public void showConfirmation(Context context) {
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

    public void botao() {

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

        // tvLatitude = (TextView) getActivity().findViewById(R.id.tvLatitude);
        //  tvLongitude = (TextView) getActivity().findViewById(R.id.tvLongitude);


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

            // tvLatitude.setText("Latitude: " + latitude);
            // tvLongitude.setText("Longitude: " + longitude);
        }


    }


}
