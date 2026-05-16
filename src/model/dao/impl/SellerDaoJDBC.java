package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;

import model.Department;
import model.dao.SellerDao;
import model.entities.Seller;

/*
 * Classe SellerDaoJDBC
 * --------------------
 * 
 * Implementação da interface SellerDao utilizando JDBC.
 * 
 * Essa classe é responsável por realizar todas as operações
 * de acesso ao banco de dados relacionadas à entidade Seller.
 * 
 * JDBC = Java Database Connectivity
 * 
 * Operações implementadas:
 * 
 * - insert
 * - update
 * - delete
 * - findById
 * - findAll
 * - findByDepartment
 */

public class SellerDaoJDBC implements SellerDao {

    /*
     * Objeto responsável pela conexão com o banco
     */
    private Connection conn;

    /*
     * Construtor que recebe a conexão
     */
    public SellerDaoJDBC(Connection conn) {

        this.conn = conn;
    }

    // =========================================================
    // INSERT
    // =========================================================

    @Override
    public void insert(Seller obj) {

        PreparedStatement st = null;

        try {

            /*
             * PreparedStatement:
             * Utilizado para executar SQL parametrizado
             * com maior segurança.
             */

            st = conn.prepareStatement(
                    "INSERT INTO seller "
                            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            /*
             * Preenchimento dos parâmetros do SQL
             */

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());

            /*
             * Conversão de java.util.Date
             * para java.sql.Date
             */
            st.setDate(3,
                    new java.sql.Date(
                            obj.getBirthDate().getTime()));

            st.setDouble(4, obj.getBaseSalary());

            st.setInt(5,
                    obj.getDepartment().getId());

            /*
             * Executa INSERT
             * Retorna quantidade de linhas afetadas
             */
            int rowsAffected = st.executeUpdate();

            /*
             * Verifica se o INSERT funcionou
             */
            if (rowsAffected > 0) {

                /*
                 * Recupera ID gerado automaticamente
                 */
                ResultSet rs = st.getGeneratedKeys();

                if (rs.next()) {

                    int id = rs.getInt(1);

                    obj.setId(id);
                }

                DB.closeResultSet(rs);

            } else {

                throw new DbException(
                        "Unexpected error! No rows affected!");
            }

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        } finally {

            /*
             * Fecha PreparedStatement
             */
            DB.closeStatement(st);
        }
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Override
    public void update(Seller obj) {

        PreparedStatement st = null;

        try {

            st = conn.prepareStatement(
                    "UPDATE seller "
                            + "SET Name = ?, "
                            + "Email = ?, "
                            + "BirthDate = ?, "
                            + "BaseSalary = ?, "
                            + "DepartmentId = ? "
                            + "WHERE Id = ?");

            /*
             * Atualiza os dados do seller
             */
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());

            st.setDate(3,
                    new java.sql.Date(
                            obj.getBirthDate().getTime()));

            st.setDouble(4, obj.getBaseSalary());

            st.setInt(5,
                    obj.getDepartment().getId());

            st.setInt(6, obj.getId());

            /*
             * Executa UPDATE
             */
            st.executeUpdate();

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(st);
        }
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Override
    public void deleteById(Integer id) {

        PreparedStatement st = null;

        try {

            st = conn.prepareStatement(
                    "DELETE FROM seller WHERE Id = ?");

            /*
             * Define ID que será removido
             */
            st.setInt(1, id);

            /*
             * Executa DELETE
             */
            st.executeUpdate();

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(st);
        }
    }

    // =========================================================
    // FIND BY ID
    // =========================================================

    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;

        ResultSet rs = null;

        try {

            /*
             * INNER JOIN:
             * Junta seller com department
             */
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name AS DepName "
                            + "FROM seller "
                            + "INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            /*
             * Verifica se encontrou resultado
             */
            if (rs.next()) {

                /*
                 * Instancia Department
                 */
                Department dep =
                        instantiateDepartment(rs);

                /*
                 * Instancia Seller
                 */
                Seller obj =
                        instantiateSeller(rs, dep);

                return obj;
            }

            return null;

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(st);

            DB.closeResultSet(rs);
        }
    }

    // =========================================================
    // AUXILIAR -> INSTANCIAR SELLER
    // =========================================================

    /*
     * Método auxiliar responsável por criar
     * um objeto Seller usando os dados do ResultSet
     */
    private Seller instantiateSeller(
            ResultSet rs,
            Department dep) throws SQLException {

        Seller obj = new Seller();

        obj.setId(rs.getInt("Id"));

        obj.setName(rs.getString("Name"));

        obj.setEmail(rs.getString("Email"));

        obj.setBaseSalary(rs.getDouble("BaseSalary"));

        obj.setBirthDate(rs.getDate("BirthDate"));

        obj.setDepartment(dep);

        return obj;
    }

    // =========================================================
    // AUXILIAR -> INSTANCIAR DEPARTMENT
    // =========================================================

    /*
     * Cria objeto Department com dados
     * vindos do banco
     */
    private Department instantiateDepartment(
            ResultSet rs) throws SQLException {

        Department dep = new Department();

        dep.setId(rs.getInt("DepartmentId"));

        dep.setName(rs.getString("DepName"));

        return dep;
    }

    // =========================================================
    // FIND ALL
    // =========================================================

    @Override
    public List<Seller> findAll() {

        PreparedStatement st = null;

        ResultSet rs = null;

        try {

            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name AS DepName "
                            + "FROM seller "
                            + "INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            rs = st.executeQuery();

            /*
             * Lista de sellers
             */
            List<Seller> list = new ArrayList<>();

            /*
             * Map utilizado para evitar criar
             * departamentos repetidos em memória
             */
            Map<Integer, Department> map =
                    new HashMap<>();

            while (rs.next()) {

                Department dep =
                        map.get(rs.getInt("DepartmentId"));

                /*
                 * Se departamento ainda não existe no map
                 */
                if (dep == null) {

                    dep = instantiateDepartment(rs);

                    map.put(
                            rs.getInt("DepartmentId"),
                            dep);
                }

                Seller obj =
                        instantiateSeller(rs, dep);

                list.add(obj);
            }

            return list;

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(st);

            DB.closeResultSet(rs);
        }
    }

    // =========================================================
    // FIND BY DEPARTMENT
    // =========================================================

    @Override
    public List<Seller> findByDepartment(
            Department department) {

        PreparedStatement st = null;

        ResultSet rs = null;

        try {

            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name AS DepName "
                            + "FROM seller "
                            + "INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name");

            st.setInt(1, department.getId());

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();

            Map<Integer, Department> map =
                    new HashMap<>();

            while (rs.next()) {

                Department dep =
                        map.get(rs.getInt("DepartmentId"));

                if (dep == null) {

                    dep = instantiateDepartment(rs);

                    map.put(
                            rs.getInt("DepartmentId"),
                            dep);
                }

                Seller obj =
                        instantiateSeller(rs, dep);

                list.add(obj);
            }

            return list;

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(st);

            DB.closeResultSet(rs);
        }
    }
}