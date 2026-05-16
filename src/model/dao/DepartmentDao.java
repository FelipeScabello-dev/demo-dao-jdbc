package model.dao;

import java.util.List;

import model.Department;

/*
 * Interface DepartmentDao
 * -----------------------
 * 
 * Define as operações de acesso ao banco de dados
 * relacionadas à entidade Department.
 * 
 * DAO = Data Access Object
 * 
 * Essa interface separa as regras de acesso ao banco
 * da lógica principal da aplicação.
 * 
 * Aqui são definidos os métodos CRUD:
 * 
 * Create -> insert
 * Read   -> find
 * Update -> update
 * Delete -> delete
 */

public interface DepartmentDao {

    /*
     * Insere um novo departamento no banco de dados
     */
    void insert(Department obj);

    /*
     * Atualiza os dados de um departamento existente
     */
    void update(Department obj);

    /*
     * Remove um departamento pelo ID
     */
    void deleteById(Integer id);

    /*
     * Busca um departamento pelo ID
     * 
     * Retorna um objeto Department
     */
    Department findById(Integer id);

    /*
     * Retorna uma lista contendo
     * todos os departamentos do banco
     */
    List<Department> findAll();
}