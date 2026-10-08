package za.ac.tut.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import za.ac.tut.Customer;
import za.ac.tut.Item;

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

        String sql = "SELECT c from Customer c WHERE c.email = :email AND c.password = :password";
        Query query = entity.createQuery(sql);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<Customer> customers = (List<Customer>) query.getResultList();

        Customer customer = null;
        if (!customers.isEmpty()) {
            customer = customers.get(0);
        }
        return customer;
    }

    @Override
    public Item findItem(int id) {

        return entity.find(Item.class, id);

    }

    @Override
    public List<Item> getAllItems(String items) {
        String sql = "SELECT i from Item i";
        Query query = entity.createQuery(sql);
        List<Item> item = (List<Item>) query.getResultList();

        return item;
    }

}
