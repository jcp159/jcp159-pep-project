package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    //The creation of the message will be successful if and 
    //only if the message_text is not blank, is not over 255 characters, 
    //and posted_by refers to a real, existing user.
    public Message addMessage (Message message) {
        if (message.getMessage_text().length() > 0 && message.getMessage_text().length() <= 255 && messageDAO.getMessageByPostedBy(message.getPosted_by()) != null) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageId(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message updateMessage(int message_id, Message message) {
        //The update of a message should be successful if and only
        // if the message id already exists and the new message_text
        // is not blank and is not over 255 characters.
        if (messageDAO.getMessageById(message_id) != null && message.getMessage_text().length() <= 255 && message.getMessage_text().length() > 0) {
            messageDAO.updateMessage(message_id, message);
            return messageDAO.getMessageById(message_id);
        }
        return null;
    }

    public List<Message> getAllMessagesByAccountId (int account_id) {
        return messageDAO.getAllMessagesByAccountId(account_id);
    }

    public Message deleteMessageById(int message_id) {
        //System.out.println("this is the message: " + messageDAO.getMessageById(message_id));
        messageDAO.deleteMessageById(message_id);
        //System.out.println("this is the message After delete message: " + messageDAO.getMessageById(message_id));
        return messageDAO.getMessageById(message_id);
    }
}
