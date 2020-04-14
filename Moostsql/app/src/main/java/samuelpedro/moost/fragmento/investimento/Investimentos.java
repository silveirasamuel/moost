package samuelpedro.moost.fragmento.investimento;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import samuelpedro.moost.fragmento.SectionsPageAdapter;

public class Investimentos extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(samuelpedro.moost.R.layout.activity_investimentos);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager)findViewById(samuelpedro.moost.R.id.containerInvestimentos);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(samuelpedro.moost.R.id.investimentos);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private  void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Poupanca(), "Poupança");
        //adapter.addFragment(new LFT(), "LFT");
        adapter.addFragment(new CDB(), "CDB");
        adapter.addFragment(new LCI(), "LCI");

        viewPager.setAdapter(adapter);
    }


}
