package application;

import java.util.Date;
import java.util.List;

import model.Department;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {
     public static void main(String[] args) {
        
        Department obj = new Department(1, "Books");
        
        Seller seller = new Seller(21,"Bob","bob@gmail.com",new Date(),3000.0,obj);

        SellerDao SellerDao = DaoFactory.creatSellerDao();
        
        System.out.println("=== TEST 1: seller findByid ====");
        seller = SellerDao.findByid(3);
        System.out.println(seller);

         System.out.println("\n=== TEST 2: seller findByid ====");
         Department department = new Department(2,null);
         List<Seller> list = SellerDao.findyByDepartment(department);
         for(Seller sellerObj : list){
            System.out.println(sellerObj);
         }
         System.out.println("\n=== TEST 3: seller findAll ====");
          list = SellerDao.findAll();
         for(Seller Obj : list){
            System.out.println(Obj);
         }
          System.out.println("\n=== TEST 4: seller insert ====");
          Seller newSeller = new Seller(null, "Greg", "greg@gmail.com",new Date(),4000.0,department);
          SellerDao.insert(newSeller);
          System.out.println("Inserted!New id ="+ newSeller.getId());

          System.out.println("\n=== TEST 5: seller insert ====");
          seller = SellerDao.findByid(1);
          seller.setName("Mateus wilson");
          SellerDao.update(seller);
          System.out.println("Update completed");

     }
}
