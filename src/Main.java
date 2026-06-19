import dao.MembreDao;
import model.Membre;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("🔄 Tentative d'ajout d'un membre de test...");

            // On crée un membre fictif pour tester
            Membre testMembre = new Membre("Akaouch", "Noura", "0600000000", "noura@email.com", LocalDate.now());

            // On appelle votre DAO pour l'insérer dans phpMyAdmin
            MembreDao dao = new MembreDao();
            dao.ajouterMembre(testMembre);

            System.out.println("✅ Succès ! Le membre a été ajouté à la base de données.");
            System.out.println("👉 Ouvrez phpMyAdmin et regardez votre table 'membre' !");

        } catch (Exception e) {
            System.out.println("❌ Erreur lors du test : " + e.getMessage());
            e.printStackTrace();
        }
    }
}