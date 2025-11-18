package com.maxfit.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoUsuario {
    PERSONAL,
    ALUNO;

    @JsonCreator
    public static TipoUsuario from(String value) {
        if (value == null) return null;
        return TipoUsuario.valueOf(value.trim().toUpperCase());
    }
}
