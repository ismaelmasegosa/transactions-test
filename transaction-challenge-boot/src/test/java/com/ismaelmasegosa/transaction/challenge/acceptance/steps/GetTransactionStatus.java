package com.ismaelmasegosa.transaction.challenge.acceptance.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismaelmasegosa.transaction.challenge.acceptance.config.AcceptanceConfiguration;
import com.ismaelmasegosa.transaction.challenge.acceptance.config.World;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles(profiles = {"test", "acceptance"})
public class GetTransactionStatus extends AcceptanceConfiguration {

  @Autowired
  private World world;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private TransactionRepository transactionRepository;

  @Given("reference {string} and channel {string} are provided")
  public void referenceAndChannelAreProvided(String reference, String channel) {
    world.setReference(reference);
    world.setChannel(channel);
    world.setTransactionEntity(transactionRepository.findByReference(reference).orElseGet(TransactionEntity::new));
  }

  @When("a request of check transaction status is received")
  public void aRequestOfCheckTransactionStatusIsReceived() throws Exception {
    ResultActions resultActions = mockMvc.perform(get("/transaction/" + world.getReference()).queryParam("channel", world.getChannel()));

    world.setResultActions(resultActions);
  }

  @Then("{string} status should be retrieve")
  public void statusShouldBeRetrieve(String channel) throws Exception {
    ResultActions resultActions = world.getResultActions();
    resultActions.andExpect(status().isNotFound());
    resultActions.andExpect(jsonPath("$.refence", is(world.getReference())));
    resultActions.andExpect(jsonPath("$.channel", is(channel)));
  }
}
