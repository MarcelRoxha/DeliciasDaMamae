package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.CaixaLojaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;

public class AdicionarProdutoComoVendidoNoSistema extends AppCompatActivity {

    //COMPONENTES DE TELA
    private ImageView imagemDoProdutoNoProcessoDeVenda;
    private TextView textoNomeDoProdutoNoProcessoDeVenda;
    private TextView nomeRecuperadoDoProdutoNoProcessoDeVenda;

    private TextView textoValorVendaLojaDoProdutoNoProcessoDeVenda;
    private TextView valorVendaLojaRecuperadoDoProdutoNoProcessoDeVenda;
    private TextView textoValorVendaIfoodDoProdutoNoProcessoDeVenda;
    private TextView valorVendaIfoodRecuperadoDoProdutoNoProcessoDeVenda;
    private TextView textoValorQueVaiSerVendidoMesmo;




    private RadioGroup radioGroupEscolherValoresCadastrados;


    private RadioButton radioButtonEscolherValoresCadastrados;
    private RadioButton radioButtonEditarValorParaVender;
    private RadioButton radioButtonSelecionarPrecoDeVendaNaLoja;
    private RadioButton radioButtonSelecionarPrecoDeVendaNoIfood;

    private Button botaoConfirmar;
    private Button botaoCancelar;
    private TextView textoInformativoParaEscolherUmDosValoresCadastradosOu;
    private Button botaoParaEditarValorParaVenda;

    private TextInputEditText editValorQueFoiVendidoOProdutoNaHoraDaVenda;

    public AlertDialog progressDialogCarregando;



    //INFO BANCO
    private FirebaseFirestore firestoreProdutoRecuperadoParaProcessoDeVenda = ConfiguracaoFirebase.getFirestor();
    private final String COLLECTION_BOLOS_EXPOSTOS_VITRINE="BOLOS_EXPOSTOS_VITRINE";
    private CollectionReference collectionReferenceProdutoRecuperadoParaProcessoDeVenda = firestoreProdutoRecuperadoParaProcessoDeVenda.collection(COLLECTION_BOLOS_EXPOSTOS_VITRINE);


    //VARIAVEIS DO PRODUTO
    private String itemKey;

    private String enderecoFotoProduto;
    private String nomeDoProdutoRecuperado;
    private String valorDoIfoodDoProdutoRecuperado;
    private String valorNaLojaDoProdutoRecuperado;
    private String valoQueFoiVendidoOProdutoRecuperado;
    private boolean oValorFoiEditado = false;


