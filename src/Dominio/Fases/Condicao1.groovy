package Dominio.Fases

import Dominio.Classe
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Condicao1 implements Jsonable {

    List<Instrucao> instrucoes
    List<Classe> classes
    int numeroRepeticoes

    private static String regexLetras = '@'
    private static String regexNumeros = '#'

    Condicao1(List<Instrucao> instrucoes, List<Classe> classes, int numeroRepeticoes) {
        if (instrucoes && classes && numeroRepeticoes > 0) {
            this.instrucoes = instrucoes
            this.classes = classes
            this.numeroRepeticoes = numeroRepeticoes

        } else {
            throw new EntradaInvalidaException("Informações incompletas ou inválidas para Condicao Um!")
        }
    }

    Map<Classe, List<Instrucao>> getInstrucoesParaClasses() {
        Map<Classe, List<Instrucao>> instrucoesParaClasses = [:]

        classes.eachWithIndex{ Classe entry, int i ->
            instrucoes.each { Instrucao instrucao ->
                instrucao.texto = instrucao.texto.replaceAll(regexNumeros, (i + 1).toString()).replaceAll(regexLetras, ((i + 65) as Character).toString())
            }
            instrucoesParaClasses.put(entry, instrucoes)
        }
        return instrucoesParaClasses
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"instrucoes\": [")
        for (int i = 0; i < instrucoes.size(); i++) {
            Instrucao instrucao = instrucoes.get(i)
            json.append(instrucao.toJson())
            i != instrucoes.size() - 1 ? json.append(',') : json.append('],')
        }

        json.append("\"numeroRepeticoes\": \"${numeroRepeticoes}\"")
        json.append('}')

        return json.toString()
    }

    @Override
    String montaNomeArquivo() {
        return null
    }
}
