@media @gallery @photogallery @javascript @allow-rescue @important
Feature: Photogallery page
  Presents a index for relevant image galleries from the most recent news.

  Background:
    Given a page named "Photogalleries" located at "http://m.oglobo.globo.com/fotogalerias/"
    And a list of "Editorials":
      | Todas      |
      | Rio        |
      | Brasil     |
      | Mundo      |
      | Economia   |
      | Sociedade  |
      | Tecnologia |
      | Ciência    |
      | Saúde      |
      | Cultura    |
      | Ela        |
      | Esportes   |
      | TV         |
      | Boa Viagem |
    And using one "Samsung Galaxy S4" mobile device

  Scenario:
    Given I navigate to "Photogalleries" as a visitor
    Then I should see the header "FOTOGALERIAS"
    And Under the section "RECOMENDADAS" I should see a list of 6 items, each containing:
      | a link       |
      | an image     |
      | a short text |
    And Under the section "MAIS VISTAS" I should see a list of 3 items, each containing:
      | a link       |
      | an image     |
      | a short text |
    And I should see an advertisement panel with 300px of width by 250px of height
    And Under the section "ULTIMAS DE" I should see a select containing items from "Editorials" list
    And I should see a link "VER MAIS" which references ""
    And I should see an advertisement panel with 320px of width by 50px of height