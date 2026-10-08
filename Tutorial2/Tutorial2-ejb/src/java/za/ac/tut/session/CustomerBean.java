package za.ac.tut.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CustomerBean implements CustomerService {

    @PersistenceContext(unitName = "Tutorial2-ejbPU")
    EntityManager entity;

    @Override
    public void storeCustomer(Customer customer) {
        entity.persist(customer);

    }

    @Override
    public Customer validateLogon(String email, String password) {

        String sql = "SELECT Customer from Customer customer WHERE customer.email LIKE :email AND customer.password LIKE :password";
        Query query = entity.createQuery(sql);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<Customer> customers = (List<Customer>) query.getResultList();
        Customer customer = customers.isEmpty() ? null : customers.get(0);
        return customer;
    }

    @Override
    public Item findItem(int id) {

        return entity.find(Item.class, id);

    }

    @Override
    public List<Item> getAllItems(String items) {
        String sql = "SELECT Item from Item item";
        Query query = entity.createQuery(sql);
        List<Item> item = (List<Item>) query.getResultList();

        return item;
    }

}
