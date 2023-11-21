package br.unipar.swiftsales.dao;

import java.util.ArrayList;

import br.unipar.swiftsales.model.Vendedor;

public interface GenericDAO<Object> {
    //Manipulação de dados
    long insert(Object obj);
    long update(Object obj);
    long delete(Object obj);
    ArrayList<Object> getAll();
    Object getById(int id);

    //Manipulação de dados para filtros e exibição
    ArrayList<Object> getByListNome(String nome);
    int getProximoCodigo();

}
