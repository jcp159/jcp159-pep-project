package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postNewUserHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postCreateMessageHandler);
        app.get("/messages",this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageById);
        app.patch("/messages/{message_id}", this::patchMessageById);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountId);
        app.delete("/messages/{message_id}", this::deleteMessageById);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postNewUserHandler (Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount != null) {
            context.json(mapper.writeValueAsString(addedAccount));
        } else {
            context.status(400);
        }
    }

    private void postLoginHandler (Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account verifyAccount = accountService.verifyAccount(account);
        if (verifyAccount != null) {
            context.json(mapper.writeValueAsString(verifyAccount));
        } else {
            context.status(401);
        }
    }

    private void postCreateMessageHandler (Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addMessage = messageService.addMessage(message);
        if (addMessage != null) {
            context.json(mapper.writeValueAsString(addMessage));
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesHandler (Context context) {
        context.json(messageService.getAllMessages());
    }

    private void getMessageById (Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message getMessageId = messageService.getMessageId(message_id);
        if (getMessageId != null) {
            context.json(messageService.getMessageId(message_id));
        }
        context.status(200);
    }

    private void patchMessageById (Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(),Message.class);
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);

        if (updatedMessage != null) {
            context.json(mapper.writeValueAsString(updatedMessage));
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesByAccountId (Context context){
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageService.getAllMessagesByAccountId(account_id));
    }

    private void deleteMessageById (Context context){
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message getMessageId = messageService.getMessageId(message_id);
        Message deletedMessage = messageService.deleteMessageById(message_id);
        if (getMessageId != null && deletedMessage == null) {
            context.json(getMessageId);
        } 
    }


}