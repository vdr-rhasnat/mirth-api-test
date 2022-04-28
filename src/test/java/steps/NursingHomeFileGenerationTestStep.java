package steps;

import GenericInfo.GenericFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;

public class NursingHomeFileGenerationTestStep {
    GenericFactory gf = new GenericFactory();
    @When("user hit the server with the IP address {string} on port {int}")
    public void  userHitTheServerWithTheIpAddressOnPort(String ipAddress, Integer port) {
       boolean isMessageSentToServer = gf.isMessageSentToServer(ipAddress,port);
       Assert.assertTrue("Message cannot send to the Server", isMessageSentToServer);
    }

    @Then("user should get the file from the filepath {string}")
    public void userShouldGetTheFileFromTheFilePath(String filePath) {
        String fileName = gf.getFileName(filePath);
        String extension = FilenameUtils.getExtension(fileName);
        boolean isFileExist = gf.checkFileIsExistOrNot(fileName, extension);
        Assert.assertTrue("FileName Is Not Exist ",isFileExist);
    }

    @And("user should receive the patient medication details {string}")
    public void userShouldReceiveThePatientMedicationDetails(String expectedResult) {
        String fileContent = gf.getFilePatientContent();
        Assert.assertEquals(expectedResult, fileContent);
    }
}
