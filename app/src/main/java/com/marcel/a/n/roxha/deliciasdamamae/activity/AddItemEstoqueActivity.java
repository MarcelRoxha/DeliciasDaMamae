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
    private RadioButton radioButton_gramas_ml_unid, radioButton_litro_kilo, radioButton_gramas_ml_unid_utilizado_na_receita, radioButton_litro_kilo_utilizado_na_receita;


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
        radioButton_gramas_ml_unid = findViewById(R.id.radioButton_grama_ml_und_id);
        radioButton_litro_kilo = findViewById(R.id.radioButton_Litro_Kilo_id);
        radioButton_gramas_ml_unid_utilizado_na_receita = findViewById(R.id.radioButton_gramas_ml_unidade_utilizada_na_receita_id);
        radioButton_litro_kilo_utilizado_na_receita = findViewById(R.id.radioButton_litros_kilo_utilizada_na_receita_id);

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
                    edit_quantPacote_item_estoque.setText(modeloItemEstoque.getQuantidadePorPacoteItemEstoque());
                    edit_quantUsadaReceita_item_estoque.setText(modeloItemEstoque.getQuantidadeUtilizadaNasReceitas());

                    if (undMedidaPacoteCadastrado.contains("1000")) {
                        radioButton_litro_kilo.toggle();
                    } else  {
                        radioButton_gramas_ml_unid.toggle();
                    }

                    if(unidadeMedidaReceitaItemRecuperado.contains("1000")){
                        radioButton_litro_kilo_utilizado_na_receita.toggle();
                    }else{
                        radioButton_gramas_ml_unid_utilizado_na_receita.toggle();
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

        switch (item.getItemId()) {

            case R.id.salvar_item_estoque:

                ModeloItemEstoque cadastrarNovoItemEstoque = new ModeloItemEstoque();
                ModeloItemEstoqueDAO modeloItemEstoqueDAO = new ModeloItemEstoqueDAO(AddItemEstoqueActivity.this);

                String nomeItemDigitado = edit_nome_item_estoque.getText().toString();
                String valorItemDigitado = edit_valor_item_estoque.getText().toString();
                String quantItemDigitado = edit_quantTotal_item_estoque.getText().toString();
                String quantPacoteItemDigitado = edit_quantPacote_item_estoque.getText().toString();
                String quantUsadaReceitaItemDigitado = edit_quantUsadaReceita_item_estoque.getText().toString();



                cadastrarNovoItemEstoque.setNomeItemEstoque(nomeItemDigitado);
                cadastrarNovoItemEstoque.setValorIndividualItemEstoque(valorItemDigitado);
                cadastrarNovoItemEstoque.setQuantidadeTotalItemEstoque(quantItemDigitado);
                cadastrarNovoItemEstoque.setQuantidadePorPacoteItemEstoque(quantPacoteItemDigitado);
                cadastrarNovoItemEstoque.setQuantidadeUtilizadaNasReceitas(quantUsadaReceitaItemDigitado);

                if (radioButton_gramas_ml_unid.isChecked()) {
                    cadastrarNovoItemEstoque.setUnidadeMedidaPacoteItemEstoque("1");

                } else if (radioButton_litro_kilo.isChecked()) {
                    cadastrarNovoItemEstoque.setUnidadeMedidaPacoteItemEstoque("1000");
                }

                if (radioButton_gramas_ml_unid_utilizado_na_receita.isChecked()) {
                    cadastrarNovoItemEstoque.setUnidadeMedidaUtilizadoNasReceitas("1");


                } else if (radioButton_litro_kilo_utilizado_na_receita.isChecked()) {
                    cadastrarNovoItemEstoque.setUnidadeMedidaUtilizadoNasReceitas("1000");
                }

                cadastrarNovoItemEstoque.calcularValorFracionadoModeloItemEstoque();
                cadastrarNovoItemEstoque.calcularValorItemPorReceita();


                modeloItemEstoqueDAO.salvarItemEstoque(cadastrarNovoItemEstoque);


            case R.id.bt_atualizar:


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
                modeloItemEstoqueAtualizar.setQuantidadePorPacoteItemEstoque(quantPacoteItemDigitadoAtualiza);
                modeloItemEstoqueAtualizar.setQuantidadeUtilizadaNasReceitas(quantUsadaReceitaItemDigitadoAtualiza);

                if (radioButton_gramas_ml_unid.isChecked()) {
                    modeloItemEstoqueAtualizar.setUnidadeMedidaPacoteItemEstoque("1");

                } else if (radioButton_litro_kilo.isChecked()) {
                    modeloItemEstoqueAtualizar.setUnidadeMedidaPacoteItemEstoque("1000");
                }

                if (radioButton_gramas_ml_unid_utilizado_na_receita.isChecked()) {
                    modeloItemEstoqueAtualizar.setUnidadeMedidaUtilizadoNasReceitas("1");


                } else if (radioButton_litro_kilo_utilizado_na_receita.isChecked()) {
                    modeloItemEstoqueAtualizar.setUnidadeMedidaUtilizadoNasReceitas("1000");
                }

                modeloItemEstoqueAtualizar.calcularValorFracionadoModeloItemEstoque();
                modeloItemEstoqueAtualizar.calcularValorItemPorReceita();


                atualizarModeloItemEstoqueDAO.atualizarItemEstoque(itemKey, modeloItemEstoqueAtualizar);


               // atualizarItem();


            default:
                carregarTelaEstoque();
                return super.onOptionsItemSelected(item);

        }

    }

    public void carregarTelaEstoque() {
        Intent intent = new Intent(AddItemEstoqueActivity.this, EstoqueActivity.class);
        startActivity(intent);

    }
}