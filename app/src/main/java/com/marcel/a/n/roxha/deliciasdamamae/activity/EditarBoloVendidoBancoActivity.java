package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.BoloVendidoModel;

public class EditarBoloVendidoBancoActivity extends AppCompatActivity {

    //Componentes de tela:

    private Button botaoVoltarTela;
    private Button botaoAtualizarBoloVendido = null;
    private TextInputEditText edit_nome_bolo_vendido;
    private TextInputEditText edit_valor_venda_bolo_vendido;
    private TextInputEditText edit_valor_custo_bolo_vendido;

    //Variaveis:

    private String idRecuperadoBoloVendido = null;
    private String nomeBoloVendido = null;
    private String valorVendaBoloVendido = null;
    private String valorCustoBoloVendido = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_bolo_vendido_banco);


        botaoVoltarTela = findViewById(R.id.botao_voltar_bolo_vendido_activity_id);
        botaoAtualizarBoloVendido = findViewById(R.id.botao_atualizar_bolo_vendido_id);
        edit_nome_bolo_vendido = findViewById(R.id.edit_nome_bolo_vendido_id);
        edit_valor_venda_bolo_vendido = findViewById(R.id.edit_valor_venda_bolo_id);
        edit_valor_custo_bolo_vendido = findViewById(R.id.edit_valor_custo_bolo_vendido_id);


        idRecuperadoBoloVendido = getIntent().getStringExtra("idBoloVendido");

            if(idRecuperadoBoloVendido != null){
                FirebaseFirestore.getInstance().collection("BOLOS_VENDIDOS").document(idRecuperadoBoloVendido).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        BoloVendidoModel boloVendidoModel = documentSnapshot.toObject(BoloVendidoModel.class);

                        nomeBoloVendido = boloVendidoModel.getNomeBolo();
                        valorVendaBoloVendido = String.valueOf(boloVendidoModel.getVelorVenda());
                        valorCustoBoloVendido = String.valueOf(boloVendidoModel.getCustoBolo());

                        edit_nome_bolo_vendido.setText(nomeBoloVendido);
                        edit_valor_venda_bolo_vendido.setText(valorVendaBoloVendido);
                        edit_valor_custo_bolo_vendido.setText(valorCustoBoloVendido);



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            }



    }
}