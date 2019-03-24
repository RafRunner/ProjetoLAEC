package Dominio

import Dominio.Exceptions.EntradaInvalidaException
import groovy.transform.CompileStatic

import javax.imageio.ImageIO
import java.awt.Graphics2D
import java.awt.image.BufferedImage

@CompileStatic
class MyImage {

    String titulo
    BufferedImage bufferedImage

    MyImage(String caminho, String titulo) {
        try {
            File arquivo = new File(caminho)
            bufferedImage = ImageIO.read(arquivo)

        } catch (Exception ignored) {
            throw new EntradaInvalidaException('Arquivo de imagem não existe, não pôde ser lido ou não é uma imagem! :' + caminho)
        }

        this.titulo = titulo
    }

    void resize(int scaledWidth, int scaledHeight) throws IOException {
        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, bufferedImage.getType())

        Graphics2D g2d = outputImage.createGraphics()
        g2d.drawImage(bufferedImage, 0, 0, scaledWidth, scaledHeight, null)
        g2d.dispose()

        bufferedImage = outputImage
    }
}
