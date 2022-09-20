package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ReceitasBolosAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ModeloBoloCadastradoParaVendaDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfeccionarBoloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfeccionarBoloFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfeccionarBoloFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfeccionarBoloFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfeccionarBoloFragment newInstance(String param1, String param2) {
        ConfeccionarBoloFragment fragment = new ConfeccionarBoloFragment();
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


    private FirebaseStorage storageReference = ConfiguracaoFirebase.getStorage();
    private StorageReference storageRef;
    private TextView textoNomeBolo;
    private TextView textoQuantRendeFornada;
    private TextView textoCustoTotalBolo;
    private TextView textoValorSugeridoVenda;
    private TextView textoNome;
    private TextView textoNomeAdd;
    private TextView textCusto;
    private TextView textCustoAdd;
    private TextView textoRendi;
    private TextView textoRendiAdd;
    private TextView textValorSuge;
    private TextView textValorSugeAdd;
    private TextView textInfo;
    private TextView textValorVendaIfoodBolo;
    private TextView textValorVendaBoleriaBolo;
    private TextView textPorcentagemAdicionadoVendaBolo;
    private TextView textPorcentagemAdicionadoPorContaDoIfood;
    private TextView textInfoPorcentagemAdicionadaPorContaDoIfood;
    private TextView textInfoPorcentagemLucro;
    private TextView textLucroDigitadoPeloUsuarioNoAlert;
    private TextView textValorSugeridoVendaIfood;
    private TextView textoValorDeVendaNaLoja;
    private ImageView imagemBoloCadastrado;
    private Uri imageUri;


    private String currentPatch;


    private ImageView imagemCapturada;

    private static final int REQUEST_CODE_PERMISSIONS = 1;

    private TextInputEditText editValorVendaBolo;
    private TextInputEditText editValorVendaBoloIfood;
    private TextInputEditText editValorCadastradoParaVendasNoIfood;
    private ReceitasBolosAdapter adapter;

    private RecyclerView recyclerViewListaReceitasProntas;

    private String valorSugerido;
    private String porcentagemDeLucroAdicionado;
    private String valorSugeridoDeVendaDoIfood;
    private String valorDeVendaComAPorcentagemdoIfoodAdicionado;
    private String porcentagemAdicionadoPorContaDoIfood;
    private String idReferenciaReceitaCadastrada;
    private String nomeReceita;
    private String nomeParaCadastroParaVendas;
    private String valorTotalReceita;
    private String quantRendimentoReceita;
    private String currentImagePath = null;
    private String valorVendaDigitado;
    private String enderecoSalvoFoto;
    private double porcentConvert;
    private double porcentIfoodConvert;
    private double custoConvert;
    private double custoIfoodConvert;
    private double resultado;
    private double resultadoIfood;
    private int verificaGaleriaCamera;
    private int quantidadeQueRendeACadaFornadaConvertido;

    private static final int REQUEST_CODE_CAMERA = 101;
    private static final int REQUEST_CODE_GALERIA = 100;

    private ReceitaModel receitaRecuperada = new ReceitaModel();
    private BolosModel boloCadastroFinaliza = new BolosModel();

    private BolosModel bolosModelPreperParaCadastro = new BolosModel();

    private ModeloBoloCadastradoParaVendaDAO modeloBoloCadastradoParaVendaDAO;

    private Button botaoSalvarBoloVendas;
    private Button botaParaEditarPorcentagensDeLucroEIfood;

    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confeccionar_bolo, container, false);

        recyclerViewListaReceitasProntas = view.findViewById(R.id.recyclerViewReceitasProntasParaBolo_id);
        textoCustoTotalBolo = view.findViewById(R.id.textViewValorCustoBolo_id);
        textoValorSugeridoVenda = view.findViewById(R.id.textViewValorSugeridoVenda_id);
        textoNomeBolo = view.findViewById(R.id.textViewNomBolo_id);
        textoQuantRendeFornada = view.findViewById(R.id.textViewQuantPorFornada_id);
        imagemBoloCadastrado = view.findViewById(R.id.imagem_bolo_cadastrado_id);

        botaoSalvarBoloVendas = view.findViewById(R.id.botao_salvar_receita_venda_id);
        editValorVendaBolo = view.findViewById(R.id.edit_preco_venda_bolo_id);
        editValorCadastradoParaVendasNoIfood = view.findViewById(R.id.input_valor_cadastrado_venda_no_ifood_id);

        textoNome = view.findViewById(R.id.textView18);
        textoNomeAdd = view.findViewById(R.id.textoNome);
        textCusto = view.findViewById(R.id.textView21);
        textCustoAdd = view.findViewById(R.id.textoCustoReceita);
        textoRendi = view.findViewById(R.id.textView22);
        textoRendiAdd = view.findViewById(R.id.textoRendimentoReceitaBolo);
        textValorSuge = view.findViewById(R.id.textView23);
        textValorVendaBoleriaBolo = view.findViewById(R.id.textoValorSugerido);
        textInfo = view.findViewById(R.id.textView26);
        textPorcentagemAdicionadoVendaBolo = view.findViewById(R.id.textView80);
        textValorVendaIfoodBolo = view.findViewById(R.id.texto_valor_sugerido_venda_porcentagem_ifood);
        botaParaEditarPorcentagensDeLucroEIfood = view.findViewById(R.id.botao_para_editar_porcentagem_lucro_e_ifood);

        textValorSugeridoVendaIfood = view.findViewById(R.id.textView89);


        textInfoPorcentagemLucro = view.findViewById(R.id.textView85);
        textLucroDigitadoPeloUsuarioNoAlert = view.findViewById(R.id.texto_porcentagem_lucro_bolo_cadastrando);


        textoValorDeVendaNaLoja = view.findViewById(R.id.textView77);



        textInfoPorcentagemAdicionadaPorContaDoIfood = view.findViewById(R.id.textView87);
        textPorcentagemAdicionadoPorContaDoIfood = view.findViewById(R.id.texto_porcentagem_adicionado_ifood);


        imagemBoloCadastrado.setVisibility(View.GONE);
        textoCustoTotalBolo.setVisibility(View.GONE);

        textoValorSugeridoVenda.setVisibility(View.GONE);

        textoQuantRendeFornada.setVisibility(View.GONE);

        textInfo.setVisibility(View.GONE);

        textValorSugeridoVendaIfood.setVisibility(View.GONE);



        botaoSalvarBoloVendas.setVisibility(View.GONE);
        botaParaEditarPorcentagensDeLucroEIfood.setVisibility(View.GONE);
        editValorVendaBolo.setVisibility(View.GONE);
        textoNomeBolo.setVisibility(View.GONE);
        textoNome.setVisibility(View.GONE);
        textoNomeAdd.setVisibility(View.GONE);
        textCusto.setVisibility(View.GONE);
        textCustoAdd.setVisibility(View.GONE);
        textoRendi.setVisibility(View.GONE);
        textoRendiAdd.setVisibility(View.GONE);
        textValorSuge.setVisibility(View.GONE);
       // textValorSugeAdd.setVisibility(View.GONE);
        textPorcentagemAdicionadoVendaBolo.setVisibility(View.GONE);
        textValorVendaIfoodBolo.setVisibility(View.GONE);
        textValorVendaBoleriaBolo.setVisibility(View.GONE);
        editValorCadastradoParaVendasNoIfood.setVisibility(View.GONE);
       // editValorVendaBoloIfood.setVisibility(View.GONE);



        textInfoPorcentagemLucro.setVisibility(View.GONE);
        textLucroDigitadoPeloUsuarioNoAlert.setVisibility(View.GONE);
        textoValorDeVendaNaLoja.setVisibility(View.GONE);


        textInfoPorcentagemAdicionadaPorContaDoIfood.setVisibility(View.GONE);
        textPorcentagemAdicionadoPorContaDoIfood.setVisibility(View.GONE);


        storageRef = storageReference.getReference();


        botaoSalvarBoloVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String valorDigitado = editValorVendaBolo.getText().toString().replace(",", ".");
                String valorDigitadoIfood = editValorCadastradoParaVendasNoIfood.getText().toString().replace(",", ".");

                if (valorDigitado.isEmpty() || valorDigitadoIfood.isEmpty()) {
                    Toast.makeText(getActivity(), "Favor insira o valor que será vendido o bolo na boleria e no ifood e tente novamente", Toast.LENGTH_SHORT).show();
                } else {



                    boloCadastroFinaliza = new BolosModel();
                    modeloBoloCadastradoParaVendaDAO = new ModeloBoloCadastradoParaVendaDAO(getActivity());

                    boloCadastroFinaliza.setNomeBoloCadastrado(bolosModelPreperParaCadastro.getNomeBoloCadastrado());
                    boloCadastroFinaliza.setIdReferenciaReceitaCadastrada(bolosModelPreperParaCadastro.getIdReferenciaReceitaCadastrada());
                    boloCadastroFinaliza.setCustoTotalDaReceitaDoBolo(bolosModelPreperParaCadastro.getCustoTotalDaReceitaDoBolo());
                    boloCadastroFinaliza.setValorCadastradoParaVendasNaBoleria(valorDigitado);
                    boloCadastroFinaliza.setValorCadastradoParaVendasNoIfood(valorDigitadoIfood);
                    boloCadastroFinaliza.setPorcentagemAdicionadoPorContaDoIfood(bolosModelPreperParaCadastro.getPorcentagemAdicionadoPorContaDoIfood());
                    boloCadastroFinaliza.setPorcentagemAdicionadoPorContaDoLucro(bolosModelPreperParaCadastro.getPorcentagemAdicionadoPorContaDoLucro());
                    boloCadastroFinaliza.setValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem(bolosModelPreperParaCadastro.getValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem());
                    boloCadastroFinaliza.setValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro(bolosModelPreperParaCadastro.getValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro());
                    boloCadastroFinaliza.setEnderecoFoto(bolosModelPreperParaCadastro.getEnderecoFoto());
                    boloCadastroFinaliza.setValorQueOBoloRealmenteFoiVendido(bolosModelPreperParaCadastro.getValorQueOBoloRealmenteFoiVendido());
                    boloCadastroFinaliza.setVerificaCameraGaleria(bolosModelPreperParaCadastro.getVerificaCameraGaleria());
                    System.out.println("---------------CLICOU NO SALVAR BOLO-------------------- \n " + boloCadastroFinaliza.toString());
                    System.out.println("---------------CLICOU NO SALVAR BOLO BOLO PREPER-------------------- \n " + bolosModelPreperParaCadastro.toString());

                    modeloBoloCadastradoParaVendaDAO.cadastrarBoloParaVenda(boloCadastroFinaliza);


                }

            }
        });

        botaParaEditarPorcentagensDeLucroEIfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                carregarAlertEditarPorcent();


            }
        });


        return view;
    }

    private void carregarListaReceitasProntas() {

        FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
        CollectionReference reference = db.collection("RECEITA_CADASTRADA");

        Query query = reference.orderBy("nomeReceita", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ReceitaModel> options = new FirestoreRecyclerOptions.Builder<ReceitaModel>()
                .setQuery(query, ReceitaModel.class)
                .build();

        adapter = new ReceitasBolosAdapter(options);
        recyclerViewListaReceitasProntas.setHasFixedSize(true);
        recyclerViewListaReceitasProntas.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewListaReceitasProntas.setAdapter(adapter);
        recyclerViewListaReceitasProntas.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        adapter.setOnItemClickListerner(new ReceitasBolosAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {


                ReceitaModel receitaSelecionada = documentSnapshot.toObject(ReceitaModel.class);

                assert receitaSelecionada != null;

                idReferenciaReceitaCadastrada = receitaSelecionada.getIdReceita();
                nomeReceita = receitaSelecionada.getNomeReceita();
                valorTotalReceita = receitaSelecionada.getValorTotalReceita();
                quantRendimentoReceita = receitaSelecionada.getQuantRendimentoReceita();

                receitaRecuperada.setNomeReceita(nomeReceita);
                receitaRecuperada.setValorTotalReceita(valorTotalReceita);
                receitaRecuperada.setQuantRendimentoReceita(quantRendimentoReceita);
                System.out.println("cliquei na receita -------------------------------------------- \n idRecuperado: " + idReferenciaReceitaCadastrada + "\n nome recuperado: " + nomeReceita);
                carregarAlertFoto();

            }

        });

    }

    public void carregarAlertFoto() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("FOTO NECESSÁRIA");
        dialog.setMessage("Para cadastrar um produto para venda, é necessário inserir uma foto desse produto, indicamos fotos selecionadas na GALERIA por conta da qualidade da foto.");
        dialog.setCancelable(false);
        dialog.setPositiveButton("CÂMERA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){


                }else {

                    ActivityCompat.requestPermissions(getActivity(), new String[] {
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_PERMISSIONS);
                    verificaGaleriaCamera = 1;
                    dispatchCaptureImageIntent();
               }



            }
        }).setNeutralButton("GALERIA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_GALERIA);
                }
                verificaGaleriaCamera = 2;
                selecionarFoto();

            }
        });
        dialog.create();
        dialog.show();
    }

    public void selecionarFoto() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecionar Foto"), REQUEST_CODE_GALERIA);
    }

    public void carregarAlertEditarPorcent(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("EDITANDO");
        View viewLayout = getLayoutInflater().inflate(R.layout.alerta_para_adicionar_porcentagens_do_ifood, null);
        EditText texNomeBoloCadastrando = viewLayout.findViewById(R.id.edit_alert_nome_bolo_cadastrando_id);
        EditText texPorcentDig = viewLayout.findViewById(R.id.edit_porcentagem_de_lucro_receita_id);
        EditText texPorcentagemIfood = viewLayout.findViewById(R.id.edit_porcentagem_de_acrescimo_do_ifood_id);

        dialog.setView(texPorcentDig);
        dialog.setView(texNomeBoloCadastrando);
        dialog.setView(texPorcentagemIfood);

        texNomeBoloCadastrando.setText(bolosModelPreperParaCadastro.getNomeBoloCadastrado());

        dialog.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String porct = texPorcentDig.getText().toString();
                String porctIfood = texPorcentagemIfood.getText().toString();


                if (!porct.isEmpty() && !porctIfood.isEmpty() && !texNomeBoloCadastrando.getText().toString().isEmpty() ) {


                    calcularValorSugerido(valorTotalReceita, porct);
                    calcularValorSugeridoIfood(valorTotalReceita, porctIfood, porct);

                    nomeParaCadastroParaVendas = texNomeBoloCadastrando.getText().toString();
                    textoNomeBolo.setVisibility(View.GONE);
                    textoCustoTotalBolo.setVisibility(View.GONE);
                    textoQuantRendeFornada.setVisibility(View.GONE);
                    recyclerViewListaReceitasProntas.setVisibility(View.GONE);

                    bolosModelPreperParaCadastro = new BolosModel();

                    bolosModelPreperParaCadastro.setNomeBoloCadastrado(nomeParaCadastroParaVendas);
                    bolosModelPreperParaCadastro.setIdReferenciaReceitaCadastrada(idReferenciaReceitaCadastrada);
                    bolosModelPreperParaCadastro.setCustoTotalDaReceitaDoBolo(valorTotalReceita);
                    bolosModelPreperParaCadastro.setValorCadastradoParaVendasNaBoleria("PREPER");
                    bolosModelPreperParaCadastro.setValorCadastradoParaVendasNoIfood("PREPER");
                    bolosModelPreperParaCadastro.setPorcentagemAdicionadoPorContaDoIfood(texPorcentagemIfood.getText().toString());
                    bolosModelPreperParaCadastro.setPorcentagemAdicionadoPorContaDoLucro(texPorcentDig.getText().toString());
                    bolosModelPreperParaCadastro.setValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem(valorSugeridoDeVendaDoIfood);
                    bolosModelPreperParaCadastro.setValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro(valorSugerido);
                    bolosModelPreperParaCadastro.setEnderecoFoto(enderecoSalvoFoto);
                    bolosModelPreperParaCadastro.setValorQueOBoloRealmenteFoiVendido("CADASTRO");
                    bolosModelPreperParaCadastro.setVerificaCameraGaleria(verificaGaleriaCamera);

                    imagemBoloCadastrado.setVisibility(View.VISIBLE);
                    textoNome.setVisibility(View.VISIBLE);
                    textoNomeAdd.setVisibility(View.VISIBLE);
                    textCusto.setVisibility(View.VISIBLE);
                    textCustoAdd.setVisibility(View.VISIBLE);
                    textoRendi.setVisibility(View.VISIBLE);
                    textoRendiAdd.setVisibility(View.VISIBLE);
                    textValorSuge.setVisibility(View.VISIBLE);

                    textPorcentagemAdicionadoVendaBolo.setVisibility(View.VISIBLE);
                    textValorVendaIfoodBolo.setVisibility(View.VISIBLE);
                    textValorVendaBoleriaBolo.setVisibility(View.VISIBLE);

                    textValorSugeridoVendaIfood.setVisibility(View.VISIBLE);
                    textoValorDeVendaNaLoja.setVisibility(View.VISIBLE);



                    textInfoPorcentagemLucro.setVisibility(View.VISIBLE);
                    textLucroDigitadoPeloUsuarioNoAlert.setVisibility(View.VISIBLE);

                    textPorcentagemAdicionadoPorContaDoIfood.setVisibility(View.VISIBLE);
                    textInfoPorcentagemAdicionadaPorContaDoIfood.setVisibility(View.VISIBLE);


                    textLucroDigitadoPeloUsuarioNoAlert.setText(texPorcentDig.getText().toString());
                    textPorcentagemAdicionadoPorContaDoIfood.setText(texPorcentagemIfood.getText().toString());

                    textValorVendaIfoodBolo.setText(valorSugeridoDeVendaDoIfood);


                    textoNomeAdd.setText(nomeReceita);
                    textCustoAdd.setText(valorTotalReceita);
                    textoRendiAdd.setText(quantRendimentoReceita);

                    textValorVendaBoleriaBolo.setText(valorSugerido);


                    textInfo.setVisibility(View.VISIBLE);
                    editValorVendaBolo.setVisibility(View.VISIBLE);
                    editValorCadastradoParaVendasNoIfood.setVisibility(View.VISIBLE);
                    botaoSalvarBoloVendas.setVisibility(View.VISIBLE);
                    botaParaEditarPorcentagensDeLucroEIfood.setVisibility(View.VISIBLE);
        System.out.println("----------------PASSOU PELO EDITAR CADASTRO RECUPERADO--------------------- \n " + bolosModelPreperParaCadastro.toString());

                } else {

                    Toast.makeText(getActivity(), "É necessário informar a % de lucro e de acrescimo do ifoods", Toast.LENGTH_SHORT).show();
                }


            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.setView(viewLayout);
        dialog.create();
        dialog.show();

    }

    public void carregarAlertPorcent() {

        System.out.println("---------------ALERT PORCENTAGEM--------------------");

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View viewLayout = getLayoutInflater().inflate(R.layout.alerta_para_adicionar_porcentagens_do_ifood, null);
        EditText texNomeBoloCadastrando = viewLayout.findViewById(R.id.edit_alert_nome_bolo_cadastrando_id);
        EditText texPorcentDig = viewLayout.findViewById(R.id.edit_porcentagem_de_lucro_receita_id);
        EditText texPorcentagemIfood = viewLayout.findViewById(R.id.edit_porcentagem_de_acrescimo_do_ifood_id);

        dialog.setView(texPorcentDig);
        dialog.setView(texNomeBoloCadastrando);
        dialog.setView(texPorcentagemIfood);

        texNomeBoloCadastrando.setText(nomeReceita);

        dialog.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String porct = texPorcentDig.getText().toString();
                String porctIfood = texPorcentagemIfood.getText().toString();


                if (!porct.isEmpty() && !porctIfood.isEmpty()) {
                    System.out.println("---------------DENTRO DO IF QUANDO NÃO TEM PORCENTAGEM VAZIA--------------------");

                    calcularValorSugerido(valorTotalReceita, porct);
                    calcularValorSugeridoIfood(valorTotalReceita, porctIfood, porct);
                    nomeParaCadastroParaVendas = texNomeBoloCadastrando.getText().toString();

                    textoNomeBolo.setVisibility(View.GONE);
                    textoCustoTotalBolo.setVisibility(View.GONE);
                    textoQuantRendeFornada.setVisibility(View.GONE);
                    recyclerViewListaReceitasProntas.setVisibility(View.GONE);

                    bolosModelPreperParaCadastro = new BolosModel();

                    bolosModelPreperParaCadastro.setNomeBoloCadastrado(nomeParaCadastroParaVendas);
                    bolosModelPreperParaCadastro.setIdReferenciaReceitaCadastrada(idReferenciaReceitaCadastrada);
                    bolosModelPreperParaCadastro.setCustoTotalDaReceitaDoBolo(valorTotalReceita);
                    bolosModelPreperParaCadastro.setValorCadastradoParaVendasNaBoleria("PREPER");
                    bolosModelPreperParaCadastro.setValorCadastradoParaVendasNoIfood("PREPER");
                    bolosModelPreperParaCadastro.setPorcentagemAdicionadoPorContaDoIfood(porctIfood);
                    bolosModelPreperParaCadastro.setPorcentagemAdicionadoPorContaDoLucro(porct);
                    bolosModelPreperParaCadastro.setValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem(valorSugeridoDeVendaDoIfood);
                    bolosModelPreperParaCadastro.setValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro(valorSugerido);
                    bolosModelPreperParaCadastro.setEnderecoFoto(enderecoSalvoFoto);
                    bolosModelPreperParaCadastro.setValorQueOBoloRealmenteFoiVendido("CADASTRO");
                    bolosModelPreperParaCadastro.setVerificaCameraGaleria(verificaGaleriaCamera);

                    System.out.println("---------------BOLO MODELO PREPARANDO-------------------- \n " + bolosModelPreperParaCadastro.toString());


                    imagemBoloCadastrado.setVisibility(View.VISIBLE);
                    textoNome.setVisibility(View.VISIBLE);
                    textoNomeAdd.setVisibility(View.VISIBLE);
                    textCusto.setVisibility(View.VISIBLE);
                    textCustoAdd.setVisibility(View.VISIBLE);
                    textoRendi.setVisibility(View.VISIBLE);
                    textoRendiAdd.setVisibility(View.VISIBLE);
                    textValorSuge.setVisibility(View.VISIBLE);

                    textPorcentagemAdicionadoVendaBolo.setVisibility(View.VISIBLE);
                    textValorVendaIfoodBolo.setVisibility(View.VISIBLE);
                    textValorVendaBoleriaBolo.setVisibility(View.VISIBLE);

                    textValorSugeridoVendaIfood.setVisibility(View.VISIBLE);
                    textoValorDeVendaNaLoja.setVisibility(View.VISIBLE);



                    textInfoPorcentagemLucro.setVisibility(View.VISIBLE);
                    textLucroDigitadoPeloUsuarioNoAlert.setVisibility(View.VISIBLE);

                    textPorcentagemAdicionadoPorContaDoIfood.setVisibility(View.VISIBLE);
                    textInfoPorcentagemAdicionadaPorContaDoIfood.setVisibility(View.VISIBLE);


                    textLucroDigitadoPeloUsuarioNoAlert.setText(bolosModelPreperParaCadastro.getPorcentagemAdicionadoPorContaDoLucro());
                    textPorcentagemAdicionadoPorContaDoIfood.setText(bolosModelPreperParaCadastro.getPorcentagemAdicionadoPorContaDoIfood());

                    textValorVendaIfoodBolo.setText(valorSugeridoDeVendaDoIfood);


                    textoNomeAdd.setText(bolosModelPreperParaCadastro.getNomeBoloCadastrado());
                    textCustoAdd.setText(bolosModelPreperParaCadastro.getCustoTotalDaReceitaDoBolo());
                    textoRendiAdd.setText(quantRendimentoReceita);

                    textValorVendaBoleriaBolo.setText(valorSugerido);


                    textInfo.setVisibility(View.VISIBLE);
                    editValorVendaBolo.setVisibility(View.VISIBLE);
                    editValorCadastradoParaVendasNoIfood.setVisibility(View.VISIBLE);
                    botaoSalvarBoloVendas.setVisibility(View.VISIBLE);
                    botaParaEditarPorcentagensDeLucroEIfood.setVisibility(View.VISIBLE);


                } else {

                    Toast.makeText(getActivity(), "É necessário informar a % de lucro e de acrescimo do ifoods", Toast.LENGTH_SHORT).show();
                }


            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.setView(viewLayout);
        dialog.create();
        dialog.show();

    }

    public void calcularValorSugerido(String custo, String porcDi) {

        String custoFormatado = custo.replace(",", ".");
        String porncetFormatado = porcDi.replace(",", ".");
        String quantidadeRendimentoFornada = quantRendimentoReceita;


        custoConvert = Double.parseDouble(custoFormatado);
        porcentConvert = Double.parseDouble(porncetFormatado);
        quantidadeQueRendeACadaFornadaConvertido = Integer.parseInt(quantidadeRendimentoFornada);

        double dividindoQuantidades = custoConvert /quantidadeQueRendeACadaFornadaConvertido ;


        resultado = ((dividindoQuantidades * porcentConvert) / 100) + dividindoQuantidades;

        String totalVend = String.valueOf(resultado);
        valorSugerido = String.format("%.2f", resultado);

    }

    public void calcularValorSugeridoIfood(String custo, String porcDi, String porcentagemDeLucro) {

        String custoFormatado = custo.replace(",", ".");
        String porncetFormatado = porcDi.replace(",", ".");
        String porncetLucroFormatado = porcentagemDeLucro.replace(",", ".");
        String quantidadeRendimentoFornada = quantRendimentoReceita;

        custoIfoodConvert = Double.parseDouble(custoFormatado);
        porcentIfoodConvert = Double.parseDouble(porncetFormatado);
        quantidadeQueRendeACadaFornadaConvertido = Integer.parseInt(quantidadeRendimentoFornada);
        double porcentagemLucroConvertido = Double.parseDouble(porncetLucroFormatado);
        double dividindoQuantidades = custoIfoodConvert /quantidadeQueRendeACadaFornadaConvertido ;

        double resultadoDoLucroAdicionado = ((dividindoQuantidades * porcentagemLucroConvertido) / 100) + dividindoQuantidades;
        resultadoIfood = ((dividindoQuantidades * porcentIfoodConvert) / 100) + resultadoDoLucroAdicionado;

        String totalVendIfood = String.valueOf(resultadoIfood);
        valorSugeridoDeVendaDoIfood = String.format("%.2f", resultadoIfood);

    }


    @Override
    public void onStart() {
        super.onStart();
        carregarListaReceitasProntas();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == REQUEST_CODE_GALERIA && resultCode == RESULT_OK) {


                imageUri = data.getData();
                imagemBoloCadastrado.setImageURI(data.getData());
                enviarFotoGaleria();


        } else if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {

            rotateImage(BitmapFactory.decodeFile(currentPatch));
            enviarFotoCamera();

        }



            super.onActivityResult(requestCode, resultCode, data);

}


    private void enviarFotoCamera() {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Aguarde enquanto a foto é carregada");
        pd.show();

        //imagemBoloCadastrado.setRotation(90f);
//imagemBoloCadastrado.getRotation();


        Bitmap bitmap = ((BitmapDrawable) imagemBoloCadastrado.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

       /* String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "val", null);
        Uri uri = Uri.parse(path);
        imagemBoloCadastrado.setImageURI(uri);*/
        byte[] image = byteArrayOutputStream.toByteArray();


        StorageReference reference = FirebaseStorage.getInstance().getReference().child("fotosSalvas").child(nomeReceita + ".jpeg");
        UploadTask uploadTask = reference.putBytes(image);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(getActivity(), "Sucesso ao salvar imagem da camera", Toast.LENGTH_SHORT).show();
                        enderecoSalvoFoto = uri.toString();

                        pd.dismiss();
                        carregarAlertPorcent();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Erro ao salvar imagem da camera", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void enviarFotoGaleria() {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Aguarde enquanto a foto é carregada");
        pd.show();


        if (imageUri != null) {
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("fotosSalvas").child(nomeReceita + ".jpeg");

            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            enderecoSalvoFoto = uri.toString();
                            pd.dismiss();
                            Toast.makeText(getActivity(), "Imagem enviada da galeria com sucesso", Toast.LENGTH_SHORT).show();
                            carregarAlertPorcent();
                        }
                    });
                }
            });
        }


    }

    private void dispatchCaptureImageIntent() {

        Intent inte = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (inte.resolveActivity(getActivity().getPackageManager()) != null) {
            File ImageFile = null;
            try {
                ImageFile = createImageFile();

            } catch (Exception e) {

                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
            if (ImageFile != null) {

                Uri imageUri = FileProvider.getUriForFile(getActivity(), "com.marcel.a.n.roxha.deliciasdamamae.fileprovider", ImageFile);

                inte.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(inte, REQUEST_CODE_CAMERA);
            }
        }

    }

    private File createImageFile() {

        String fileName = "IMAGE_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());


        File directory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(fileName, ".jpg", directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPatch = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if(requestCode == REQUEST_CODE_PERMISSIONS && grantResults.length > 0){
                if(grantResults [0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    //dispatchCaptureImageIntent();
                }else {
                    Toast.makeText(getActivity(), "É necessário permitir os acessos a câmera e aos arquivos", Toast.LENGTH_SHORT).show();
                }
            }
        }


     private void rotateImage (Bitmap bitmap){

         ExifInterface exifInterface = null;
         try{
             exifInterface = new ExifInterface(currentPatch);
         }catch (Exception e){
         }
         int orientatio = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
         Matrix matrix = new Matrix();
         switch (orientatio){
             case ExifInterface.ORIENTATION_ROTATE_90:
                 matrix.setRotate(90);
                 break;
                 case ExifInterface.ORIENTATION_ROTATE_180:
                     matrix.setRotate(180);
                     break;
             default:
         }
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                imagemBoloCadastrado.setImageBitmap(rotatedBitmap);
     }

}