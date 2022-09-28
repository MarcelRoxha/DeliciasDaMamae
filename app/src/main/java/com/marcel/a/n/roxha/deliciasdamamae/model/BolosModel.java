package com.marcel.a.n.roxha.deliciasdamamae.model;

import android.content.pm.PackageInfo;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BolosModel implements Serializable {

    private String idBoloCadastrado;
    private String idReferenciaReceitaCadastrada;
    private String nomeBoloCadastrado;
    private String custoTotalDaReceitaDoBolo;
    private String valorCadastradoParaVendasNaBoleria;
    private String valorCadastradoParaVendasNoIfood;
    private String porcentagemAdicionadoPorContaDoIfood;
    private String porcentagemAdicionadoPorContaDoLucro;
    private String valorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem;
    private String valorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro;
    private String enderecoFoto;
    private String valorQueOBoloRealmenteFoiVendido;
    private int verificaCameraGaleria;


    public BolosModel() {
    }

    public String getIdBoloCadastrado() {
        return idBoloCadastrado;
    }

    public void setIdBoloCadastrado(String idBoloCadastrado) {
        this.idBoloCadastrado = idBoloCadastrado;
    }
    public String getIdReferenciaReceitaCadastrada() {
        return idReferenciaReceitaCadastrada;
    }

    public void setIdReferenciaReceitaCadastrada(String idReferenciaReceitaCadastrada) {
        this.idReferenciaReceitaCadastrada = idReferenciaReceitaCadastrada;
    }

    public String getNomeBoloCadastrado() {
        return nomeBoloCadastrado;
    }

    public void setNomeBoloCadastrado(String nomeBoloCadastrado) {
        this.nomeBoloCadastrado = nomeBoloCadastrado;
    }

    public String getCustoTotalDaReceitaDoBolo() {
        return custoTotalDaReceitaDoBolo;
    }

    public void setCustoTotalDaReceitaDoBolo(String custoTotalDaReceitaDoBolo) {
        this.custoTotalDaReceitaDoBolo = custoTotalDaReceitaDoBolo;
    }

    public String getValorCadastradoParaVendasNaBoleria() {
        return valorCadastradoParaVendasNaBoleria;
    }

    public void setValorCadastradoParaVendasNaBoleria(String valorCadastradoParaVendasNaBoleria) {
        this.valorCadastradoParaVendasNaBoleria = valorCadastradoParaVendasNaBoleria;
    }

    public String getValorCadastradoParaVendasNoIfood() {
        return valorCadastradoParaVendasNoIfood;
    }

    public void setValorCadastradoParaVendasNoIfood(String valorCadastradoParaVendasNoIfood) {
        this.valorCadastradoParaVendasNoIfood = valorCadastradoParaVendasNoIfood;
    }

    public String getPorcentagemAdicionadoPorContaDoIfood() {
        return porcentagemAdicionadoPorContaDoIfood;
    }

    public void setPorcentagemAdicionadoPorContaDoIfood(String porcentagemAdicionadoPorContaDoIfood) {
        this.porcentagemAdicionadoPorContaDoIfood = porcentagemAdicionadoPorContaDoIfood;
    }

    public String getPorcentagemAdicionadoPorContaDoLucro() {
        return porcentagemAdicionadoPorContaDoLucro;
    }

    public void setPorcentagemAdicionadoPorContaDoLucro(String porcentagemAdicionadoPorContaDoLucro) {
        this.porcentagemAdicionadoPorContaDoLucro = porcentagemAdicionadoPorContaDoLucro;
    }

    public String getValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem() {
        return valorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem;
    }

    public void setValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem(String valorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem) {
        this.valorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem = valorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem;
    }


    public String getValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro() {
        return valorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro;
    }

    public void setValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro(String valorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro) {
        this.valorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro = valorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro;
    }



    public String getEnderecoFoto() {
        return enderecoFoto;
    }

    public void setEnderecoFoto(String enderecoFoto) {
        this.enderecoFoto = enderecoFoto;
    }

    public String getValorQueOBoloRealmenteFoiVendido() {
        return valorQueOBoloRealmenteFoiVendido;
    }

    public void setValorQueOBoloRealmenteFoiVendido(String valorQueOBoloRealmenteFoiVendido) {
        this.valorQueOBoloRealmenteFoiVendido = valorQueOBoloRealmenteFoiVendido;
    }

    public int getVerificaCameraGaleria() {
        return verificaCameraGaleria;
    }

    public void setVerificaCameraGaleria(int verificaCameraGaleria) {
        this.verificaCameraGaleria = verificaCameraGaleria;
    }

    @Override
    public String toString() {
        return "BolosModel{" +
                "idBoloCadastrado='" + idBoloCadastrado + '\'' + "\n" +
                ", idReferenciaReceitaCadastrada='" + idReferenciaReceitaCadastrada + '\'' + "\n" +
                ", nomeBoloCadastrado='" + nomeBoloCadastrado + '\'' + "\n" +
                ", custoTotalDaReceitaDoBolo='" + custoTotalDaReceitaDoBolo + '\'' + "\n" +
                ", valorCadastradoParaVendasNaBoleria='" + valorCadastradoParaVendasNaBoleria + '\'' + "\n" +
                ", valorCadastradoParaVendasNoIfood='" + valorCadastradoParaVendasNoIfood + '\'' + "\n" +
                ", porcentagemAdicionadoPorContaDoIfood='" + porcentagemAdicionadoPorContaDoIfood + '\'' + "\n" +
                ", porcentagemAdicionadoPorContaDoLucro='" + porcentagemAdicionadoPorContaDoLucro + '\'' + "\n" +
                ", valorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem='" + valorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem + '\'' + "\n" +
                ", valorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro='" + valorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro + '\'' + "\n" +
                ", valorQueOBoloRealmenteFoiVendido='" + valorQueOBoloRealmenteFoiVendido + '\'' + "\n" +
                ", verificaCameraGaleria=" + verificaCameraGaleria + "\n" +
                ", enderecoFoto='" + enderecoFoto + '\'' + "\n" +
                '}';
    }
}