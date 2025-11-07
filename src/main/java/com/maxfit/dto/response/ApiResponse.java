package com.maxfit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean sucesso;
    private String mensagem;
    private T data;

    public static <T> ApiResponse<T> success(String mensagem, T data) {
        return ApiResponse.<T>builder()
                .sucesso(true)
                .mensagem(mensagem)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String mensagem) {
        return ApiResponse.<T>builder()
                .sucesso(true)
                .mensagem(mensagem)
                .build();
    }

    public static <T> ApiResponse<T> error(String mensagem) {
        return ApiResponse.<T>builder()
                .sucesso(false)
                .mensagem(mensagem)
                .build();
    }
}