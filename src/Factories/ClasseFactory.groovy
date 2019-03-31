package Factories

import Dominio.Classe
import groovy.transform.CompileStatic

@CompileStatic
class ClasseFactory {

    static Classe fromJsonMap(Map<String,String> jsonMap) {
        String palavraComSentido = jsonMap.palavraComSentido
        String palavraSemSentido = jsonMap.palavraSemSentido
        String cor = jsonMap.cor
        String imagem = jsonMap.imagem

        return new Classe(palavraComSentido, palavraSemSentido, cor, imagem)
    }
}
