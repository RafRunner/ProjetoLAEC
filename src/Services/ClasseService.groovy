package Services

import Dominio.Classe
import Files.Ambiente
import groovy.transform.CompileStatic

@CompileStatic
class ClasseService {

    private static String pastaClasses = 'Classes'

    private Ambiente ambiente = Ambiente.instancia

    static ClasseService instancia = new ClasseService()
    private ClasseService() {}

    void salvarClasses(List<Classe> classes) {
        List<File> classesExistentes = ambiente.getFiles(pastaClasses)

        for (Classe classe : classes) {
            String nomeArquivo = classe.montaNomeArquivo()

            if (!(nomeArquivo in classesExistentes.name)) {
                String caminhoArquivo = ambiente.getFullPath(pastaClasses, nomeArquivo)
                File arquivo = new File(caminhoArquivo)
                arquivo.write(classe.toJson())
            }
        }
    }
}
