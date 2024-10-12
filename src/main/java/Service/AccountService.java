package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    //The registration will be successful if and only if 
    //the username is not blank, the password is at least 4 
    //characters long, and an Account with that username does not already exist.
    public Account addAccount (Account account) {
        if (account.getUsername().length() > 0 && account.getPassword().length() >= 4 && accountDAO.getAccountByUserName(account.getUsername()) == null) {
            return accountDAO.insertAccount(account);
        }
        return null;
    }

    //The login will be successful if and only if the username and
    //password provided in the request body JSON match a real account existing on the database.
    public Account verifyAccount (Account account) {
        if (accountDAO.getAccountByUserName(account.getUsername()) != null && accountDAO.getAccountByPassWord(account.getPassword()) != null) {
            return accountDAO.getAccountByUserName(account.getUsername());
        }
        return null;
    }
}
