package com.marcel.a.n.roxha.deliciasdamamae.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ItemEstoqueDAO;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloItemEstoqueDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;

import java.util.ArrayList;
import java.util.List;

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
    private ModeloItemEstoque ModeloItemEstoque, ModeloItemEstoqueAtualizar;
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
         itemKey = getIntent().getStringExtra("idMaquininha");



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
                    System.out.println("modeloEstoque retornado do banco: " + modeloItemEstoque.toString());

                    switch (undMedidaPacoteCadastrado){
                        case "GRAMA(S)":
                            radioButton_gramas.toggle();
                            break;
                        case "ML(S)":
                            radioButton_ml.toggle();

                            break;
                        case "UNIDADE(S)":
                            radioButton_unid.toggle();

                            break;
                        case "LITRO(S)":
                            radioButton_litro.toggle();
                            break;
                        case "KILO(S)":
                            radioButton_kilo.toggle();
                            break;
                        default:
                            break;

                    }

                    switch (unidadeMedidaReceitaItemRecuperado){
                        case "GRAMA(S)":
                            radioButton_gramas_utilizado_na_receita.toggle();
                            break;
                        case "ML(S)":
                            radioButton_ml_utilizado_na_receita.toggle();
                            break;
                        case "UNIDADE(S)":
                            radioButton_unid_utilizado_na_receita.toggle();

                            break;
                        case "LITRO(S)":
                            radioButton_litro_utilizado_na_receita.toggle();
                            break;
                        case "KILO(S)":
                            radioButton_kilo_utilizado_na_receita.toggle();
                            break;
                        case "XICARA(S)":
                            radioButton_xicara_utilizado_na_receita.toggle();
                            break;
                        case "1/2XICARA(S)":
                            radioButton_1_2_xicara_utilizado_na_receita.toggle();
                            break;
                        case "1/3XICARA(S)":
                            radioButton_1_3_xicara_utilizado_na_receita.toggle();
                            break;
                        case "1/4XICARA(S)":
                            radioButton_1_4_xicara_utilizado_na_receita.toggle();
                            break;
                        default:
                            break;

                    }



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

            if(edit_nome_item_estoque.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "PARA CADASTRAR UM ITEM EM ESTOQUE \n É NECESSARIO INFORMAR ALGUNS DADOS COMO O NOME DO PRODUTO", Toast.LENGTH_SHORT).show();
            }else{
                System.out.println("Passou pelo else primeiro");
                String nomeParaVerificarDuplicidade = edit_nome_item_estoque.getText().toString().toUpperCase();
                FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
                CollectionReference collectionReferenceItemEstoque = firestore.collection("ITEM_ESTOQUE");

                collectionReferenceItemEstoque.whereEqualTo("nomeItemEstoque",nomeParaVerificarDuplicidade)
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        System.out.println("Entrou no collections antes do string verifica duplicidade");
                        String verificarDupIdReceita = "";
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();


                        List<String> listaReceita = new ArrayList<>();

                        for (DocumentSnapshot snapshot : snapshotList) {
                            verificarDupIdReceita = snapshot.getId();
                            listaReceita.add(snapshot.getId());
                            System.out.println("Entrou for");
                        }

                        System.out.println("lisraReceita: " + listaReceita);
                        System.out.println("nome para verificar: " + nomeParaVerificarDuplicidade);

                        if(listaReceita.size() > 0){
                            System.out.println("if se lista for maior que zero");
                            AlertDialog.Builder alertDuplicidadeDeNome = new AlertDialog.Builder(AddItemEstoqueActivity.this);
                            alertDuplicidadeDeNome.setTitle("DUPLICIDADE NO NOME");
                            alertDuplicidadeDeNome.setMessage("Identificamos que o nome inserido já existe e por fins financeiros \n" +
                                    " não é possível cadastrar o mesmo item duas vezes, favor insira um nome único para esse item");
                            EditText editText = new EditText(AddItemEstoqueActivity.this);
                            alertDuplicidadeDeNome.setView(editText);
                            alertDuplicidadeDeNome.setCancelable(false);
                            editText.setText(edit_nome_item_estoque.getText().toString());

                            alertDuplicidadeDeNome.setNeutralButton("PRONTO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   String novoNome = editText.getText().toString();
                                   if (novoNome != "" | novoNome != null){
                                       edit_nome_item_estoque.setText(novoNome);
                                   }else{
                                       Toast.makeText(getApplicationContext(), "Não é possível salvar um nome vazio, insira um nome único", Toast.LENGTH_SHORT).show();
                                   }
                                }
                            });
                            alertDuplicidadeDeNome.create();
                            alertDuplicidadeDeNome.show();


                        }else{
                            if(edit_nome_item_estoque.getText().toString().isEmpty() || edit_valor_item_estoque.getText().toString().isEmpty()
                                    || edit_quantPacote_item_estoque.getText().toString().isEmpty() || edit_quantUsadaReceita_item_estoque.getText().toString().isEmpty()){
                                Toast.makeText(AddItemEstoqueActivity.this, "PARA CADASTRAR UM ITEM EM ESTOQUE \n" +
                                        " É NECESSÁIO INFORMAR OS DADOS DE TODOS OS CAMPOS SOLICITADOS", Toast.LENGTH_LONG).show();

                            }else if(!radioButton_gramas.isChecked() && !radioButton_ml.isChecked() && !radioButton_unid.isChecked()
                                    && !radioButton_litro.isChecked() && !radioButton_kilo.isChecked()) {

                                Toast.makeText(AddItemEstoqueActivity.this, "PARA CADASTRAR UM ITEM EM ESTOQUE \n" +
                                        " É NECESSÁIO INFORMAR A UNIDADE DE MEDIDA UTILIZADA NO ITEM PARA CALCULOS", Toast.LENGTH_LONG).show();

                            }else if ( !radioButton_gramas_utilizado_na_receita.isChecked() && !radioButton_ml_utilizado_na_receita.isChecked() && !radioButton_unid_utilizado_na_receita.isChecked()
                                    && !radioButton_litro_utilizado_na_receita.isChecked() && !radioButton_kilo_utilizado_na_receita.isChecked() && !radioButton_xicara_utilizado_na_receita.isChecked()
                                    && !radioButton_1_2_xicara_utilizado_na_receita.isChecked() && !radioButton_1_3_xicara_utilizado_na_receita.isChecked() && !radioButton_1_4_xicara_utilizado_na_receita.isChecked() && !radioButton_xicara_utilizado_na_receita.isChecked()){
                                Toast.makeText(AddItemEstoqueActivity.this, "PARA CADASTRAR UM ITEM EM ESTOQUE \n" +
                                        " É NECESSÁIO INFORMAR A UNIDADE DE MEDIDA UTILIZADA NA RECEITA DO ITEM PARA CALCULOS", Toast.LENGTH_LONG).show();
                            }else {

                                ModeloItemEstoque cadastrarNovoItemEstoque = new ModeloItemEstoque();
                                ModeloItemEstoqueDAO modeloItemEstoqueDAO = new ModeloItemEstoqueDAO(AddItemEstoqueActivity.this);



                                String nomeItemDigitado = edit_nome_item_estoque.getText().toString().trim();
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
                                cadastrarNovoItemEstoque.calcularQuantidadeTotalItemEmEstoqueEmGramas();
                                cadastrarNovoItemEstoque.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
                                cadastrarNovoItemEstoque.calcularCustoTotalDoItemEmEstoque();

                                modeloItemEstoqueDAO.salvarItemEstoque(cadastrarNovoItemEstoque);

                                System.out.println("ITEM A SER SALVO: " + cadastrarNovoItemEstoque.toString());
                                // modeloItemEstoqueDAO.salvarItemEstoque(cadastrarNovoItemEstoque);

                            }

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                }


            }



            if (item.getItemId() == R.id.bt_atualizar){



                ModeloItemEstoque atualizarItemEstoque = new ModeloItemEstoque();
                ModeloItemEstoqueDAO modeloItemEstoqueDAO = new ModeloItemEstoqueDAO(AddItemEstoqueActivity.this);



                String nomeItemDigitado = edit_nome_item_estoque.getText().toString().trim();
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


                atualizarItemEstoque.setNomeItemEstoque(nomeItemDigitado);
                atualizarItemEstoque.setValorIndividualItemEstoque(valorItemDigitado);
                atualizarItemEstoque.setQuantidadePorVolumeItemEstoque(valorItemDigitado);
                atualizarItemEstoque.setQuantidadeTotalItemEstoque(quantItemDigitado);
                atualizarItemEstoque.setQuantidadePorVolumeItemEstoque(quantPacoteItemDigitado);
                atualizarItemEstoque.setQuantidadeUtilizadaNasReceitas(quantUsadaReceitaItemDigitado);
                atualizarItemEstoque.setUnidadeMedidaPacoteItemEstoque(unidadeMedidaItemEstoque);
                atualizarItemEstoque.setUnidadeMedidaUtilizadoNasReceitas(unidadeMedidaItemNaReceita);

                atualizarItemEstoque.calcularValorFracionadoModeloItemEstoque();
                atualizarItemEstoque.calcularValorItemPorReceita();
                atualizarItemEstoque.calcularQuantidadeTotalItemEmEstoqueEmGramas();
                atualizarItemEstoque.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
                atualizarItemEstoque.calcularCustoTotalDoItemEmEstoque();

                modeloItemEstoqueDAO.atualizarItemEstoque(itemKey, atualizarItemEstoque);


            }





        return super.onOptionsItemSelected(item);
    }

    public void carregarTelaEstoque() {
        Intent intent = new Intent(AddItemEstoqueActivity.this, EstoqueActivity.class);
        startActivity(intent);

    }
}