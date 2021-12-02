@test
Feature: testing full stack | check Users, Books, Borrowed Books numbers and compare at end

  Background:
    Given establish the database connection
     @ui
    Scenario: test all 3 layers ui, api, database
      When I check the homepage and get numbers
      When I check the database for the numbers
      When I check the API for the numbers
      Then All the number sets should match