package ma.tp.gestionlivres.dao;

import ma.tp.gestionlivres.model.Livre;

public class TestLivreDAO {

    public static void main(String[] args) {

        LivreDAO dao = new LivreDAO();

        for (Livre l : dao.findAll()) {
            System.out.println(
                    l.getIsbn() + " - " +
                            l.getTitre() + " - " +
                            l.getAuteur().getNom()
            );
        }
    }
}
