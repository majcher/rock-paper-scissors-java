Waste an Hour Having Fun

Narrative:
In order to spend an hour of my day having fun
As a frequent games player
I want to play rock, paper, scissors

Scenario: Play Rock vs. Scissors

Given a RPS game with 2 players: Mike and Peter
When Mike shows Rock gesture
And Peter shows Scissors gesture
Then Mike wins the game
And Peter looses the game

Scenario: Play Paper vs. Scissors

Given a RPS game with 2 players: Mike and Peter
When Mike shows Paper gesture
And Peter shows Scissors gesture
Then Mike looses the game
And Peter wins the game

Scenario: Play Paper vs. Rock

Given a RPS game with 2 players: Mike and Peter
When Mike shows Paper gesture
And Peter shows Rock gesture
Then Mike wins the game
And Peter looses the game

Scenario: Play the same gestures

Given a RPS game with 2 players: Mike and Peter
When both players shows <gesture> gesture
Then there is a tie

Examples:
|gesture|
|Paper|
|Rock|
|Scissors|
