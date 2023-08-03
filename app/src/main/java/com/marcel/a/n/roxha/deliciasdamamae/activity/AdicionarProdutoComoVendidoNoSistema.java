package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    private TextView nomeRecuperadoDoProdutoNoProcessoDeVenda;
    private TextView textoNomeDoProdutoNoProcessoDeVenda;

    private RadioGroup radioGroupEscolherValoresCadastrados;
    private RadioGroup radioGroupEditarValorParaVender;

    private RadioButton radioButtonEscolherValoresCadastrados;
    private RadioButton radioButtonEditarValorParaVender;

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

    //CLASSES
    private BolosModel produtoRecuperadoPraExibirNaTela;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_produto_como_vendido_no_sistema);


        imagemDoProdutoNoProcessoDeVenda = findViewById(R.id.imagemDoProdutoNoProcessoDeVenda);
        nomeRecuperadoDoProdutoNoProcessoDeVenda = findViewById(R.id.nomeDoProdutoExibidoNoProcessoDeVenda);
        textoNomeDoProdutoNoProcessoDeVenda = findViewById(R.id.textView89);


        radioGroupEscolherValoresCadastrados = findViewById(R.id.radioGroupExcolherValoresCadastrados);
        radioGroupEditarValorParaVender = findViewById(R.id.radioGroupEditarValorDeVenda);

        radioButtonEscolherValoresCadastrados = findViewById(R.id.idEscolherValoresCadastradosParaVenda);
        radioButtonEditarValorParaVender = findViewById(R.id.idEditarOValoParaVender);



        AlertDialog.Builder builder = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);

        LayoutInflater inflaterLayout = AdicionarProdutoComoVendidoNoSistema.this.getLayoutInflater();
        builder.setView(inflaterLayout.inflate(R.layout.progress_dialog_carregando_informacoes_caixa_diario, null));
        builder.setCancelable(true);

        this.progressDialogCarregando = builder.create();
        this.progressDialogCarregando.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        this.progressDialogCarregando.show();
        carregarCargaDeDesaparecerConteudoDaTela();

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





    }

    private void carregarCargaDeDesaparecerConteudoDaTela(){

        nomeRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        textoNomeDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        radioGroupEscolherValoresCadastrados.setVisibility(View.GONE);
        radioGroupEditarValorParaVender.setVisibility(View.GONE);
        radioButtonEscolherValoresCadastrados.setVisibility(View.GONE);
        radioButtonEditarValorParaVender.setVisibility(View.GONE);


    }

    private void carregarCargaDeAparecimentoDoConteudoDaTela(){

        nomeRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        textoNomeDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        radioGroupEscolherValoresCadastrados.setVisibility(View.VISIBLE);
        radioGroupEditarValorParaVender.setVisibility(View.VISIBLE);
        radioButtonEscolherValoresCadastrados.setVisibility(View.VISIBLE);
        radioButtonEditarValorParaVender.setVisibility(View.VISIBLE);


    }

}