/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.tut.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import za.ac.tut.Customer;
import za.ac.tut.Item;

/**
 *
 * @author samuk
 */
@Stateless
public class CustomerBean implements CustomerService
{

    @PersistenceContext(unitName = "Tutorial2-ejbPU")
    EntityManager entity;
    @Override
    public void storeCustomer(Customer customer)
    {
        entity.persist(customer);
    }

    @Override
    public Customer validateLogon(String email, String password)
    {
        String sql = "SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password";
        Query query = entity.createQuery(sql);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<Customer> customers = query.getResultList();

        Customer customer = null;
        //IF THE LOGONS ARE VALID RETURN THE CUSTOMER OTHERWISE NULL
        if (customers.size() > 0)
        {
            customer = customers.get(0);
        }
        return customer;
    }

    @Override
    public Item findItem(int id)
    {
        return entity.find(Item.class, id);
    }

    @Override
    public List<Item> getAllItems(String items)
    {
        String sql = "SELECT i FROM Item i";
        Query query = entity.createQuery(sql);
        List<Item> item = query.getResultList();
        return item;
    }

}
