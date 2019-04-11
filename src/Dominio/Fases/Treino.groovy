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
    Integer condicaoParadaAcerto
    Integer condicaoParadaErro
    List<Classe> classes

    Treino(List<Classe> classes, Integer pontuacaoInicial, Integer pontosPorAcerto, Integer pontosPorErro, Integer condicaoParadaAcerto, Integer condicaoParadaErro) {
        if (!classes || !pontuacaoInicial || !pontosPorAcerto || !pontosPorErro || condicaoParadaAcerto  <= 0 || condicaoParadaErro <= 0) {
            throw new EntradaInvalidaException('Treino não posso ser criado sem todos os parâmetros não nulos!')
        }
        
        this.classes = classes
        this.pontos = pontuacaoInicial
        this.pontuacaoInicial = pontuacaoInicial
        this.pontosPorAcerto = pontosPorAcerto
        this.pontosPorErro = pontosPorErro
        this.condicaoParadaAcerto = condicaoParadaAcerto
        this.condicaoParadaErro = condicaoParadaErro
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"pontuacaoInicial\": \"${pontuacaoInicial}\",")
        json.append("\"pontosPorAcerto\": \"${pontosPorAcerto}\",")
        json.append("\"pontosPorErro\": \"${pontosPorErro}\",")
        json.append("\"condicaoParadaAcerto\": \"${condicaoParadaAcerto}\",")
        json.append("\"condicaoParadaErro\": \"${condicaoParadaErro}\"")
        json.append('}')

        return json.toString()
    }

    @Override
    String montaNomeArquivo() {
        return null
    }
}
