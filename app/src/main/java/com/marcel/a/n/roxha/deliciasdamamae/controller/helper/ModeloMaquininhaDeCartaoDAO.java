package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMaquininhaDePassarCartao;

import java.util.HashMap;
import java.util.Map;

public class ModeloMaquininhaDeCartaoDAO implements InterfaceModeloMaquininhaDeCartaoDAO{
    private static final String COLLECTIO_MAQUININHA_CARTAO = "MAQUININHA_CARTAO";
    private Context context;
    public boolean verificaSucessoCadastroMaquininha = false;
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenceMaquininhaCartao = firestore.collection(COLLECTIO_MAQUININHA_CARTAO);
//    private LiveData<Boolean> inseriuDado = new LiveData<>();

    public ModeloMaquininhaDeCartaoDAO(Context context) {

        this.context = context;
    }

    @Override
    public boolean cadastrarMaquininha(ModeloMaquininhaDePassarCartao modeloMaquininhaDePassarCartao) {

        Map<String, Object> item = new HashMap<>();

        item.put("nomeDaMaquininha",modeloMaquininhaDePassarCartao.getNomeDaMaquininha().trim());
        item.put("porcentagemDeDescontoCredito", modeloMaquininhaDePassarCartao.getPorcentagemDeDescontoCredito());
        item.put("porcentagemDeDescontoDebito", modeloMaquininhaDePassarCartao.getPorcentagemDeDescontoDebito());
        item.put("dataPrevistaParaPagamentoDosValoresPassados", modeloMaquininhaDePassarCartao.getDataPrevistaParaPagamentoDosValoresPassados());

        referenceMaquininhaCartao.add(item).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                
                if(task.isSuccessful()){
                    Toast.makeText(context, "Maquininha " + modeloMaquininhaDePassarCartao.getNomeDaMaquininha() + " cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
//                    inseriuDado.
                }else{
                    Toast.makeText(context, "Algo deu errado, verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();
                    verificaSucessoCadastroMaquininha = false;
                }
                
            }
        });
return verificaSucessoCadastroMaquininha;
    }

    @Override
    public boolean atualizarMaquininha(ModeloMaquininhaDePassarCartao modeloMaquininhaDePassarCartao) {
        return false;
    }

    @Override
    public boolean deletarMaquininha(ModeloMaquininhaDePassarCartao modeloMaquininhaDePassarCartao) {
        return false;
    }
}
