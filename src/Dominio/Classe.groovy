package Dominio

import Dominio.Enums.CoresDisponiveis
import Dominio.Exceptions.EntradaInvalidaException
import groovy.json.JsonOutput
import groovy.transform.CompileStatic

@CompileStatic
class Classe implements Jsonable {

    String palavraComSentido
    String palavraSemSentido
    CoresDisponiveis cor
    MyImage imagem

    Classe(String palavraComSentido, String palavraSemSentido, String cor, String imagem) throws EntradaInvalidaException {
        if (palavraComSentido && palavraSemSentido && cor && imagem) {

            this.palavraComSentido = palavraComSentido.trim()
            this.palavraSemSentido = palavraSemSentido.trim()
            setImagem(imagem.trim())

            this.cor = CoresDisponiveis.values().find { CoresDisponiveis corDisponiveis -> corDisponiveis.nomeCor == cor.trim() }

            if (!this.cor) {
                throw new EntradaInvalidaException("Cor não disponível!")
            }

        } else {
            throw new EntradaInvalidaException("A Classe criada deve ter todos os parâmetros!")
        }
    }

    void setImagem(String caminho) {
        imagem = new MyImage(caminho, palavraComSentido)
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"palavraComSentido\": \"${palavraComSentido}\",")
        json.append("\"palavraSemSentido\": \"${palavraSemSentido}\",")
        json.append("\"cor\": \"${cor.nomeCor}\",")
        json.append("\"imagem\": \"${imagem.titulo}\"")
        json.append('}')

        return JsonOutput.prettyPrint(json.toString())
    }
}
