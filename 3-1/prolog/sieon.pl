% --work1
parent(hs, sieon). %hs is parent
parent(hs, seongyeon).
parent(ym, sieon).
parent(ym, seongyeon).
parent(sj, hs).
parent(bs, hs).
parent(ph, ym).
parent(sy, ym).
female(seongyeon).
female(ym).
female(sy).
male(sieon).
male(hs).
male(ph).
mother(X,Y):-parent(X,Y), female(X).
father(X,Y):-parent(X,Y), male(X).
grandmother(X,Z):-parent(X,Y), parent(Y,Z), female(X).
grandfather(X,Z):-parent(X,Y), parent(Y,Z), male(X).
sister(X,Y):-parent(Z,X), parent(Z,Y), female(X), X\=Y.
brother(X,Y):-parent(Z,X), parent(Z,Y), male(X), X\=Y.
% --work2
life_stage(A, child):- A<13, !.
life_stage(A, teen):- A>=13, A<20, !.
life_stage(A, adult):- A>=20, A<65, !.
life_stage(A, senior):- A>=65.
% --work3
sum_list([], 0).
sum_list([Head|Tail], Sum) :-sum_list(Tail, Sum2), Sum is Head + Sum2.