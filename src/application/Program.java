package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.Department;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

/*
 * Classe principal do sistema
 * ---------------------------
 * 
 * Essa classe foi utilizada para realizar testes das operações
 * CRUD usando JDBC e padrão DAO.
 * 
 * CRUD:
 * Create  -> Insert
 * Read    -> Find
 * Update  -> Update
 * Delete  -> Delete
 */

public class Program {

    public static void main(String[] args) {

        // Scanner utilizado para leitura de dados via teclado
        Scanner sc = new Scanner(System.in);

        /*
         * Criação de um departamento para testes
         * 
         * id = 1
         * name = Books
         */
        Department obj = new Department(1, "Books");

        /*
         * Criação de um vendedor de exemplo
         */
        Seller seller = new Seller(
                21,
                "Bob",
                "bob@gmail.com",
                new Date(),
                3000.0,
                obj
        );

        /*
         * Instanciação do DAO usando Factory Method
         * 
         * O DaoFactory é responsável por criar o objeto SellerDao.
         */
        SellerDao sellerDao = DaoFactory.creatSellerDao();

        // =========================================================
        // TESTE 1 -> Buscar vendedor pelo ID
        // =========================================================

        System.out.println("=== TEST 1: seller findById ====");

        /*
         * Busca no banco o seller com id = 3
         */
        seller = sellerDao.findById(3);

        System.out.println(seller);

        // =========================================================
        // TESTE 2 -> Buscar vendedores por departamento
        // =========================================================

        System.out.println("\n=== TEST 2: seller findByDepartment ====");

        /*
         * Criação de departamento apenas com ID
         * para realizar filtro.
         */
        Department department = new Department(2, null);

        /*
         * Busca lista de vendedores do departamento 2
         */
        List<Seller> list = sellerDao.findByDepartment(department);

        /*
         * Percorre a lista exibindo os sellers
         */
        for (Seller sellerObj : list) {

            System.out.println(sellerObj);
        }

        // =========================================================
        // TESTE 3 -> Buscar todos os vendedores
        // =========================================================

        System.out.println("\n=== TEST 3: seller findAll ====");

        /*
         * Busca todos os sellers do banco
         */
        list = sellerDao.findAll();

        /*
         * Exibe todos os sellers encontrados
         */
        for (Seller objSeller : list) {

            System.out.println(objSeller);
        }

        // =========================================================
        // TESTE 4 -> Inserir novo vendedor
        // =========================================================

        System.out.println("\n=== TEST 4: seller insert ====");

        /*
         * Criação de novo seller para inserção
         */
        Seller newSeller = new Seller(
                null,
                "Greg",
                "greg@gmail.com",
                new Date(),
                4000.0,
                department
        );

        /*
         * Inserção no banco de dados
         */
        sellerDao.insert(newSeller);

        /*
         * Exibe o ID gerado automaticamente pelo banco
         */
        System.out.println("Inserted! New id = " + newSeller.getId());

        // =========================================================
        // TESTE 5 -> Atualizar vendedor
        // =========================================================

        System.out.println("\n=== TEST 5: seller update ====");

        /*
         * Busca seller com id = 1
         */
        seller = sellerDao.findById(1);

        /*
         * Altera o nome do seller
         */
        seller.setName("Mateus Wilson");

        /*
         * Atualiza os dados no banco
         */
        sellerDao.update(seller);

        System.out.println("Update completed");

        // =========================================================
        // TESTE 6 -> Deletar vendedor
        // =========================================================

        System.out.println("\n=== TEST 6: seller delete ====");

        /*
         * Usuário informa o ID do seller que será removido
         */
        System.out.print("Enter id for delete test: ");

        int id = sc.nextInt();

        /*
         * Remove seller do banco
         */
        
        sellerDao.deleteById(id);

        System.out.println("Delete completed");

        // Fecha Scanner
        sc.close();
    }
}