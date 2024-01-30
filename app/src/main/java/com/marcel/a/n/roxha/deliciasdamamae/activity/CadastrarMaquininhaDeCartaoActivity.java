package com.marcel.a.n.roxha.deliciasdamamae.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloMaquininhaDeCartaoDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMaquininhaDePassarCartao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CadastrarMaquininhaDeCartaoActivity extends AppCompatActivity {

    EditText inputNomeMaquininha, inputPorcentagemCredito, inputPorcentagemDebido, dataEscolhidaParaPagamentoDaMaquininha;

    private String idAtualizacao;
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();

    private ModeloMaquininhaDePassarCartao modeloMaquininhaDePassarCartaoAtualiza;

    Button botaoCadastrarMaquininha, botaoCancelarCadastro;

    private ModeloMaquininhaDePassarCartao modeloMaquininhaDePassarCartao;
    private ModeloMaquininhaDeCartaoDAO modeloMaquininhaDeCartaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);

        setContentView(R.layout.activity_cadastrar_maquininha_de_cartao);

        inputNomeMaquininha = findViewById(R.id.inputNomeMaquininhaDeCartoesId);
        inputPorcentagemCredito = findViewById(R.id.inputPorcentagemDeCreditoId);
        inputPorcentagemDebido = findViewById(R.id.inputPorcentagemDeDebitoId);

        botaoCadastrarMaquininha = findViewById(R.id.botaoCadastrarMaquininhaId);
        botaoCancelarCadastro = findViewById(R.id.botaoCancelarCadastroDaMaquininhaId);
        dataEscolhidaParaPagamentoDaMaquininha = findViewById(R.id.dataDePagamentoDaMaquininhaId);



        idAtualizacao = getIntent().getStringExtra("idMaquininha");
        if(idAtualizacao != null){
            System.out.println("Entrou no if");
            modeloMaquininhaDePassarCartaoAtualiza = new ModeloMaquininhaDePassarCartao();
            carregarDadosParaAtualizacao();
        }

        botaoCadastrarMaquininha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nomeDigitado = inputNomeMaquininha.getText().toString();
                String porcentagemCreditoDigitado = inputPorcentagemCredito.getText().toString();
                String porcentagemDebitoDigitado = inputPorcentagemDebido.getText().toString();
                String dataEscolhida = dataEscolhidaParaPagamentoDaMaquininha.getText().toString().replace("_", "0");
                int dataEscolhidaConvertida = Integer.parseInt(dataEscolhida);
                if(!nomeDigitado.isEmpty()){
                    if(!porcentagemCreditoDigitado.isEmpty()){
                        if(!porcentagemDebitoDigitado.isEmpty()){
                            if(!dataEscolhida.isEmpty() && dataEscolhidaConvertida > 0 && dataEscolhidaConvertida < 32){

                                if(idAtualizacao != null){
                                    double creditoConvertido = Double.parseDouble(porcentagemCreditoDigitado);
                                    double debitoConvertido = Double.parseDouble(porcentagemDebitoDigitado);
                                    modeloMaquininhaDePassarCartaoAtualiza = new ModeloMaquininhaDePassarCartao();
                                    modeloMaquininhaDePassarCartaoAtualiza.setNomeDaMaquininha(nomeDigitado);
                                    modeloMaquininhaDePassarCartaoAtualiza.setPorcentagemDeDescontoCredito(creditoConvertido);
                                    modeloMaquininhaDePassarCartaoAtualiza.setPorcentagemDeDescontoDebito(debitoConvertido);
                                    modeloMaquininhaDePassarCartaoAtualiza.setDataPrevistaParaPagamentoDosValoresPassados(dataEscolhida);

                                    atualizarMaquininha(modeloMaquininhaDePassarCartaoAtualiza, idAtualizacao);

                                }else{
                                    double creditoConvertido = Double.parseDouble(porcentagemCreditoDigitado);
                                    double debitoConvertido = Double.parseDouble(porcentagemDebitoDigitado);
                                    modeloMaquininhaDePassarCartao = new ModeloMaquininhaDePassarCartao();
                                    modeloMaquininhaDePassarCartao.setNomeDaMaquininha(nomeDigitado);
                                    modeloMaquininhaDePassarCartao.setPorcentagemDeDescontoCredito(creditoConvertido);
                                    modeloMaquininhaDePassarCartao.setPorcentagemDeDescontoDebito(debitoConvertido);
                                    modeloMaquininhaDePassarCartao.setDataPrevistaParaPagamentoDosValoresPassados(dataEscolhida);
                                    System.out.println("modelo antes ---- " + modeloMaquininhaDePassarCartao.toString());
                                    cadastrarMaquininha(modeloMaquininhaDePassarCartao);
                                }
                            }else{

                                if(dataEscolhidaConvertida > 31){
                                    carregaAlertaDataDeRepasseNaoSelecionadaOuInvalido("Não existe data maior que 31, favor digite uma data válida");
                                }
                                if(dataEscolhidaConvertida == 0){
                                    carregaAlertaDataDeRepasseNaoSelecionadaOuInvalido("Não existe data 0, favor digite uma data válida");
                                }
                            }

                        }else{
                            Toast.makeText(CadastrarMaquininhaDeCartaoActivity.this, "Porcentagem de débito inválida!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CadastrarMaquininhaDeCartaoActivity.this, "Porcentagem de crédito inválida!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastrarMaquininhaDeCartaoActivity.this, "Nome invalido", Toast.LENGTH_SHORT).show();
                }

            }
        });

        botaoCancelarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void carregarDadosParaAtualizacao() {
        System.out.println("Entrou no carregarDadosParaAtualizacao");
        firestore.collection("MAQUININHA_CARTAO").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> listaMaquininhas = queryDocumentSnapshots.getDocuments();

                if(listaMaquininhas.size() > 0){
                    for (DocumentSnapshot list: listaMaquininhas){
                        String idRecuperado = list.getId();
                        System.out.println("Entrou no carregarDadosParaAtualizacao");
                        firestore.collection("MAQUININHA_CARTAO").document(idRecuperado)
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        ModeloMaquininhaDePassarCartao maquininhaDePassarCartaoCadastrada = documentSnapshot.toObject(ModeloMaquininhaDePassarCartao.class);
                                        System.out.println("maquininhaDePassarCartaoCadastrada ++++++++ " + maquininhaDePassarCartaoCadastrada.toString());
                                        String nomeRecuperadoDaMaquininha = maquininhaDePassarCartaoCadastrada.getNomeDaMaquininha();
                                        String porcentegemConvertidaCredito = String.valueOf(maquininhaDePassarCartaoCadastrada.getPorcentagemDeDescontoCredito());
                                        String porcentegemConvertidaDebito = String.valueOf(maquininhaDePassarCartaoCadastrada.getPorcentagemDeDescontoDebito());
                                        String dataRepasse = String.valueOf(maquininhaDePassarCartaoCadastrada.getDataPrevistaParaPagamentoDosValoresPassados());
                                        inputNomeMaquininha.setText(nomeRecuperadoDaMaquininha);
                                        inputPorcentagemCredito.setText(porcentegemConvertidaCredito);
                                        inputPorcentagemDebido.setText(porcentegemConvertidaDebito);
                                        dataEscolhidaParaPagamentoDaMaquininha.setText(dataRepasse);
                                        botaoCadastrarMaquininha.setText("ATUALIZAR");


                                    }
                                });
                    }
                }else{

                }

            }
        });
    }

    private void carregaAlertaDataDeRepasseNaoSelecionadaOuInvalido(String tipoMensagem) {

        AlertDialog.Builder alertaDiaInvalido = new AlertDialog.Builder(CadastrarMaquininhaDeCartaoActivity.this);
        alertaDiaInvalido.setTitle("DATA INVÁLIDA");
        alertaDiaInvalido.setMessage(tipoMensagem);
        alertaDiaInvalido.setCancelable(false);
        alertaDiaInvalido.setNeutralButton("ENTENDI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertaDiaInvalido.setCancelable(true);
            }
        });

        alertaDiaInvalido.show();
        alertaDiaInvalido.create();

    }

    private void cadastrarMaquininha(ModeloMaquininhaDePassarCartao modeloMaquininhaDePassarCartao){

        CollectionReference referenceMaquininhaCartao = ConfiguracaoFirebase.getFirestor().collection("MAQUININHA_CARTAO");

        Map<String, Object> item = new HashMap<>();

        item.put("nomeDaMaquininha",modeloMaquininhaDePassarCartao.getNomeDaMaquininha().trim());
        item.put("porcentagemDeDescontoCredito", modeloMaquininhaDePassarCartao.getPorcentagemDeDescontoCredito());
        item.put("porcentagemDeDescontoDebito", modeloMaquininhaDePassarCartao.getPorcentagemDeDescontoDebito());
        item.put("dataPrevistaParaPagamentoDosValoresPassados", modeloMaquininhaDePassarCartao.getDataPrevistaParaPagamentoDosValoresPassados());

        referenceMaquininhaCartao.add(item).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                if(task.isSuccessful()){
                    Toast.makeText(CadastrarMaquininhaDeCartaoActivity.this, "Maquininha " + modeloMaquininhaDePassarCartao.getNomeDaMaquininha() + " cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.popBackStackImmediate();
                    finish();
                }else{
                    Toast.makeText(CadastrarMaquininhaDeCartaoActivity.this, "Algo deu errado, verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void atualizarMaquininha(ModeloMaquininhaDePassarCartao modeloMaquininhaDePassarCartao, String idRecebido){
        CollectionReference referenceMaquininhaCartaoAtualizar = ConfiguracaoFirebase.getFirestor().collection("MAQUININHA_CARTAO");

        Map<String, Object> itemAtualizado = new HashMap<>();

        itemAtualizado.put("nomeDaMaquininha",modeloMaquininhaDePassarCartao.getNomeDaMaquininha().trim());
        itemAtualizado.put("porcentagemDeDescontoCredito", modeloMaquininhaDePassarCartao.getPorcentagemDeDescontoCredito());
        itemAtualizado.put("porcentagemDeDescontoDebito", modeloMaquininhaDePassarCartao.getPorcentagemDeDescontoDebito());
        itemAtualizado.put("dataPrevistaParaPagamentoDosValoresPassados", modeloMaquininhaDePassarCartao.getDataPrevistaParaPagamentoDosValoresPassados());

        referenceMaquininhaCartaoAtualizar.document(idRecebido).update(itemAtualizado).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(CadastrarMaquininhaDeCartaoActivity.this, "Maquininha " + modeloMaquininhaDePassarCartao.getNomeDaMaquininha() + " atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
                finish();
            }
        });
    }
}