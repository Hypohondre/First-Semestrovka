package services;

import models.User;

import static services.LoginService.userList;

public class RegService {

    public boolean registration(User user){
        if (userList.contains(user)) {
            return false;
        } else {
            userList.add(user);
            return true;
        }
    }
}
