package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ModeloBoloCadastradoParaVendaDAO;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ModeloReceitaCadastradaDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class EditarBoloCadastradoVendaActivity extends AppCompatActivity {

    // Componentes de tela
    private ImageView imagem_bolo_cadastrado_venda;
    private TextInputEditText edit_nome_bolo_cadastrado_venda;
    private TextInputEditText edit_valor_venda_bolo_cadastrado_venda_boleria;
    private TextInputEditText edit_valor_venda_bolo_cadastrado_venda_ifood;


    private TextView edit_custo_bolo_cadastrado_venda;
    private TextView edit_porcentagem_cadastrada_lucro;
    private TextView edit_porcentagem_cadastrada_do_ifood;
    private TextView edit_valor_sugerido_venda_boleria;
    private TextView edit_valor_sugerido_venda_ifood;


    private Button botao_atualizar_bolo_cadastrado_venda;
    private Button botao_editar_porcentagens;
    private Button botao_excluir_bolo_cadastrado_venda;
    private Button botao_trocar_foto_bolo_cadastrado;
    private Uri imageUri;

    //Banco de dados

    private FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    private final String COLLECTION_PRODUTOS_CADASTRADOS_PARA_ADICIONAR_PARA_VENDA = "BOLOS_CADASTRADOS_PARA_ADICIONAR_PARA_VENDA";
    private final String COLLECTION_RECEITA_CADASTRADOS_PARA_RECUPERAR_QUANTIDADE_DE_RENDIMENTO_POR_FORNADA = "RECEITA_CADASTRADA";
    private CollectionReference referenciaProdutoCadastradoParaVendas = firebaseFirestore.collection(COLLECTION_PRODUTOS_CADASTRADOS_PARA_ADICIONAR_PARA_VENDA);
    private CollectionReference referenciaReceitaCadastradoDoProdutoEditando = firebaseFirestore.collection(COLLECTION_RECEITA_CADASTRADOS_PARA_RECUPERAR_QUANTIDADE_DE_RENDIMENTO_POR_FORNADA);

    //Final

    private static final int REQUEST_CODE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_CAMERA = 101;
    private static final int REQUEST_CODE_GALERIA = 100;
    private static final String CHAVE_SEGURANCA = "DeliciasDaMamae";

    //Variareis

    private String currentPatch = null;
    private String enderecoSalvoFoto;
    private String fileName;
    private String idReferenciaReceitaParaRecuperarQuantidadeDeRendimentoPoReceita;
    private String nomeCadastradoRecuperado;
    private String valorVendaBoleriaCadastradoRecuperado;
    private String custoTotalReceitaCadastradaRecuperado;
    private String valorVendaIfoodCadastradoRecuperado;
    private String porcentagemDeLucroCadastrado;
    private String porcentagemPorContaDoIfood;
    private String porcentagemLucroAlert;
    private String porcentagemIfoodAlert;
    private String valorSugeridoParaVendasNaBoleria;
    private String valorSugeridoParaVendasNoIfoodEditandoAlert;
    private String valorSugeridoParaVendasNoIfood;
    private String valorSugeridoDeVendaNaBoleriaEditandoAlert;
    private String quantRendimentoReceita;
    private String boloId;

    private int verificaGaleriaCamera;
    private int quantidadeRendimentoConvertido;


    private double porcentConvertEditando;
    private double porcentIfoodConvertEditando;
    private double custoConvertEditando;
    private double custoIfoodConvertEditando;
    private double resultadoEditando;
    private double resultadoIfoodEditando;
    private double valorSugeridoDeVendaDoIfoodEditando;

    private double dividindoQuantidades;



    private double porcentConvertLucroEditando;
    private double porcentIfoodConvertLucroEditando;
    private double custoConvertLucroEditando;
    private double custoIfoodConvertLucroEditando;
    private double resultadoLucroEditando;
    private double resultadoIfoodLucroEditando;
    private double valorSugeridoDeVendaDoIfoodLucroEditando;

    //Classes

    private BolosModel bolosModelJaCadastradoAtualizando;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_editar_bolo_cadastrado_venda);

        boloId = getIntent().getStringExtra("boloKey");


        imagem_bolo_cadastrado_venda = findViewById(R.id.foto_editar_bolo_cadastrado_venda_id);


        edit_nome_bolo_cadastrado_venda = findViewById(R.id.edit_nome_bolo_cadastrado_venda_id);
        edit_valor_venda_bolo_cadastrado_venda_boleria = findViewById(R.id.edit_valor_venda_bolo_cadastrado_venda_id);
        edit_valor_venda_bolo_cadastrado_venda_ifood = findViewById(R.id.edit_valor_venda_cadastrado_para_vendas_no_ifood_editando_id);


        edit_custo_bolo_cadastrado_venda = findViewById(R.id.texto_custo_total_receita_cadastrado_do_produto_editando_id);
        edit_porcentagem_cadastrada_lucro = findViewById(R.id.texto_porcentagem_cadastrada_de_lucro);
        edit_porcentagem_cadastrada_do_ifood = findViewById(R.id.texto_porcentagem_cadastrada_do_ifood);
        edit_valor_sugerido_venda_boleria = findViewById(R.id.texto_valor_sugerido_com_lucro);
        edit_valor_sugerido_venda_ifood = findViewById(R.id.texto_valor_sugerido_com_porcentagem_ifood);


        botao_editar_porcentagens = findViewById(R.id.botao_editar_porcentagens);
        botao_atualizar_bolo_cadastrado_venda = findViewById(R.id.botao_atualizar_bolo_cadastrado_venda_id);
        botao_excluir_bolo_cadastrado_venda = findViewById(R.id.botao_excluir_bolo_cadastrado_venda_id);
        botao_trocar_foto_bolo_cadastrado = findViewById(R.id.botao_trocar_foto_bolo_cadastrado_venda_id);

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Aguarde enquanto carregamos as informações");
        pd.setCancelable(false);
        pd.show();

        referenciaProdutoCadastradoParaVendas.document(boloId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                BolosModel bolosModelRecuperado = documentSnapshot.toObject(BolosModel.class);
                nomeCadastradoRecuperado = bolosModelRecuperado.getNomeBoloCadastrado() ;
                idReferenciaReceitaParaRecuperarQuantidadeDeRendimentoPoReceita = bolosModelRecuperado.getIdReferenciaReceitaCadastrada();
                valorVendaBoleriaCadastradoRecuperado= bolosModelRecuperado.getValorCadastradoParaVendasNaBoleria() ;
                custoTotalReceitaCadastradaRecuperado= bolosModelRecuperado.getCustoTotalDaReceitaDoBolo() ;
                valorVendaIfoodCadastradoRecuperado= bolosModelRecuperado.getValorCadastradoParaVendasNoIfood() ;
                porcentagemDeLucroCadastrado= bolosModelRecuperado.getPorcentagemAdicionadoPorContaDoLucro() ;
                porcentagemPorContaDoIfood= bolosModelRecuperado.getPorcentagemAdicionadoPorContaDoIfood() ;
                valorSugeridoParaVendasNaBoleria= bolosModelRecuperado.getValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro() ;
                valorSugeridoParaVendasNoIfood= bolosModelRecuperado.getValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem() ;
                enderecoSalvoFoto = bolosModelRecuperado.getEnderecoFoto();


                    Glide.with(getApplicationContext()).load(enderecoSalvoFoto).into(imagem_bolo_cadastrado_venda);


                pd.setMessage("Só mais alguns instantes...");


                referenciaReceitaCadastradoDoProdutoEditando.document(idReferenciaReceitaParaRecuperarQuantidadeDeRendimentoPoReceita)
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        pd.setMessage("Prontinho tudo carregado...");
                        ReceitaModel receitaModelRecuperadaParaEditacao = documentSnapshot.toObject(ReceitaModel.class);

                        quantRendimentoReceita = receitaModelRecuperadaParaEditacao.getQuantRendimentoReceita();


                        edit_nome_bolo_cadastrado_venda.setText(nomeCadastradoRecuperado);
                        edit_valor_venda_bolo_cadastrado_venda_boleria.setText(valorVendaBoleriaCadastradoRecuperado);
                        edit_valor_venda_bolo_cadastrado_venda_ifood.setText(valorVendaIfoodCadastradoRecuperado);
                        edit_porcentagem_cadastrada_lucro.setText(porcentagemDeLucroCadastrado + " %");
                        edit_porcentagem_cadastrada_do_ifood.setText(porcentagemPorContaDoIfood  + " %");
                        edit_valor_sugerido_venda_boleria.setText(valorSugeridoParaVendasNaBoleria);
                        edit_valor_sugerido_venda_ifood.setText(valorSugeridoParaVendasNoIfood);
                        edit_custo_bolo_cadastrado_venda.setText(custoTotalReceitaCadastradaRecuperado);


                        System.out.println("RECUPERANDO DENTRO DO RECEITA CADASTRADAS: " + quantRendimentoReceita);
                        pd.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(), "Algo deu errado, verifique a internet e tente novamente.", Toast.LENGTH_SHORT).show();

                    }
                });




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        botao_trocar_foto_bolo_cadastrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(EditarBoloCadastradoVendaActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(EditarBoloCadastradoVendaActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditarBoloCadastradoVendaActivity.this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS);

                } else {
                    carregarAlertFoto();
                }

            }
        });

        botao_atualizar_bolo_cadastrado_venda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                atualizarBolo();
            }
        });

        botao_excluir_bolo_cadastrado_venda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deletarBolo();

            }
        });

        botao_editar_porcentagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                carregarAlertEditarPorcent();

            }
        });

    }

    private void deletarBolo() {

        AlertDialog.Builder alertaDeletar = new AlertDialog.Builder(EditarBoloCadastradoVendaActivity.this);
        alertaDeletar.setTitle("ATENÇÃO");
        alertaDeletar.setMessage("Você está prestes a excluir o, " + nomeCadastradoRecuperado + ", permanentemente. Deseja continuar?");
        alertaDeletar.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                AlertDialog.Builder alertaChaveDelete = new AlertDialog.Builder(EditarBoloCadastradoVendaActivity.this);
                alertaChaveDelete.setTitle("CHAVE DE SEGURANÇA");
                alertaChaveDelete.setMessage("Digite a chave de segurança e selecione CONFIRMAR para finalizar a exclusão");
                alertaChaveDelete.setCancelable(false);
                EditText editChave = new EditText(getApplicationContext());
                alertaChaveDelete.setView(editChave);
                alertaChaveDelete.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String chaveDigitada = editChave.getText().toString();
                        if (!chaveDigitada.isEmpty() && chaveDigitada.contains(CHAVE_SEGURANCA)) {


                            referenciaProdutoCadastradoParaVendas.document(boloId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditarBoloCadastradoVendaActivity.this, "Exclusão feita", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditarBoloCadastradoVendaActivity.this, BolosActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditarBoloCadastradoVendaActivity.this, "Exclusão cancelada", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(EditarBoloCadastradoVendaActivity.this, "Chave de segurança não identificada, tente novamente", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertaChaveDelete.show();
                alertaChaveDelete.create();
            }

        }).setNeutralButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        alertaDeletar.show();
        alertaDeletar.create();
    }

    private void atualizarBolo() {
        String nomeAtualizando = edit_nome_bolo_cadastrado_venda.getText().toString();

        String valorCadastradoParaVendaNaBoleriaAtualizando = edit_valor_venda_bolo_cadastrado_venda_boleria.getText().toString().replace(",", ".");
        String valorCadastradoParaVendaNIfoodAtualizando = edit_valor_venda_bolo_cadastrado_venda_ifood.getText().toString().replace(",", ".");



        bolosModelJaCadastradoAtualizando = new BolosModel();

        ModeloBoloCadastradoParaVendaDAO modeloBoloCadastradoParaVendaDAOEditando = new ModeloBoloCadastradoParaVendaDAO(this);



        if (edit_valor_venda_bolo_cadastrado_venda_boleria.getText().toString().isEmpty() && edit_valor_venda_bolo_cadastrado_venda_ifood.getText().toString().isEmpty()) {

            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("ATENÇÃO - CAMPOS VÁZIOS");
            alerta.setMessage("Foi identificado que um dos campos está vazio, para completar a atualização é necessário que todos os campos estejam preenchidos, favor insira as informações para completar a ação");
            alerta.setCancelable(false);
            alerta.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alerta.create();
            alerta.show();
        } else {


            bolosModelJaCadastradoAtualizando.setIdBoloCadastrado(boloId);
            bolosModelJaCadastradoAtualizando.setNomeBoloCadastrado(nomeAtualizando);
            bolosModelJaCadastradoAtualizando.setIdReferenciaReceitaCadastrada(idReferenciaReceitaParaRecuperarQuantidadeDeRendimentoPoReceita);
            bolosModelJaCadastradoAtualizando.setCustoTotalDaReceitaDoBolo(custoTotalReceitaCadastradaRecuperado);
            bolosModelJaCadastradoAtualizando.setValorCadastradoParaVendasNaBoleria(valorCadastradoParaVendaNaBoleriaAtualizando);
            bolosModelJaCadastradoAtualizando.setValorCadastradoParaVendasNoIfood(valorCadastradoParaVendaNIfoodAtualizando);
            bolosModelJaCadastradoAtualizando.setPorcentagemAdicionadoPorContaDoIfood(porcentagemPorContaDoIfood);
            bolosModelJaCadastradoAtualizando.setPorcentagemAdicionadoPorContaDoLucro(porcentagemDeLucroCadastrado);
            bolosModelJaCadastradoAtualizando.setValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem(valorSugeridoParaVendasNoIfood);
            bolosModelJaCadastradoAtualizando.setValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro(valorSugeridoParaVendasNaBoleria);
            bolosModelJaCadastradoAtualizando.setEnderecoFoto(enderecoSalvoFoto);
            bolosModelJaCadastradoAtualizando.setValorQueOBoloRealmenteFoiVendido("CADASTRO");
            bolosModelJaCadastradoAtualizando.setVerificaCameraGaleria(verificaGaleriaCamera);

            System.out.println("------------- DENTRO DO ATUALIZAR BOLO QUANDO OS VALOS CADASTRADOS NÃO ESTÃO VAZIOS-------------------");
            System.out.println("------------- bolosModelJaCadastradoAtualizando-------------------" + bolosModelJaCadastradoAtualizando.toString());

            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("CONFIRMANDO ATUALIZAÇÕES");
            alerta.setMessage("Para finalizar as alterações, por favor confirme os dados a serem alterados de " + bolosModelJaCadastradoAtualizando.getNomeBoloCadastrado());
            alerta.setCancelable(false);
            alerta.setNeutralButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    modeloBoloCadastradoParaVendaDAOEditando.editarBoloCadastrado(bolosModelJaCadastradoAtualizando);



                }
            }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alerta.create();
            alerta.show();


        }


    }

    private void tirarFoto() {

        Intent inte = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (inte.resolveActivity(EditarBoloCadastradoVendaActivity.this.getPackageManager()) != null) {
            File ImageFile = null;
            try {
                ImageFile = createImageFile();

            } catch (Exception e) {

                Toast.makeText(EditarBoloCadastradoVendaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
            if (ImageFile != null) {

                Uri imageUri = FileProvider.getUriForFile(EditarBoloCadastradoVendaActivity.this, "com.marcel.a.n.roxha.deliciasdamamae.fileprovider", ImageFile);

                inte.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(inte, REQUEST_CODE_CAMERA);
            }
        }

    }

    private File createImageFile() {

        fileName = "IMAGE_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());


        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(fileName, ".jpg", directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPatch = imageFile.getAbsolutePath();
        return imageFile;
    }

    private void enviarFoto() {

        ProgressDialog pd = new ProgressDialog(EditarBoloCadastradoVendaActivity.this);
        pd.setMessage("Aguarde enquanto a foto é carregada");
        pd.setCancelable(false);
        pd.show();
        Bitmap bitmap = ((BitmapDrawable) imagem_bolo_cadastrado_venda.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        Date dateId = new Date();
        long dataIdlong = dateId.getTime();
        String convertdataId = Long.toString(dataIdlong);


        byte[] image = byteArrayOutputStream.toByteArray();

        if (verificaGaleriaCamera == 1) {

            StorageReference reference = FirebaseStorage.getInstance().getReference().child("fotosSalvas").child(fileName + "_" + nomeCadastradoRecuperado + "_"+ convertdataId+ ".jpeg");
            UploadTask uploadTask = reference.putBytes(image);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                            imagem_bolo_cadastrado_venda.setRotation(90f);
                            imagem_bolo_cadastrado_venda.setVisibility(View.VISIBLE);


                            imagem_bolo_cadastrado_venda.setImageBitmap(BitmapFactory.decodeFile(currentPatch));


                            Toast.makeText(EditarBoloCadastradoVendaActivity.this, "Sucesso ao salvar imagem da camera", Toast.LENGTH_SHORT).show();
                            enderecoSalvoFoto = uri.toString();

                            pd.dismiss();

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditarBoloCadastradoVendaActivity.this, "Erro ao salvar imagem da camera", Toast.LENGTH_SHORT).show();
                }
            });


        } else if (verificaGaleriaCamera == 2) {


            if (imageUri != null) {

                StorageReference reference = FirebaseStorage.getInstance().getReference().child("fotosSalvas").child(fileName + "_" + nomeCadastradoRecuperado + ".jpeg");

                reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                enderecoSalvoFoto = uri.toString();
                                pd.dismiss();
                                Toast.makeText(EditarBoloCadastradoVendaActivity.this, "Imagem enviada da galeria com sucesso", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            imagem_bolo_cadastrado_venda.setVisibility(View.GONE);
            imagem_bolo_cadastrado_venda.setImageBitmap(BitmapFactory.decodeFile(currentPatch));

            if (verificaGaleriaCamera == 1) {
                imagem_bolo_cadastrado_venda.setRotation(90f);
                enviarFoto();
            }


        } else if (requestCode == REQUEST_CODE_GALERIA && resultCode == RESULT_OK) {
            imageUri = data.getData();
            imagem_bolo_cadastrado_venda.setImageURI(imageUri);
            enviarFoto();

        }
    }

    private void selecionarFoto() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecionar Foto"), REQUEST_CODE_GALERIA);
    }

    private void carregarAlertFoto() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(EditarBoloCadastradoVendaActivity.this);
        dialog.setTitle("ATUALIZAR FOTO");
        dialog.setMessage("Escolha uma das opções abaixo para atualizar a foto, ou clique fora para cancelar a ação");

        dialog.setPositiveButton("CÂMERA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(EditarBoloCadastradoVendaActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditarBoloCadastradoVendaActivity.this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS);

                } else {
                    verificaGaleriaCamera = 1;
                    tirarFoto();
                }


            }
        }).setNeutralButton("GALERIA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditarBoloCadastradoVendaActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_GALERIA);
                }
                verificaGaleriaCamera = 2;
                selecionarFoto();

            }
        });
        dialog.create();
        dialog.show();
    }

    public void carregarAlertEditarPorcent(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("VOCÊ ESTÁ EDITANDO: " + nomeCadastradoRecuperado);
        View viewLayout = getLayoutInflater().inflate(R.layout.layout_alert_editando_porcentagem_produto_ja_cadastrado, null);
        EditText textoPorcentagemEditandoLucro = viewLayout.findViewById(R.id.alerta_edit_porcentagem_de_lucro_receita_id);
        EditText textoPorcentagemEditandoIfood = viewLayout.findViewById(R.id.alerta_edit_porcentagem_de_acrescimo_do_ifood_id);

        dialog.setView(textoPorcentagemEditandoLucro);
        dialog.setView(textoPorcentagemEditandoIfood);


        dialog.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                porcentagemDeLucroCadastrado = textoPorcentagemEditandoLucro.getText().toString();
                porcentagemPorContaDoIfood	 = textoPorcentagemEditandoIfood.getText().toString();


                if (!porcentagemDeLucroCadastrado.isEmpty() && !porcentagemPorContaDoIfood.isEmpty()) {

                    calcularValorSugeridoEditandoComAsPorcentagensEditadas(custoTotalReceitaCadastradaRecuperado, porcentagemDeLucroCadastrado, porcentagemPorContaDoIfood);



                } else {

                    Toast.makeText(getApplicationContext(), "É necessário informar a % de lucro e de acrescimo do ifoods", Toast.LENGTH_SHORT).show();
                }


            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.setView(viewLayout);
        dialog.create();
        dialog.show();

    }

    private void calcularValorSugeridoIfoodEditando(String custoTotalReceitaCadastradaRecuperado, String porctIfood, String porct) {


        String custoFormatado = custoTotalReceitaCadastradaRecuperado.replace(",", ".");
        String porncetFormatado = porctIfood.replace(",", ".");
        String porncetLucroFormatado = porct.replace(",", ".");
        String quantidadeRendimentoFornada = quantRendimentoReceita;



    }

    private void calcularValorSugeridoEditandoComAsPorcentagensEditadas(String custoTotalReceitaCadastradaRecuperado, String porcentagemPassaLucro, String porcentagemPassadaIfood) {

        String custoFormatado = custoTotalReceitaCadastradaRecuperado.replace(",", ".");
        String porncetFormatadoLucro = porcentagemPassaLucro.replace(",", ".");
        String porncetFormatadoIfood = porcentagemPassadaIfood.replace(",", ".");
        String quantidadeRendimentoFornada = quantRendimentoReceita;


        custoConvertLucroEditando = Double.parseDouble(custoFormatado);
        porcentConvertLucroEditando = Double.parseDouble(porncetFormatadoLucro);
        porcentIfoodConvertEditando = Double.parseDouble(porncetFormatadoIfood);



         quantidadeRendimentoConvertido = Integer.parseInt(quantidadeRendimentoFornada);

         dividindoQuantidades = custoConvertLucroEditando /quantidadeRendimentoConvertido ;

         resultadoLucroEditando = ((dividindoQuantidades * porcentConvertLucroEditando) / 100) + dividindoQuantidades;



        resultadoIfoodEditando = ((resultadoLucroEditando * porcentIfoodConvertEditando) / 100) + resultadoLucroEditando;


        valorSugeridoParaVendasNoIfood = String.format("%.2f", resultadoIfoodEditando);


        valorSugeridoParaVendasNaBoleria = String.format("%.2f", resultadoLucroEditando);


        edit_porcentagem_cadastrada_lucro.setText(porncetFormatadoLucro + " %");
        edit_porcentagem_cadastrada_do_ifood.setText(porncetFormatadoIfood  + " %");
        edit_valor_sugerido_venda_boleria.setText(valorSugeridoParaVendasNaBoleria);
        edit_valor_sugerido_venda_ifood.setText(valorSugeridoParaVendasNoIfood);


    }

}