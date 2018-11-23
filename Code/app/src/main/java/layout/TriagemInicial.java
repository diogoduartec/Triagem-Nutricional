package layout;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.github.aakira.expandablelayout.Utils;
import com.infokmg.hugv.triagemnutricional.MainActivity;
import com.infokmg.hugv.triagemnutricional.R;
import com.infokmg.hugv.triagemnutricional.layout.expansive.DividerItemDecoration;
import com.infokmg.hugv.triagemnutricional.layout.expansive.GRadioGroup;
import com.infokmg.hugv.triagemnutricional.layout.expansive.ItemModel;
import com.infokmg.hugv.triagemnutricional.layout.expansive.RecyclerViewRecyclerAdapter;
import com.infokmg.hugv.triagemnutricional.layout.expansive.UpdateDataListener;
import com.infokmg.hugv.triagemnutricional.layout.expansive.UpdateTriageResultListener;
import com.infokmg.hugv.triagemnutricional.model.UserData;
import com.infokmg.hugv.triagemnutricional.service.NutritionalCalculations;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TriagemInicial.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TriagemInicial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TriagemInicial extends Fragment implements UpdateDataListener, UpdateTriageResultListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM_ACTIVITY = "activityTriagem";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //workaround - score global para salvar no get data - solução provisória para não dar overflow
    private Integer SCORE_GLOBAL;

    GRadioGroup grState;
    GRadioGroup grDisease;

    /* RadioButtons from triage page - Nutritional state*/
    RadioButton rbState1;
    RadioButton rbState2;
    RadioButton rbState3;
    RadioButton rbState4;

    /* RadioButtons from triage page - Disease Risk*/
    RadioButton rbDisease1;
    RadioButton rbDisease2;
    RadioButton rbDisease3;
    RadioButton rbDisease4;

    RadioButton rbSexFemale;
    RadioButton rbSexMale;

    CheckBox checkBoxIMC;

    LinearLayout linearLayout;

    RecyclerView recyclerResults;

    private OnFragmentInteractionListener mListener;
    UpdateDataListener updateDataListener;

    public TriagemInicial() {
        // Required empty public constructor
    }

    public TriagemInicial(MainActivity mainActivity) {
        updateDataListener = mainActivity;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TriagemInicial.
     */
    // TODO: Rename and change types and number of parameters
    public static TriagemInicial newInstance(MainActivity mainActivity) {
        TriagemInicial fragment = new TriagemInicial();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_ACTIVITY, mainActivity);
        fragment.setArguments(args);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            updateDataListener = (UpdateDataListener) getArguments().getSerializable(ARG_PARAM_ACTIVITY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rbDisease1 = (RadioButton) view.findViewById(R.id.rd_triage_disease1_absent);
       bindRadios(view);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutResult);
        checkBoxIMC = (CheckBox) view.findViewById(R.id.cb_1_imc);

        /*Recycler from triage page Nutritional state - The expansive layout*/
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_triage_state1_absent);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.rv_triage_state2_low);
        recyclerView2.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        final RecyclerView recyclerView3 = (RecyclerView) view.findViewById(R.id.rv_triage_state3_medium);
        recyclerView3.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));

        final RecyclerView recyclerView4 = (RecyclerView) view.findViewById(R.id.rv_triage_state4_high);
        recyclerView4.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView4.setLayoutManager(new LinearLayoutManager(getContext()));

        /*Recycler from triage page Disease Risk - The expansive layout*/
        final RecyclerView recyclerViewDisease = (RecyclerView) view.findViewById(R.id.rv_triage_disease1_absent);
        recyclerViewDisease.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerViewDisease.setLayoutManager(new LinearLayoutManager(getContext()));

        final RecyclerView recyclerViewDisease2 = (RecyclerView) view.findViewById(R.id.rv_triage_disease2_low);
        recyclerViewDisease2.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerViewDisease2.setLayoutManager(new LinearLayoutManager(getContext()));

        final RecyclerView recyclerViewDisease3 = (RecyclerView) view.findViewById(R.id.rv_triage_disease3_medium);
        recyclerViewDisease3.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerViewDisease3.setLayoutManager(new LinearLayoutManager(getContext()));

        final RecyclerView recyclerViewDisease4 = (RecyclerView) view.findViewById(R.id.rv_triage_disease4_high);
        recyclerViewDisease4.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerViewDisease4.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerResults = (RecyclerView) view.findViewById(R.id.rv_triage_results);
        recyclerResults.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerResults.setLayoutManager(new LinearLayoutManager(getContext()));

        /*Load data to triage page Nutritional state - The expansive layout*/
        final List<ItemModel> dataState = getDataListItemModel(getResources().getString(R.string.rd_label_triage_risk_absent),
                getResources().getString(R.string.description_nutritional_state1_absent), R.color.rd_green);
        recyclerView.setAdapter(new RecyclerViewRecyclerAdapter(dataState));

        final List<ItemModel> dataState2 = getDataListItemModel(getResources().getString(R.string.rd_label_triage_risk_low_level),
                getResources().getString(R.string.description_nutritional_state2_low_level), R.color.rd_green);
        recyclerView2.setAdapter(new RecyclerViewRecyclerAdapter(dataState2));

        final List<ItemModel> dataState3 = getDataListItemModel(getResources().getString(R.string.rd_label_triage_risk_medium_level),
                getResources().getString(R.string.description_nutritional_state3_medium_level), R.color.rd_green);
        recyclerView3.setAdapter(new RecyclerViewRecyclerAdapter(dataState3));

        final List<ItemModel> dataState4 = getDataListItemModel(getResources().getString(R.string.rd_label_triage_risk_high_level),
                getResources().getString(R.string.description_nutritional_state4_high_level), R.color.rd_green);
        recyclerView4.setAdapter(new RecyclerViewRecyclerAdapter(dataState4));

        /*Load data to triage page Disease Risk - The expansive layout*/
        final List<ItemModel> dataDisease = getDataListItemModel(getResources().getString(R.string.rd_label_triage_risk_absent),
                getResources().getString(R.string.description_disease_risk1_absent), R.color.rd_green);
        recyclerViewDisease.setAdapter(new RecyclerViewRecyclerAdapter(dataDisease));

        final List<ItemModel> dataDisease2 = getDataListItemModel(getResources().getString(R.string.rd_label_triage_risk_low_level),
                getResources().getString(R.string.description_disease_risk2_low_level), R.color.rd_green);
        recyclerViewDisease2.setAdapter(new RecyclerViewRecyclerAdapter(dataDisease2));

        final List<ItemModel> dataDisease3 = getDataListItemModel(getResources().getString(R.string.rd_label_triage_risk_medium_level),
                getResources().getString(R.string.description_disease_risk3_medium_level), R.color.rd_green);
        recyclerViewDisease3.setAdapter(new RecyclerViewRecyclerAdapter(dataDisease3));

        final List<ItemModel> dataDisease4 = getDataListItemModel(getResources().getString(R.string.rd_label_triage_risk_high_level),
                getResources().getString(R.string.description_disease_risk4_high_level), R.color.rd_green);
        recyclerViewDisease4.setAdapter(new RecyclerViewRecyclerAdapter(dataDisease4));

