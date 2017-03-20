@subscription @newsletter @javascript @allow-rescue
Feature: Newsletter subscription
  In order to receive email notifications with the latest site news, anuser must be able to subscribe to the site newsletter.

  Background:
    Given a page named "Subscription" located at "http://oglobo.globo.com/newsletter/cardapio/"
    And a page named "Confirmation" shown after subscribing to the newsletter

  Scenario: Newsletter page must have all required information
    Given I navigate to "Subscription page" as a visitor
    Then I should see the header "NEWSLETTER O GLOBO"
    And I should see a short text "Receba gratuitamente o melhor conteúdo do GLOBO direto em seu e-mail!"
    And I should see the subscription step number 1
    And I should see a short text "Escolha as edições de sua preferência:"
    And I should see 3 subscription channel options
    And The subscription channel option number 1 must contain:
    """
    [ ]
    Diárias
    =======
    *Comece e termine o dia bem informado*
    Direto no seu email: notícias, análises e informações exclusivas de manhã e à noite.
    """
    And The subscription channel option number 2 must contain:
    """
    [ ]
    Gente do Globo
    ==============
    *Encontro marcado com os melhores colunistas*
    Leia uma seleção de conteúdos dos nossos articulistas.
    """
    And The subscription channel option number 3 must contain:
    """
    [ ]
    Opinião
    =======
    *Não perca as discussões do momento*
    Receba nossos editoriais e textos assinados por especialistas.
    """
    And I should be able to select a subscription channel clicking on its checkbox
    And I should see the subscription step number 2
    And I should see a short text "Informe seu e-mail para recebimento da newsletter:"
    And I should see a form field "Endereço de e-mail"
    And I should see a form field "Confirmar seu e-mail"
    And I should see a form field with a captcha challenge
    And I should see a "INSCREVA-SE" button
    And I should see a short text "Já se cadastrou, mas não está recebendo a newsletter?"
    And I should see a big text
    """
    1) Acesse sua caixa de spam ou lixo eletrônico, selecione o e-mail e marque como não é spam
    2) Se não resolveu, envie um e-mail para faleconosco@infoglobo.com.br ou ligue 4002-5300 para capitais e grandes cidades | 0800 021 8433 para demais localidades
    Segunda a sexta, das 6h30 às 19h | Sábados, domingos e feriados, das 7h às 12h.
    """
    And I should see an advertisement panel with 300px of width by 250px of height
    And I should see an unordered list with:
      | Nós não iremos divulgar o seu endereço de e-mail.                                                             |
      | Você pode desfazer sua inscrição a qualquer momento através do link na parte inferior da newsletter recebida. |
      | Ao inscrever-se para as newsletters você concorda com nossos termos de uso.                                   |
    And I should see a link "termos de uso" which references "http://oglobo.globo.com/termos-de-uso/"
    And I should see a short text "Precisando de ajuda? Acesse http://oglobo.globo.com/fale-conosco/."
    And I should see a link "http://oglobo.globo.com/fale-conosco/" which references "http://oglobo.globo.com/fale-conosco/"

  Scenario: Confirmation page must have all required information
    Given I navigate to "Subscription page" as a visitor
    When I fill the subscription form
    And I click "INSCREVA-SE"
    Then I should be presented the "Confirmation page" after the asynchronous request
    And I should see a short text "Quase pronto..."
    And I should see a short text "Acesse sua caixa de e-mail e confirme sua inscrição para começar a receber nossa newsletter."

  Scenario Outline: Subscribe to the newsletter
    Given I navigate to "Subscription page" as <visitor>
    Then I select <channels in interest> from the subscription channel options
    And I fill the email address field with <email address>
    And I fill the email address confirmation field with <email address>
    And I answer the captcha challenge correctly
    And I click "INSCREVA-SE"
    When My previous subscription state (<subscription state>) is one of:
      | subscribed     |
      | not subscribed |
    Then I should see a "Confirmation page"

    Examples:
      | visitor | email address | subscription state | channels in interest |
      | Joe     | joe@globo.com | subscribed         | 1,                   |
      | Don     | don@globo.com | not subscribed     | 1,2                  |
      | Tim     | tim@notvalid  | not subscribed     | 1,2,3                |

  Scenario: Not selecting a subscription channel gives an error
    Given I navigate to "Subscription page" as a visitor
    When I fill the subscription form
    But I unselect all subscription channel options
    And I click "INSCREVA-SE"
    Then I should see a short text "Escolha ao menos uma das opções acima." colored in red inside the viewport

  Scenario: Filling incorrect email information gives an error
    Given I navigate to "Subscription page" as a visitor
    When I fill the subscription form
    But I fill the email address field with an invalid email address
    And I fill the email address confirmation field with an invalid email address
    And I click "INSCREVA-SE"
    Then I should see a short text "Verifique se preencheu corretamente os campos acima." colored in red inside the viewport
