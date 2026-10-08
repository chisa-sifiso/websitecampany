package za.ac.tut.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
        String jpql = "SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password";
        TypedQuery<Customer> query = entity.createQuery(jpql, Customer.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        List<Customer> customers = query.getResultList();

        // valid logons return the customer, otherwise null
        return customers.isEmpty() ? null : customers.get(0);
    }

    @Override
    public Item findItem(int id) {
        return entity.find(Item.class, id);
    }

    @Override
    public List<Item> getAllItems(String itemType) {
        // no item type given -> return every item
        if (itemType == null || itemType.trim().isEmpty()) {
            return entity.createQuery("SELECT i FROM Item i", Item.class).getResultList();
        }
        TypedQuery<Item> query = entity.createQuery("SELECT i FROM Item i WHERE i.itemType = :itemType", Item.class);
        query.setParameter("itemType", itemType);
        return query.getResultList();
    }

}
