temperature(X, normal) :- X =< 25, !.
temperature(X, warm) :- X<30, !.
temperature(X, warning) :- X>35, !.