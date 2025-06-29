parent(hs, sieon). %pam is parent of bob.
parent(hs, seongyeon).
parent(ym, sieon).
parent(ym, seongyeon).
parent(sj, hs).
parent(bs, hs).
parent(ph, ym).
female(pam).
female(liz).
female(ann).
female(pat).
male(tom).
male(bob).
male(jim).
offspring(X,Y):-parent(Y,X).
mother(X,Y):-parent(X,Y), female(X).
grandparent(X,Z):-parent(X,Y), parent(Y,Z).
sister(X,Y):-parent(Z,X),parent(Z,Y),female(X).
predecessor(X,Z):-parent(X,Z).
predecessor(X,Z):-parent(X,Y), predecessor(Y,Z).
haschild(X):-parent(X, _).