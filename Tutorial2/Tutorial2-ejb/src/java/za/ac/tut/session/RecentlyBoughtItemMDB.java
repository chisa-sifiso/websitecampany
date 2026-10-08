package za.ac.tut.session;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(mappedName = "Jms/recentBoughtItems", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "Jms/recentBoughtItems"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class RecentlyBoughtItemMDB implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                Item item = (Item) objectMessage.getObject();

                System.out.println("Recently bought item -> ID: " + item.getItemID()
                        + ", Name: " + item.getName()
                        + ", Type: " + item.getItemType()
                        + ", Qty: " + item.getQty()
                        + ", Price: R" + String.format("%.2f", item.getPrice()));
            }
        } catch (JMSException e) {
            System.out.println("Error reading message: " + e.getMessage());
        }
    }

}