//        setTriageChecksAndResults();
    }



    private void setTriageChecksAndResults(){
        Double hei, wei;

        try{
            hei = updateDataListener.getData().getHeigth();
            wei = updateDataListener.getData().getActualWeigth();
            Double imc = NutritionalCalculations.getIMCValue(hei, wei);
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }


        int punc = 0;
        try {
            punc = getTriagePontuation();
            SCORE_GLOBAL = punc;
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }

        final List<ItemModel> dataResults = getDataListItemModel(getTitleByPontuation(punc), getDescriptionByPontuation(punc), getColorByPontuation(punc));
        recyclerResults.setAdapter(new RecyclerViewRecyclerAdapter(dataResults));
    }

    private void bindRadios(View view){

        rbSexMale = (RadioButton) view.findViewById(R.id.rd_sex_male);
        rbSexFemale= (RadioButton) view.findViewById(R.id.rd_sex_female);

        /* RadioButtons from triage page - Nutritional state*/
        rbState1 = (RadioButton) view.findViewById(R.id.rd_triage_state1_absent);
        rbState2 = (RadioButton) view.findViewById(R.id.rd_triage_state2_low);
        rbState3 = (RadioButton) view.findViewById(R.id.rd_triage_state3_medium);
        rbState4 = (RadioButton) view.findViewById(R.id.rd_triage_state4_high);

        /* RadioButtons from triage page - Disease Risk*/
        rbDisease1 = (RadioButton) view.findViewById(R.id.rd_triage_disease1_absent);
        rbDisease2 = (RadioButton) view.findViewById(R.id.rd_triage_disease2_low);
        rbDisease3 = (RadioButton) view.findViewById(R.id.rd_triage_disease3_medium);
        rbDisease4 = (RadioButton) view.findViewById(R.id.rd_triage_disease4_high);

        /*Workaround to work with RadioButtons instead of radio groups*/
        grState = new GRadioGroup(rbState1, rbState2, rbState3, rbState4);
        grDisease = new GRadioGroup(rbDisease1, rbDisease2, rbDisease3, rbDisease4);
        CompoundButton.OnCheckedChangeListener onChangeCheckRd = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setTriageChecksAndResults();
            }
        };

        rbState1.setOnCheckedChangeListener(onChangeCheckRd);
        rbState2.setOnCheckedChangeListener(onChangeCheckRd);
        rbState3.setOnCheckedChangeListener(onChangeCheckRd);
        rbState4.setOnCheckedChangeListener(onChangeCheckRd);

        rbDisease1.setOnCheckedChangeListener(onChangeCheckRd);
        rbDisease2.setOnCheckedChangeListener(onChangeCheckRd);
        rbDisease3.setOnCheckedChangeListener(onChangeCheckRd);
        rbDisease4.setOnCheckedChangeListener(onChangeCheckRd);
    }

    private int getTriagePontuation (){
        int total = grState.getCheckedRadioByOrder() + grDisease.getCheckedRadioByOrder();

        if(updateDataListener != null) {
            if (updateDataListener.getData().getAge()!= null && updateDataListener.getData().getAge() >= 70) {
                total = total + 1;
            }
        } else {
            Log.d("ERRO", "erro ao recuperar a idade");
        }
        return total;
    }

    private String getTitleByPontuation (int punctuation){
          switch (punctuation){
            case 0:
                return getResources().getString(R.string.title_punctuation_0);
            case 1:
                return getResources().getString(R.string.title_punctuation_1);
            case 2:
                return getResources().getString(R.string.title_punctuation_2);
            default:
                return "Apresenta Risco - Pontuação " + punctuation;
        }
    }

    private String getDescriptionByPontuation (int punctuation){
        switch (punctuation){
            case 0:
                return getResources().getString(R.string.description_punctuation_0);
            case 1:
                return getResources().getString(R.string.description_punctuation_1);
            case 2:
                return getResources().getString(R.string.description_punctuation_2);
            default:
                return getResources().getString(R.string.description_punctuation_3_more);
        }
    }

    private int getColorByPontuation (int punctuation){
        if(punctuation <3 ){
            linearLayout.setBackground(getResources().getDrawable(R.drawable.bg_results_good));
            return R.color.colorPrimary;
        }else {
            linearLayout.setBackground(getResources().getDrawable(R.drawable.bg_results_bad));
            return R.color.lightRed;
        }
    }

    private List<ItemModel> getDataListItemModel (String title,String description, int color){
        List<ItemModel> data = new ArrayList<>();
        data.add(new ItemModel(title, description,  color, color, Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR)));
        return data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_triagem_inicial, container, false);
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
        data.setNutritionalState(grState.getCheckedRadioByOrder());
        data.setDiseaseRisk(grDisease.getCheckedRadioByOrder());
        if(rbSexMale.isChecked()){
            data.setSex("M");
        } else if(rbSexFemale.isChecked()) {
            data.setSex("F");
        }else{
            data.setSex("-");
        }

        try{
            data.setScore(SCORE_GLOBAL);
        }catch(Exception ex){
            Log.d("Pontuacao", ex.toString());
        }
        return data;
    }

    @Override
    public void updateTriageResults() {
        setTriageChecksAndResults();
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
