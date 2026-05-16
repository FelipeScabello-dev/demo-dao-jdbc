package model;

import java.io.Serializable;

/*
 * Classe Department
 * -----------------
 * Representa a entidade Department (departamento) do sistema.
 * 
 * Essa classe é usada para armazenar os dados da tabela department
 * do banco de dados.
 * 
 * Implementa Serializable para permitir que objetos da classe
 * possam ser convertidos em bytes (serialização).
 */

public class Department implements Serializable {

    // Identificador único do departamento
    private Integer id;

    // Nome do departamento
    private String name;

    /*
     * Construtor vazio
     * Necessário para frameworks e também para criar o objeto
     * sem passar parâmetros.
     */
    public Department() {
    }

    /*
     * Construtor com parâmetros
     * Utilizado para criar um departamento já preenchido.
     */
    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /*
     * Retorna o id do departamento
     */
    public Integer getId() {
        return id;
    }

    /*
     * Define o id do departamento
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /*
     * Retorna o nome do departamento
     */
    public String getName() {
        return name;
    }

    /*
     * Define o nome do departamento
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * hashCode
     * --------
     * Gera um número baseado no id do objeto.
     * 
     * Muito usado em estruturas como HashMap e HashSet.
     */
    @Override
    public int hashCode() {

        final int prime = 31;

        int result = 1;

        result = prime * result + ((id == null) ? 0 : id.hashCode());

        return result;
    }

    /*
     * equals
     * ------
     * Compara dois objetos Department.
     * 
     * Dois departamentos serão considerados iguais
     * se possuírem o mesmo id.
     */
    @Override
    public boolean equals(Object obj) {

        // Verifica se os objetos são a mesma referência
        if (this == obj)
            return true;

        // Verifica se o objeto é nulo
        if (obj == null)
            return false;

        // Verifica se as classes são iguais
        if (getClass() != obj.getClass())
            return false;

        // Faz o cast do objeto
        Department other = (Department) obj;

        // Comparação dos ids
        if (id == null) {

            if (other.id != null)
                return false;

        } else if (!id.equals(other.id))
            return false;

        return true;
    }

    /*
     * toString
     * --------
     * Retorna uma representação textual do objeto.
     * 
     * Muito útil para testes e debug.
     */
    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + "]";
    }
}