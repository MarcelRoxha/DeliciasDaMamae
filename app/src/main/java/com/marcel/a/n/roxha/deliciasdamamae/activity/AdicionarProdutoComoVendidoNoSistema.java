package com.marcel.a.n.roxha.deliciasdamamae.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ajudateconta.helper.FormatTextSaldo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloMontanteDiarioDAO;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloMontanteMensalDAO;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloProcessaVendaFeitaDAO;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloProdutoVendidoDAO;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.CaixaLojaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMaquininhaDePassarCartao;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloProdutoVendido;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdicionarProdutoComoVendidoNoSistema extends AppCompatActivity {

    //COMPONENTES DE TELA
    private ImageView imagemDoProdutoNoProcessoDeVenda;
    private TextView textoNomeDoProdutoNoProcessoDeVenda;
    private TextView nomeRecuperadoDoProdutoNoProcessoDeVenda;

    private TextView textoValorVendaLojaDoProdutoNoProcessoDeVenda;
    private TextView valorVendaLojaRecuperadoDoProdutoNoProcessoDeVenda;
    private TextView textoValorVendaIfoodDoProdutoNoProcessoDeVenda;
    private TextView valorVendaIfoodRecuperadoDoProdutoNoProcessoDeVenda;
    private TextView textoValorQueVaiSerVendidoMesmo;
    private TextView textoMetodoDePagamento;
    private RadioGroup radioGroupEscolherValoresCadastrados;
    private RadioGroup radioGroupEscolherMetodoDePagamento;
    private RadioButton radioButtonSelecionarPrecoDeVendaNaLoja;
    private RadioButton radioButtonSelecionarPrecoDeVendaNoIfood;
    private RadioButton radioButtonPagouNoDinheiroOuPix;
    private RadioButton radioButtonPagouNoDinheiro;
    private RadioButton radioButtonPagouNoDebito;
    private RadioButton radioButtonPagouNoCredito;
    private Button botaoConfirmar;
    private Button botaoCancelar;
    private TextView textoInformativoParaEscolherUmDosValoresCadastradosOu;
    private Button botaoParaEditarValorParaVenda;

    public AlertDialog progressDialogCarregando;

    public Date dataAtual = new Date();
    private SimpleDateFormat formatoDataDiaMesAno = new SimpleDateFormat("dd/MM/yyyy");

    private String dataRegistroDoProcessoDeVenda = "";
    private AlertDialog.Builder alertaEditarValorDoProdutoNoProcessoDeVenda;

    //INFO BANCO
    private FirebaseFirestore firestoreProdutoRecuperadoParaProcessoDeVenda = ConfiguracaoFirebase.getFirestor();
    private final String COLLECTION_BOLOS_EXPOSTOS_VITRINE = "BOLOS_EXPOSTOS_VITRINE";
    private CollectionReference collectionReferenceProdutoRecuperadoParaProcessoDeVenda = firestoreProdutoRecuperadoParaProcessoDeVenda.collection(COLLECTION_BOLOS_EXPOSTOS_VITRINE);


    //VARIAVEIS DO PRODUTO
    private String itemKey;
    private String nomeRecuperadoExpostoVitrine;
    private String itemKeyMontanteMensal;
    private String itemKeyMontanteDiario;
    private String itemKeyIdProdutoCadastrado;
    private String custoDoProdutoRecuperadoExpostoVitrine;

    private String enderecoFotoProduto;
    private String nomeDoProdutoRecuperado;
    private String valorDoIfoodDoProdutoRecuperado;
    private String valorNaLojaDoProdutoRecuperado;
    private String valoQueFoiVendidoOProdutoRecuperado = "0";
    private boolean oValorFoiEditado = false;

    private String METODO_DE_PAGAMENTO = "";
    private String PLATAFORMA_VENDIDA = "";

    //CLASSES
    private BolosModel produtoRecuperadoPraExibirNaTela;
    private ModeloProdutoVendido produtoVendidoProcessado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_adicionar_produto_como_vendido_no_sistema);
        imagemDoProdutoNoProcessoDeVenda = findViewById(R.id.imagemDoProdutoNoProcessoDeVenda);
        nomeRecuperadoDoProdutoNoProcessoDeVenda = findViewById(R.id.nomeDoProdutoExibidoNoProcessoDeVenda);
        textoNomeDoProdutoNoProcessoDeVenda = findViewById(R.id.textView89);


        textoValorVendaLojaDoProdutoNoProcessoDeVenda = findViewById(R.id.textView96);
        textoValorQueVaiSerVendidoMesmo = findViewById(R.id.textoValorQueVaiSerVendidoMesmoId);
        textoMetodoDePagamento = findViewById(R.id.textoMetodoDePagamentoId);

        valorVendaLojaRecuperadoDoProdutoNoProcessoDeVenda = findViewById(R.id.valorCadastradoParaVendaNaLojaProcessoDeVenda);


        textoValorVendaIfoodDoProdutoNoProcessoDeVenda = findViewById(R.id.textView98);
        valorVendaIfoodRecuperadoDoProdutoNoProcessoDeVenda = findViewById(R.id.valorCadastradoParaVendaNoIfoodProcessoDeVenda);


        textoInformativoParaEscolherUmDosValoresCadastradosOu = findViewById(R.id.textoInformaParaEscolherUmDosValoresCadastradaosId);
        botaoParaEditarValorParaVenda = findViewById(R.id.botaoParaEditarOValorNaHoraDaVenda);


        radioGroupEscolherValoresCadastrados = findViewById(R.id.radioGroupEcolherValoresCadatradosNoProcessoDeVenda);
        radioButtonSelecionarPrecoDeVendaNaLoja = findViewById(R.id.radioButtonPrecoCadastradoParaVendaNaLojaNoProcessoDeVenda);
        radioButtonSelecionarPrecoDeVendaNoIfood = findViewById(R.id.radioButtonPrecoCadastradoParaVendaNoIfoodNoProcessoDeVenda);


        radioGroupEscolherMetodoDePagamento = findViewById(R.id.radioGroupMetodoDePagamentoId);
        radioButtonPagouNoDinheiroOuPix = findViewById(R.id.radioButtonPagouNoDinheiroOuPixId);
        radioButtonPagouNoDebito = findViewById(R.id.radioButtonPagouNoDebitoId);
        radioButtonPagouNoCredito = findViewById(R.id.radioButtonPagouNoCreditoId);
        radioButtonPagouNoDinheiro = findViewById(R.id.radioButtonPagouNoDinheiroId);


        botaoConfirmar = findViewById(R.id.botaoConfirmarVendaNoProcessoDeVenda);
        botaoCancelar = findViewById(R.id.botaoCancelarProcessoDeVendaId);
        carregarCargaDeDesaparecerConteudoDaTela();

        AlertDialog.Builder builder = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);

        LayoutInflater inflaterLayout = AdicionarProdutoComoVendidoNoSistema.this.getLayoutInflater();
        builder.setView(inflaterLayout.inflate(R.layout.progress_dialog_carregando_informacoes_caixa_diario, null));

        this.progressDialogCarregando = builder.create();
        this.progressDialogCarregando.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        this.progressDialogCarregando.show();
        produtoRecuperadoPraExibirNaTela = new BolosModel();

        itemKey = getIntent().getStringExtra("itemKey");
        itemKeyMontanteMensal = getIntent().getStringExtra("itemKeyMontanteMensal");
        itemKeyMontanteDiario = getIntent().getStringExtra("itemKeyMontanteDiario");
        itemKeyIdProdutoCadastrado = getIntent().getStringExtra("itemKeyIdProdutoCadastrado");
        nomeRecuperadoExpostoVitrine = getIntent().getStringExtra("nomeRecuperadoExpostoVitrine");
        custoDoProdutoRecuperadoExpostoVitrine = getIntent().getStringExtra("custoDoProdutoRecuperadoExpostoVitrine");
        enderecoFotoProduto = getIntent().getStringExtra("fotoDoProduto");

        if (itemKey != null) {
            produtoVendidoProcessado = new ModeloProdutoVendido();
            produtoVendidoProcessado.setIdDoProdutoVendido(itemKey);
            produtoVendidoProcessado.setIdDoProdutoCadastrado(itemKeyIdProdutoCadastrado);
            collectionReferenceProdutoRecuperadoParaProcessoDeVenda.document(itemKey).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            carregarCargaDeAparecimentoDoConteudoDaTela();
                            progressDialogCarregando.dismiss();

                            produtoRecuperadoPraExibirNaTela = documentSnapshot.toObject(BolosModel.class);

//                            enderecoFotoProduto = produtoRecuperadoPraExibirNaTela.getEnderecoFoto();
                            nomeDoProdutoRecuperado = produtoRecuperadoPraExibirNaTela.getNomeBoloCadastrado();
                            valorDoIfoodDoProdutoRecuperado = produtoRecuperadoPraExibirNaTela.getValorCadastradoParaVendasNoIfood();
                            valorNaLojaDoProdutoRecuperado = produtoRecuperadoPraExibirNaTela.getValorCadastradoParaVendasNaBoleria();
                            Glide.with(AdicionarProdutoComoVendidoNoSistema.this).load(enderecoFotoProduto).into(imagemDoProdutoNoProcessoDeVenda);

                            nomeRecuperadoDoProdutoNoProcessoDeVenda.setText(nomeDoProdutoRecuperado);

                            valorVendaIfoodRecuperadoDoProdutoNoProcessoDeVenda.setText("R$: " + valorDoIfoodDoProdutoRecuperado);
                            valorVendaLojaRecuperadoDoProdutoNoProcessoDeVenda.setText("R$: " + valorNaLojaDoProdutoRecuperado);
                            radioButtonSelecionarPrecoDeVendaNaLoja.setText("Vendi pela Loja no valor de R$: " + valorNaLojaDoProdutoRecuperado);
                            radioButtonSelecionarPrecoDeVendaNoIfood.setText("Vendi pelo Ifood no valor de R$: " + valorDoIfoodDoProdutoRecuperado);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdicionarProdutoComoVendidoNoSistema.this, "Algo deu erro verifique a internet e tente novamente", Toast.LENGTH_SHORT).show();
                        }
                    });


        } else {

            Intent intentVoltaPorQueNaoPegouOId = new Intent(this.getApplicationContext(), CaixaLojaFragment.class);
            startActivity(intentVoltaPorQueNaoPegouOId);
            finish();
        }


        botaoParaEditarValorParaVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButtonSelecionarPrecoDeVendaNoIfood.setChecked(false);
                radioButtonSelecionarPrecoDeVendaNaLoja.setChecked(false);
                carregarAlertaParaEditarOValorDoProduto();

            }
        });

        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (radioButtonSelecionarPrecoDeVendaNoIfood.isChecked()) {
                        carregaAlertaVendaFeitaPeloIfood();
                    } else {
                        System.out.println("valor recuperado ================= " + valorNaLojaDoProdutoRecuperado);
                        processaVenda(valorNaLojaDoProdutoRecuperado);
                    }

                    /*TODO necessario adicionar tratativa para o valor do ifood quando tiver desconto inesperado */


                } catch (Exception e) {
                    System.out.println("Error ao confirmar a venda feita --XH-- " + e.getMessage());
                }

            }
        });

        radioButtonPagouNoDinheiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioButtonPagouNoDinheiro.isChecked()) {
                    carregaAlertaQueVendeuNoDinheiro();
                }

            }
        });


        radioButtonSelecionarPrecoDeVendaNoIfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textoValorQueVaiSerVendidoMesmo.setText("Valor que será vendido é R$: " + valorDoIfoodDoProdutoRecuperado);

            }
        });

        radioButtonSelecionarPrecoDeVendaNaLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textoValorQueVaiSerVendidoMesmo.setText("Valor que será vendido é R$: " + valorNaLojaDoProdutoRecuperado);
                valoQueFoiVendidoOProdutoRecuperado = valorNaLojaDoProdutoRecuperado;
            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentVoltaPorQueCancelouOProcesso = new Intent(AdicionarProdutoComoVendidoNoSistema.this, LojaActivityV2.class);
                startActivity(intentVoltaPorQueCancelouOProcesso);
                finish();
            }
        });


        radioButtonPagouNoCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textoMetodoDePagamento.setText("Pagou no: CRÉDITO");

            }
        });

        radioButtonPagouNoDinheiroOuPix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textoMetodoDePagamento.setText("Pagou no: DINHEIRO OU PIX");

            }
        });

        radioButtonPagouNoDebito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textoMetodoDePagamento.setText("Pagou no: DÉBITO");


            }
        });

    }

    private void carregaAlertaQueVendeuNoDinheiro() {

        AlertDialog.Builder alertaVendaNoDinheiro = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);
        AlertDialog dialog = alertaVendaNoDinheiro.create();
        LayoutInflater inflater = AdicionarProdutoComoVendidoNoSistema.this.getLayoutInflater();
        dialog.setView(inflater.inflate(R.layout.alerta_venda_no_dinheiro, null));
        View v = getLayoutInflater().inflate(R.layout.alerta_venda_no_dinheiro, null);


        EditText editarValorTroco, valorPagoPeloCliente;
        Button botaoConfirmar, botaoCancelar;


        botaoConfirmar = v.findViewById(R.id.botaoConfirmarVendaNoDinheiroId);
        botaoCancelar = v.findViewById(R.id.botaoCancelarVendaNoDinheiroId);
        editarValorTroco = v.findViewById(R.id.valorDoTrocoId);
        valorPagoPeloCliente = v.findViewById(R.id.valorPagoPeloClienteId);

        dialog.setView(v);
        dialog.create();
        dialog.show();

        System.out.println("Valor que o produto foi vendido --XH-- " + valoQueFoiVendidoOProdutoRecuperado);
        valorPagoPeloCliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                System.out.println("valor que foi vendido editable --XH-- " + valorQueOProdutoFoiVendidoConvertido);
