package Factories

import Dominio.Classe
import groovy.transform.CompileStatic

@CompileStatic
class ClasseFactory {

    static Classe fromStringMap(Map<String,String> map) {
        String palavraComSentido = map.palavraComSentido
        String palavraSemSentido = map.palavraSemSentido
        String cor = map.cor
        String imagem = map.imagem

        return new Classe(palavraComSentido, palavraSemSentido, cor, imagem)
    }
}
