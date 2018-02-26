package com.example.bookpub.editors;

import org.springframework.util.StringUtils;
import com.example.bookpub.model.Isbn;

import java.beans.PropertyEditorSupport;

public class IsbnEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) {
        if (text == null) {
            setValue(null);
        }
        else {
            String value = text.trim();
            if (!StringUtils.isEmpty(value)) {
                setValue(Isbn.parseFrom(value));
            } else {
                setValue(null);
            }
        }
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return (value != null ? value.toString() : "");
    }
}
