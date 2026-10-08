package za.ac.tut.session;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(mappedName = "Jms/recentBoughtItems", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "Jms/recentBoughtItems"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "NonDurable")
})
public class RecentlyBoughtItemMDB implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                Item item = (Item) ((ObjectMessage) message).getObject();
                System.out.println("Recently bought item -> ID: " + item.getItemID()
                        + ", Name: " + item.getName()
                        + ", Type: " + item.getItemType()
                        + ", Qty: " + item.getQty()
                        + ", Price: R" + String.format("%.2f", item.getPrice()));
            } else {
                System.out.println("Recently bought item: " + message.getBody(String.class));
            }
        } catch (JMSException e) {
            System.out.println("RecentlyBoughtItemMDB error: " + e.getMessage());
        }
    }
}
