package Dominio

import Dominio.Enums.CoresDisponiveis
import Dominio.Exceptions.EntradaInvalidaException
import Files.MyImage
import groovy.json.JsonOutput
import groovy.transform.CompileStatic

@CompileStatic
class Classe implements Jsonable {

    String palavraComSentido
    String palavraSemSentido
    CoresDisponiveis cor
    MyImage imagem

    Classe(Map jsonMap) {
        String palavraComSentido = jsonMap.palavraComSentido.toString()
        String palavraSemSentido = jsonMap.palavraSemSentido.toString()
        String cor = jsonMap.cor.toString()
        String imagem = jsonMap.imagem.toString()

        if (palavraComSentido && palavraSemSentido && cor && imagem) {

            this.palavraComSentido = palavraComSentido.trim()
            this.palavraSemSentido = palavraSemSentido.trim()
            this.imagem = new MyImage(imagem)

            this.cor = CoresDisponiveis.values().find { CoresDisponiveis corDisponiveis -> corDisponiveis.nomeCor == cor.trim() }

            if (!this.cor) {
                throw new EntradaInvalidaException("Cor não disponível!")
            }

        } else {
            throw new EntradaInvalidaException("A Classe criada deve ter todos os parâmetros!")
        }
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