//                System.out.println("valor que foi digitado editable --XH-- " + valorDigitado);

                if(editable.toString().length() > 0){
                    atualizarValorDoCampoDoTroco(editable.toString(), editarValorTroco);
                }
//                System.out.println("valor que foi digitado editable --XH-- " + editable.toString());
            }
        });


        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String valorDigitado = valorPagoPeloCliente.getText().toString();

                double valorQueOProdutoFoiVendidoConvertido = Double.parseDouble(valoQueFoiVendidoOProdutoRecuperado);

                double valorQueOClienteDeuConvertido = Double.parseDouble(valorDigitado);

                double resultado = valorQueOClienteDeuConvertido - valorQueOProdutoFoiVendidoConvertido;
                String trocoCalculado = String.valueOf(resultado);
                System.out.println("Resultado da subtração do valor dado pelo cliente e o valor vendido do produto --XH-- " + trocoCalculado);

                if(resultado < 1){

                    AlertDialog.Builder alertaValorPagoPeloCliente = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);
                    alertaValorPagoPeloCliente.setTitle("VALOR INDEVIDO");
                    alertaValorPagoPeloCliente.setMessage("Atenção, de acordo com o cálculo, não é possível ter um lucro" +
                            "considerado com o valor sendo pago abaixo do valor vendido");
                    alertaValorPagoPeloCliente.create();
                    alertaValorPagoPeloCliente.show();

                    editarValorTroco.addTextChangedListener(new FormatTextSaldo(editarValorTroco));

                    alertaValorPagoPeloCliente.setNegativeButton("ENTENDIDO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });


                }else{
                    ModeloProcessaVendaFeitaDAO modeloProcessaVendaFeitaDAO = new ModeloProcessaVendaFeitaDAO(AdicionarProdutoComoVendidoNoSistema.this);


                    System.out.println("Resultado da subtração do valor dado pelo cliente e o valor vendido do produto --XH-- " + trocoCalculado);
                    processaVenda(valoQueFoiVendidoOProdutoRecuperado);
                    ModeloMontanteMensalDAO montanteMensalDAO = new ModeloMontanteMensalDAO(AdicionarProdutoComoVendidoNoSistema.this);
                    ModeloMontanteDiarioDAO montanteDiarioDAO = new ModeloMontanteDiarioDAO(AdicionarProdutoComoVendidoNoSistema.this);
                    montanteMensalDAO.somaValorSaidaDevidoAoTrocoMontanteMensal(itemKeyMontanteMensal, trocoCalculado);
                    montanteDiarioDAO.somaValorDeTrocoDesseDia(itemKeyMontanteDiario, trocoCalculado);
                    ModeloProcessaVendaFeitaDAO processaVendaFeitaDAO = new ModeloProcessaVendaFeitaDAO(AdicionarProdutoComoVendidoNoSistema.this);
                    processaVendaFeitaDAO.removeBoloVitrineVendido(itemKey);




//                    editarValorTroco.setText(trocoCalculado);


                }







            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

                Toast.makeText(AdicionarProdutoComoVendidoNoSistema.this, "Clicou Cancelar", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void atualizarValorDoCampoDoTroco(String toString, EditText editText) {
        double valorDigitadoConvertido = Double.parseDouble(toString);
        double valorQueFoiVendido = Double.parseDouble(valoQueFoiVendidoOProdutoRecuperado);
        double troco = valorDigitadoConvertido - valorQueFoiVendido;
        String result = "";
        result = String.valueOf(troco);
        editText.setText(result);

    }

    private void carregaAlertaVendaFeitaPeloIfood() {


        AlertDialog.Builder builder = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);
        LayoutInflater inflaterLayout = AdicionarProdutoComoVendidoNoSistema.this.getLayoutInflater();
        builder.setView(inflaterLayout.inflate(R.layout.alerta_desconto_indevido_venda_ifood, null));
        builder.setCancelable(true);
        View v = getLayoutInflater().inflate(R.layout.alerta_desconto_indevido_venda_ifood, null);
        EditText valorDescontoIfood = v.findViewById(R.id.descontoIndevidoIfoodId);
        Button botaoConfirmar, botaoCancelar;

        botaoCancelar = v.findViewById(R.id.botaoCancelarDescontoIndevidoIfoodId);
        botaoConfirmar = v.findViewById(R.id.botaoConfirmarVendaComDescontoIndevidoIfoodId);
        builder.setView(v);
        builder.create();
        builder.show();

        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String valorDescontoInesperado = valorDescontoIfood.getText().toString();
                if (valorDescontoInesperado.isEmpty() || valorDescontoInesperado.equals("0")) {
                    String valorQueVaiSerVendidoMesmo = textoValorQueVaiSerVendidoMesmo.getText().toString();

                    if (!radioButtonSelecionarPrecoDeVendaNaLoja.isChecked() && !radioButtonSelecionarPrecoDeVendaNoIfood.isChecked() && valoQueFoiVendidoOProdutoRecuperado == "0") {
                        //Se cair nessa condição é por que não foi digitado um valor valido para ser processada a venda.

                        Toast.makeText(AdicionarProdutoComoVendidoNoSistema.this, "É NECESSÁRIO QUE SEJA SELECIONADO UM DOS VALORES PARA VENDA, ESCOLHA ENTRE VALORES CADASTRADOS NA LOJA E/OU IFOOD OU EDITE O VALOR DE VENDA", Toast.LENGTH_LONG).show();

                    } else {

                        processaVenda(valoQueFoiVendidoOProdutoRecuperado);

//                        if(radioButtonSelecionarPrecoDeVendaNoIfood.isChecked()){
//                            PLATAFORMA_VENDIDA = "IFOOD";
//                            valoQueFoiVendidoOProdutoRecuperado = valorDoIfoodDoProdutoRecuperado;
//                        }else if(radioButtonSelecionarPrecoDeVendaNaLoja.isChecked()){
//                            valoQueFoiVendidoOProdutoRecuperado = valorNaLojaDoProdutoRecuperado;
//                            PLATAFORMA_VENDIDA = "LOJA";
//                        }
//                        if(radioButtonPagouNoCredito.isChecked()){
//                            METODO_DE_PAGAMENTO = "CREDITO";
//                        }else if(radioButtonPagouNoDinheiroOuPix.isChecked()){
//                            METODO_DE_PAGAMENTO = "DINHEIROOUPIX";
//                        }else if(radioButtonPagouNoDebito.isChecked()){
//                            METODO_DE_PAGAMENTO = "DEBITO";
//                        }
//
//                        dataRegistroDoProcessoDeVenda = formatoDataDiaMesAno.format(dataAtual);
//                        String custoPreper=custoDoProdutoRecuperadoExpostoVitrine.replace(",", ".");
//                        double custoConvertidoDoProdutoVendido = Double.parseDouble(custoPreper);
//
//                        produtoVendidoProcessado.setPlataformaVendida(PLATAFORMA_VENDIDA);
//                        produtoVendidoProcessado.setMetodoDePagamento(METODO_DE_PAGAMENTO);
//                        produtoVendidoProcessado.setNomeDoProdutoVendido(nomeRecuperadoExpostoVitrine);
//                        produtoVendidoProcessado.setValorQueOBoloFoiVendido(valoQueFoiVendidoOProdutoRecuperado);
//                        produtoVendidoProcessado.setRegistroDaVenda(dataRegistroDoProcessoDeVenda);
//                        produtoVendidoProcessado.setCustoProduto(custoConvertidoDoProdutoVendido);
//                        ModeloProcessaVendaFeitaDAO modeloProcessaVendaFeitaDAO = new ModeloProcessaVendaFeitaDAO(AdicionarProdutoComoVendidoNoSistema.this);
//                        modeloProcessaVendaFeitaDAO.processaNoMontanteMensalVendaFeita(produtoVendidoProcessado, itemKeyMontanteMensal, itemKeyMontanteDiario);
//
//                    }
                    }
                } else {
                    String valorQueVaiSerVendidoMesmo = textoValorQueVaiSerVendidoMesmo.getText().toString();

                    if (!radioButtonSelecionarPrecoDeVendaNaLoja.isChecked() && !radioButtonSelecionarPrecoDeVendaNoIfood.isChecked() && valoQueFoiVendidoOProdutoRecuperado == "0") {
                        //Se cair nessa condição é por que não foi digitado um valor valido para ser processada a venda.

                        Toast.makeText(AdicionarProdutoComoVendidoNoSistema.this, "É NECESSÁRIO QUE SEJA SELECIONADO UM DOS VALORES PARA VENDA, ESCOLHA ENTRE VALORES CADASTRADOS NA LOJA E/OU IFOOD OU EDITE O VALOR DE VENDA", Toast.LENGTH_LONG).show();

                    } else {

//                        if(radioButtonSelecionarPrecoDeVendaNoIfood.isChecked()){
//                            PLATAFORMA_VENDIDA = "IFOOD";
//                            valoQueFoiVendidoOProdutoRecuperado = valorDoIfoodDoProdutoRecuperado;
//                        }else if(radioButtonSelecionarPrecoDeVendaNaLoja.isChecked()){
//                            valoQueFoiVendidoOProdutoRecuperado = valorNaLojaDoProdutoRecuperado;
//                            PLATAFORMA_VENDIDA = "LOJA";
//                        }
//                        if(radioButtonPagouNoCredito.isChecked()){
//                            METODO_DE_PAGAMENTO = "CREDITO";
//                        }else if(radioButtonPagouNoDinheiroOuPix.isChecked()){
//                            METODO_DE_PAGAMENTO = "DINHEIROOUPIX";
//                        }else if(radioButtonPagouNoDebito.isChecked()){
//                            METODO_DE_PAGAMENTO = "DEBITO";
//                        }

//                        dataRegistroDoProcessoDeVenda = formatoDataDiaMesAno.format(dataAtual);
                        String custoPreper = custoDoProdutoRecuperadoExpostoVitrine.replace(",", ".");
//                        double custoConvertidoDoProdutoVendido = Double.parseDouble(custoPreper);
                        double valorDescontoIndevidoConvertido = Double.parseDouble(valorDescontoInesperado);
                        double valorConverter = Double.parseDouble(valoQueFoiVendidoOProdutoRecuperado);
                        double valorQueVaiSerVendidoOProdutoComDesconto = valorConverter - valorDescontoIndevidoConvertido;

                        String valorQueVaiSerVendidoComDesconto = String.valueOf(valorQueVaiSerVendidoOProdutoComDesconto);

                        processaVenda(valorQueVaiSerVendidoComDesconto);
//                        produtoVendidoProcessado.setPlataformaVendida(PLATAFORMA_VENDIDA);
//                        produtoVendidoProcessado.setMetodoDePagamento(METODO_DE_PAGAMENTO);
//                        produtoVendidoProcessado.setNomeDoProdutoVendido(nomeRecuperadoExpostoVitrine);
//                        produtoVendidoProcessado.setValorQueOBoloFoiVendido(valorQueVaiSerVendidoComDesconto);
//                        produtoVendidoProcessado.setRegistroDaVenda(dataRegistroDoProcessoDeVenda);
//                        produtoVendidoProcessado.setCustoProduto(custoConvertidoDoProdutoVendido);
//                        ModeloProcessaVendaFeitaDAO modeloProcessaVendaFeitaDAO = new ModeloProcessaVendaFeitaDAO(AdicionarProdutoComoVendidoNoSistema.this);
//                        modeloProcessaVendaFeitaDAO.processaNoMontanteMensalVendaFeita(produtoVendidoProcessado, itemKeyMontanteMensal, itemKeyMontanteDiario);

                    }
                }


            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdicionarProdutoComoVendidoNoSistema.this, LojaActivityV2.class);
                startActivity(intent);
                finish();

            }
        });


    }

    public void carregarAlertaParaEditarOValorDoProduto() {
        alertaEditarValorDoProdutoNoProcessoDeVenda = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);
        LayoutInflater layoutAlertaEditarValorParaVenda = AdicionarProdutoComoVendidoNoSistema.this.getLayoutInflater();
        alertaEditarValorDoProdutoNoProcessoDeVenda.setView(layoutAlertaEditarValorParaVenda.inflate(R.layout.alerta_editar_valor_de_venda_do_produto, null));
        View viewAlert = getLayoutInflater().inflate(R.layout.alerta_editar_valor_de_venda_do_produto, null);

        alertaEditarValorDoProdutoNoProcessoDeVenda.setView(viewAlert);
        EditText editNomeReceitaCadastrando = viewAlert.findViewById(R.id.editValorDoProdutoNoProcessoDeVendaId);

        alertaEditarValorDoProdutoNoProcessoDeVenda.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                oValorFoiEditado = true;
                valoQueFoiVendidoOProdutoRecuperado = editNomeReceitaCadastrando.getText().toString();
                Toast.makeText(AdicionarProdutoComoVendidoNoSistema.this, "teste valor digitado " + valoQueFoiVendidoOProdutoRecuperado, Toast.LENGTH_SHORT).show();
                textoValorQueVaiSerVendidoMesmo = findViewById(R.id.textoValorQueVaiSerVendidoMesmoId);
                textoValorQueVaiSerVendidoMesmo.setText("Valor que será vendido é R$: " + valoQueFoiVendidoOProdutoRecuperado);
            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                oValorFoiEditado = true;

            }
        });

        alertaEditarValorDoProdutoNoProcessoDeVenda.create();
        alertaEditarValorDoProdutoNoProcessoDeVenda.show();
    }

    private void carregarCargaDeDesaparecerConteudoDaTela() {

        imagemDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        nomeRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        textoNomeDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        textoValorQueVaiSerVendidoMesmo.setVisibility(View.GONE);
        textoValorVendaLojaDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        textoMetodoDePagamento.setVisibility(View.GONE);
        valorVendaLojaRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        textoValorVendaIfoodDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        valorVendaIfoodRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.GONE);
        radioButtonSelecionarPrecoDeVendaNaLoja.setVisibility(View.GONE);
        radioButtonSelecionarPrecoDeVendaNoIfood.setVisibility(View.GONE);
        radioButtonPagouNoDebito.setVisibility(View.GONE);
        radioButtonPagouNoCredito.setVisibility(View.GONE);
        radioButtonPagouNoDinheiroOuPix.setVisibility(View.GONE);
        botaoConfirmar.setVisibility(View.GONE);
        botaoCancelar.setVisibility(View.GONE);

    }

    private void processaVenda(String valor) {

        if (radioButtonSelecionarPrecoDeVendaNoIfood.isChecked()) {
            PLATAFORMA_VENDIDA = "IFOOD";
            valoQueFoiVendidoOProdutoRecuperado = valorDoIfoodDoProdutoRecuperado;
        } else if (radioButtonSelecionarPrecoDeVendaNaLoja.isChecked()) {
            valoQueFoiVendidoOProdutoRecuperado = valorNaLojaDoProdutoRecuperado;
            PLATAFORMA_VENDIDA = "LOJA";
        }
        if (radioButtonPagouNoCredito.isChecked()) {
            METODO_DE_PAGAMENTO = "CREDITO";
        } else if (radioButtonPagouNoDinheiroOuPix.isChecked()) {
            METODO_DE_PAGAMENTO = "DINHEIROOUPIX";
        } else if (radioButtonPagouNoDebito.isChecked()) {
            METODO_DE_PAGAMENTO = "DEBITO";
        }


        if (METODO_DE_PAGAMENTO.equals("CREDITO") && !PLATAFORMA_VENDIDA.equals("IFOOD")) {

            FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
            CollectionReference referenceTaxMaquininha = firebaseFirestore.collection("MAQUININHA_CARTAO");


            referenceTaxMaquininha.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            double taxaMaquininha = document.getDouble("porcentagemDeDescontoCredito");
                            double valorVendaConvertida = Double.parseDouble(valor);
                            double descontoTaxa = (valorVendaConvertida * taxaMaquininha) / 100;
                            double valorFinal = valorVendaConvertida - descontoTaxa;
                            String taxaMaquininhaConvertida = String.valueOf(taxaMaquininha);
                            String valorFinalDeVendaProduto = String.valueOf(valorFinal);
                            carregaAlertaPorcentagemDaMaquininha(taxaMaquininhaConvertida, valorFinalDeVendaProduto, valor);

                            // ...
                        }
                    } else {
                        carregaAlertaPorcentagemDaMaquininha(null, null, null);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdicionarProdutoComoVendidoNoSistema.this, "Algo deu errado " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            dataRegistroDoProcessoDeVenda = formatoDataDiaMesAno.format(dataAtual);
            String custoPreper = custoDoProdutoRecuperadoExpostoVitrine.replace(",", ".");
            double custoConvertidoDoProdutoVendido = Double.parseDouble(custoPreper);
            produtoVendidoProcessado.setIdDoProdutoVendido(itemKey);
            produtoVendidoProcessado.setIdDoProdutoCadastrado(itemKeyIdProdutoCadastrado);
            produtoVendidoProcessado.setPlataformaVendida(PLATAFORMA_VENDIDA);
            produtoVendidoProcessado.setMetodoDePagamento(METODO_DE_PAGAMENTO);
            produtoVendidoProcessado.setNomeDoProdutoVendido(nomeRecuperadoExpostoVitrine);
            produtoVendidoProcessado.setValorQueOBoloFoiVendido(valoQueFoiVendidoOProdutoRecuperado);
            produtoVendidoProcessado.setRegistroDaVenda(dataRegistroDoProcessoDeVenda);
            produtoVendidoProcessado.setCustoProduto(custoConvertidoDoProdutoVendido);
            ModeloProcessaVendaFeitaDAO modeloProcessaVendaFeitaDAO = new ModeloProcessaVendaFeitaDAO(AdicionarProdutoComoVendidoNoSistema.this);
            modeloProcessaVendaFeitaDAO.processaNoMontanteMensalVendaFeita(produtoVendidoProcessado, itemKeyMontanteMensal, itemKeyMontanteDiario);

        }


    }

    private void carregaAlertaPorcentagemDaMaquininha(String porcentagem, String valorVenda, String valorVendaOriginal) {

        if (porcentagem != null) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);
            alerta.setTitle("DESCONTO TAXA DA MAQUININHA");
            alerta.setMessage("Devido a taxa na máquininha de " + porcentagem + "% o valor do produto ao ser vendido passará de R$ " + valorVendaOriginal + " para R$ " + valorVenda + " clique em confirmar para vender com o desconto da taxa, caso a venda foi feita no valor original clique em valor original");

            alerta.setCancelable(false);
            alerta.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    produtoVendidoProcessado = new ModeloProdutoVendido();
                    dataRegistroDoProcessoDeVenda = formatoDataDiaMesAno.format(dataAtual);
                    String custoPreper = custoDoProdutoRecuperadoExpostoVitrine.replace(",", ".");
                    double custoConvertidoDoProdutoVendido = Double.parseDouble(custoPreper);
                    produtoVendidoProcessado.setIdDoProdutoVendido(itemKey);
                    produtoVendidoProcessado.setIdDoProdutoCadastrado(itemKeyIdProdutoCadastrado);
                    produtoVendidoProcessado.setPlataformaVendida(PLATAFORMA_VENDIDA);
                    produtoVendidoProcessado.setMetodoDePagamento(METODO_DE_PAGAMENTO);
                    produtoVendidoProcessado.setNomeDoProdutoVendido(nomeRecuperadoExpostoVitrine);
                    produtoVendidoProcessado.setValorQueOBoloFoiVendido(valorVenda);
                    produtoVendidoProcessado.setRegistroDaVenda(dataRegistroDoProcessoDeVenda);
                    produtoVendidoProcessado.setCustoProduto(custoConvertidoDoProdutoVendido);
                    ModeloProcessaVendaFeitaDAO modeloProcessaVendaFeitaDAO = new ModeloProcessaVendaFeitaDAO(AdicionarProdutoComoVendidoNoSistema.this);
                    modeloProcessaVendaFeitaDAO.processaNoMontanteMensalVendaFeita(produtoVendidoProcessado, itemKeyMontanteMensal, itemKeyMontanteDiario);

                }
            }).setNeutralButton("VALOR ORIGINAL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    produtoVendidoProcessado = new ModeloProdutoVendido();
                    dataRegistroDoProcessoDeVenda = formatoDataDiaMesAno.format(dataAtual);
                    String custoPreper = custoDoProdutoRecuperadoExpostoVitrine.replace(",", ".");
                    double custoConvertidoDoProdutoVendido = Double.parseDouble(custoPreper);
                    produtoVendidoProcessado.setIdDoProdutoVendido(itemKey);
                    produtoVendidoProcessado.setIdDoProdutoCadastrado(itemKeyIdProdutoCadastrado);
                    produtoVendidoProcessado.setPlataformaVendida(PLATAFORMA_VENDIDA);
                    produtoVendidoProcessado.setMetodoDePagamento(METODO_DE_PAGAMENTO);
                    produtoVendidoProcessado.setNomeDoProdutoVendido(nomeRecuperadoExpostoVitrine);
                    produtoVendidoProcessado.setValorQueOBoloFoiVendido(valorVendaOriginal);
                    produtoVendidoProcessado.setRegistroDaVenda(dataRegistroDoProcessoDeVenda);
                    produtoVendidoProcessado.setCustoProduto(custoConvertidoDoProdutoVendido);
                    ModeloProcessaVendaFeitaDAO modeloProcessaVendaFeitaDAO = new ModeloProcessaVendaFeitaDAO(AdicionarProdutoComoVendidoNoSistema.this);
                    modeloProcessaVendaFeitaDAO.processaNoMontanteMensalVendaFeita(produtoVendidoProcessado, itemKeyMontanteMensal, itemKeyMontanteDiario);
                }
            }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            alerta.show();
            alerta.create();

        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(AdicionarProdutoComoVendidoNoSistema.this);
            alerta.setTitle("PORCENTAGEM NÃO CADASTRADA");
            alerta.setMessage("Atenção, foi identificado que não há porcentagem cadastrada nas vendas feitas no crédito, se não houver porcentagem de desconto clique em continuar ou então cancele e cadastre uma porcentagem de máquininha na aba FINANCEIRO");

            alerta.setCancelable(false);
            alerta.setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dataRegistroDoProcessoDeVenda = formatoDataDiaMesAno.format(dataAtual);
                    String custoPreper = custoDoProdutoRecuperadoExpostoVitrine.replace(",", ".");
                    double custoConvertidoDoProdutoVendido = Double.parseDouble(custoPreper);
                    produtoVendidoProcessado.setIdDoProdutoVendido(itemKey);
                    produtoVendidoProcessado.setIdDoProdutoCadastrado(itemKeyIdProdutoCadastrado);
                    produtoVendidoProcessado.setPlataformaVendida(PLATAFORMA_VENDIDA);
                    produtoVendidoProcessado.setMetodoDePagamento(METODO_DE_PAGAMENTO);
                    produtoVendidoProcessado.setNomeDoProdutoVendido(nomeRecuperadoExpostoVitrine);
                    produtoVendidoProcessado.setValorQueOBoloFoiVendido(valorVenda);
                    produtoVendidoProcessado.setRegistroDaVenda(dataRegistroDoProcessoDeVenda);
                    produtoVendidoProcessado.setCustoProduto(custoConvertidoDoProdutoVendido);
                    ModeloProcessaVendaFeitaDAO modeloProcessaVendaFeitaDAO = new ModeloProcessaVendaFeitaDAO(AdicionarProdutoComoVendidoNoSistema.this);
                    modeloProcessaVendaFeitaDAO.processaNoMontanteMensalVendaFeita(produtoVendidoProcessado, itemKeyMontanteMensal, itemKeyMontanteDiario);

                }
            }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alerta.show();
            alerta.create();
        }

    }

    private void carregarCargaDeAparecimentoDoConteudoDaTela() {
        imagemDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        nomeRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        textoNomeDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        textoValorQueVaiSerVendidoMesmo.setVisibility(View.VISIBLE);
        textoMetodoDePagamento.setVisibility(View.VISIBLE);
        textoValorVendaLojaDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        valorVendaLojaRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        textoValorVendaIfoodDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        valorVendaIfoodRecuperadoDoProdutoNoProcessoDeVenda.setVisibility(View.VISIBLE);
        botaoConfirmar.setVisibility(View.VISIBLE);
        botaoCancelar.setVisibility(View.VISIBLE);
        radioGroupEscolherValoresCadastrados.setVisibility(View.VISIBLE);
        radioButtonSelecionarPrecoDeVendaNaLoja.setVisibility(View.VISIBLE);
        radioButtonSelecionarPrecoDeVendaNoIfood.setVisibility(View.VISIBLE);
        radioButtonPagouNoDebito.setVisibility(View.VISIBLE);
        radioButtonPagouNoCredito.setVisibility(View.VISIBLE);
        radioButtonPagouNoDinheiroOuPix.setVisibility(View.VISIBLE);
        radioButtonPagouNoDinheiroOuPix.setChecked(true);
        textoMetodoDePagamento.setText("Pagou no: DINHEIRO OU PIX");
    }

}