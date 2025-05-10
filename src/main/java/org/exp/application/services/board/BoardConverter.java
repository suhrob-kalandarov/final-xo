package org.exp.application.services.board;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BoardConverter implements AttributeConverter<int[][], String> {

    @Override
    public String convertToDatabaseColumn(int[][] board) {
        if (board == null) return null;
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                sb.append(cell).append(",");
            }
        }
        return sb.toString();
    }

    @Override
    public int[][] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return new int[3][3];
        String[] values = dbData.split(",");
        int[][] board = new int[3][3];
        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = Integer.parseInt(values[i]);
        }
        return board;
    }
}