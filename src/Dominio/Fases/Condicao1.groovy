package Dominio.Fases

import Dominio.Classe
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Dominio.Jsonable
import Utils.TextUtils
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
            List<Instrucao> instrucoesTextoAtualizado = instrucoes.collect { Instrucao instrucao ->
                String textoAtualizado = instrucao.texto.replaceAll(regexNumeros, (i + 1).toString()).replaceAll(regexLetras, ((i + 65) as Character).toString())
                return new Instrucao(textoAtualizado, instrucao.tempo, instrucao.podeSerPulada)
            }
            instrucoesParaClasses.put(entry, instrucoesTextoAtualizado)
        }
        return instrucoesParaClasses
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"instrucoes\": ${TextUtils.listToJsonString(instrucoes.collect { it.toJson() })}, ")
        json.append("\"numeroRepeticoes\": \"${numeroRepeticoes}\"")
        json.append('}')

        return json.toString()
    }

    @Override
    String montaNomeArquivo() {
        return null
    }
}
