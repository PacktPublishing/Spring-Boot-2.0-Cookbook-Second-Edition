@txn
Feature: Finding a book by ISBN
  Background: Preload DB Mock Data
    Given packt-books fixture is loaded

  Scenario: Load one book
    Given 3 books available in the catalogue
    When searching for book by isbn 978-1-78398-478-7
    Then book title will be Orchestrating Docker