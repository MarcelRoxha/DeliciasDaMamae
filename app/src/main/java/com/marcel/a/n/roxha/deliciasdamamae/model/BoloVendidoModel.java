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

    private String idDoProdutoVendido;
    private String idDoCaixaQueFoiAdicionado;
    private String nomeDoProdutoVendido;
    private String valorQueOProdutoFoiVendido;
    private String custoQueOProdutoTeveAoSerConfeccionado;
    private String vendaFoiFeitaPorQualPlataforma;
    private String dataDaVenda;


    public BoloVendidoModel() {
    }

    public BoloVendidoModel(String idDoProdutoVendido, String idDoCaixaQueFoiAdicionado, String nomeDoProdutoVendido, String valorQueOProdutoFoiVendido, String custoQueOProdutoTeveAoSerConfeccionado, String vendaFoiFeitaPorQualPlataforma, String dataDaVenda) {
        this.idDoProdutoVendido = idDoProdutoVendido;
        this.idDoCaixaQueFoiAdicionado = idDoCaixaQueFoiAdicionado;
        this.nomeDoProdutoVendido = nomeDoProdutoVendido;
        this.valorQueOProdutoFoiVendido = valorQueOProdutoFoiVendido;
        this.custoQueOProdutoTeveAoSerConfeccionado = custoQueOProdutoTeveAoSerConfeccionado;
        this.vendaFoiFeitaPorQualPlataforma = vendaFoiFeitaPorQualPlataforma;
        this.dataDaVenda = dataDaVenda;
    }

    public String getIdDoProdutoVendido() {
        return idDoProdutoVendido;
    }

    public void setIdDoProdutoVendido(String idDoProdutoVendido) {
        this.idDoProdutoVendido = idDoProdutoVendido;
    }

    public String getIdDoCaixaQueFoiAdicionado() {
        return idDoCaixaQueFoiAdicionado;
    }

    public void setIdDoCaixaQueFoiAdicionado(String idDoCaixaQueFoiAdicionado) {
        this.idDoCaixaQueFoiAdicionado = idDoCaixaQueFoiAdicionado;
    }

    public String getNomeDoProdutoVendido() {
        return nomeDoProdutoVendido;
    }

    public void setNomeDoProdutoVendido(String nomeDoProdutoVendido) {
        this.nomeDoProdutoVendido = nomeDoProdutoVendido;
    }

    public String getValorQueOProdutoFoiVendido() {
        return valorQueOProdutoFoiVendido;
    }

    public void setValorQueOProdutoFoiVendido(String valorQueOProdutoFoiVendido) {
        this.valorQueOProdutoFoiVendido = valorQueOProdutoFoiVendido;
    }

    public String getCustoQueOProdutoTeveAoSerConfeccionado() {
        return custoQueOProdutoTeveAoSerConfeccionado;
    }

    public void setCustoQueOProdutoTeveAoSerConfeccionado(String custoQueOProdutoTeveAoSerConfeccionado) {
        this.custoQueOProdutoTeveAoSerConfeccionado = custoQueOProdutoTeveAoSerConfeccionado;
    }

    public String getVendaFoiFeitaPorQualPlataforma() {
        return vendaFoiFeitaPorQualPlataforma;
    }

    public void setVendaFoiFeitaPorQualPlataforma(String vendaFoiFeitaPorQualPlataforma) {
        this.vendaFoiFeitaPorQualPlataforma = vendaFoiFeitaPorQualPlataforma;
    }

    public String getDataDaVenda() {
        return dataDaVenda;
    }

    public void setDataDaVenda(String dataDaVenda) {
        this.dataDaVenda = dataDaVenda;
    }

    @Override
    public String toString() {
        return "BoloVendidoModel{" +
                "idDoProdutoVendido='" + idDoProdutoVendido + '\'' +
                ", idDoCaixaQueFoiAdicionado='" + idDoCaixaQueFoiAdicionado + '\'' +
                ", nomeDoProdutoVendido='" + nomeDoProdutoVendido + '\'' +
                ", valorQueOProdutoFoiVendido='" + valorQueOProdutoFoiVendido + '\'' +
                ", custoQueOProdutoTeveAoSerConfeccionado='" + custoQueOProdutoTeveAoSerConfeccionado + '\'' +
                ", vendaFoiFeitaPorQualPlataforma='" + vendaFoiFeitaPorQualPlataforma + '\'' +
                ", dataDaVenda='" + dataDaVenda + '\'' +
                '}';
    }
}

