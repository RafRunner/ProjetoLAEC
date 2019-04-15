package Dominio.Fases

import Dominio.Classe
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Dominio.Jsonable
import Utils.TextUtils
import groovy.transform.CompileStatic

@CompileStatic
class LinhaDeBase implements Jsonable {

    Instrucao instrucaoInicial
    List<Instrucao> instrucoes
    List<Classe> classes
    int tempoLimite
    int numeroRepeticoes

    private static String regexLetras = '@'
    private static String regexNumeros = '#'

    LinhaDeBase(List<Classe> classes, Instrucao instrucaoInicial, List<Instrucao> instrucoes, int repeticoes, int tempoLimite) {
        if (!classes || repeticoes <= 0 || !instrucoes || !instrucaoInicial || tempoLimite <= 0) {
            throw new EntradaInvalidaException("Informações incompletas ou inválidas para Linha de Base!")
        }

        this.classes = classes
        this.instrucoes = instrucoes
        this.instrucaoInicial = instrucaoInicial
        this.numeroRepeticoes = repeticoes
        this.tempoLimite = tempoLimite
    }

    Map<Classe, List<Instrucao>> getInstrucoesParaClasses() {
        Map<Classe, List<Instrucao>> instrucoesParaClasses = [:]

        classes.eachWithIndex{ Classe entry, int i ->
            List<Instrucao> instrucoesTextoAtualizado = instrucoes.collect { Instrucao instrucao ->
                String textoAtualizado = instrucao.texto.replaceAll(regexNumeros, (i + 1).toString()).replaceAll(regexLetras, ((i + 65) as Character).toString())
                return new Instrucao(textoAtualizado)
            }
            instrucoesParaClasses.put(entry, instrucoesTextoAtualizado)
        }
        return instrucoesParaClasses
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"instrucoes\": ${TextUtils.listToJsonString(instrucoes.collect { it?.toJson() })}, ")
        json.append("\"instrucaoInicial\": ${instrucaoInicial.toJson()},")
        json.append("\"numeroRepeticoes\": \"${numeroRepeticoes}\",")
        json.append("\"tempoLimite\": \"${tempoLimite}\"")
        json.append('}')

        return json.toString()
    }

    @Override
    String montaNomeArquivo() {
        return null
    }
}
