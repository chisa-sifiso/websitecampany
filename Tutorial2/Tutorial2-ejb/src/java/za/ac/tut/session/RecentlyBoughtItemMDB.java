package za.ac.tut.session;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import za.ac.tut.Item;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "Jms/recentBoughtItems"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "Jms/recentBoughtItems"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "Jms/recentBoughtItems"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class RecentlyBoughtItemMDB implements MessageListener {

    public RecentlyBoughtItemMDB() {
    }

    @Override
    public void onMessage(Message message)
    {
        if (message instanceof ObjectMessage)
        {
            try {
                ObjectMessage objM = (ObjectMessage) message;
                Item item = (Item) objM.getObject();
                System.out.println("Recently bought item -> ID: " + item.getItemID()
                        + ", Name: " + item.getName()
                        + ", Type: " + item.getItemType()
                        + ", Qty: " + item.getQty()
                        + ", Price: R" + String.format("%.2f", item.getPrice()));
            } catch (JMSException ex) {
                Logger.getLogger(RecentlyBoughtItemMDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
