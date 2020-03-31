package com.ismaelmasegosa.transaction.challenge.acceptance.steps;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismaelmasegosa.transaction.challenge.acceptance.config.AcceptanceConfiguration;
import com.ismaelmasegosa.transaction.challenge.acceptance.config.World;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import com.ismaelmasegosa.transaction.challenge.infrastructure.rest.dto.TransactionDto;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles(profiles = {"test", "acceptance"})
public class CreateTransaction extends AcceptanceConfiguration {

  @Autowired
  private World world;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private TransactionRepository transactionRepository;

  @Before
  public void setUp() {
    world.reset();
  }

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

  @Given(
      "a exist reference {string}, account_iban {string}, date {string}, amount {double}, fee {double} and description {string} are provided")
  public void aExistReferenceAccount_ibanDateAmountFeeAndDescriptionAreProvided(String reference, String accountIban, String date,
      double amount, double fee, String description) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    world.setReference(reference);
    world.setAccountIban(accountIban);
    world.setDate(LocalDateTime.parse(date, formatter));
    world.setAmount(amount);
    world.setFee(fee);
    world.setDescription(description);
    transactionRepository.save(new TransactionEntity(reference, accountIban, Instant.now().toEpochMilli(), amount, fee, description));
  }

  @When("a request of create a transaction is received")
  public void when() throws Exception {
    TransactionDto transaction =
        new TransactionDto(world.getReference(), world.getAccountIban(), world.getDate(), world.getAmount(), world.getFee(),
            world.getDescription());
    ResultActions resultActions = mockMvc.perform(post("/transaction").contentType(APPLICATION_JSON).content(toJson(transaction)));

    world.setResultActions(resultActions);
  }

  @Then("the transaction is saved and returned")
  public void then() throws Exception {
    ResultActions resultActions = world.getResultActions();
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.reference", is(world.getReference())));
    resultActions.andExpect(jsonPath("$.account_iban", is(world.getAccountIban())));
    resultActions.andExpect(jsonPath("$.date", is(world.getDate().toString())));
    resultActions.andExpect(jsonPath("$.amount", is(world.getAmount())));
    resultActions.andExpect(jsonPath("$.fee", is(world.getFee())));
    resultActions.andExpect(jsonPath("$.description", is(world.getDescription())));
  }

  @Given("reference {string}, date {string}, fee {double} and description {string} are provided")
  public void referenceDateFeeAndDescriptionAreProvided(String reference, String date, double fee, String description) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    world.setReference(reference);
    world.setDate(LocalDateTime.parse(date, formatter));
    world.setFee(fee);
    world.setDescription(description);
  }

  @Then("the validation error should be returned")
  public void theValidationErrorShouldBeReturned() throws Exception {
    List<String> errors = asList("Account IBAN can not be empty", "Amount can not be zero");
    ResultActions resultActions = world.getResultActions();
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.errors", is(errors)));
  }

  @Then("an uncreated transaction error should be returned")
  public void theForbiddenErrorShouldBeReturned() throws Exception {
    List<String> errors = singletonList("Transaction not created, the total account balance can not be bellow zero");
    ResultActions resultActions = world.getResultActions();
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.errors", is(errors)));
  }

  @Then("an existing transaction error should be returned")
  public void anExistingTransactionErrorShouldBeReturned() throws Exception {
    List<String> errors = singletonList("Al ready exist a transaction with same reference");
    ResultActions resultActions = world.getResultActions();
    resultActions.andExpect(status().isConflict());
    resultActions.andExpect(jsonPath("$.errors", is(errors)));
  }

  @After
  public void tearDown() {
    transactionRepository.delete(world.getReference());
  }

  private String toJson(TransactionDto transactionDto) throws JsonProcessingException {
    return objectMapper.writeValueAsString(transactionDto);
  }
}
