package finalmission.application.service;

import org.springframework.stereotype.Service;

@Service
public class RandomNameService {
    private final NameClient nameClient;

    public RandomNameService(NameClient nameClient) {
        this.nameClient = nameClient;
    }

    public String approve(){
        return nameClient.approve();
    }
}
