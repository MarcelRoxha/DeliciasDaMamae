package com.marcel.a.n.roxha.deliciasdamamae.model;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.activity.LojaActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BoloVendidoModel implements Serializable {

    private String idReferencia;
    private String nomeBolo;
    private double velorVenda;
    private double custoBolo;
    public Context context;
    double resultadocustobolos = 0;
    double resultadovendabolos = 0;
    int contAddBolo = 0;

    String idReferenciaBolo;
    String idMontanteReferencia ;
    String nomeReferencia ;
    double valorReferencia ;
    double custoReferencia;

    CaixaMensalModel caixaMensalModel;

    public BoloVendidoModel() {


    }

    public String getId() {
        return idReferencia;
    }

    public void setId(String id) {
        this.idReferencia = id;
    }

    public String getNomeBolo() {
        return nomeBolo;
    }

    public void setNomeBolo(String nomeBolo) {
        this.nomeBolo = nomeBolo;
    }

    public double getVelorVenda() {
        return velorVenda;
    }

    public void setVelorVenda(double velorVenda) {
        this.velorVenda = velorVenda;
    }

    public double getCustoBolo() {
        return custoBolo;
    }

    public void setCustoBolo(double custoBolo) {
        this.custoBolo = custoBolo;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void salvarBolobanco(String idMontante, String id, String nomeBolo, double valorVenda, double custoBolo){

         idReferenciaBolo = id;
         idMontanteReferencia = idMontante;
         nomeReferencia = nomeBolo;
         valorReferencia = valorVenda;
         custoReferencia = custoBolo;

        Map<String, Object> boloVendido = new HashMap<>();
        boloVendido.put("idReferencia", idReferenciaBolo );
        boloVendido.put("nomeBolo", nomeReferencia );
        boloVendido.put("velorVenda", valorReferencia );
        boloVendido.put("custoBolo", custoReferencia );
        FirebaseFirestore.getInstance().collection("BOLOS_VENDIDOS").add(boloVendido).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(context, "Parabéns pela venda!", Toast.LENGTH_SHORT).show();
                FirebaseFirestore.getInstance().collection("CAIXA_MENSAL").document(idMontanteReferencia).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                       CaixaMensalModel caixaMensalModel = documentSnapshot.toObject(CaixaMensalModel.class);
                         String idRecuperado = caixaMensalModel.getIdentificadorCaixaMensal();
                        int mesRecuperado = caixaMensalModel.getMesReferencia();
                        int quantBoloAddRecuperado = caixaMensalModel.getQuantTotalBolosAdicionadosMensal();
                        double totalGastoMensalRecuperado = caixaMensalModel.getTotalGastoMensal();
                        double totalBolosVendidosRecuperado = caixaMensalModel.getValorTotalBolosVendidosMensal();
                        double totalCustoBolosrecuperaod = caixaMensalModel.getValorTotalCustosBolosVendidosMensal();
                        precessaVendaMontante(idReferenciaBolo, idRecuperado, valorReferencia, custoReferencia, mesRecuperado, quantBoloAddRecuperado,
                        totalGastoMensalRecuperado, totalBolosVendidosRecuperado,  totalCustoBolosrecuperaod);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        });

    }

    private void precessaVendaMontante(
            String idBoloDelete,
            String idRecuperado,
            double valorReferencia,
            double custoReferencia,
            int mesRecuperado,
            int quantBoloAddRecuperado,
            double totalGastoMensalRecuperado,
            double totalBolosVendidosRecuperado,
            double totalCustoBolosrecuperaod) {

        double valorbolo = valorReferencia;
        double valorbolosmontante = totalBolosVendidosRecuperado;

        resultadovendabolos = valorbolo + valorbolosmontante;

        double custobolo = custoReferencia;
        double valorcustomontante = totalCustoBolosrecuperaod;

        resultadocustobolos = custobolo + valorcustomontante;

        contAddBolo = quantBoloAddRecuperado + 1;

        if(resultadocustobolos > 0 && resultadocustobolos > 0){

            String idMontante = idRecuperado;
            String idBoloe = idBoloDelete;
            int mesmMontante = mesRecuperado;
            double totalGastoMontante = totalGastoMensalRecuperado;

            CaixaMensalModel caixaProcessa = new CaixaMensalModel();
            caixaProcessa.processaVendaBolo(idBoloe,idMontante, mesmMontante, contAddBolo, resultadovendabolos, resultadocustobolos, totalGastoMontante);

            Intent intent = new Intent(context, LojaActivity.class);
            context.startActivity(intent);

        }


    }
}
