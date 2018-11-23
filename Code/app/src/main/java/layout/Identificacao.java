package layout;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.infokmg.hugv.triagemnutricional.MainActivity;
import com.infokmg.hugv.triagemnutricional.R;
import com.infokmg.hugv.triagemnutricional.layout.expansive.UpdateDataListener;
import com.infokmg.hugv.triagemnutricional.model.UserData;
import com.infokmg.hugv.triagemnutricional.service.NutritionalCalculations;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Identificacao.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Identificacao#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Identificacao extends Fragment implements UpdateDataListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM_ACTIVITY = "mainActivity";

    TextInputLayout tilName;
    TextInputLayout tilRegister;
    TextInputLayout tilComorb;
    TextInputLayout tilDoctorDiagnostic;
    TextInputLayout tilClinic;
    TextInputLayout tilRoom;
    TextInputLayout tilActualWeight;
    TextInputLayout tivUsualWeight;
    TextInputLayout tilAge;
    TextInputLayout tilHeight;
    TextInputLayout tilIMC;
    TextInputLayout tilIMCDiagnostic;

    UpdateDataListener updateDataListener;

    EditText edName;
    EditText edRegister;
    EditText edComorb;
    EditText edDoctorDiagnostic;
    EditText edClinic;
    EditText edRoom;
    EditText edActualWeight;
    EditText edUsualWeight;
    EditText edHeight;
    EditText edAge;
    EditText edIMC;
    EditText edIMCDiagnostic;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Identificacao() {
        // Required empty public constructor
    }

    public Identificacao(MainActivity mainActivity) {
        updateDataListener = mainActivity;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mainActivity Parameter to get the main activity, used to synchronize data between fragments.
     * @return A new instance of fragment Identificacao.
     */
    // TODO: Rename and change types and number of parameters
    public static Identificacao newInstance(MainActivity mainActivity) {
        Identificacao fragment = new Identificacao();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_ACTIVITY, mainActivity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            updateDataListener = (UpdateDataListener) getArguments().getSerializable(ARG_PARAM_ACTIVITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_identificacao, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilName = (TextInputLayout) view.findViewById(R.id.tiv_patient);
        tilRegister = (TextInputLayout) view.findViewById(R.id.tiv_id_number);
        tilComorb = (TextInputLayout) view.findViewById(R.id.tiv_comorb);
        tilDoctorDiagnostic = (TextInputLayout) view.findViewById(R.id.tiv_doctor_diagnostic);
        tilClinic = (TextInputLayout) view.findViewById(R.id.tiv_clinic);
        tilRoom = (TextInputLayout) view.findViewById(R.id.tiv_room);
        tivUsualWeight = (TextInputLayout) view.findViewById(R.id.tiv_usual_weight);
        tilActualWeight = (TextInputLayout) view.findViewById(R.id.tiv_actual_weight);
        tilAge = (TextInputLayout) view.findViewById(R.id.tiv_age);
        tilHeight = (TextInputLayout) view.findViewById(R.id.tiv_height);
        tilIMC = (TextInputLayout) view.findViewById(R.id.tiv_imc);
        tilIMCDiagnostic = (TextInputLayout) view.findViewById(R.id.tiv_diag_nutri);

        edName = tilName.getEditText();
        edRegister = tilRegister.getEditText();
        edComorb = tilComorb.getEditText();
        edDoctorDiagnostic = tilDoctorDiagnostic.getEditText();
        edClinic = tilClinic.getEditText();
        edRoom = tilRoom.getEditText();
        edActualWeight = tilActualWeight.getEditText();
        edUsualWeight = tivUsualWeight.getEditText();
        edHeight = tilHeight.getEditText();
        edAge = tilAge.getEditText();
        edIMC = tilIMC.getEditText();
        edIMCDiagnostic = tilIMCDiagnostic.getEditText();

        edActualWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                updateIMC();
            }
        });

        edHeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                updateIMC();
            }
        });

    }

    private void updateIMC(){
        Double hei = null;
        Double wei = null;
        try {
            hei = Double.parseDouble(edHeight.getText().toString());
            wei = Double.parseDouble(edActualWeight.getText().toString());
        } catch (NumberFormatException nfe){
            Log.i("RUNTIME_EXECPTION", nfe.getMessage());
        }
        Double imc = NutritionalCalculations.getIMCValue(hei, wei);
        if (imc != null){
            edIMC.setText(String.format("%.2f", imc));
            edIMCDiagnostic.setText(NutritionalCalculations.getIMCDiagnostic(imc).toString());
        }
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
    public UserData getData() {
        UserData data = new UserData();
        try {
            data.setName(edName.getText().toString().equals("") ? null : edName.getText().toString());
            data.setRegister(edRegister.getText().toString().equals("") ? null : edRegister.getText().toString());
            data.setCoMorb(edComorb.getText().toString().equals("") ? null : edComorb.getText().toString());
            data.setMedicalDiegnostic(edDoctorDiagnostic.getText().toString().equals("") ? null : edDoctorDiagnostic.getText().toString());
            data.setClinic(edClinic.getText().toString().equals("") ? null : edClinic.getText().toString());
            data.setRoom(edRoom.getText().toString().equals("") ? null : Integer.parseInt(edRoom.getText().toString()));
            data.setActualWeigth(edActualWeight.getText().toString().equals("") ? null : Double.parseDouble(edActualWeight.getText().toString()));
            data.setUsualWeigth(edUsualWeight.getText().toString().equals("") ? null : Double.parseDouble(edUsualWeight.getText().toString()));
            data.setHeigth(edHeight.getText().toString().equals("") ? null : Double.parseDouble(edHeight.getText().toString()));
            data.setAge(edAge.getText().toString().equals("") ? null : Integer.parseInt(edAge.getText().toString()));
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }
        return data;
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
