package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.marcel.a.n.roxha.deliciasdamamae.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinanceiroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinanceiroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //Componentes de tela
    public AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario;
    private RecyclerView recyclerView_relatorios_mensal;
    private RecyclerView recyclerView_relatorios_Diario;



    //Variaveis
    private String valorQueOCaixaIniciouODia = "0";
    private String totalDeVendasNoCaixaHoje = "0";
    private String totalDeVendasNoIfood = "0";
    private String totalDeVendasEmGeral = "0";
    private String totalDeVendasEmDinheiro = "0";
    private String totalDeSaidaCaixaHoje = "0";
    private String totalDeVendasNoCreditoHoje = "0";
    private String totalDeVendasNoDebitoNoCaixHoje = "0";


    public FinanceiroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinanceiroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinanceiroFragment newInstance(String param1, String param2) {
        FinanceiroFragment fragment = new FinanceiroFragment();
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

        BarChart barChart;
        BarData barData;
        BarDataSet barDataSet;

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_financeiro, container, false);
    }
}