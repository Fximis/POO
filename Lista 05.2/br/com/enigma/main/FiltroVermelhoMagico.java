package br.com.enigma.main;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Color;

public class FiltroVermelhoMagico implements FiltroImagem {
    @Override
    public void aplicarFiltro(String caminhoEntrada, String caminhoSaida) {
        try {
            BufferedImage img = ImageIO.read(new File(caminhoEntrada));
            for (int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {
                    int rgb = img.getRGB(x, y);
                    Color c = new Color(rgb);
                    if (c.getRed() % 2 != 0) { // IMPAR -> PRETO
                        img.setRGB(x, y, Color.BLACK.getRGB());
                    } else { // PAR -> BRANCO
                        img.setRGB(x, y, Color.WHITE.getRGB());
                    }
                }
            }
            ImageIO.write(img, "bmp", new File(caminhoSaida));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
