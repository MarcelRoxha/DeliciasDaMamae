package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloGastosFixosDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosFixos;

public class CadastrarOuAtualizarGastoFixoActivity extends AppCompatActivity {

    private Button botaoVoltar, botaoCadastrar;
    private EditText nomeGastoFixo, valorDesseGasto, DiaDeCobrancaDesseGasto;

    private ModeloGastosFixos modeloGastosFixosCadastra, modeloGastosFixosAtualiza;

    private ModeloGastosFixosDAO modeloGastosFixosDAO;

    private String mensagemDeCamposInvalidos = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_cadastrar_ou_atualizar_gasto_fixo);


        botaoVoltar = findViewById(R.id.botaoCancelaOCadastroGastoFixoId);
        botaoCadastrar = findViewById(R.id.botaoCadastrarGastoFixoId);

        nomeGastoFixo = findViewById(R.id.inputNomeGastoFixoId);
        valorDesseGasto = findViewById(R.id.inputValorGastoFixoId);
        DiaDeCobrancaDesseGasto = findViewById(R.id.inputDiaCobradoDoGastoFixoId);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                String nomeDigitado = nomeGastoFixo.getText().toString();
                String valorDigitado = valorDesseGasto.getText().toString();
                String diaDigitado = DiaDeCobrancaDesseGasto.getText().toString();
                int diaConvertido = Integer.parseInt(diaDigitado);

                if(!nomeDigitado.isEmpty() && nomeDigitado.length() > 2){
                    if(!valorDigitado.isEmpty() && !valorDigitado.equals("0") & !valorDigitado.equals("00")){
                        if(!diaDigitado.isEmpty() && diaConvertido > 0 && diaConvertido < 32){
                            modeloGastosFixosDAO = new ModeloGastosFixosDAO(CadastrarOuAtualizarGastoFixoActivity.this);
                            double valorDigitadoConvertido = Double.parseDouble(valorDigitado);

                            modeloGastosFixosCadastra = new ModeloGastosFixos();
                            modeloGastosFixosCadastra.setNomeDoGastoFixo(nomeDigitado);
                            modeloGastosFixosCadastra.setValorGastoFixo(valorDigitadoConvertido);
                            modeloGastosFixosCadastra.setDataPrevistaDePagamento("Todo dia " + diaDigitado);
                            modeloGastosFixosCadastra.setDataPagamento("CADASTRO");
                            System.out.println("modeloGastosFixosCadastra " + modeloGastosFixosCadastra.toString());
                            modeloGastosFixosDAO.cadastrarGastoFixo(modeloGastosFixosCadastra);
                            finish();


                        }else{
                            mensagemDeCamposInvalidos = "Essa data não é valida, digite um valor entre 0 e 31";
                        }
                    }else {
                        mensagemDeCamposInvalidos = "Valor Digitado é invalido, não pode ser 0 nem 00 precisa ser um valor que seja passível de cálculo";
                    }

                }else{
                    mensagemDeCamposInvalidos = "Nome digitado inválido, não pode ser vazio e nem sigla";
                }

                System.out.println("nome " + nomeDigitado + "\nvalor " + valorDigitado + "\ndia " + diaDigitado);

            }
        });
    }
}