package samuelpedro.moost.fragmento.graficos;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import samuelpedro.moost.fragmento.graficos.GraficoDespesas;
import samuelpedro.moost.fragmento.SectionsPageAdapter;


public class GraficosMoost extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(samuelpedro.moost.R.layout.activity_graficos_moost);
        mSectionsPageAdapter = new SectionsPageAdapter( getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(samuelpedro.moost.R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(samuelpedro.moost.R.id.graficos);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new GraficoDespesas(), "Despesas");
        adapter.addFragment(new GraficoReceitas(), "Receitas");
        adapter.addFragment(new GraficoBalanco(), "Balanço");

        viewPager.setAdapter(adapter);
    }

}
