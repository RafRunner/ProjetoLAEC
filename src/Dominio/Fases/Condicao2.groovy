package Dominio.Fases

import Dominio.Classe
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Condicao2 implements Jsonable {

    int acertos
    int erros
    int tempoLimite
    int condicaoParadaAcerto
    int condicaoParadaErro
    List<Classe> classes

    Condicao2(List<Classe> classes, int condicaoParadaAcerto, int condicaoParadaErro, int tempoLimite) {
        if (!classes || condicaoParadaAcerto  <= 0 || condicaoParadaErro <= 0 || tempoLimite <= 0) {
            throw new EntradaInvalidaException('Condicao2 não posso ser criado sem todos os parâmetros não nulos!')
        }
        
        this.classes = classes
        this.condicaoParadaAcerto = condicaoParadaAcerto
        this.condicaoParadaErro = condicaoParadaErro
        this.tempoLimite = tempoLimite
        this.acertos = 0
        this.erros = 0
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"condicaoParadaAcerto\": \"${condicaoParadaAcerto}\",")
        json.append("\"condicaoParadaErro\": \"${condicaoParadaErro}\",")
        json.append("\"tempoLimite\": \"${tempoLimite}\"")
        json.append('}')

        return json.toString()
    }

    @Override
    String montaNomeArquivo() {
        return null
    }
}
