package com.example.ajudateconta.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatTextSaldo implements TextWatcher {

    private EditText editTextRecebido;
    private static final Pattern DIGITS_PATTERN = Pattern.compile("\\d+$");

    public FormatTextSaldo(EditText editText) {
        this.editTextRecebido = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        String input = editable.toString();
        System.out.println(input);
        if (input.isEmpty()) {
            return;
        }

        editTextRecebido.removeTextChangedListener(this);

//
        // Extrai os últimos dígitos usando a expressão regular
        Matcher matcher = DIGITS_PATTERN.matcher(input);
        if (matcher.find()) {
            String digits = matcher.group(); // Obtém os últimos dígitos

            // Formata os dígitos como casas decimais
            try {

                BigDecimal parsed = parseToBigDecimal(editable.toString());
                String formatted = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(parsed);
                //Remove o símbolo da moeda e espaçamento pra evitar bug
                String replaceable = String.format("[%s\\s]", getCurrencySymbol());

                String cleanString = formatted.replaceAll(replaceable, "");
                System.out.println("exibe================== " + cleanString);
                editTextRecebido.setText(cleanString);
                editTextRecebido.setSelection(cleanString.length());
                editTextRecebido.addTextChangedListener(this);
            } catch (NumberFormatException e) {
                // Erro na conversão para inteiro
            }
        }

    }

    private BigDecimal parseToBigDecimal(String value) {
        String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());

        String cleanString = value.replaceAll(replaceable, "");

        try {
            return new BigDecimal(cleanString).setScale(
                    2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
        } catch (NumberFormatException e) {
            //ao apagar todos valores de uma só vez dava erro
            //Com a exception o valor retornado é 0.00
            return new BigDecimal(0);

        }
    }

    public String formatPrice(String price) {
        //Ex - price = 2222
        //retorno = 2222.00
        DecimalFormat df = new DecimalFormat("0.00");
        return String.valueOf(df.format(Double.valueOf(price)));

    }

    public String formatTextPrice(String price) {
        //Ex - price = 3333.30
        //retorna formato monetário em Br = 3.333,30
        //retorna formato monetário EUA: 3,333.30
        //retornar formato monetário de alguns países europeu: 3 333,30
        BigDecimal bD = new BigDecimal(formatPriceSave(formatPrice(price)));
        String newFormat = String.valueOf(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(bD));
        String replaceable = String.format("[%s]", getCurrencySymbol());
        return newFormat.replaceAll(replaceable, "");

    }

    public String formatPriceSave(String price) {
        //Ex - price = $ 5555555
        //return = 55555.55 para salvar no banco de dados
        String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());
        String cleanString = price.replaceAll(replaceable, "");
        StringBuilder stringBuilder = new StringBuilder(cleanString.replaceAll(" ", ""));

        return String.valueOf(stringBuilder.insert(cleanString.length() - 2, '.'));

    }

    public String getCurrencySymbol() {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

    }

}