package com.marcel.a.n.roxha.deliciasdamamae.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ItemEstoqueDAO;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ModeloItemEstoqueDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;

import java.util.HashMap;
import java.util.Map;

public class AddItemEstoqueActivity extends AppCompatActivity {


    //Componentes de tela:

    //-----------------------------------------------INPUTS-------------------USER
    private TextInputEditText edit_nome_item_estoque;
    private TextInputEditText edit_valor_item_estoque;
    private TextInputEditText edit_quantTotal_item_estoque;
    private TextInputEditText edit_quantPacote_item_estoque;
    private TextInputEditText edit_quantUsadaReceita_item_estoque;

    //-----------------------------------------------RADIOBUTTON-------------------USER
    private RadioButton
            radioButton_gramas,
            radioButton_ml,
            radioButton_unid,
            radioButton_litro,
            radioButton_kilo,

            radioButton_gramas_utilizado_na_receita,
            radioButton_ml_utilizado_na_receita,
            radioButton_unid_utilizado_na_receita,
            radioButton_litro_utilizado_na_receita,
            radioButton_kilo_utilizado_na_receita,
            radioButton_xicara_utilizado_na_receita,
            radioButton_1_2_xicara_utilizado_na_receita,
            radioButton_1_3_xicara_utilizado_na_receita,
            radioButton_1_4_xicara_utilizado_na_receita;



    //Classes:
    private ItemEstoqueModel itemEstoqueModel, itemEstoqueModelAtualizar;
    private ItemEstoqueDAO itemEstoqueDAO;
    private ModeloItemEstoque modeloItemEstoqueAtualizar;

    //Firebase
    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    private CollectionReference ref = db.collection("Item_Estoque");


