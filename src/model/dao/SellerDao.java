package model.dao;

import java.util.List;

import model.Department;
import model.entities.Seller;

/*
 * Interface SellerDao
 * -------------------
 * 
 * Responsável por definir as operações de acesso
 * ao banco de dados relacionadas à entidade Seller.
 * 
 * DAO = Data Access Object
 * 
 * Essa interface contém os métodos CRUD:
 * 
 * Create -> insert
 * Read   -> find
 * Update -> update
 * Delete -> delete
 * 
 * A implementação desses métodos será feita
 * na classe SellerDaoJDBC.
 */

public interface SellerDao {

    /*
     * Insere um novo seller no banco de dados
     */
    void insert(Seller obj);

    /*
     * Atualiza os dados de um seller existente
     */
    void update(Seller obj);

    /*
     * Remove um seller pelo ID
     */
    void deleteById(Integer id);

    /*
     * Busca um seller pelo ID
     * 
     * Retorna um objeto Seller
     */
    Seller findById(Integer id);

    /*
     * Retorna todos os sellers cadastrados
     */
    List<Seller> findAll();

    /*
     * Retorna uma lista de sellers
     * pertencentes a um departamento específico
     */
    List<Seller> findByDepartment(Department department);
}