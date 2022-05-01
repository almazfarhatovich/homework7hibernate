package peaksoft;

import peaksoft.dao.UserDao;
import peaksoft.dao.UserDaoHibernateImpl;
import peaksoft.util.Util;

public class Main {
    public static void main(String[] args) throws Exception {

        UserDaoHibernateImpl hibernate = new UserDaoHibernateImpl();
//        hibernate.saveUser("Almaz", "Janybekov", (byte) 20);
//        hibernate.saveUser("Ulan","Tashkojoev",(byte) 26);
//        hibernate.saveUser("Tologon","Tekebaev",(byte) 25);
//        hibernate.saveUser("Turatbek","Temirbekov",(byte) 29);
//        hibernate.saveUser("Dinara","Rahatbek kyzy",(byte) 18);
       // System.out.println(hibernate.existsByFirstName("Almaz"));
       // System.out.println(hibernate.getAllUsers());
 //hibernate.removeUserById(5L);
        hibernate.cleanUsersTable();
        // hibernate.createUsersTable();
      hibernate.close();
    }
}
