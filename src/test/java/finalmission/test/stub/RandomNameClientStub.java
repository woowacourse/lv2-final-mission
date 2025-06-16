package finalmission.test.stub;

import finalmission.client.RandomNameClient;
import java.util.List;

public class RandomNameClientStub implements RandomNameClient {

    private List<String> generateRandomNamesSuccess;
    private RuntimeException generateRandomNamesFail;

    @Override
    public List<String> generateRandomNames(int quantity) {
        if (generateRandomNamesSuccess != null) {
            return generateRandomNamesSuccess;
        }
        throw generateRandomNamesFail;
    }

    public void setGenerateRandomNamesSuccess(List<String> generateRandomNamesSuccess) {
        this.generateRandomNamesSuccess = generateRandomNamesSuccess;
    }

    public void setGenerateRandomNamesFail(RuntimeException generateRandomNamesFail) {
        this.generateRandomNamesFail = generateRandomNamesFail;
    }
}
