package com.marcel.a.n.roxha.deliciasdamamae.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.LojaActivityV2;

public class LoadingDialog {

    public  Activity activity;
    public Context context;
    public AlertDialog dialog;


    public LoadingDialog(Activity activity){
            this.activity = activity;
    }

   public void comecandoCarregarDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);

        LayoutInflater inflaterLayout = this.activity.getLayoutInflater();
        builder.setView(inflaterLayout.inflate(R.layout.progress_dialog_carregando_informacoes_caixa_diario, null));
        builder.setCancelable(true);

        this.dialog = builder.create();
        this.dialog.show();
    }

   public  void finalizandoDialog(){
        dialog.dismiss();
    }

}