    //CLASSES
    private BolosModel produtoRecuperadoPraExibirNaTela;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_produto_como_vendido_no_sistema);


        imagemDoProdutoNoProcessoDeVenda = findViewById(R.id.imagemDoProdutoNoProcessoDeVenda);
        nomeRecuperadoDoProdutoNoProcessoDeVenda = findViewById(R.id.nomeDoProdutoExibidoNoProcessoDeVenda);
        textoNomeDoProdutoNoProcessoDeVenda = findViewById(R.id.textView89);


        textoValorVendaLojaDoProdutoNoProcessoDeVenda = findViewById(R.id.textView96);
        textoValorQueVaiSerVendidoMesmo = findViewById(R.id.textoValorQueVaiSerVendidoMesmoId);
        valorVendaLojaRecuperadoDoProdutoNoProcessoDeVenda = findViewById(R.id.valorCadastradoParaVendaNaLojaProcessoDeVenda);


        textoValorVendaIfoodDoProdutoNoProcessoDeVenda = findViewById(R.id.textView98);
        valorVendaIfoodRecuperadoDoProdutoNoProcessoDeVenda = findViewById(R.id.valorCadastradoParaVendaNoIfoodProcessoDeVenda);




        textoInformativoParaEscolherUmDosValoresCadastradosOu = findViewById(R.id.textoInformaParaEscolherUmDosValoresCadastradaosId);
        botaoParaEditarValorParaVenda = findViewById(R.id.botaoParaEditarOValorNaHoraDaVenda);


        radioGroupEscolherValoresCadastrados = findViewById(R.id.radioGroupEcolherValoresCadatradosNoProcessoDeVenda);
        radioButtonSelecionarPrecoDeVendaNaLoja = findViewById(R.id.radioButtonPrecoCadastradoParaVendaNaLojaNoProcessoDeVenda);
        radioButtonSelecionarPrecoDeVendaNoIfood = findViewById(R.id.radioButtonPrecoCadastradoParaVendaNoIfoodNoProcessoDeVenda);

        botaoConfirmar = findViewById(R.id.botaoConfirmarVendaNoProcessoDeVenda);
        botaoCancelar = findViewById(R.id.botaoCancelarProcessoDeVendaId);
        carregarCargaDeDesaparecerConteudoDaTela();


        AlertDialog.Builder builder = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);

        LayoutInflater inflaterLayout = AdicionarProdutoComoVendidoNoSistema.this.getLayoutInflater();
        builder.setView(inflaterLayout.inflate(R.layout.progress_dialog_carregando_informacoes_caixa_diario, null));
        builder.setCancelable(true);

        this.progressDialogCarregando = builder.create();
        this.progressDialogCarregando.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        this.progressDialogCarregando.show();


        itemKey = getIntent().getStringExtra("itemKey");

        if (itemKey != null){

            collectionReferenceProdutoRecuperadoParaProcessoDeVenda.document(itemKey).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            carregarCargaDeAparecimentoDoConteudoDaTela();
                            progressDialogCarregando.dismiss();

                            produtoRecuperadoPraExibirNaTela = documentSnapshot.toObject(BolosModel.class);

                            enderecoFotoProduto = produtoRecuperadoPraExibirNaTela.getEnderecoFoto();
                            nomeDoProdutoRecuperado = produtoRecuperadoPraExibirNaTela.getNomeBoloCadastrado();
                            valorDoIfoodDoProdutoRecuperado = produtoRecuperadoPraExibirNaTela.getValorCadastradoParaVendasNoIfood();
                            valorNaLojaDoProdutoRecuperado = produtoRecuperadoPraExibirNaTela.getValorCadastradoParaVendasNaBoleria();
                            Glide.with(AdicionarProdutoComoVendidoNoSistema.this).load(enderecoFotoProduto).into(imagemDoProdutoNoProcessoDeVenda);

                            nomeRecuperadoDoProdutoNoProcessoDeVenda.setText(nomeDoProdutoRecuperado);

                            valorVendaIfoodRecuperadoDoProdutoNoProcessoDeVenda.setText("R$: " + valorDoIfoodDoProdutoRecuperado);
                            valorVendaLojaRecuperadoDoProdutoNoProcessoDeVenda.setText("R$: " + valorNaLojaDoProdutoRecuperado);
                            radioButtonSelecionarPrecoDeVendaNaLoja.setText("Vendi pelo valor de R$: " + valorNaLojaDoProdutoRecuperado);
                            radioButtonSelecionarPrecoDeVendaNoIfood.setText("Vendi pelo valor de R$: " + valorDoIfoodDoProdutoRecuperado);



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdicionarProdutoComoVendidoNoSistema.this, "Algo deu erro verifique a internet e tente novamente", Toast.LENGTH_SHORT).show();
                        }
                    });


        }else {

            Intent intentVoltaPorQueNaoPegouOId = new Intent(this.getApplicationContext(), CaixaLojaFragment.class);
            startActivity(intentVoltaPorQueNaoPegouOId);
            finish();
        }


        botaoParaEditarValorParaVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oValorFoiEditado = true;
                AlertDialog.Builder alert = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);
                alert.setTitle("EDITANDO O VALOR DE VENDA");
                alert.setMessage("O valor editado aqui será processado nessa venda, digite o valor vendido e clique em CONFIRMAR");
                alert.setIcon(R.drawable.ic_alertdialogatencao_24);
                alert.setCancelable(false);


            }
        });

        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        radioButtonSelecionarPrecoDeVendaNoIfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textoValorQueVaiSerVendidoMesmo.setText("Valor que será vendido é R$: " + valorDoIfoodDoProdutoRecuperado);
            }
        });

        radioButtonSelecionarPrecoDeVendaNaLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textoValorQueVaiSerVendidoMesmo.setText("Valor que será vendido é R$: " + valorNaLojaDoProdutoRecuperado);
            }
        });

    }

    private void carregarCargaDeDesaparecerConteudoDaTela(){

        imagemDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        nomeRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        textoNomeDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        textoValorVendaLojaDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        valorVendaLojaRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        textoValorVendaIfoodDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        valorVendaIfoodRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        radioButtonSelecionarPrecoDeVendaNaLoja.setVisibility(View.GONE);
        radioButtonSelecionarPrecoDeVendaNoIfood.setVisibility(View.GONE);
        botaoConfirmar.setVisibility(View.GONE);
        botaoCancelar.setVisibility(View.GONE);

    }

    private void carregarCargaDeAparecimentoDoConteudoDaTela(){
        imagemDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        nomeRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        textoNomeDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        textoValorVendaLojaDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        valorVendaLojaRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        textoValorVendaIfoodDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        valorVendaIfoodRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        botaoConfirmar.setVisibility(View.VISIBLE);
        botaoCancelar.setVisibility(View.VISIBLE);
        radioGroupEscolherValoresCadastrados.setVisibility(View.VISIBLE);
        radioButtonSelecionarPrecoDeVendaNaLoja.setVisibility(View.VISIBLE);
        radioButtonSelecionarPrecoDeVendaNoIfood.setVisibility(View.VISIBLE);

    }

}