package Utils

import groovy.transform.CompileStatic

@CompileStatic
class TextUtils {

    static String listToString(List<String> lista) {
        StringBuilder listaString = new StringBuilder('[')

        for (int i = 0; i < lista.size(); i++) {
            listaString.append(lista.get(i))
            i == lista.size() - 1 ? listaString.append(']') : listaString.append(', ')
        }
        return listaString.toString()
    }

    static String mapToString(Map<String, String> mapa) {
        StringBuilder mapaString = new StringBuilder()

        mapa.keySet().eachWithIndex { String key, int i ->
            String value = mapa.get(key)

            mapaString.append(key).append(': ').append(value)
            if (i != mapa.size() - 1) {
                mapaString.append('\n')
            }
        }
        return mapaString.toString()
    }

    static String mapToJsonString(Map<String, String> mapa) {
        StringBuilder mapaString = new StringBuilder()

        mapa.keySet().eachWithIndex { String key, int i ->
            String value = mapa.get(key)

            mapaString.append("$key").append(': ').append("$value")
            if (i != mapa.size() - 1) {
                mapaString.append(',\n')
            }
        }
        return mapaString.toString()
    }
}
