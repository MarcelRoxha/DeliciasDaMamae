package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.CaixaLojaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.FinanceiroFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.ReceitaNovaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.ReceitasProntasFragment;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.VitrineFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


public class LojaActivityV2 extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_loja_v2);

        smartTabLayout = findViewById(R.id.smartTabLoja);
        viewPager = findViewById(R.id.viewPagerLoja);

        getSupportActionBar().setElevation(0);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("CAIXA", CaixaLojaFragment.class)
                        .add("VITRINE", VitrineFragment.class)
                        .add("FINANCEIRO", FinanceiroFragment.class)
                        .create()
        );
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

    }
}