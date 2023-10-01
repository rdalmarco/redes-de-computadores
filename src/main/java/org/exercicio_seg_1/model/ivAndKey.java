package org.exercicio_seg_1.model;

public class ivAndKey {
    private byte[] iv;
    private byte[] chave;

    public ivAndKey(byte[] iv, byte[] chave) {
        this.iv = iv;
        this.chave = chave;
    }

    public byte[] getIV() {
        return iv;
    }

    public byte[] getChave() {
        return chave;
    }

}

