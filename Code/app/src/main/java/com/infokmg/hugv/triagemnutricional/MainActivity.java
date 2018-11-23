package com.infokmg.hugv.triagemnutricional;

import android.app.usage.UsageEvents;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.infokmg.hugv.triagemnutricional.layout.expansive.UpdateDataListener;
import com.infokmg.hugv.triagemnutricional.layout.expansive.UpdateTriageResultListener;
import com.infokmg.hugv.triagemnutricional.model.UserData;

import java.io.Serializable;
import java.util.Date;

import layout.Antropometria;
import layout.Identificacao;
import layout.TriagemInicial;

public class MainActivity extends AppCompatActivity implements Identificacao.OnFragmentInteractionListener, TriagemInicial.OnFragmentInteractionListener, Antropometria.OnFragmentInteractionListener, UpdateDataListener, Serializable {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    UpdateDataListener dataListenerIdentificacao, dataListenerTriagem;
    UpdateTriageResultListener updateTriageResultListener;
    int lastPageViewer = 0;
    String idNutri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Bundle extra = getIntent().getExtras();
        idNutri = extra.getString("id");

        //Toast.makeText(MainActivity.this, "id: "+idNutri, Toast.LENGTH_LONG).show();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    updateTriageResultListener.updateTriageResults();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("FRAGMENTS", "implementacao on fragment na main");
    }

    @Override
    public UserData getData() {
        UserData userData =  new UserData();
        if(dataListenerIdentificacao != null){
            UserData idData = dataListenerIdentificacao.getData();
            userData.setName(idData.getName());
            userData.setRegister(idData.getRegister());
            userData.setCoMorb(idData.getCoMorb());
            userData.setMedicalDiegnostic(idData.getMedicalDiegnostic());
            userData.setClinic(idData.getClinic());
            userData.setRoom(idData.getRoom());
            userData.setActualWeigth(idData.getActualWeigth());
            userData.setUsualWeigth(idData.getUsualWeigth());
            userData.setHeigth(idData.getHeigth());
            userData.setAge(idData.getAge());
            userData.setDateCreated(new Date());
         } else{
            Log.d("MAIN", "dataListenerIdentificacao - nulo");
        }

        if(dataListenerTriagem != null){
            UserData idDataT = dataListenerTriagem.getData();
            userData.setNutritionalState(idDataT.getNutritionalState());
            userData.setDiseaseRisk(idDataT.getDiseaseRisk());
            userData.setSex(idDataT.getSex());
            userData.setScore(idDataT.getScore());
        }else{
            Log.d("MAIN", "dataListenerTriagem - nulo");
        }

        return userData;
    }




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    Identificacao id = new Identificacao(MainActivity.this);
                    dataListenerIdentificacao = id;
                    return id;
                case 1:
                    TriagemInicial tr = new TriagemInicial(MainActivity.this);
                    dataListenerTriagem = tr;
                    updateTriageResultListener = tr;
                    return tr;
                case 2:
                    return new Antropometria(MainActivity.this, dataListenerIdentificacao.getData(), idNutri);
            }
            return new Identificacao();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "IDENTIFICAÇÃO";
                case 1:
                    return "TRIAGEM";
                case 2:
                    return "FINALIZAR";
            }
            return null;
        }

    }
}
