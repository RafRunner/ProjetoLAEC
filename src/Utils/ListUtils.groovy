package Utils

import groovy.transform.CompileStatic

@CompileStatic
class ListUtils {

    static void embaralhaMudandoPosicao(List lista) {
        int tamanho = lista.size()

        if (tamanho <= 1) {
            return
        }

        List<String> listaPosicoes = [0..tamanho - 1].flatten().collect { it.toString() }
        Map<Integer, Integer> mapaTrocas = [:]

        for (Integer i = 0; i < tamanho; i ++) {
            if (!(i.toString() in listaPosicoes)) {
                continue
            }

            if (listaPosicoes.size() == 1) {
                realizaTroca(lista, mapaTrocas)
                int posicaoQualquer = (int) (Math.random() * (tamanho - 2))
                Collections.swap(lista, i, posicaoQualquer)
                return
            }

            Integer novaPosicao = sorteiaNumeroDiferente(i, i, tamanho - 1)

            while (!(novaPosicao.toString() in listaPosicoes)) {
                novaPosicao = sorteiaNumeroDiferente(i, i, tamanho - 1)
            }

            listaPosicoes.remove(i.toString())
            listaPosicoes.remove(novaPosicao.toString())
            mapaTrocas.put(i, novaPosicao)
        }
        realizaTroca(lista, mapaTrocas)
    }

    private static void realizaTroca(List lista, Map<Integer, Integer> mapaTrocas) {
        for (Map.Entry<Integer, Integer> par : mapaTrocas.entrySet()) {
            int posicao1 = par.key
            int posicao2 = par.value

            Collections.swap(lista, posicao1, posicao2)
        }
    }

    static int sorteiaNumeroDiferente(int numero, int menorValor, int maiorValor) {
        if (menorValor == maiorValor) {
            return menorValor
        }

        int intervalo = maiorValor - menorValor
        int numeroSorteado = (int) (Math.random() * intervalo) + menorValor

        while (numeroSorteado == numero) {
            numeroSorteado = (int) (Math.random() * intervalo) + menorValor
        }
        return numeroSorteado
    }
}
