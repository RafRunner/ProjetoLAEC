package Dominio.Fases

import Dominio.Classe
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Treino implements Jsonable {

    Integer pontos
    Integer pontuacaoInicial
    Integer pontosPorAcerto
    Integer pontosPorErro
    List<Classe> classes

    Treino(List<Classe> classes, Integer pontuacaoInicial, Integer pontosPorAcerto, Integer pontosPorErro) {
        if (!classes || !pontuacaoInicial || !pontosPorAcerto || !pontosPorErro) {
            throw new EntradaInvalidaException('Treino não posso ser criado sem todos os parâmetros não nulos!')
        }
        
        this.classes = classes
        this.pontos = pontuacaoInicial
        this.pontuacaoInicial = pontuacaoInicial
        this.pontosPorAcerto = pontosPorAcerto
        this.pontosPorErro = pontosPorErro
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"pontuacaoInicial\": \"${pontuacaoInicial}\",")
        json.append("\"pontosPorAcerto\": \"${pontosPorAcerto}\",")
        json.append("\"pontosPorErro\": \"${pontosPorErro}\"")
        json.append('}')

        return json.toString()
    }

    @Override
    String montaNomeArquivo() {
        return null
    }
}
