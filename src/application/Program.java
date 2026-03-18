package application;

import java.util.Date;
import model.Department;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {
     public static void main(String[] args) {
        
        Department obj = new Department(1, "Books");
        
        Seller seller = new Seller(21,"Bob","bob@gmail.com",new Date(),3000.0,obj);

        SellerDao SellerDao = DaoFactory.creatSellerDao();

        seller = SellerDao.findByid(3);

        System.out.println(seller);
     }
}