    //Variaveis temporarias:
    private String itemKey = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);

        setContentView(R.layout.activity_add_item_estoque);


        //----->identificando os componentes da tela de acordo com o id inserido<-----
        edit_nome_item_estoque = findViewById(R.id.edit_nome_item_estoque_id);
        edit_valor_item_estoque = findViewById(R.id.edit_valor_item_estoque_id);
        edit_quantTotal_item_estoque = findViewById(R.id.edit_quant_item_estoque_id);
        edit_quantPacote_item_estoque = findViewById(R.id.edit_quant_pacote_item_estoque_id);
        edit_quantUsadaReceita_item_estoque = findViewById(R.id.edit_quant_usada_receita_id);

        radioButton_gramas = findViewById(R.id.radioButton_grama_id);
        radioButton_ml = findViewById(R.id.radioButton_ml_id);
        radioButton_unid = findViewById(R.id.radioButton_und_id);
        radioButton_kilo = findViewById(R.id.radioButton_Kilo_id);
        radioButton_litro = findViewById(R.id.radioButton_Litro_id);

        radioButton_gramas_utilizado_na_receita = findViewById(R.id.radioButton_grama_utilizada_receita_id);
        radioButton_ml_utilizado_na_receita = findViewById(R.id.radioButton_ml_utilizada_receita_id);
        radioButton_unid_utilizado_na_receita = findViewById(R.id.radioButton_und_utilizada_receita_id);
        radioButton_litro_utilizado_na_receita = findViewById(R.id.radioButton_Litro_utilizada_receita_id);
        radioButton_kilo_utilizado_na_receita = findViewById(R.id.radioButton_Kilo_utilizada_receita_id);
        radioButton_xicara_utilizado_na_receita = findViewById(R.id.radioButton_xicara_utilizada_receita_id);
        radioButton_1_2_xicara_utilizado_na_receita = findViewById(R.id.radioButton_1_2_xicara_utilizada_receita_id);
        radioButton_1_3_xicara_utilizado_na_receita = findViewById(R.id.radioButton_1_3_xicara_utilizada_receita_id);
        radioButton_1_4_xicara_utilizado_na_receita = findViewById(R.id.radioButton_1_4_xicara_utilizada_receita_id);


        //Recuperando a chave do item estoque caso seja atualização
         itemKey = getIntent().getStringExtra("itemKey");

         /*Verificando se o item recuperado é diferente de nulo, pois se for direfente de nulo significa
             que é uma atualização e os inputs e RadionButtons serão preenchidos com os dados*/
        if (itemKey != null) {

            //Quando a condição for verdadeira, será recuperado o item do estoque no banco de dados
            FirebaseFirestore.getInstance().collection("ITEM_ESTOQUE").document(itemKey).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    ModeloItemEstoque modeloItemEstoque = documentSnapshot.toObject(ModeloItemEstoque.class);

                    String undMedidaPacoteCadastrado = modeloItemEstoque.getUnidadeMedidaPacoteItemEstoque();
                    String unidadeMedidaReceitaItemRecuperado = modeloItemEstoque.getUnidadeMedidaUtilizadoNasReceitas();

                    edit_nome_item_estoque.setText(modeloItemEstoque.getNomeItemEstoque());
                    edit_valor_item_estoque.setText(modeloItemEstoque.getValorIndividualItemEstoque());
                    edit_quantTotal_item_estoque.setText(modeloItemEstoque.getQuantidadeTotalItemEstoque());
                    edit_quantPacote_item_estoque.setText(modeloItemEstoque.getQuantidadePorVolumeItemEstoque());
                    edit_quantUsadaReceita_item_estoque.setText(modeloItemEstoque.getQuantidadeUtilizadaNasReceitas());



                }
            });
        }



    }

    //Verificando qual icone será exibido no menu suspenso de acordo com a condição de atualização ou cadastro
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        //Verificando a chave para alternar entre os icones
        if(itemKey != null) {
            //Se a condição for verdaderia, será uma atualização, então o icone utilizdo é o de atualização
            getMenuInflater().inflate(R.menu.menu_atualizar_item_estoque, menu);

        }else {
            //Se a condição for falsa, será um cadastro, então o icone utilizado é o de salvar
            getMenuInflater().inflate(R.menu.menu_salvar_item_estoque, menu);
        }

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId() == R.id.salvar_item_estoque){

            if(edit_nome_item_estoque.getText().toString().isEmpty() || edit_valor_item_estoque.getText().toString().isEmpty()
                    || edit_quantPacote_item_estoque.getText().toString().isEmpty() || edit_quantUsadaReceita_item_estoque.getText().toString().isEmpty()){
                Toast.makeText(AddItemEstoqueActivity.this, "PARA CADASTRAR UM ITEM EM ESTOQUE \n" +
                        " É NECESSÁIO INFORMAR OS DADOS DE TODOS OS CAMPOS SOLICITADOS", Toast.LENGTH_LONG).show();

             }else if(!radioButton_gramas.isChecked() && !radioButton_ml.isChecked() && !radioButton_unid.isChecked()
                        && !radioButton_litro.isChecked() && !radioButton_kilo.isChecked()) {

                Toast.makeText(AddItemEstoqueActivity.this, "PARA CADASTRAR UM ITEM EM ESTOQUE \n" +
                        " É NECESSÁIO INFORMAR A UNIDADE DE MEDIDA UTILIZADA NO ITEM PARA CALCULOS", Toast.LENGTH_LONG).show();

             }else if ( !radioButton_gramas_utilizado_na_receita.isChecked() && !radioButton_ml_utilizado_na_receita.isChecked() && !radioButton_unid_utilizado_na_receita.isChecked()
                        && !radioButton_litro_utilizado_na_receita.isChecked() && !radioButton_kilo_utilizado_na_receita.isChecked() && !radioButton_xicara_utilizado_na_receita.isChecked()){
                Toast.makeText(AddItemEstoqueActivity.this, "PARA CADASTRAR UM ITEM EM ESTOQUE \n" +
                        " É NECESSÁIO INFORMAR A UNIDADE DE MEDIDA UTILIZADA NA RECEITA DO ITEM PARA CALCULOS", Toast.LENGTH_LONG).show();
            }else {

                 ModeloItemEstoque cadastrarNovoItemEstoque = new ModeloItemEstoque();
                 ModeloItemEstoqueDAO modeloItemEstoqueDAO = new ModeloItemEstoqueDAO(AddItemEstoqueActivity.this);



                String nomeItemDigitado = edit_nome_item_estoque.getText().toString();
                String valorItemDigitado = edit_valor_item_estoque.getText().toString();
                String quantItemDigitado = edit_quantTotal_item_estoque.getText().toString();
                String quantPacoteItemDigitado = edit_quantPacote_item_estoque.getText().toString();
                String quantUsadaReceitaItemDigitado = edit_quantUsadaReceita_item_estoque.getText().toString();
                String unidadeMedidaItemEstoque = "";
                String unidadeMedidaItemNaReceita = "";

                if(radioButton_gramas.isChecked()){
                    unidadeMedidaItemEstoque = "GRAMA(S)";

                }else if (radioButton_ml.isChecked()){
                    unidadeMedidaItemEstoque = "ML(S)";

                }else if (radioButton_unid.isChecked()){
                    unidadeMedidaItemEstoque = "UNIDADE(S)";

                }else if (radioButton_litro.isChecked()){
                    unidadeMedidaItemEstoque = "LITRO(S)";

                }else if (radioButton_kilo.isChecked()){
                    unidadeMedidaItemEstoque = "KILO(S)";
                }


                if(radioButton_gramas_utilizado_na_receita.isChecked()){
                    unidadeMedidaItemNaReceita = "GRAMA(S)";

                }else if (radioButton_ml_utilizado_na_receita.isChecked()){
                    unidadeMedidaItemNaReceita = "ML(S)";

                }else if (radioButton_unid_utilizado_na_receita.isChecked()){
                    unidadeMedidaItemNaReceita = "UNIDADE(S)";

                }else if (radioButton_litro_utilizado_na_receita.isChecked()){
                    unidadeMedidaItemNaReceita = "LITRO(S)";

                }else if (radioButton_kilo_utilizado_na_receita.isChecked()){
                    unidadeMedidaItemNaReceita = "KILO(S)";

                }else if (radioButton_xicara_utilizado_na_receita.isChecked()){
                    unidadeMedidaItemNaReceita = "XICARA(S)";

                }else if (radioButton_1_2_xicara_utilizado_na_receita.isChecked()){
                    unidadeMedidaItemNaReceita = "1/2XICARA(S)";

                }else if (radioButton_1_3_xicara_utilizado_na_receita.isChecked()){
                    unidadeMedidaItemNaReceita = "1/3XICARA(S)";

                }else if (radioButton_1_4_xicara_utilizado_na_receita.isChecked()){
                    unidadeMedidaItemNaReceita = "1/4XICARA(S)";

                }


                cadastrarNovoItemEstoque.setNomeItemEstoque(nomeItemDigitado);
                cadastrarNovoItemEstoque.setValorIndividualItemEstoque(valorItemDigitado);
                cadastrarNovoItemEstoque.setQuantidadePorVolumeItemEstoque(valorItemDigitado);
                cadastrarNovoItemEstoque.setQuantidadeTotalItemEstoque(quantItemDigitado);
                cadastrarNovoItemEstoque.setQuantidadePorVolumeItemEstoque(quantPacoteItemDigitado);
                cadastrarNovoItemEstoque.setQuantidadeUtilizadaNasReceitas(quantUsadaReceitaItemDigitado);
                cadastrarNovoItemEstoque.setUnidadeMedidaPacoteItemEstoque(unidadeMedidaItemEstoque);
                cadastrarNovoItemEstoque.setUnidadeMedidaUtilizadoNasReceitas(unidadeMedidaItemNaReceita);

                cadastrarNovoItemEstoque.calcularValorFracionadoModeloItemEstoque();
                cadastrarNovoItemEstoque.calcularValorItemPorReceita();

                //     modeloItemEstoqueDAO.salvarItemEstoque(cadastrarNovoItemEstoque);

                System.out.println("ITEM A SER SALVO: " + cadastrarNovoItemEstoque.toString());

               boolean resultado = modeloItemEstoqueDAO.salvarItemEstoque(cadastrarNovoItemEstoque);

               if(resultado == true){
                   carregarTelaEstoque();
               }
            }

            }



            if (item.getItemId() == R.id.bt_atualizar){
            Toast.makeText(getApplicationContext(), "Clicou no atualizar", Toast.LENGTH_SHORT).show();
        }



      /*  switch (item.getItemId()) {

            case R.id.salvar_item_estoque:

              *//* // ModeloItemEstoque cadastrarNovoItemEstoque = new ModeloItemEstoque();
                //ModeloItemEstoqueDAO modeloItemEstoqueDAO = new ModeloItemEstoqueDAO(AddItemEstoqueActivity.this);

                if(1 == 1){
                    Toast.makeText(AddItemEstoqueActivity.this, "PARA CADASTRAR UM ITEM EM ESTOQUE \n" +
                                                                 " É NECESSÁIO INFORMAR OS DADOS DE TODOS OS CAMPOS SOLICITADOS", Toast.LENGTH_LONG).show();
                }else {






                    String nomeItemDigitado = edit_nome_item_estoque.getText().toString();
                    String valorItemDigitado = edit_valor_item_estoque.getText().toString();
                    String quantItemDigitado = edit_quantTotal_item_estoque.getText().toString();
                    String quantPacoteItemDigitado = edit_quantPacote_item_estoque.getText().toString();
                    String quantUsadaReceitaItemDigitado = edit_quantUsadaReceita_item_estoque.getText().toString();


                    String unidadeMedidaItemEstoque = "";
                    String unidadeMedidaItemNaReceita = "";
                    if(radioButton_gramas.isChecked()){
                        unidadeMedidaItemEstoque = "GRAMAS";

                    }else if (radioButton_ml.isChecked()){
                        unidadeMedidaItemEstoque = "ML(S)";

                    }else if (radioButton_unid.isChecked()){
                        unidadeMedidaItemEstoque = "UNIDADE(S)";

                    }else if (radioButton_litro.isChecked()){
                        unidadeMedidaItemEstoque = "LITRO(OS)";

                    }else if (radioButton_kilo.isChecked()){
                        unidadeMedidaItemEstoque = "KILO(OS)";
                    }


                    if(radioButton_gramas_utilizado_na_receita.isChecked()){
                        unidadeMedidaItemNaReceita = "GRAMAS";

                    }else if (radioButton_ml_utilizado_na_receita.isChecked()){
                        unidadeMedidaItemNaReceita = "ML(S)";

                    }else if (radioButton_unid_utilizado_na_receita.isChecked()){
                        unidadeMedidaItemNaReceita = "UNIDADE(S)";

                    }else if (radioButton_litro_utilizado_na_receita.isChecked()){
                        unidadeMedidaItemNaReceita = "LITRO(OS)";

                    }else if (radioButton_kilo_utilizado_na_receita.isChecked()){
                        unidadeMedidaItemNaReceita = "KILO(OS)";

                    }

                    if(unidadeMedidaItemNaReceita != "" && unidadeMedidaItemEstoque != ""){

*//**//*
                        cadastrarNovoItemEstoque.setNomeItemEstoque(nomeItemDigitado);
                        cadastrarNovoItemEstoque.setValorIndividualItemEstoque(valorItemDigitado);
                        cadastrarNovoItemEstoque.setQuantidadeTotalItemEstoque(quantItemDigitado);
                        cadastrarNovoItemEstoque.setQuantidadePorVolumeItemEstoque(quantPacoteItemDigitado);
                        cadastrarNovoItemEstoque.setQuantidadeUtilizadaNasReceitas(quantUsadaReceitaItemDigitado);

                        cadastrarNovoItemEstoque.calcularValorFracionadoModeloItemEstoque();
                        cadastrarNovoItemEstoque.calcularValorItemPorReceita();


                        modeloItemEstoqueDAO.salvarItemEstoque(cadastrarNovoItemEstoque);*//**//*
                    }
                }

*//*

                break;
            case R.id.bt_atualizar:




*//*
                ModeloItemEstoqueDAO atualizarModeloItemEstoqueDAO = new ModeloItemEstoqueDAO(AddItemEstoqueActivity.this);

                String nomeItemDigitadoAtualiza = edit_nome_item_estoque.getText().toString();
                String valorItemDigitadoAtualiza = edit_valor_item_estoque.getText().toString();
                String quantItemDigitadoAtualiza = edit_quantTotal_item_estoque.getText().toString();
                String quantPacoteItemDigitadoAtualiza = edit_quantPacote_item_estoque.getText().toString();
                String quantUsadaReceitaItemDigitadoAtualiza = edit_quantUsadaReceita_item_estoque.getText().toString();

                modeloItemEstoqueAtualizar = new ModeloItemEstoque();

                modeloItemEstoqueAtualizar.setNomeItemEstoque(nomeItemDigitadoAtualiza);
                modeloItemEstoqueAtualizar.setValorIndividualItemEstoque(valorItemDigitadoAtualiza);
                modeloItemEstoqueAtualizar.setQuantidadeTotalItemEstoque(quantItemDigitadoAtualiza);
                modeloItemEstoqueAtualizar.setQuantidadePorVolumeItemEstoque(quantPacoteItemDigitadoAtualiza);
                modeloItemEstoqueAtualizar.setQuantidadeUtilizadaNasReceitas(quantUsadaReceitaItemDigitadoAtualiza);

         *//**//*       if (radioButton_gramas_ml_unid.isChecked()) {
                    modeloItemEstoqueAtualizar.setUnidadeMedidaPacoteItemEstoque("1");

                } else if (radioButton_litro_kilo.isChecked()) {
                    modeloItemEstoqueAtualizar.setUnidadeMedidaPacoteItemEstoque("1000");
                }

                if (radioButton_gramas_ml_unid_utilizado_na_receita.isChecked()) {
                    modeloItemEstoqueAtualizar.setUnidadeMedidaUtilizadoNasReceitas("1");


                } else if (radioButton_litro_kilo_utilizado_na_receita.isChecked()) {
                    modeloItemEstoqueAtualizar.setUnidadeMedidaUtilizadoNasReceitas("1000");
                }
*//**//*
                modeloItemEstoqueAtualizar.calcularValorFracionadoModeloItemEstoque();
                modeloItemEstoqueAtualizar.calcularValorItemPorReceita();


                atualizarModeloItemEstoqueDAO.atualizarItemEstoque(itemKey, modeloItemEstoqueAtualizar);


               // atualizarItem();*//*


            default:
                carregarTelaEstoque();


        }*/
        return super.onOptionsItemSelected(item);
    }

    public void carregarTelaEstoque() {
        Intent intent = new Intent(AddItemEstoqueActivity.this, EstoqueActivity.class);
        startActivity(intent);

    }
}