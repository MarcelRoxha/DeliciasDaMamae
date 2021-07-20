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
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;

import java.util.HashMap;
import java.util.Map;

public class AddItemEstoqueActivity extends AppCompatActivity {

    private TextInputEditText edit_nome_item_estoque;
    private TextInputEditText edit_valor_item_estoque;
    private TextInputEditText edit_quantTotal_item_estoque;
    private TextInputEditText edit_quantPacote_item_estoque;
    private TextInputEditText edit_quantUsadaReceita_item_estoque;

    private RadioButton radioButton_gramas_ml_unid, radioButton_litro_kilo;
    private RadioButton radioButton_gramas_ml_unid_receita, radioButton_litro_kilo_receita;

    private ItemEstoqueModel itemEstoqueModel, itemEstoqueModelAtualizar;
    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    private CollectionReference ref = db.collection("Item_Estoque");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_estoque);

        edit_nome_item_estoque = findViewById(R.id.edit_nome_item_estoque_id);
        edit_valor_item_estoque = findViewById(R.id.edit_valor_item_estoque_id);
        edit_quantTotal_item_estoque = findViewById(R.id.edit_quant_item_estoque_id);
        edit_quantPacote_item_estoque = findViewById(R.id.edit_quant_pacote_item_estoque_id);
        edit_quantUsadaReceita_item_estoque = findViewById(R.id.edit_quant_usada_receita_id);

        radioButton_gramas_ml_unid = findViewById(R.id.radioButton_grama_ml_und_id);


        radioButton_litro_kilo = findViewById(R.id.radioButton_Litro_Kilo_id);


        String itemKey = getIntent().getStringExtra("itemKey");

        if (itemKey != null) {

            FirebaseFirestore.getInstance().collection("Item_Estoque").document(itemKey).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                    String undMedidaRecebida = itemEstoqueModel.getUnidMedida();


                    edit_nome_item_estoque.setText(itemEstoqueModel.getNameItem());
                    edit_valor_item_estoque.setText(itemEstoqueModel.getValorItem());
                    edit_quantTotal_item_estoque.setText(itemEstoqueModel.getQuantItem());
                    edit_quantPacote_item_estoque.setText(itemEstoqueModel.getQuantPacote());
                    edit_quantUsadaReceita_item_estoque.setText(itemEstoqueModel.getQuantUsadaReceita());

                    if (undMedidaRecebida.contains("1000")) {
                        radioButton_litro_kilo.toggle();
                    } else  {
                        radioButton_gramas_ml_unid.toggle();
                    }


                }
            });
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        String itemKey = getIntent().getStringExtra("itemKey");

        if(itemKey != null) {
            getMenuInflater().inflate(R.menu.menu_atualizar_item_estoque, menu);

        }else {
            getMenuInflater().inflate(R.menu.menu_salvar_item_estoque, menu);
        }

        return super.onCreateOptionsMenu(menu);

    }

    public void atualizarItem(){

        String itemKey = getIntent().getStringExtra("itemKey");

        if (itemKey != null) {

            FirebaseFirestore.getInstance().collection("Item_Estoque").document(itemKey).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                    final String IdEstoque = "Estoque_DeliciasDaMamae";

                    String nomeItemDigitado = edit_nome_item_estoque.getText().toString();
                    String valorItemDigitado = edit_valor_item_estoque.getText().toString();
                    String quantItemDigitado = edit_quantTotal_item_estoque.getText().toString();
                    String quantPacoteItemDigitado = edit_quantPacote_item_estoque.getText().toString();
                    String quantUsadaReceitaItemDigitado = edit_quantUsadaReceita_item_estoque.getText().toString();

                    if (radioButton_gramas_ml_unid.isChecked()) {
                        itemEstoqueModel.setUnidMedida("1");
                    } else if (radioButton_litro_kilo.isChecked()) {
                        itemEstoqueModel.setUnidMedida("1000");
                    }


                     DocumentReference ref = FirebaseFirestore.getInstance().collection("Item_Estoque")
                                                                                 .document(itemKey);

                    Map<String, Object > itemAtualizado = new HashMap<>();

                    itemAtualizado.put("nameItem", nomeItemDigitado);
                    itemAtualizado.put("versionEstoque", IdEstoque);
                    itemAtualizado.put("valorItem",valorItemDigitado);
                    itemAtualizado.put("quantItem", quantItemDigitado);
                    itemAtualizado.put("unidMedida", itemEstoqueModel.getUnidMedida());
                    /*itemAtualizado.put("unidReceita", getUnidReceita());*/
                    itemAtualizado.put("valorFracionado",       itemEstoqueModel.calcularValorFracionado());
                    itemAtualizado.put("valorItemPorReceita", itemEstoqueModel.valorItemPorReceita());
                    itemAtualizado.put("quantPacote", quantPacoteItemDigitado);
                    itemAtualizado.put("quantUsadaReceita", quantUsadaReceitaItemDigitado);

                    ref.update(itemAtualizado).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(AddItemEstoqueActivity.this, "Sucesso ao atualizar item", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.salvar_item_estoque:

                String nomeItemDigitado = edit_nome_item_estoque.getText().toString();
                String valorItemDigitado = edit_valor_item_estoque.getText().toString();
                String quantItemDigitado = edit_quantTotal_item_estoque.getText().toString();
                String quantPacoteItemDigitado = edit_quantPacote_item_estoque.getText().toString();
                String quantUsadaReceitaItemDigitado = edit_quantUsadaReceita_item_estoque.getText().toString();

                itemEstoqueModel = new ItemEstoqueModel();

                itemEstoqueModel.setNameItem(nomeItemDigitado);
                itemEstoqueModel.setValorItem(valorItemDigitado);
                itemEstoqueModel.setQuantItem(quantItemDigitado);
                itemEstoqueModel.setQuantPacote(quantPacoteItemDigitado);
                itemEstoqueModel.setQuantUsadaReceita(quantUsadaReceitaItemDigitado);

                if (radioButton_gramas_ml_unid.isChecked()) {
                   itemEstoqueModel.setUnidMedida("1");
                } else if (radioButton_litro_kilo.isChecked()) {
                  itemEstoqueModel.setUnidMedida("1000");
                }


                itemEstoqueModel.salvarItemEstoque();
                Toast.makeText(this, "Sucesso ao salvar item em estoque", Toast.LENGTH_SHORT).show();

            case R.id.bt_atualizar:

                atualizarItem();


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