package br.com.enigma.main;
public class DecodificadorCesar implements Decodificador {
    private int chave;
    public DecodificadorCesar(int chave) {
        this.chave = chave;
    }
    @Override
    public String decodificar(String texto) {
        StringBuilder sb = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int offset = (c - base - chave) % 26;
                if (offset < 0) offset += 26;
                sb.append((char) (base + offset));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
