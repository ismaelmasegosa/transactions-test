package com.ismaelmasegosa.transaction.challenge.acceptance.steps;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismaelmasegosa.transaction.challenge.acceptance.config.AcceptanceConfiguration;
import com.ismaelmasegosa.transaction.challenge.acceptance.config.World;
import com.ismaelmasegosa.transaction.challenge.dto.TransactionDto;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles(profiles = {"test", "acceptance"})
public class CreateTransaction extends AcceptanceConfiguration {

  @Autowired
  private World world;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Given("reference {string}, account_iban {string}, date {string}, amount {double}, fee {double} and description {string} are provided")
  public void referenceAccountIbanDateAmountFeeAndDescriptionAreProvided(String reference, String accountIban, String date, double amount,
      double fee, String description) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    world.setReference(reference);
    world.setAccountIban(accountIban);
    world.setDate(LocalDateTime.parse(date, formatter));
    world.setAmount(amount);
    world.setFee(fee);
    world.setDescription(description);
  }

  @When("a request of create a transaction is received")
  public void when() throws Exception {
    TransactionDto transaction =
        new TransactionDto(world.getReference(), world.getAccountIban(), world.getDate(), world.getAmount(), world.getFee(),
            world.getDescription());
    ResultActions resultActions = mockMvc.perform(post("/api/v1/transaction").contentType(APPLICATION_JSON).content(toJson(transaction)));

    world.setResultActions(resultActions);
  }

  @Then("the transaction is saved and returned")
  public void then() throws Exception {
    ResultActions resultActions = world.getResultActions();
    resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    resultActions.andExpect(jsonPath("$.reference").value(world.getReference()));
    resultActions.andExpect(jsonPath("$.account_iban").value(world.getAccountIban()));
    resultActions.andExpect(jsonPath("$.date").value(world.getDate()));
    resultActions.andExpect(jsonPath("$.amount").value(world.getReference()));
    resultActions.andExpect(jsonPath("$.fee").value(world.getReference()));
    resultActions.andExpect(jsonPath("$.description").value(world.getDescription()));
  }

  private String toJson(TransactionDto transactionDto) throws JsonProcessingException {
    return objectMapper.writeValueAsString(transactionDto);
  }
}
