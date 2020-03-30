package com.ismaelmasegosa.transaction.challenge.acceptance.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismaelmasegosa.transaction.challenge.acceptance.config.World;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles(profiles = {"test", "acceptance"})
public class SearchTransactions {

  @Autowired
  private World world;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private TransactionRepository transactionRepository;

  @Given("the account_iban and the sort are not provided")
  public void theAccountIbanAndTheSortAreNotProvided() {
    world.setAccountIban("");
  }

  @When("the list of transaction is requested")
  public void theListOfTransactionIsRequested() throws Exception {
    ResultActions resultActions = mockMvc.perform(get("/transactions"));

    world.setResultActions(resultActions);
  }

  @Then("the list of transaction is returned in descending order by date")
  public void theListOfTransactionIsReturnedInDescendingOrderByDate() throws Exception {
    ResultActions resultActions = world.getResultActions();
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.[0].reference", is("44444A")));
    resultActions.andExpect(jsonPath("$.[1].reference", is("33333A")));
    resultActions.andExpect(jsonPath("$.[2].reference", is("22222A")));
    resultActions.andExpect(jsonPath("$.[3].reference", is("11111A")));
  }
}