package com.example.boaviagem;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class Mask {
    public static TextWatcher apply(final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString().replaceAll("[^\\d]", "");
                String mascara = "";
                if (str.length() > 10) {
                    mascara = "(##) #####-####";
                } else {
                    mascara = "(##) ####-####";
                }

                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }

                int i = 0;
                StringBuilder formatado = new StringBuilder();
                for (char m : mascara.toCharArray()) {
                    if (m != '#') {
                        formatado.append(m);
                        continue;
                    }
                    try {
                        formatado.append(str.charAt(i));
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }

                isUpdating = true;
                editText.setText(formatado.toString());
                editText.setSelection(formatado.length());
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        };
    }
}

