package com.marcel.a.n.roxha.deliciasdamamae.model;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*import com.marcel.a.n.roxha.deliciasdamamae.helper.Base64Custom;*/

public class ItemEstoqueModel implements Serializable {


    String id;
    String versionEstoque;
    String nameItem;
    String valorItem;
    String quantItem;
    String unidMedida;
    String unidReceita;
    String valorFracionado;
    String valorItemPorReceita;
    String quantPacote;
    String quantUsadaReceita;
    String valorIngredienteEstoqueReceita;
    private boolean isChecked = false;

    final String IdEstoque = "Estoque_DeliciasDaMamae";


    public ItemEstoqueModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionEstoque() {
        return versionEstoque;
    }

    public void setVersionEstoque(String versionEstoque) {
        this.versionEstoque = versionEstoque;
    }


    public void listaInicial() {


        DatabaseReference reference = ConfiguracaoFirebase.getReference();
        reference.child("Item_Estoque").push()
                .setValue(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference ref = db.collection("Item_Estoque");


        String veriosnCodi = IdEstoque;

        ItemEstoqueModel farinhaTrigo = new ItemEstoqueModel();
        farinhaTrigo.setNameItem("Farinha de trigo");
        farinhaTrigo.setQuantItem("1");
        farinhaTrigo.setQuantPacote("5");
        farinhaTrigo.setQuantUsadaReceita("375");
        farinhaTrigo.setUnidMedida("1000");
        farinhaTrigo.setUnidReceita("1");
        farinhaTrigo.setValorItem("9.98");

        DatabaseReference referencel = ConfiguracaoFirebase.getReference();
        referencel.child("Item_Estoqueeee").push()
                .setValue(farinhaTrigo);

        Map<String, String> FarinhaTrigo = new HashMap<>();

        FarinhaTrigo.put("nameItem", farinhaTrigo.getNameItem());
        FarinhaTrigo.put("versionEstoque", veriosnCodi);
        FarinhaTrigo.put("valorItem", farinhaTrigo.getValorItem());
        FarinhaTrigo.put("quantItem", farinhaTrigo.getQuantItem());
        FarinhaTrigo.put("unidMedida", farinhaTrigo.getUnidMedida());
        FarinhaTrigo.put("unidReceita", farinhaTrigo.getUnidReceita());
        FarinhaTrigo.put("valorFracionado", farinhaTrigo.calcularValorFracionado());
        FarinhaTrigo.put("valorItemPorReceita", farinhaTrigo.valorItemPorReceita());
        FarinhaTrigo.put("quantPacote", farinhaTrigo.getQuantPacote());
        FarinhaTrigo.put("quantUsadaReceita", farinhaTrigo.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(FarinhaTrigo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        ItemEstoqueModel fuba = new ItemEstoqueModel();
        fuba.setNameItem("Fubá");
        fuba.setQuantItem("3.39");
        fuba.setQuantPacote("1");
        fuba.setQuantUsadaReceita("200");
        fuba.setUnidMedida("1000");
        fuba.setUnidReceita("1");
        fuba.setValorItem("2.50");

        Map<String, String> Fuba = new HashMap<>();

        Fuba.put("nameItem", fuba.getNameItem());
        Fuba.put("versionEstoque", veriosnCodi);
        Fuba.put("valorItem", fuba.getValorItem());
        Fuba.put("quantItem", fuba.getQuantItem());
        Fuba.put("unidMedida", fuba.getUnidMedida());
        Fuba.put("unidReceita", fuba.getUnidReceita());
        Fuba.put("valorFracionado", fuba.calcularValorFracionado());
        Fuba.put("valorItemPorReceita", fuba.valorItemPorReceita());
        Fuba.put("quantPacote", fuba.getQuantPacote());
        Fuba.put("quantUsadaReceita", fuba.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(Fuba).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel ovo = new ItemEstoqueModel();
        ovo.setNameItem("Ovo");
        ovo.setValorItem("12.00");
        ovo.setQuantItem("1");
        ovo.setUnidMedida("1");
        ovo.setUnidReceita("1");
        ovo.setQuantPacote("30");
        ovo.setQuantUsadaReceita("5");


        Map<String, String> Ovo = new HashMap<>();

        Ovo.put("nameItem", ovo.getNameItem());
        Ovo.put("versionEstoque", veriosnCodi);
        Ovo.put("valorItem", ovo.getValorItem());
        Ovo.put("quantItem", ovo.getQuantItem());
        Ovo.put("unidMedida", ovo.getUnidMedida());
        Ovo.put("unidReceita", ovo.getUnidReceita());
        Ovo.put("valorFracionado", ovo.calcularValorFracionado());
        Ovo.put("valorItemPorReceita", ovo.valorItemPorReceita());
        Ovo.put("quantPacote", ovo.getQuantPacote());
        Ovo.put("quantUsadaReceita", ovo.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(Ovo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel fermento = new ItemEstoqueModel();

        fermento.setNameItem("Fermento");
        fermento.setValorItem("1.79");
        fermento.setQuantItem("1");
        fermento.setUnidMedida("1");
        fermento.setUnidReceita("1");
        fermento.setQuantPacote("100");
        fermento.setQuantUsadaReceita("30");


        Map<String, String> Fermento = new HashMap<>();

        Fermento.put("nameItem", fermento.getNameItem());
        Fermento.put("versionEstoque", veriosnCodi);
        Fermento.put("valorItem", fermento.getValorItem());
        Fermento.put("quantItem", fermento.getQuantItem());
        Fermento.put("unidMedida", fermento.getUnidMedida());
        Fermento.put("unidReceita", fermento.getUnidReceita());
        Fermento.put("valorFracionado", fermento.calcularValorFracionado());
        Fermento.put("valorItemPorReceita", fermento.valorItemPorReceita());
        Fermento.put("quantPacote", fermento.getQuantPacote());
        Fermento.put("quantUsadaReceita", fermento.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(Fermento).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel essencia = new ItemEstoqueModel();

        essencia.setNameItem("Essência");
        essencia.setValorItem("3.00");
        essencia.setQuantItem("1");
        essencia.setUnidMedida("1");
        essencia.setUnidReceita("1");
        essencia.setQuantPacote("30");
        essencia.setQuantUsadaReceita("15");

        Map<String, String> Essencia = new HashMap<>();

        Essencia.put("nameItem", essencia.getNameItem());
        Essencia.put("versionEstoque", veriosnCodi);
        Essencia.put("valorItem", essencia.getValorItem());
        Essencia.put("quantItem", essencia.getQuantItem());
        Essencia.put("unidMedida", essencia.getUnidMedida());
        Essencia.put("unidReceita", essencia.getUnidReceita());
        Essencia.put("valorFracionado", essencia.calcularValorFracionado());
        Essencia.put("valorItemPorReceita", essencia.valorItemPorReceita());
        Essencia.put("quantPacote", essencia.getQuantPacote());
        Essencia.put("quantUsadaReceita", essencia.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(Essencia).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel acucar = new ItemEstoqueModel();

        acucar.setNameItem("Açucar");
        acucar.setValorItem("1.50");
        acucar.setQuantItem("1");
        acucar.setUnidMedida("1000");
        acucar.setUnidReceita("1");
        acucar.setQuantPacote("1");
        acucar.setQuantUsadaReceita("400");

        Map<String, String> Acucar = new HashMap<>();

        Acucar.put("nameItem", acucar.getNameItem());
        Acucar.put("versionEstoque", veriosnCodi);

        Acucar.put("valorItem", acucar.getValorItem());
        Acucar.put("quantItem", acucar.getQuantItem());
        Acucar.put("unidMedida", acucar.getUnidMedida());
        Acucar.put("unidReceita", acucar.getUnidReceita());
        Acucar.put("valorFracionado", acucar.calcularValorFracionado());
        Acucar.put("valorItemPorReceita", acucar.valorItemPorReceita());
        Acucar.put("quantPacote", acucar.getQuantPacote());
        Acucar.put("quantUsadaReceita", acucar.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(Acucar).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel leite = new ItemEstoqueModel();

        leite.setNameItem("Leite");
        leite.setValorItem("2.14");
        leite.setQuantItem("1");
        leite.setUnidMedida("1000");
        leite.setUnidReceita("1");
        leite.setQuantPacote("1");
        leite.setQuantUsadaReceita("250");


        Map<String, String> Leite = new HashMap<>();

        Leite.put("nameItem", leite.getNameItem());
        Leite.put("versionEstoque", veriosnCodi);

        Leite.put("valorItem", leite.getValorItem());
        Leite.put("quantItem", leite.getQuantItem());
        Leite.put("unidMedida", leite.getUnidMedida());
        Leite.put("unidReceita", leite.getUnidReceita());
        Leite.put("valorFracionado", leite.calcularValorFracionado());
        Leite.put("valorItemPorReceita", leite.valorItemPorReceita());
        Leite.put("quantPacote", leite.getQuantPacote());
        Leite.put("quantUsadaReceita", leite.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(Leite).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel oleo = new ItemEstoqueModel();

        oleo.setNameItem("Óleo");
        oleo.setValorItem("2.89");
        oleo.setQuantItem("1");
        oleo.setUnidMedida("1000");
        oleo.setUnidReceita("1");
        oleo.setQuantPacote("1");
        oleo.setQuantUsadaReceita("250");

        Map<String, String> Oleo = new HashMap<>();

        Oleo.put("nameItem", oleo.getNameItem());
        Oleo.put("versionEstoque", veriosnCodi);
        Oleo.put("valorItem", oleo.getValorItem());
        Oleo.put("quantItem", oleo.getQuantItem());
        Oleo.put("unidMedida", oleo.getUnidMedida());
        Oleo.put("unidReceita", oleo.getUnidReceita());
        Oleo.put("valorFracionado", oleo.calcularValorFracionado());
        Oleo.put("valorItemPorReceita", oleo.valorItemPorReceita());
        Oleo.put("quantPacote", oleo.getQuantPacote());
        Oleo.put("quantUsadaReceita", oleo.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(Oleo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel antimofo = new ItemEstoqueModel();

        antimofo.setNameItem("Anti Mofo");
        antimofo.setValorItem("2.00");
        antimofo.setQuantItem("1");
        antimofo.setUnidMedida("1");
        antimofo.setUnidReceita("1");
        antimofo.setQuantPacote("50");
        antimofo.setQuantUsadaReceita("15");


        Map<String, String> AntiMofo = new HashMap<>();

        AntiMofo.put("nameItem", antimofo.getNameItem());
        AntiMofo.put("versionEstoque", veriosnCodi);
        AntiMofo.put("valorItem", antimofo.getValorItem());
        AntiMofo.put("quantItem", antimofo.getQuantItem());
        AntiMofo.put("unidMedida", antimofo.getUnidMedida());
        AntiMofo.put("unidReceita", antimofo.getUnidReceita());
        AntiMofo.put("valorFracionado", antimofo.calcularValorFracionado());
        AntiMofo.put("valorItemPorReceita", antimofo.valorItemPorReceita());
        AntiMofo.put("quantPacote", antimofo.getQuantPacote());
        AntiMofo.put("quantUsadaReceita", antimofo.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(AntiMofo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel queijoRalado = new ItemEstoqueModel();

        queijoRalado.setNameItem("Queijo Ralado");
        queijoRalado.setValorItem("2.00");
        queijoRalado.setQuantItem("1");
        queijoRalado.setUnidMedida("1");
        queijoRalado.setUnidReceita("1");
        queijoRalado.setQuantPacote("50");
        queijoRalado.setQuantUsadaReceita("50");

        Map<String, String> QueijoRalado = new HashMap<>();

        QueijoRalado.put("nameItem", queijoRalado.getNameItem());
        QueijoRalado.put("versionEstoque", veriosnCodi);
        QueijoRalado.put("valorItem", queijoRalado.getValorItem());
        QueijoRalado.put("quantItem", queijoRalado.getQuantItem());
        QueijoRalado.put("unidMedida", queijoRalado.getUnidMedida());
        QueijoRalado.put("unidReceita", queijoRalado.getUnidReceita());
        QueijoRalado.put("valorFracionado", queijoRalado.calcularValorFracionado());
        QueijoRalado.put("valorItemPorReceita", queijoRalado.valorItemPorReceita());
        QueijoRalado.put("quantPacote", queijoRalado.getQuantPacote());
        QueijoRalado.put("quantUsadaReceita", queijoRalado.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(QueijoRalado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel sazonMassa = new ItemEstoqueModel();

        sazonMassa.setNameItem("Sazon Massa");
        sazonMassa.setValorItem("2.00");
        sazonMassa.setQuantItem("1");
        sazonMassa.setUnidMedida("1");
        sazonMassa.setUnidReceita("1");
        sazonMassa.setQuantPacote("60");
        sazonMassa.setQuantUsadaReceita("5");

        Map<String, String> SazonMassa = new HashMap<>();

        SazonMassa.put("nameItem", sazonMassa.getNameItem());
        SazonMassa.put("versionEstoque", veriosnCodi);
        SazonMassa.put("valorItem", sazonMassa.getValorItem());
        SazonMassa.put("quantItem", sazonMassa.getQuantItem());
        SazonMassa.put("unidMedida", sazonMassa.getUnidMedida());
        SazonMassa.put("unidReceita", sazonMassa.getUnidReceita());
        SazonMassa.put("valorFracionado", sazonMassa.calcularValorFracionado());
        SazonMassa.put("valorItemPorReceita", sazonMassa.valorItemPorReceita());
        SazonMassa.put("quantPacote", sazonMassa.getQuantPacote());
        SazonMassa.put("quantUsadaReceita", sazonMassa.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(SazonMassa).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel acucarMascavo = new ItemEstoqueModel();

        acucarMascavo.setNameItem("Açucar Mascavo");
        acucarMascavo.setValorItem("9.00");
        acucarMascavo.setQuantItem("1");
        acucarMascavo.setUnidMedida("1000");
        acucarMascavo.setUnidReceita("1");
        acucarMascavo.setQuantPacote("1");
        acucarMascavo.setQuantUsadaReceita("75");


        Map<String, String> AcucarMascavo = new HashMap<>();

        AcucarMascavo.put("nameItem", acucarMascavo.getNameItem());
        AcucarMascavo.put("versionEstoque", veriosnCodi);
        AcucarMascavo.put("valorItem", acucarMascavo.getValorItem());
        AcucarMascavo.put("quantItem", acucarMascavo.getQuantItem());
        AcucarMascavo.put("unidMedida", acucarMascavo.getUnidMedida());
        AcucarMascavo.put("unidReceita", acucarMascavo.getUnidReceita());
        AcucarMascavo.put("valorFracionado", acucarMascavo.calcularValorFracionado());
        AcucarMascavo.put("valorItemPorReceita", acucarMascavo.valorItemPorReceita());
        AcucarMascavo.put("quantPacote", acucarMascavo.getQuantPacote());
        AcucarMascavo.put("quantUsadaReceita", acucarMascavo.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(AcucarMascavo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel manteiga = new ItemEstoqueModel();

        manteiga.setNameItem("Manteiga");
        manteiga.setValorItem("2.30");
        manteiga.setQuantItem("1");
        manteiga.setUnidMedida("1");
        manteiga.setUnidReceita("1");
        manteiga.setQuantPacote("500");
        manteiga.setQuantUsadaReceita("30");

        Map<String, String> Manteiga = new HashMap<>();

        Manteiga.put("nameItem", manteiga.getNameItem());
        Manteiga.put("versionEstoque", veriosnCodi);
        Manteiga.put("valorItem", manteiga.getValorItem());
        Manteiga.put("quantItem", manteiga.getQuantItem());
        Manteiga.put("unidMedida", manteiga.getUnidMedida());
        Manteiga.put("unidReceita", manteiga.getUnidReceita());
        Manteiga.put("valorFracionado", manteiga.calcularValorFracionado());
        Manteiga.put("valorItemPorReceita", manteiga.valorItemPorReceita());
        Manteiga.put("quantPacote", manteiga.getQuantPacote());
        Manteiga.put("quantUsadaReceita", manteiga.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(Manteiga).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel bicarbonatoSodio = new ItemEstoqueModel();

        bicarbonatoSodio.setNameItem("Bicarbonado de Sódio");
        bicarbonatoSodio.setValorItem("1.00");
        bicarbonatoSodio.setQuantItem("1");
        bicarbonatoSodio.setUnidMedida("1");
        bicarbonatoSodio.setUnidReceita("1");
        bicarbonatoSodio.setQuantPacote("30");
        bicarbonatoSodio.setQuantUsadaReceita("10");


        Map<String, String> BicarbonadoSodio = new HashMap<>();

        BicarbonadoSodio.put("nameItem", bicarbonatoSodio.getNameItem());
        BicarbonadoSodio.put("versionEstoque", veriosnCodi);
        BicarbonadoSodio.put("valorItem", bicarbonatoSodio.getValorItem());
        BicarbonadoSodio.put("quantItem", bicarbonatoSodio.getQuantItem());
        BicarbonadoSodio.put("unidMedida", bicarbonatoSodio.getUnidMedida());
        BicarbonadoSodio.put("unidReceita", bicarbonatoSodio.getUnidReceita());
        BicarbonadoSodio.put("valorFracionado", bicarbonatoSodio.calcularValorFracionado());
        BicarbonadoSodio.put("valorItemPorReceita", bicarbonatoSodio.valorItemPorReceita());
        BicarbonadoSodio.put("quantPacote", bicarbonatoSodio.getQuantPacote());
        BicarbonadoSodio.put("quantUsadaReceita", bicarbonatoSodio.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(BicarbonadoSodio).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel canelaPo = new ItemEstoqueModel();

        canelaPo.setNameItem("Canela em Pó");
        canelaPo.setValorItem("4.00");
        canelaPo.setQuantItem("1");
        canelaPo.setUnidMedida("1");
        canelaPo.setUnidReceita("1");
        canelaPo.setQuantPacote("30");
        canelaPo.setQuantUsadaReceita("10");

        Map<String, String> CanelaPo = new HashMap<>();

        CanelaPo.put("nameItem", canelaPo.getNameItem());
        CanelaPo.put("versionEstoque", veriosnCodi);
        CanelaPo.put("valorItem", canelaPo.getValorItem());
        CanelaPo.put("quantItem", canelaPo.getQuantItem());
        CanelaPo.put("unidMedida", canelaPo.getUnidMedida());
        CanelaPo.put("unidReceita", canelaPo.getUnidReceita());
        CanelaPo.put("valorFracionado", canelaPo.calcularValorFracionado());
        CanelaPo.put("valorItemPorReceita", canelaPo.valorItemPorReceita());
        CanelaPo.put("quantPacote", canelaPo.getQuantPacote());
        CanelaPo.put("quantUsadaReceita", canelaPo.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(CanelaPo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel cravoPo = new ItemEstoqueModel();

        cravoPo.setNameItem("Cravo em Pó");
        cravoPo.setValorItem("4.00");
        cravoPo.setQuantItem("1");
        cravoPo.setUnidMedida("1");
        cravoPo.setUnidReceita("1");
        cravoPo.setQuantPacote("30");
        cravoPo.setQuantUsadaReceita("2.5");

        Map<String, String> CravoPo = new HashMap<>();

        CravoPo.put("nameItem", cravoPo.getNameItem());
        CravoPo.put("versionEstoque", veriosnCodi);
        CravoPo.put("valorItem", cravoPo.getValorItem());
        CravoPo.put("quantItem", cravoPo.getQuantItem());
        CravoPo.put("unidMedida", cravoPo.getUnidMedida());
        CravoPo.put("unidReceita", cravoPo.getUnidReceita());
        CravoPo.put("valorFracionado", cravoPo.calcularValorFracionado());
        CravoPo.put("valorItemPorReceita", cravoPo.valorItemPorReceita());
        CravoPo.put("quantPacote", cravoPo.getQuantPacote());
        CravoPo.put("quantUsadaReceita", cravoPo.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(CravoPo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel nozMoscada = new ItemEstoqueModel();

        nozMoscada.setNameItem("Noz-Moscada");
        nozMoscada.setValorItem("3.00");
        nozMoscada.setQuantItem("1");
        nozMoscada.setUnidMedida("1");
        nozMoscada.setUnidReceita("1");
        nozMoscada.setQuantPacote("30");
        nozMoscada.setQuantUsadaReceita("2.5");

        Map<String, String> NosMoscada = new HashMap<>();

        NosMoscada.put("nameItem", nozMoscada.getNameItem());
        NosMoscada.put("versionEstoque", veriosnCodi);
        NosMoscada.put("valorItem", nozMoscada.getValorItem());
        NosMoscada.put("quantItem", nozMoscada.getQuantItem());
        NosMoscada.put("unidMedida", nozMoscada.getUnidMedida());
        NosMoscada.put("unidReceita", nozMoscada.getUnidReceita());
        NosMoscada.put("valorFracionado", nozMoscada.calcularValorFracionado());
        NosMoscada.put("valorItemPorReceita", nozMoscada.valorItemPorReceita());
        NosMoscada.put("quantPacote", nozMoscada.getQuantPacote());
        NosMoscada.put("quantUsadaReceita", nozMoscada.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(NosMoscada).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel melFavinho = new ItemEstoqueModel();

        melFavinho.setNameItem("Mel Favinho");
        melFavinho.setValorItem("6.39");
        melFavinho.setQuantItem("1");
        melFavinho.setUnidMedida("1000");
        melFavinho.setUnidReceita("1");
        melFavinho.setQuantPacote("1.1");
        melFavinho.setQuantUsadaReceita("150");

        Map<String, String> MelFavinho = new HashMap<>();

        MelFavinho.put("nameItem", melFavinho.getNameItem());
        MelFavinho.put("versionEstoque", veriosnCodi);
        MelFavinho.put("valorItem", melFavinho.getValorItem());
        MelFavinho.put("quantItem", melFavinho.getQuantItem());
        MelFavinho.put("unidMedida", melFavinho.getUnidMedida());
        MelFavinho.put("unidReceita", melFavinho.getUnidReceita());
        MelFavinho.put("valorFracionado", melFavinho.calcularValorFracionado());
        MelFavinho.put("valorItemPorReceita", melFavinho.valorItemPorReceita());
        MelFavinho.put("quantPacote", melFavinho.getQuantPacote());
        MelFavinho.put("quantUsadaReceita", melFavinho.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(MelFavinho).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel leiteCondensado = new ItemEstoqueModel();

        leiteCondensado.setNameItem("Leite condensado");
        leiteCondensado.setValorItem("2.99");
        leiteCondensado.setQuantItem("1");
        leiteCondensado.setUnidMedida("1");
        leiteCondensado.setUnidReceita("1");
        leiteCondensado.setQuantPacote("395");
        leiteCondensado.setQuantUsadaReceita("395");

        Map<String, String> LeiteCondensado = new HashMap<>();

        LeiteCondensado.put("nameItem", leiteCondensado.getNameItem());
        LeiteCondensado.put("versionEstoque", veriosnCodi);
        LeiteCondensado.put("valorItem", leiteCondensado.getValorItem());
        LeiteCondensado.put("quantItem", leiteCondensado.getQuantItem());
        LeiteCondensado.put("unidMedida", leiteCondensado.getUnidMedida());
        LeiteCondensado.put("unidReceita", leiteCondensado.getUnidReceita());
        LeiteCondensado.put("valorFracionado", leiteCondensado.calcularValorFracionado());
        LeiteCondensado.put("valorItemPorReceita", leiteCondensado.valorItemPorReceita());
        LeiteCondensado.put("quantPacote", leiteCondensado.getQuantPacote());
        LeiteCondensado.put("quantUsadaReceita", leiteCondensado.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(LeiteCondensado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel cremedeLeite = new ItemEstoqueModel();

        cremedeLeite.setNameItem("Creme de Leite");
        cremedeLeite.setValorItem("1.69");
        cremedeLeite.setQuantItem("1");
        cremedeLeite.setUnidMedida("1");
        cremedeLeite.setUnidReceita("1");
        cremedeLeite.setQuantPacote("200");
        cremedeLeite.setQuantUsadaReceita("200");

        Map<String, String> CremeDeLeite = new HashMap<>();

        CremeDeLeite.put("nameItem", cremedeLeite.getNameItem());
        CremeDeLeite.put("versionEstoque", veriosnCodi);
        CremeDeLeite.put("valorItem", cremedeLeite.getValorItem());
        CremeDeLeite.put("quantItem", cremedeLeite.getQuantItem());
        CremeDeLeite.put("unidMedida", cremedeLeite.getUnidMedida());
        CremeDeLeite.put("unidReceita", cremedeLeite.getUnidReceita());
        CremeDeLeite.put("valorFracionado", cremedeLeite.calcularValorFracionado());
        CremeDeLeite.put("valorItemPorReceita", cremedeLeite.valorItemPorReceita());
        CremeDeLeite.put("quantPacote", cremedeLeite.getQuantPacote());
        CremeDeLeite.put("quantUsadaReceita", cremedeLeite.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(CremeDeLeite).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel leitePo = new ItemEstoqueModel();

        leitePo.setNameItem("Leite em Pó");
        leitePo.setValorItem("8.15");
        leitePo.setQuantItem("1");
        leitePo.setUnidMedida("1");
        leitePo.setUnidReceita("1");
        leitePo.setQuantPacote("400");
        leitePo.setQuantUsadaReceita("120");

        Map<String, String> LeitePo = new HashMap<>();

        LeitePo.put("nameItem", leitePo.getNameItem());
        LeitePo.put("versionEstoque", veriosnCodi);
        LeitePo.put("valorItem", leitePo.getValorItem());
        LeitePo.put("quantItem", leitePo.getQuantItem());
        LeitePo.put("unidMedida", leitePo.getUnidMedida());
        LeitePo.put("unidReceita", leitePo.getUnidReceita());
        LeitePo.put("valorFracionado", leitePo.calcularValorFracionado());
        LeitePo.put("valorItemPorReceita", leitePo.valorItemPorReceita());
        LeitePo.put("quantPacote", leitePo.getQuantPacote());
        LeitePo.put("quantUsadaReceita", leitePo.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(LeitePo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel leiteCoco = new ItemEstoqueModel();

        leiteCoco.setNameItem("Leite de coco ");
        leiteCoco.setValorItem("2.00");
        leiteCoco.setQuantItem("1");
        leiteCoco.setUnidMedida("1");
        leiteCoco.setUnidReceita("1");
        leiteCoco.setQuantPacote("200");
        leiteCoco.setQuantUsadaReceita("200");

        Map<String, String> LeiteCoco = new HashMap<>();

        LeiteCoco.put("nameItem", leiteCoco.getNameItem());
        LeiteCoco.put("versionEstoque", veriosnCodi);
        LeiteCoco.put("valorItem", leiteCoco.getValorItem());
        LeiteCoco.put("quantItem", leiteCoco.getQuantItem());
        LeiteCoco.put("unidMedida", leiteCoco.getUnidMedida());
        LeiteCoco.put("unidReceita", leiteCoco.getUnidReceita());
        LeiteCoco.put("valorFracionado", leiteCoco.calcularValorFracionado());
        LeiteCoco.put("valorItemPorReceita", leiteCoco.valorItemPorReceita());
        LeiteCoco.put("quantPacote", leiteCoco.getQuantPacote());
        LeiteCoco.put("quantUsadaReceita", leiteCoco.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(LeiteCoco).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        ItemEstoqueModel cocoRalado = new ItemEstoqueModel();

        cocoRalado.setNameItem("Coco ralado");
        cocoRalado.setValorItem("1.95");
        cocoRalado.setQuantItem("1");
        cocoRalado.setUnidMedida("1");
        cocoRalado.setUnidReceita("1");
        cocoRalado.setQuantPacote("100");
        cocoRalado.setQuantUsadaReceita("100");


        Map<String, String> CocoRalado = new HashMap<>();

        CocoRalado.put("nameItem", cocoRalado.getNameItem());
        CocoRalado.put("versionEstoque", veriosnCodi);
        CocoRalado.put("valorItem", cocoRalado.getValorItem());
        CocoRalado.put("quantItem", cocoRalado.getQuantItem());
        CocoRalado.put("unidMedida", cocoRalado.getUnidMedida());
        CocoRalado.put("unidReceita", cocoRalado.getUnidReceita());
        CocoRalado.put("valorFracionado", cocoRalado.calcularValorFracionado());
        CocoRalado.put("valorItemPorReceita", cocoRalado.valorItemPorReceita());
        CocoRalado.put("quantPacote", cocoRalado.getQuantPacote());
        CocoRalado.put("quantUsadaReceita", cocoRalado.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(CocoRalado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel maizena = new ItemEstoqueModel();

        maizena.setNameItem("Maizena");
        maizena.setValorItem("4.15");
        maizena.setQuantItem("1");
        maizena.setUnidMedida("1");
        maizena.setUnidReceita("1");
        maizena.setQuantPacote("500");
        maizena.setQuantUsadaReceita("30");

        Map<String, String> Maizena = new HashMap<>();

        Maizena.put("nameItem", maizena.getNameItem());
        Maizena.put("versionEstoque", veriosnCodi);
        Maizena.put("valorItem", maizena.getValorItem());
        Maizena.put("quantItem", maizena.getQuantItem());
        Maizena.put("unidMedida", maizena.getUnidMedida());
        Maizena.put("unidReceita", maizena.getUnidReceita());
        Maizena.put("valorFracionado", maizena.calcularValorFracionado());
        Maizena.put("valorItemPorReceita", maizena.valorItemPorReceita());
        Maizena.put("quantPacote", maizena.getQuantPacote());
        Maizena.put("quantUsadaReceita", maizena.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(Maizena).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel chocolate50porcento = new ItemEstoqueModel();

        chocolate50porcento.setNameItem("Chocolate 50%");
        chocolate50porcento.setValorItem("19.60");
        chocolate50porcento.setQuantItem("1");
        chocolate50porcento.setUnidMedida("1000");
        chocolate50porcento.setUnidReceita("1");
        chocolate50porcento.setQuantPacote("1");
        chocolate50porcento.setQuantUsadaReceita("45");

        Map<String, String> Chocolate50Porcento = new HashMap<>();

        Chocolate50Porcento.put("nameItem", chocolate50porcento.getNameItem());
        Chocolate50Porcento.put("versionEstoque", veriosnCodi);
        Chocolate50Porcento.put("valorItem", chocolate50porcento.getValorItem());
        Chocolate50Porcento.put("quantItem", chocolate50porcento.getQuantItem());
        Chocolate50Porcento.put("unidMedida", chocolate50porcento.getUnidMedida());
        Chocolate50Porcento.put("unidReceita", chocolate50porcento.getUnidReceita());
        Chocolate50Porcento.put("valorFracionado", chocolate50porcento.calcularValorFracionado());
        Chocolate50Porcento.put("valorItemPorReceita", chocolate50porcento.valorItemPorReceita());
        Chocolate50Porcento.put("quantPacote", chocolate50porcento.getQuantPacote());
        Chocolate50Porcento.put("quantUsadaReceita", chocolate50porcento.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(Chocolate50Porcento).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel leiteRecheios = new ItemEstoqueModel();

        leiteRecheios.setNameItem("Leite para recheios");
        leiteRecheios.setValorItem("2.14");
        leiteRecheios.setQuantItem("1");
        leiteRecheios.setUnidMedida("1000");
        leiteRecheios.setUnidReceita("1");
        leiteRecheios.setQuantPacote("1");
        leiteRecheios.setQuantUsadaReceita("480");

        Map<String, String> LeiteRecheios = new HashMap<>();

        LeiteRecheios.put("nameItem", leiteRecheios.getNameItem());
        LeiteRecheios.put("versionEstoque", veriosnCodi);
        LeiteRecheios.put("valorItem", leiteRecheios.getValorItem());
        LeiteRecheios.put("quantItem", leiteRecheios.getQuantItem());
        LeiteRecheios.put("unidMedida", leiteRecheios.getUnidMedida());
        LeiteRecheios.put("unidReceita", leiteRecheios.getUnidReceita());
        LeiteRecheios.put("valorFracionado", leiteRecheios.calcularValorFracionado());
        LeiteRecheios.put("valorItemPorReceita", leiteRecheios.valorItemPorReceita());
        LeiteRecheios.put("quantPacote", leiteRecheios.getQuantPacote());
        LeiteRecheios.put("quantUsadaReceita", leiteRecheios.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(LeiteRecheios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel preparoSobremesaMorango = new ItemEstoqueModel();

        preparoSobremesaMorango.setNameItem("Preparo Sobremesa Morango");
        preparoSobremesaMorango.setValorItem("5.00");
        preparoSobremesaMorango.setQuantItem("1");
        preparoSobremesaMorango.setUnidMedida("1");
        preparoSobremesaMorango.setUnidReceita("1");
        preparoSobremesaMorango.setQuantPacote("100");
        preparoSobremesaMorango.setQuantUsadaReceita("45");


        Map<String, String> PrepaSobremesaMorango = new HashMap<>();

        PrepaSobremesaMorango.put("nameItem", preparoSobremesaMorango.getNameItem());
        PrepaSobremesaMorango.put("versionEstoque", veriosnCodi);
        PrepaSobremesaMorango.put("valorItem", preparoSobremesaMorango.getValorItem());
        PrepaSobremesaMorango.put("quantItem", preparoSobremesaMorango.getQuantItem());
        PrepaSobremesaMorango.put("unidMedida", preparoSobremesaMorango.getUnidMedida());
        PrepaSobremesaMorango.put("unidReceita", preparoSobremesaMorango.getUnidReceita());
        PrepaSobremesaMorango.put("valorFracionado", preparoSobremesaMorango.calcularValorFracionado());
        PrepaSobremesaMorango.put("valorItemPorReceita", preparoSobremesaMorango.valorItemPorReceita());
        PrepaSobremesaMorango.put("quantPacote", preparoSobremesaMorango.getQuantPacote());
        PrepaSobremesaMorango.put("quantUsadaReceita", preparoSobremesaMorango.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(PrepaSobremesaMorango).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel preparoSobremesaChocolateBranco = new ItemEstoqueModel();

        preparoSobremesaChocolateBranco.setNameItem("Preparo Sobremesa Chocolate Branco");
        preparoSobremesaChocolateBranco.setValorItem("3.29");
        preparoSobremesaChocolateBranco.setQuantItem("1");
        preparoSobremesaChocolateBranco.setUnidMedida("1");
        preparoSobremesaChocolateBranco.setUnidReceita("1");
        preparoSobremesaChocolateBranco.setQuantPacote("100");
        preparoSobremesaChocolateBranco.setQuantUsadaReceita("45");

        Map<String, String> PrepaSobremesaChocolateBranco = new HashMap<>();


        PrepaSobremesaChocolateBranco.put("nameItem", preparoSobremesaChocolateBranco.getNameItem());
        PrepaSobremesaChocolateBranco.put("versionEstoque", veriosnCodi);
        PrepaSobremesaChocolateBranco.put("valorItem", preparoSobremesaChocolateBranco.getValorItem());
        PrepaSobremesaChocolateBranco.put("quantItem", preparoSobremesaChocolateBranco.getQuantItem());
        PrepaSobremesaChocolateBranco.put("unidMedida", preparoSobremesaChocolateBranco.getUnidMedida());
        PrepaSobremesaChocolateBranco.put("unidReceita", preparoSobremesaChocolateBranco.getUnidReceita());
        PrepaSobremesaChocolateBranco.put("valorFracionado", preparoSobremesaChocolateBranco.calcularValorFracionado());
        PrepaSobremesaChocolateBranco.put("valorItemPorReceita", preparoSobremesaChocolateBranco.valorItemPorReceita());
        PrepaSobremesaChocolateBranco.put("quantPacote", preparoSobremesaChocolateBranco.getQuantPacote());
        PrepaSobremesaChocolateBranco.put("quantUsadaReceita", preparoSobremesaChocolateBranco.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(PrepaSobremesaChocolateBranco).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        ItemEstoqueModel milho = new ItemEstoqueModel();

        milho.setNameItem("Milho");
        milho.setValorItem("1.79");
        milho.setQuantItem("1");
        milho.setUnidMedida("1");
        milho.setUnidReceita("1");
        milho.setQuantPacote("170");
        milho.setQuantUsadaReceita("170");


        Map<String, String> Milho = new HashMap<>();


        Milho.put("nameItem", milho.getNameItem());
        Milho.put("versionEstoque", veriosnCodi);
        Milho.put("valorItem", milho.getValorItem());
        Milho.put("quantItem", milho.getQuantItem());
        Milho.put("unidMedida", milho.getUnidMedida());
        Milho.put("unidReceita", milho.getUnidReceita());
        Milho.put("valorFracionado", milho.calcularValorFracionado());
        Milho.put("valorItemPorReceita", milho.valorItemPorReceita());
        Milho.put("quantPacote", milho.getQuantPacote());
        Milho.put("quantUsadaReceita", milho.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(Milho).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        ItemEstoqueModel goiabada = new ItemEstoqueModel();

        goiabada.setNameItem("Goiabada");
        goiabada.setValorItem("1.15");
        goiabada.setQuantItem("1");
        goiabada.setUnidMedida("1");
        goiabada.setUnidReceita("1");
        goiabada.setQuantPacote("300");
        goiabada.setQuantUsadaReceita("30");

        Map<String, String> Goiabada = new HashMap<>();


        Goiabada.put("nameItem", goiabada.getNameItem());
        Goiabada.put("versionEstoque", veriosnCodi);
        Goiabada.put("valorItem", goiabada.getValorItem());
        Goiabada.put("quantItem", goiabada.getQuantItem());
        Goiabada.put("unidMedida", goiabada.getUnidMedida());
        Goiabada.put("unidReceita", goiabada.getUnidReceita());
        Goiabada.put("valorFracionado", goiabada.calcularValorFracionado());
        Goiabada.put("valorItemPorReceita", goiabada.valorItemPorReceita());
        Goiabada.put("quantPacote", goiabada.getQuantPacote());
        Goiabada.put("quantUsadaReceita", goiabada.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(Goiabada).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        ItemEstoqueModel ervaDoce = new ItemEstoqueModel();

        ervaDoce.setNameItem("Erva doce");
        ervaDoce.setValorItem("1.00");
        ervaDoce.setQuantItem("1");
        ervaDoce.setUnidMedida("1");
        ervaDoce.setUnidReceita("1");
        ervaDoce.setQuantPacote("30");
        ervaDoce.setQuantUsadaReceita("10");

        Map<String, String> ErvaDoce = new HashMap<>();

        ErvaDoce.put("nameItem", ervaDoce.getNameItem());
        ErvaDoce.put("versionEstoque", veriosnCodi);
        ErvaDoce.put("valorItem", ervaDoce.getValorItem());
        ErvaDoce.put("quantItem", ervaDoce.getQuantItem());
        ErvaDoce.put("unidMedida", ervaDoce.getUnidMedida());
        ErvaDoce.put("unidReceita", ervaDoce.getUnidReceita());
        ErvaDoce.put("valorFracionado", ervaDoce.calcularValorFracionado());
        ErvaDoce.put("valorItemPorReceita", ervaDoce.valorItemPorReceita());
        ErvaDoce.put("quantPacote", ervaDoce.getQuantPacote());
        ErvaDoce.put("quantUsadaReceita", ervaDoce.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(ErvaDoce).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel nutella = new ItemEstoqueModel();

        nutella.setNameItem("Nutella");
        nutella.setValorItem("26.50");
        nutella.setQuantItem("1");
        nutella.setUnidMedida("1");
        nutella.setUnidReceita("1");
        nutella.setQuantPacote("850");
        nutella.setQuantUsadaReceita("200");

        Map<String, String> Nutella = new HashMap<>();

        Nutella.put("nameItem", nutella.getNameItem());
        Nutella.put("versionEstoque", veriosnCodi);
        Nutella.put("valorItem", nutella.getValorItem());
        Nutella.put("quantItem", nutella.getQuantItem());
        Nutella.put("unidMedida", nutella.getUnidMedida());
        Nutella.put("unidReceita", nutella.getUnidReceita());
        Nutella.put("valorFracionado", nutella.calcularValorFracionado());
        Nutella.put("valorItemPorReceita", nutella.valorItemPorReceita());
        Nutella.put("quantPacote", nutella.getQuantPacote());
        Nutella.put("quantUsadaReceita", nutella.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(Nutella).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        ItemEstoqueModel frangoDesfiado = new ItemEstoqueModel();

        frangoDesfiado.setNameItem("Frango desfiado");
        frangoDesfiado.setValorItem("9.00");
        frangoDesfiado.setQuantItem("1");
        frangoDesfiado.setUnidMedida("1000");
        frangoDesfiado.setUnidReceita("1");
        frangoDesfiado.setQuantPacote("1");
        frangoDesfiado.setQuantUsadaReceita("1000");

        Map<String, String> FrangoDesfiado = new HashMap<>();

        FrangoDesfiado.put("nameItem", frangoDesfiado.getNameItem());
        FrangoDesfiado.put("versionEstoque", veriosnCodi);
        FrangoDesfiado.put("valorItem", frangoDesfiado.getValorItem());
        FrangoDesfiado.put("quantItem", frangoDesfiado.getQuantItem());
        FrangoDesfiado.put("unidMedida", frangoDesfiado.getUnidMedida());
        FrangoDesfiado.put("unidReceita", frangoDesfiado.getUnidReceita());
        FrangoDesfiado.put("valorFracionado", frangoDesfiado.calcularValorFracionado());
        FrangoDesfiado.put("valorItemPorReceita", frangoDesfiado.valorItemPorReceita());
        FrangoDesfiado.put("quantPacote", frangoDesfiado.getQuantPacote());
        FrangoDesfiado.put("quantUsadaReceita", frangoDesfiado.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(FrangoDesfiado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel cebola = new ItemEstoqueModel();

        cebola.setNameItem("Cebola");
        cebola.setValorItem("0.50");
        cebola.setQuantItem("1");
        cebola.setUnidMedida("1");
        cebola.setUnidReceita("1");
        cebola.setQuantPacote("1");
        cebola.setQuantUsadaReceita("1");


        Map<String, String> Cebola = new HashMap<>();

        Cebola.put("nameItem", cebola.getNameItem());
        Cebola.put("versionEstoque", veriosnCodi);
        Cebola.put("valorItem", cebola.getValorItem());
        Cebola.put("quantItem", cebola.getQuantItem());
        Cebola.put("unidMedida", cebola.getUnidMedida());
        Cebola.put("unidReceita", cebola.getUnidReceita());
        Cebola.put("valorFracionado", cebola.calcularValorFracionado());
        Cebola.put("valorItemPorReceita", cebola.valorItemPorReceita());
        Cebola.put("quantPacote", cebola.getQuantPacote());
        Cebola.put("quantUsadaReceita", cebola.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(Cebola).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel seleta = new ItemEstoqueModel();

        seleta.setNameItem("Seleta");
        seleta.setValorItem("2.50");
        seleta.setQuantItem("1");
        seleta.setUnidMedida("1");
        seleta.setUnidReceita("1");
        seleta.setQuantPacote("1");
        seleta.setQuantUsadaReceita("1");


        Map<String, String> Seleta = new HashMap<>();

        Seleta.put("nameItem", seleta.getNameItem());
        Seleta.put("versionEstoque", veriosnCodi);
        Seleta.put("valorItem", seleta.getValorItem());
        Seleta.put("quantItem", seleta.getQuantItem());
        Seleta.put("unidMedida", seleta.getUnidMedida());
        Seleta.put("unidReceita", seleta.getUnidReceita());
        Seleta.put("valorFracionado", seleta.calcularValorFracionado());
        Seleta.put("valorItemPorReceita", seleta.valorItemPorReceita());
        Seleta.put("quantPacote", seleta.getQuantPacote());
        Seleta.put("quantUsadaReceita", seleta.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(Seleta).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel massaTomate = new ItemEstoqueModel();

        massaTomate.setNameItem("Massa de Tomate");
        massaTomate.setValorItem("1.05");
        massaTomate.setQuantItem("1");
        massaTomate.setUnidMedida("1");
        massaTomate.setUnidReceita("1");
        massaTomate.setQuantPacote("1");
        massaTomate.setQuantUsadaReceita("1");


        Map<String, String> MassaTomate = new HashMap<>();

        MassaTomate.put("nameItem", massaTomate.getNameItem());
        MassaTomate.put("versionEstoque", veriosnCodi);
        MassaTomate.put("valorItem", massaTomate.getValorItem());
        MassaTomate.put("quantItem", massaTomate.getQuantItem());
        MassaTomate.put("unidMedida", massaTomate.getUnidMedida());
        MassaTomate.put("unidReceita", massaTomate.getUnidReceita());
        MassaTomate.put("valorFracionado", massaTomate.calcularValorFracionado());
        MassaTomate.put("valorItemPorReceita", massaTomate.valorItemPorReceita());
        MassaTomate.put("quantPacote", massaTomate.getQuantPacote());
        MassaTomate.put("quantUsadaReceita", massaTomate.getQuantUsadaReceita());


        db.collection("Item_Estoque").add(MassaTomate).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        ItemEstoqueModel azeitonas = new ItemEstoqueModel();

        azeitonas.setNameItem("Azeitonas");
        azeitonas.setValorItem("1.50");
        azeitonas.setQuantItem("1");
        azeitonas.setUnidMedida("1");
        azeitonas.setUnidReceita("1");
        azeitonas.setQuantPacote("50");
        azeitonas.setQuantUsadaReceita("50");

        Map<String, String> Azeitonas = new HashMap<>();


        Azeitonas.put("nameItem", azeitonas.getNameItem());
        Azeitonas.put("versionEstoque", veriosnCodi);
        Azeitonas.put("valorItem", azeitonas.getValorItem());
        Azeitonas.put("quantItem", azeitonas.getQuantItem());
        Azeitonas.put("unidMedida", azeitonas.getUnidMedida());
        Azeitonas.put("unidReceita", azeitonas.getUnidReceita());
        Azeitonas.put("valorFracionado", azeitonas.calcularValorFracionado());
        Azeitonas.put("valorItemPorReceita", azeitonas.valorItemPorReceita());
        Azeitonas.put("quantPacote", azeitonas.getQuantPacote());
        Azeitonas.put("quantUsadaReceita", azeitonas.getQuantUsadaReceita());

        db.collection("Item_Estoque").add(Azeitonas).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }


    public ItemEstoqueModel(String nameItem, String valorItem, String quantItem, String unidMedida, String unidReceita) {
        this.nameItem = nameItem;
        this.valorItem = valorItem;
        this.quantItem = quantItem;
        this.unidMedida = unidMedida;
        this.unidReceita = unidReceita;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getValorItem() {
        return valorItem;
    }

    public void setValorItem(String valorItem) {
        this.valorItem = valorItem;
    }

    public String getQuantItem() {
        return quantItem;
    }

    public void setQuantItem(String quantItem) {
        this.quantItem = quantItem;
    }

    public String getUnidMedida() {
        return unidMedida;
    }

    public void setUnidMedida(String unidMedida) {
        this.unidMedida = unidMedida;
    }

    public String getUnidReceita() {
        return unidReceita;
    }

    public void setUnidReceita(String unidReceita) {
        this.unidReceita = unidReceita;
    }

    public String getQuantPacote() {
        return quantPacote;
    }

    public String getQuantUsadaReceita() {
        return quantUsadaReceita;
    }

    public void setQuantUsadaReceita(String quantUsadaReceita) {
        this.quantUsadaReceita = quantUsadaReceita;
    }

    public void setQuantPacote(String quantPacote) {
        this.quantPacote = quantPacote;
    }

    public String getCalcularValorFracionado() {
        return valorFracionado;
    }

    public String calcularValorFracionado() {


        double valor = Double.parseDouble(getValorItem());
        double quantidadePct = Double.parseDouble(getQuantPacote());
        double unidMedida = Double.parseDouble(getUnidMedida());

        double totalPorPacote = quantidadePct * unidMedida;
        double resultado = Double.valueOf(valor / totalPorPacote);

        valorFracionado = String.valueOf(resultado);

        return valorFracionado;
    }

    public String getValorItemPorReceita() {
        return valorItemPorReceita;
    }

    public String setValorIngredienteEstoqueReceita(String valor) {

        this.valorIngredienteEstoqueReceita = valor;
        return valorIngredienteEstoqueReceita;

    }

    public String getValorIngredienteEstoqueReceita() {

        return valorIngredienteEstoqueReceita;
    }

    public String valorItemPorReceita() {


        double valorFracionado = Double.parseDouble(calcularValorFracionado());
        double quantUsadaReceita = Double.parseDouble(getQuantUsadaReceita());

        double result = valorFracionado * quantUsadaReceita;

        valorItemPorReceita = String.valueOf(result);

        return valorItemPorReceita;
    }

    public void salvarItemEstoque() {

        /*String idCodi = Base64Custom.codifcarBase64()*/


        DatabaseReference reference = ConfiguracaoFirebase.getReference();
        reference.child("Item_Estoque").push()
                .setValue(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference ref = db.collection("Item_Estoque");

        String veriosnCodi = IdEstoque;


        Map<String, String> item = new HashMap<>();

        item.put("nameItem", getNameItem());
        item.put("versionEstoque", veriosnCodi);
        item.put("valorItem", getValorItem());
        item.put("quantItem", getQuantItem());
        item.put("unidMedida", getUnidMedida());
        item.put("unidReceita", getUnidReceita());
        item.put("valorFracionado", calcularValorFracionado());
        item.put("valorItemPorReceita", valorItemPorReceita());
        item.put("quantPacote", getQuantPacote());
        item.put("quantUsadaReceita", getQuantUsadaReceita());

        db.collection("Item_Estoque").add(item).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Log.i("Firestore", documentReference.getId());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public boolean isChecked() {

        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }


}
