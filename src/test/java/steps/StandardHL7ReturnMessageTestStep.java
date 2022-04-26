package steps;

import Context.GlobalContext;
import GenericInfo.GenericFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import jxl.write.DateTime;
import org.junit.Assert;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class StandardHL7ReturnMessageTestStep {
    GenericFactory gf = new GenericFactory();

    @When("user hit on server with the port {int}")
    public void userHitOnServerWithThePort(Integer port) {
        boolean generateFlatFile = gf.generateFlatFile();
        if(generateFlatFile){
            GlobalContext.HL7Message = gf.getFlatFileContent(port);
        }
        Assert.assertNotNull(GlobalContext.HL7Message);
    }

    @And("user should receive the HL7Message {string}")
    public void userShouldReceiveTheHL7Message( String expHl7Message) {
        String expected =  expHl7Message.replaceAll("\\s", "");
        String actual =  GlobalContext.HL7Message.replaceAll("\\s", "");
        Assert.assertEquals(expected, actual);
    }
}
