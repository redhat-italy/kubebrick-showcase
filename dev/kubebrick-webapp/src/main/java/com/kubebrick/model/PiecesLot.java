package com.kubebrick.model;


public class PiecesLot {

    private int id;
    private String color;
    private String creationtimestamp;
    private int pieces;

    public PiecesLot() {
    }

    public PiecesLot(int id, String color, String creationtimestamp, int pieces) {
        this.id = id;
        this.color = color;
        this.creationtimestamp = creationtimestamp;
        this.pieces = pieces;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getCreationtimestamp() {
        return creationtimestamp;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setCreationTimestamp(String creationtimestamp) {
        this.creationtimestamp = creationtimestamp;
    }

}
