package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    private RadioGroup radioGroupEscolherMetodoDePagamento;


    private RadioButton radioButtonSelecionarPrecoDeVendaNaLoja;
    private RadioButton radioButtonSelecionarPrecoDeVendaNoIfood;
    private RadioButton radioButtonPagouNoDinheiroOuPix;
    private RadioButton radioButtonPagouNoDebito;
    private RadioButton radioButtonPagouNoCredito;

    private Button botaoConfirmar;
    private Button botaoCancelar;
    private TextView textoInformativoParaEscolherUmDosValoresCadastradosOu;
    private Button botaoParaEditarValorParaVenda;

    public AlertDialog progressDialogCarregando;

    private AlertDialog.Builder alertaEditarValorDoProdutoNoProcessoDeVenda;


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


        radioGroupEscolherMetodoDePagamento = findViewById(R.id.radioGroupMetodoDePagamentoId);
        radioButtonPagouNoDinheiroOuPix = findViewById(R.id.radioButtonPagouNoDinheiroOuPixId);
        radioButtonPagouNoDebito = findViewById(R.id.radioButtonPagouNoDebitoId);
        radioButtonPagouNoCredito = findViewById(R.id.radioButtonPagouNoCreditoId);


        botaoConfirmar = findViewById(R.id.botaoConfirmarVendaNoProcessoDeVenda);
        botaoCancelar = findViewById(R.id.botaoCancelarProcessoDeVendaId);
        carregarCargaDeDesaparecerConteudoDaTela();

        AlertDialog.Builder builder = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);

        LayoutInflater inflaterLayout = AdicionarProdutoComoVendidoNoSistema.this.getLayoutInflater();
        builder.setView(inflaterLayout.inflate(R.layout.progress_dialog_carregando_informacoes_caixa_diario, null));

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
/*
                            progressDialogCarregando.dismiss();
*/

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

                carregarAlertaParaEditarOValorDoProduto();



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

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentVoltaPorQueCancelouOProcesso = new Intent(AdicionarProdutoComoVendidoNoSistema.this, LojaActivityV2.class);
                startActivity(intentVoltaPorQueCancelouOProcesso);
                finish();
            }
        });

    }

    public void carregarAlertaParaEditarOValorDoProduto() {
        alertaEditarValorDoProdutoNoProcessoDeVenda = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);
        LayoutInflater layoutAlertaEditarValorParaVenda = AdicionarProdutoComoVendidoNoSistema.this.getLayoutInflater();
        alertaEditarValorDoProdutoNoProcessoDeVenda.setView(layoutAlertaEditarValorParaVenda.inflate(R.layout.alerta_editar_valor_de_venda_do_produto, null));
        View viewAlert = getLayoutInflater().inflate(R.layout.alerta_editar_valor_de_venda_do_produto, null);

        alertaEditarValorDoProdutoNoProcessoDeVenda.setView(viewAlert);
        EditText editNomeReceitaCadastrando = viewAlert.findViewById(R.id.editValorDoProdutoNoProcessoDeVendaId);

        alertaEditarValorDoProdutoNoProcessoDeVenda.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                oValorFoiEditado = true;
                String valorDigitado = editNomeReceitaCadastrando.getText().toString();
                Toast.makeText(AdicionarProdutoComoVendidoNoSistema.this, "teste valor digitado " + valorDigitado, Toast.LENGTH_SHORT).show();
                textoValorQueVaiSerVendidoMesmo = findViewById(R.id.textoValorQueVaiSerVendidoMesmoId);
                textoValorQueVaiSerVendidoMesmo.setText("Valor que será vendido é R$: " + valorDigitado);
            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                oValorFoiEditado = true;

            }
        });

        alertaEditarValorDoProdutoNoProcessoDeVenda.create();
        alertaEditarValorDoProdutoNoProcessoDeVenda.show();
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
        radioButtonPagouNoDebito.setVisibility(View.GONE);
        radioButtonPagouNoCredito.setVisibility(View.GONE);
        radioButtonPagouNoDinheiroOuPix.setVisibility(View.GONE);
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
        radioButtonPagouNoDebito.setVisibility(View.VISIBLE);
        radioButtonPagouNoCredito.setVisibility(View.VISIBLE);
        radioButtonPagouNoDinheiroOuPix.setVisibility(View.VISIBLE);
    }

}