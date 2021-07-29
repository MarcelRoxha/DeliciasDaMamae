package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.BoloVendidoModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.CaixaMensalModel;

import java.util.HashMap;
import java.util.Map;

public class EditarBoloVendidoBancoActivity extends AppCompatActivity {

    //Componentes de tela:

    private Button botaoVoltarTela;
    private Button botaoAtualizarBoloVendido;
    private TextInputEditText edit_nome_bolo_vendido;
    private TextInputEditText edit_valor_venda_bolo_vendido;
    private TextInputEditText edit_valor_custo_bolo_vendido;

    //Variaveis:

    private String idRecuperadoBoloVendido = null;
    private String idRecuperadoMontanteAtual = null;
    private String nomeBoloVendido = null;
    private double valorVendaBoloVendidoCadastradoFirebase = 0;
    private double valorCustoBoloVendidoCadastradoFirebase = 0;
    private String nomeUpdate = null;
    private String valorVendaUpdate = null;
    private String valorCustoUpdate = null;
    private double valorCustoConvertidoDigitado = 0;
    private double valorVendaConvertidoDigitado = 0;
    private double resultadoVenda = 0;
    private double resultadoCusto = 0;
    private double valorVendaConvert = 0;
    private double valorCustoConvert = 0;


    private String identificadorCaixaMensal;
    private int mesReferencia ;
    private int quantTotalBolosAdicionadosMensal ;
    private double valorTotalBolosVendidosMensal  ;
    private double valorTotalCustosBolosVendidosMensal ;
    private double totalGastoMensal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_bolo_vendido_banco);

        //Identificando os componentes da tela para interação com usuario:

        botaoVoltarTela = findViewById(R.id.botao_voltar_bolo_vendido_activity_id);
        botaoAtualizarBoloVendido = findViewById(R.id.botao_atualizar_bolo_vendido_id);
        edit_nome_bolo_vendido = findViewById(R.id.edit_nome_bolo_vendido_id);
        edit_valor_venda_bolo_vendido = findViewById(R.id.edit_valor_venda_bolo_id);
        edit_valor_custo_bolo_vendido = findViewById(R.id.edit_valor_custo_bolo_vendido_id);

        //Id Recuperado do Bolo vendido selecionado:

        idRecuperadoBoloVendido = getIntent().getStringExtra("idBoloVendido");
        idRecuperadoMontanteAtual = getIntent().getStringExtra("idMontanteAtual");
        Toast.makeText(this, "id Montante Recuperado: " + idRecuperadoMontanteAtual, Toast.LENGTH_SHORT).show();

        //Verificando se o id Recuperado foi enviado realmente:

            if(idRecuperadoBoloVendido != null){
                FirebaseFirestore.getInstance().collection("BOLOS_VENDIDOS").document(idRecuperadoBoloVendido).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                     //Recuperando o Objeto do banco de dados Objeto do tipo (BoloVendidoModel.class):

                        BoloVendidoModel boloVendidoModel = documentSnapshot.toObject(BoloVendidoModel.class);

                     //Recuperando os dados dos atributos do Objeto do tipo BoloVendidoModel:

                        nomeBoloVendido = boloVendidoModel.getNomeBolo();
                        valorVendaBoloVendidoCadastradoFirebase = boloVendidoModel.getVelorVenda();
                        valorCustoBoloVendidoCadastradoFirebase = boloVendidoModel.getCustoBolo();

                        //verificando realmente se foi atribuído os valores para as variaveís

                        if(nomeBoloVendido != null && valorVendaBoloVendidoCadastradoFirebase > 0 && valorCustoBoloVendidoCadastradoFirebase > 0){

                            //Caso seja realmente os dados estejam devidamente preenchidos os componentes de tela irão exibilos para devida atualização ou não:
                            String textoValoVenda = String.valueOf(valorVendaBoloVendidoCadastradoFirebase);
                            String textoValoCusto = String.valueOf(valorCustoBoloVendidoCadastradoFirebase);
                            edit_nome_bolo_vendido.setText(nomeBoloVendido);
                            edit_valor_venda_bolo_vendido.setText(textoValoVenda);
                            edit_valor_custo_bolo_vendido.setText(textoValoCusto);
                        }else {

                            //Caso algum dos valores retorne de alguma forma errados, o usuário será diretamente direcionado para Activity da Loja:

                            Intent intent = new Intent(EditarBoloVendidoBancoActivity.this, LojaActivity.class);
                            startActivity(intent);
                            finish();;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //Caso tenha algum erro de Excessão vou exibir uma msg no log para poder identificar o erro:

                        Log.i("Erro desconhecido, ", e.getMessage());

                    }
                });
            }

        botaoVoltarTela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Caso não tenha ação nessa tela o usuário pode voltar para tela anterior sem alterar os dados do banco, apertando esse botão

                Intent intent = new Intent(EditarBoloVendidoBancoActivity.this, BolosVendidosActivity2.class);
                startActivity(intent);
                finish();;
            }
        });


            botaoAtualizarBoloVendido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Ao clicar no botão o processo de update do bolo já vendido é iniciado via esse methodo abaixo:

                   updateInformacoesBoloVendido(idRecuperadoBoloVendido);

                }
            });


    }

    private void updateInformacoesBoloVendido(String idRecuperadoBoloVendido) {

        //Recebendo o id o bolo em questão:

        String id = idRecuperadoBoloVendido;

        //Recuperando os dados digitado pelo usuário para atualizar no banco

        nomeUpdate = edit_nome_bolo_vendido.getText().toString();
        valorVendaUpdate = edit_valor_venda_bolo_vendido.getText().toString();
        valorCustoUpdate = edit_valor_custo_bolo_vendido.getText().toString();

        //Verificando se realmente foi ditado corretamente os dados

        if(nomeUpdate != null && valorVendaUpdate != null && valorCustoUpdate != null){

            //Convertendo os valores digitado de String para Double

            valorCustoConvertidoDigitado = Double.parseDouble(valorCustoUpdate);
            valorVendaConvertidoDigitado = Double.parseDouble(valorVendaUpdate);

            Map<String, Object> boloVendidoUpdate = new HashMap<>();
            boloVendidoUpdate.put("idReferencia", id );
            boloVendidoUpdate.put("nomeBolo", nomeUpdate );
            boloVendidoUpdate.put("velorVenda", valorVendaConvertidoDigitado );
            boloVendidoUpdate.put("custoBolo", valorCustoConvertidoDigitado );

            FirebaseFirestore.getInstance().collection("BOLOS_VENDIDOS").document(id).update(boloVendidoUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(EditarBoloVendidoBancoActivity.this, "Sucesso ao atualizar bolo vendido", Toast.LENGTH_SHORT).show();



                    processaAtualizacaoMontante(idRecuperadoMontanteAtual, valorVendaBoloVendidoCadastradoFirebase, valorCustoBoloVendidoCadastradoFirebase);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditarBoloVendidoBancoActivity.this, "Erro ao atualizar bolo vendido", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditarBoloVendidoBancoActivity.this, BolosVendidosActivity2.class);
                    startActivity(intent);
                    finish();
                }
            });



        }else {

            Log.i("Dados Update não inseridos",valorCustoConvertidoDigitado + "e" + valorVendaConvertidoDigitado + " não foram iniciadas");
            Intent intent = new Intent(EditarBoloVendidoBancoActivity.this, BolosVendidosActivity2.class);
            startActivity(intent);
            finish();
        }




        FirebaseFirestore.getInstance().collection("BOLOS_VENDIDOS").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void processaAtualizacaoMontante(String idMontante, double valorVendaBoloVendido, double valorCustoBoloVendido) {

         double valorCustoConvert = valorVendaBoloVendido;
         double valorVendaConvert = valorCustoBoloVendido;


        Toast.makeText(this, "Entrou no processa ", Toast.LENGTH_SHORT).show();



                FirebaseFirestore.getInstance().collection("CAIXA_MENSAL").document(idRecuperadoMontanteAtual).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        CaixaMensalModel caixaMensalModel = documentSnapshot.toObject(CaixaMensalModel.class);
                          identificadorCaixaMensal = caixaMensalModel.getIdentificadorCaixaMensal();
                          mesReferencia = caixaMensalModel.getMesReferencia();
                          quantTotalBolosAdicionadosMensal = caixaMensalModel.getQuantTotalBolosAdicionadosMensal();
                          valorTotalBolosVendidosMensal = caixaMensalModel.getValorTotalBolosVendidosMensal();
                          valorTotalCustosBolosVendidosMensal = caixaMensalModel.getValorTotalCustosBolosVendidosMensal();
                          totalGastoMensal = caixaMensalModel.getTotalGastoMensal();

                        calcularValoresMontante(idRecuperadoMontanteAtual, valorCustoConvert, valorVendaConvert, valorTotalBolosVendidosMensal, valorTotalCustosBolosVendidosMensal, totalGastoMensal );
                        Toast.makeText(EditarBoloVendidoBancoActivity.this, "Entrou no Recupera Caixa Mensal ", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            }




    private void calcularValoresMontante(String idRecuperadoMontAtual, double valorCustoConvert, double valorVendaConvert, double valorCustoMontate, double valorVendaConvertMontate, double totalGastoMensal) {
        Toast.makeText(this, "Entrou no calcula Valores", Toast.LENGTH_SHORT).show();
        resultadoVenda = valorVendaConvertMontate - valorVendaBoloVendidoCadastradoFirebase;
        resultadoCusto = valorCustoMontate - valorCustoBoloVendidoCadastradoFirebase;

        double totalGastoRecebido = totalGastoMensal - valorCustoConvert;


            Toast.makeText(this, "Entrou na verificação dos resultados", Toast.LENGTH_SHORT).show();
            double valorVendaUpdateMontante = resultadoVenda + valorVendaConvertidoDigitado;
            double valorCustoUpdateMontante = resultadoCusto + valorCustoConvertidoDigitado;
            double valorTotalGastoUpdateMontante = totalGastoRecebido + valorCustoConvertidoDigitado;
            int mesUpdateMontante = mesReferencia;
            int quantBolosAdicionadosMesUpdateMontante = quantTotalBolosAdicionadosMensal;
            String idUpdateMontante = idRecuperadoMontAtual;

            Map<String, Object> updateMontante = new HashMap<>();
            updateMontante.put("identificadorCaixaMensal", idUpdateMontante);
            updateMontante.put("mesReferencia", mesUpdateMontante);
            updateMontante.put("quantTotalBolosAdicionadosMensal", quantBolosAdicionadosMesUpdateMontante);
            updateMontante.put("valorTotalBolosVendidosMensal", valorVendaUpdateMontante);
            updateMontante.put("valorTotalCustosBolosVendidosMensal", valorCustoUpdateMontante);
            updateMontante.put("totalGastoMensal", valorTotalGastoUpdateMontante);

            FirebaseFirestore.getInstance().collection("CAIXA_MENSAL").document(idUpdateMontante).update(updateMontante).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(EditarBoloVendidoBancoActivity.this, "Sucesso ao atualizar montante", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(EditarBoloVendidoBancoActivity.this, "Erro ao atualizar montante", Toast.LENGTH_SHORT).show();

                }
            });





        }




}