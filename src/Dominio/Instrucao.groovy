package Dominio

import Dominio.Exceptions.EntradaInvalidaException
import groovy.json.JsonOutput
import groovy.transform.CompileStatic

@CompileStatic
class Instrucao implements Jsonable {

    String texto

    Instrucao(String texto) {
        if(texto) {
            this.texto = texto

        } else {
            throw new EntradaInvalidaException("Informações incompletas para instrução!")
        }
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"texto\": \"${texto}\"")
        json.append('}')

        return JsonOutput.prettyPrint(json.toString())
    }

    @Override
    String montaNomeArquivo() {
        String nomeArquivo = texto.replaceAll(/[.,-]/, '')
        if (nomeArquivo.size() > 40) {
            nomeArquivo = nomeArquivo.substring(0, 40)
        }
        return nomeArquivo + '.json'
    }
}
