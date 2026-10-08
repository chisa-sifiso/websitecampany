/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.tut.session;

import java.util.List;
import javax.ejb.Local;
import za.ac.tut.Customer;
import za.ac.tut.Item;

/**
 *
 * @author samuk
 */
@Local
public interface CustomerService
{
    public void storeCustomer(Customer customer);
    public Customer validateLogon(String email, String password);
    public Item findItem(int id);
    public List<Item> getAllItems(String items);
}
