import hpn.system.dao.DAOResponsableMedical;

public class Test {

	public static void main(String[] args) {

//		DAOCompte compte = new DAOCompte();
//		Compte compte2 = new Compte("alga","hpns", "admin", new DAOResponsableMedical().selectOne(1));
//		compte.insert(compte2);

		
//		ResponsableMedical medical = new ResponsableMedical();
//		medical.setDerniereModification(new Date());
//		medical.setGrade("major");
//		medical.setNomPersonnel("Adamou");
//		medical.setPrenomPersonnel("Dieudonné");
//		medical.setSexe("masculin");
//		
//		new DAOResponsableMedical().insert(medical);

		if (new DAOResponsableMedical().selectOne(1).getMyCompte() != null) {
			System.out.println(new DAOResponsableMedical().selectOne(1).getMyCompte().getLogin());

		}		
	}
}