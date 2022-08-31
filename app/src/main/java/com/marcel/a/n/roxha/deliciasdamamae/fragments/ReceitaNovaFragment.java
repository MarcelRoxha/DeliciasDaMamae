package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.FinalizarReceitaActivity;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdicionadoAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredientesCadastradosEmEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ItemEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ModeloReceitaCadastradaDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.IngredienteModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloIngredienteAdicionadoReceita;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceitaNovaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceitaNovaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReceitaNovaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceitaNovaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceitaNovaFragment newInstance(String param1, String param2) {
        ReceitaNovaFragment fragment = new ReceitaNovaFragment();
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
//Nova Receita:

    private RecyclerView recyclerViewListItensEstoque;
    private RecyclerView recyclerViewListItensAdicionados;
    private TextInputEditText edit_nome_receita, input_editar_nome_receita_cadastrada;
    private TextView textValorIngredientes, textoIngredientesEstoque, textoIngredientesAdicionados;
    private TextView texto_tipo_producao;
    private Button botao_jaAdicioneiIngredientes, botao_salvar_nome_receita;
    private RadioButton radioButton_massa_selecionada;
    private RadioButton radioButton_cobertura_selecionada;
    private RadioButton radioButton_receita_completa_selecionada;
    private RadioGroup radioGroup_tipo_producao;

    private ReceitaModel receitaModel = new ReceitaModel();
    private IngredienteModel ingrediente = new IngredienteModel();
    private ReceitaModel receitaModelRecuperadaParaExibirValorTotalDeIngredientes = new ReceitaModel();
    private IngredienteAdapter adapterItem;
    private IngredientesCadastradosEmEstoqueAdapter adapterIngredienteCadastradoEmEstoque;
    private IngredienteAdicionadoAdapter adapterItem_0;

    private List<Double> listValoresItensAdd = new ArrayList<>();

    private String nomeReceitaDigitado;
    private final String NOME_COLLECTION_REFERENCIA_RECEITAS_CADASTRADAS = "RECEITA_CADASTRADA";
    private final String NOME_COLLECTION_REFERENCIA_ITENS_EM_ESTOQUE = "ITEM_ESTOQUE";
    private final String NOME_COLLECTION_REFERENCIA_INGREDIENTES_TEMP = "INGREDIENTES_TEMP_ADICIONADOS";
    private String idReceita;

    private final FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    ModeloReceitaCadastradaDAO receitaCadastradaDAO;

    private CollectionReference reference = db.collection("Receitas");

    private int contItem = 0;
    private String valorAdicionado;
    private  double valorConvert;
    private  int contReceitaCompleta = 0;
    private  int contMassa = 0;
    private  int contConbertura = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =  inflater.inflate(R.layout.fragment_receita_nova, container, false);


        recyclerViewListItensEstoque = view.findViewById(R.id.recycler_fragment_itens_estoque_id);
        recyclerViewListItensAdicionados = view.findViewById(R.id.recycler_fragment_receita_nova_itens_adicionados_id);

        texto_tipo_producao = view.findViewById(R.id.texto_tipo_producao_id);

        textValorIngredientes = view.findViewById(R.id.valor_id);
        textValorIngredientes.setVisibility(View.GONE);
        botao_salvar_nome_receita = view.findViewById(R.id.botao_salvar_nome_receita_fragment_id);
        /*edit_nome_receita = view.findViewById(R.id.edit_fragment_nome_receita_id);
        edit_nome_receita.setVisibility(View.VISIBLE);*/
        botao_jaAdicioneiIngredientes = view.findViewById(R.id.btProximo_fragment_receita_nova_id);

        botao_jaAdicioneiIngredientes.setVisibility(View.GONE);

        textoIngredientesEstoque = view.findViewById(R.id.textView24);
        textoIngredientesAdicionados = view.findViewById(R.id.textView25);

        textoIngredientesEstoque.setVisibility(View.GONE);
        textoIngredientesAdicionados.setVisibility(View.GONE);
        recyclerViewListItensEstoque.setVisibility(View.GONE);
        recyclerViewListItensAdicionados.setVisibility(View.GONE);
        texto_tipo_producao.setVisibility(View.GONE);

        botao_salvar_nome_receita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertSalvarNomeReceita = new AlertDialog.Builder(getActivity());
                alertSalvarNomeReceita.setTitle("SALVAR NOME DA RECEITA");
                alertSalvarNomeReceita.setMessage("Atenção, o nome cadastrado deve ser único, por tanto não será possível salvar o mesmo nome duas vezes");
                EditText editNomeReceitaCadastrando = new EditText(getActivity());
                alertSalvarNomeReceita.setCancelable(false);
                alertSalvarNomeReceita.setView(editNomeReceitaCadastrando);

                alertSalvarNomeReceita.setPositiveButton("CADASTRA NOME", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(editNomeReceitaCadastrando.getText().toString().isEmpty()){
                            Toast.makeText(getActivity(), "Favor insira um nome", Toast.LENGTH_SHORT).show();
                        }else{
                            nomeReceitaDigitado = editNomeReceitaCadastrando.getText().toString().trim();

                            verificarDuplicidadeNomeReceita(nomeReceitaDigitado);
                        }


                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertSalvarNomeReceita.create();
                alertSalvarNomeReceita.show();
              /*  nomeReceitaDigitado = edit_nome_receita.getText().toString().trim();
                if (!nomeReceitaDigitado.isEmpty()) {



                     }else {
                    Toast.makeText(getActivity(), "Favor insira o nome da Receita", Toast.LENGTH_SHORT).show();

                }
*/
            }
        });


        botao_jaAdicioneiIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Pssou pelo alert inicio");
                String valorDosIngredientesAdicionados = textValorIngredientes.getText().toString().trim();
                System.out.println("valorDosIngredientes: " + valorDosIngredientesAdicionados);
                if(valorDosIngredientesAdicionados.equals("0.0") || valorDosIngredientesAdicionados.equals("0")){
                    System.out.println("entrou na condição zero ingredientes");
                    AlertDialog.Builder alertZeroIngredientes = new AlertDialog.Builder(getActivity());
                    alertZeroIngredientes.setTitle("INGREDIENTES 0");
                    alertZeroIngredientes.setMessage("Atenção, para cadastrar a receita "+ nomeReceitaDigitado +" é necessário acrescentar ao menos 1 ingrediente, insira os ingrediente(s) e tente novamente");
                    alertZeroIngredientes.setCancelable(false);

                    alertZeroIngredientes.setNeutralButton("OK,ENTENDI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), "Insira os ingredientes para continuar", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alertZeroIngredientes.create();
                    alertZeroIngredientes.show();

                }else{
                    Intent intent = new Intent(getActivity(), FinalizarReceitaActivity.class);
                    intent.putExtra("idReceita", idReceita);
                    intent.putExtra("nameReceita", nomeReceitaDigitado);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

   public void carregarListaItemEstoqueDate(){

        Query query = FirebaseFirestore.getInstance().collection(NOME_COLLECTION_REFERENCIA_ITENS_EM_ESTOQUE).orderBy("nomeItemEstoque", Query.Direction.ASCENDING);



        FirestoreRecyclerOptions<ModeloItemEstoque> options = new FirestoreRecyclerOptions.Builder<ModeloItemEstoque>()
                .setQuery(query, ModeloItemEstoque.class)
                .build();

       adapterIngredienteCadastradoEmEstoque = new IngredientesCadastradosEmEstoqueAdapter(options);

       recyclerViewListItensEstoque.setHasFixedSize(true);
       recyclerViewListItensEstoque.setLayoutManager(new LinearLayoutManager(getActivity()));
       recyclerViewListItensEstoque.setAdapter(adapterIngredienteCadastradoEmEstoque);
       recyclerViewListItensEstoque.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

       adapterIngredienteCadastradoEmEstoque.setOnItemClickListerner(new IngredientesCadastradosEmEstoqueAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {



                    ModeloItemEstoque modeloItemEstoque = documentSnapshot.toObject(ModeloItemEstoque.class);

                    assert modeloItemEstoque != null;

                    String nomeItemAdd = modeloItemEstoque.getNomeItemEstoque();
                    String valorItemAdd = modeloItemEstoque.getCustoPorReceitaItemEstoque();
                    String quantItemAdd = modeloItemEstoque.getQuantidadeUtilizadaNasReceitas();
                    String unidadeMedidaUtilizadaReceita = modeloItemEstoque.getUnidadeMedidaUtilizadoNasReceitas();


                ModeloIngredienteAdicionadoReceita modeloIngredienteAdicionadoReceita = new ModeloIngredienteAdicionadoReceita();
                modeloIngredienteAdicionadoReceita.setIdReferenciaItemEmEstoque(documentSnapshot.getId());
                modeloIngredienteAdicionadoReceita.setNomeIngredienteAdicionadoReceita(nomeItemAdd);
                modeloIngredienteAdicionadoReceita.setQuantidadeUtilizadaReceita(quantItemAdd);
                modeloIngredienteAdicionadoReceita.setCustoIngredientePorReceita(valorItemAdd);
                modeloIngredienteAdicionadoReceita.setUnidadeMedidaUsadaReceita(unidadeMedidaUtilizadaReceita);
                receitaCadastradaDAO = new ModeloReceitaCadastradaDAO(getActivity());
                receitaCadastradaDAO.adicionarIngredienteDaReceitaCadastrando(nomeReceitaDigitado, modeloIngredienteAdicionadoReceita);
                String idRecuperado = receitaCadastradaDAO.getRetorneIdReceitaCadastrando();
                contItem = 1;
                atualizarValorTotalIngredientesAdicionados(nomeReceitaDigitado);

                   // adicionarIngredienteReceitaEmCadastramento(nomeReceitaDigitado, documentSnapshot.getId(), nomeItemAdd, valorItemAdd, quantItemAdd);


                }


        });



    }

    private void atualizarValorTotalIngredientesAdicionados(String nomeReceita) {

        receitaModelRecuperadaParaExibirValorTotalDeIngredientes = new ReceitaModel();
        System.out.println("Passou pelo atualizar valor total");
        System.out.println("nome recuperado: " + nomeReceita);
        FirebaseFirestore.getInstance().collection(NOME_COLLECTION_REFERENCIA_RECEITAS_CADASTRADAS).whereEqualTo("nomeReceita",nomeReceita.trim())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                System.out.println("Passou pelo queryDocumentSnapshots");
                for (DocumentSnapshot snapshot : snapshotList){
                    idReceita = snapshot.getId();
                    FirebaseFirestore.getInstance().collection(NOME_COLLECTION_REFERENCIA_RECEITAS_CADASTRADAS).document(idReceita)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            receitaModelRecuperadaParaExibirValorTotalDeIngredientes = documentSnapshot.toObject(ReceitaModel.class);
                            receitaModelRecuperadaParaExibirValorTotalDeIngredientes.getValoresIngredientes();

                            if(receitaModelRecuperadaParaExibirValorTotalDeIngredientes.getValoresIngredientes().equals("INICIANDO")){
                                atualizarValorTotalIngredientesAdicionados(nomeReceita);
                                carregarListaItensAdicionados();
                                adapterItem_0.startListening();
                            }else if(contItem < 4){
                                System.out.println("valorTotalTecuperado no for: " +  receitaModelRecuperadaParaExibirValorTotalDeIngredientes.getValoresIngredientes());
                                textValorIngredientes.setText(receitaModelRecuperadaParaExibirValorTotalDeIngredientes.getValoresIngredientes());
                                contItem++;
                                atualizarValorTotalIngredientesAdicionados(nomeReceita);

                            }/*else{
                                textValorIngredientes.setText(receitaModelRecuperadaParaExibirValorTotalDeIngredientes.getValoresIngredientes());
                                atualizarValorTotalIngredientesAdicionados(nomeReceita);

                            }*/



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void carregarListaItensAdicionados(){

        Query queryReceitaCompleta = FirebaseFirestore.getInstance().collection(NOME_COLLECTION_REFERENCIA_RECEITAS_CADASTRADAS).document(idReceita).collection("INGREDIENTES_ADICIONADOS")
                .orderBy("nomeIngredienteAdicionadoReceita", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ModeloIngredienteAdicionadoReceita> optionsReceitaCompleta = new FirestoreRecyclerOptions.Builder<ModeloIngredienteAdicionadoReceita>()
                .setQuery(queryReceitaCompleta, ModeloIngredienteAdicionadoReceita.class)
                .build();


        adapterItem_0 = new IngredienteAdicionadoAdapter(optionsReceitaCompleta);


        recyclerViewListItensAdicionados.setHasFixedSize(true);
        recyclerViewListItensAdicionados.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewListItensAdicionados.setAdapter(adapterItem_0);
        recyclerViewListItensAdicionados.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));

        adapterItem_0.setOnItemClickListerner(new ItemEstoqueAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                ModeloIngredienteAdicionadoReceita modeloIngredienteAdicionadoReceita = documentSnapshot.toObject(ModeloIngredienteAdicionadoReceita.class);

                assert modeloIngredienteAdicionadoReceita != null;

                String idIngredienteAdicionado = documentSnapshot.getId();
                String valorItemAdd = modeloIngredienteAdicionadoReceita.getCustoIngredientePorReceita();
                receitaCadastradaDAO.removerIngredienteDaReceitaCadastrando(idReceita, idIngredienteAdicionado,valorItemAdd);
                contItem = 1;
                atualizarValorTotalIngredientesAdicionados(nomeReceitaDigitado);

            }
        });
    }


    public void verificarDuplicidadeNomeReceita(String nomeDigit) {

       FirebaseFirestore.getInstance().collection(NOME_COLLECTION_REFERENCIA_RECEITAS_CADASTRADAS).whereEqualTo("nomeReceita", nomeDigit)
               .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               String verificarDupIdReceita = "";
               List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
               List<String> listaReceita = new ArrayList<>();

               for (DocumentSnapshot snapshot : snapshotList) {
                   verificarDupIdReceita = snapshot.getId();
                   listaReceita.add(snapshot.getId());
               }

               if (listaReceita.size() > 0) {
                   AlertDialog.Builder alertDuplicidadeEmReceita = new AlertDialog.Builder(getActivity());
                   alertDuplicidadeEmReceita.setTitle("NOME JÁ EXISTE");
                   alertDuplicidadeEmReceita.setMessage("Verificamos que já existe uma receita com o nome: " + nomeDigit + "\n" +
                           "O nome da receita cadastrada precisa ser único, digite aqui um nome único para sua receita e tente novamente.");
                   EditText editNomeDigitadoJaExistente = new EditText(getActivity());
                   alertDuplicidadeEmReceita.setView(editNomeDigitadoJaExistente);
                   alertDuplicidadeEmReceita.setCancelable(false);
                   editNomeDigitadoJaExistente.setText(nomeDigit);

                   alertDuplicidadeEmReceita.setNeutralButton("CONFIRMAR NOVO NOME", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                           String nomeDigitadoNovamente = editNomeDigitadoJaExistente.getText().toString();
                           verificarDuplicidadeNomeReceita(nomeDigitadoNovamente);
                       }
                   });
                   alertDuplicidadeEmReceita.create();
                   alertDuplicidadeEmReceita.show();

               }else {


                   ReceitaModel receitaModelIniciandoCadastro = new ReceitaModel();
                   receitaCadastradaDAO = new ModeloReceitaCadastradaDAO(getActivity());
                   receitaModelIniciandoCadastro.setNomeReceita(nomeDigit);
                   receitaCadastradaDAO.iniciarCadastroReceitaCadastrando(receitaModelIniciandoCadastro);


                   textoIngredientesEstoque.setVisibility(View.VISIBLE);
                   textoIngredientesAdicionados.setVisibility(View.VISIBLE);
                   recyclerViewListItensEstoque.setVisibility(View.VISIBLE);
                   recyclerViewListItensAdicionados.setVisibility(View.VISIBLE);
                   botao_salvar_nome_receita.setVisibility(View.GONE);
                   textValorIngredientes.setVisibility(View.VISIBLE);
                   textValorIngredientes.setText("0");
                   botao_jaAdicioneiIngredientes.setVisibility(View.VISIBLE);
                   texto_tipo_producao.setVisibility(View.VISIBLE);
                   texto_tipo_producao.setText(nomeDigit);
                   carregarListaItemEstoqueDate();
                   adapterIngredienteCadastradoEmEstoque.startListening();
               }
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
           }
       });
   }

    @Override
    public void onStop() {
        super.onStop();



    }
}