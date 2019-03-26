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
        for (Classe classe : classes) {
            String nomeArquivo = classe.montaNomeArquivo()

            if (!classeJaExiste(nomeArquivo)) {
                String caminhoArquivo = ambiente.getFullPath(pastaClasses, nomeArquivo)
                File arquivo = new File(caminhoArquivo)
                arquivo.write(classe.toJson())
            }
        }
    }

   private  boolean classeJaExiste(String nomeArquivoClasse) {
        return ambiente.getFilesFolder(pastaClasses).find { File arquivo -> arquivo.name == nomeArquivoClasse }
    }
}
