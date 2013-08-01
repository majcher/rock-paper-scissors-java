Waste an Hour Having Fun

Narrative:
In order to spend an hour of my day having fun
As a frequent games player
I want to play rock, paper, scissors

Scenario: Play Rock vs. Scissors

Given a RPS game setup for 1 play with 2 players: Mike and Peter
When Mike shows Rock gesture
And Peter shows Scissors gesture
Then Mike wins the play
And the game score is 1:0

Scenario: Play Paper vs. Scissors

Given a RPS game setup for 1 play with 2 players: Mike and Peter
When Mike shows Paper gesture
And Peter shows Scissors gesture
Then Peter wins the play
And the game score is 0:1

Scenario: Play Paper vs. Rock

Given a RPS game setup for 1 play with 2 players: Mike and Peter
When Mike shows Paper gesture
And Peter shows Rock gesture
Then Mike wins the play
And the game score is 1:0

Scenario: Play the same gestures

Given a RPS game setup for 1 play with 2 players: Mike and Peter
When both players show <gesture> gesture
Then there is no play winner
And the game score is 0:0

Examples:
|gesture|
|Paper|
|Rock|
|Scissors|

Scenario: Play RPS game until 3 wins

Given a RPS game setup for 4 play with 2 players: Mike and Peter

When Mike shows Rock gesture
And Peter shows Scissors gesture

Then Mike wins the play

When Mike shows Paper gesture
And Peter shows Rock gesture

Then Mike wins the play

When Mike shows Paper gesture
And Peter shows Scissors gesture

Then Peter wins the play

When Mike shows Rock gesture
And Peter shows Scissors gesture

Then Mike wins the play

And the game score is 3:1