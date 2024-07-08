package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosFixos;

import java.util.HashMap;
import java.util.Map;

public class ModeloGastosFixosDAO implements InterfaceModeloGastosFixosDAO{

    private static final String COLLECTION_GASTOS_FIXOS = "GASTOS_FIXOS";
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference collectionReference = firestore.collection(COLLECTION_GASTOS_FIXOS);

    private Context context;


    public ModeloGastosFixosDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean cadastrarGastoFixo(ModeloGastosFixos modeloGastosFixos) {

        if(modeloGastosFixos != null){
            try {
                Map<String, Object> gastoSendoCadastrado = new HashMap<>();
                gastoSendoCadastrado.put("nomeDoGastoFixo",modeloGastosFixos.getNomeDoGastoFixo());
                gastoSendoCadastrado.put("valorGastoFixo", modeloGastosFixos.getValorGastoFixo());
                gastoSendoCadastrado.put("dataPagamento", modeloGastosFixos.getDataPagamento());
                gastoSendoCadastrado.put("dataPrevistaDePagamento", modeloGastosFixos.getDataPrevistaDePagamento());

                collectionReference.add(gastoSendoCadastrado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Gasto " + modeloGastosFixos.getNomeDoGastoFixo() + " cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                    }
                });


            }catch (Exception e){

            }
        }




        return false;
    }

    @Override
    public boolean atualizarGastoFixo(ModeloGastosFixos modeloGastosFixos, String idRecuperado) {
        return false;
    }
}
