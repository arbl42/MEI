Section LogicaP.

Variables A B C:Prop.

Lemma ex1 : (A\/B)\/C -> A\/(B\/C).
Proof.
  intro.
  destruct H.
  destruct H.
  -left. assumption.
  -right. left. assumption.
  -right. right. assumption.
Qed.

Lemma ex1' : (A\/B)\/C -> A\/(B\/C).
Proof.
  firstorder.
Qed.

Lemma ex1'' : (A\/B)\/C -> A\/(B\/C).
Proof.
  tauto.
Qed.

Lemma ex1''' : (A\/B)\/C -> A\/(B\/C).
Proof.
  intro.
  destruct H.
  destruct H.
  -auto.
  -auto.
  -auto.
Qed.

Lemma ex2 : (B->C)->A\/B->A\/C.
Proof.
  intros.
  destruct H0.
  -auto.
  -right. apply H. assumption.
Qed.

Lemma ex3 : (A/\B)/\C -> A /\ (B/\C).
Proof.
  intro.
  destruct H.
  destruct H.
  split.
  - assumption.
  - split.
   * assumption.
   * assumption.
Qed.

Lemma ex4 : A\/(B/\C)->(A\/B)/\(A\/C).
Proof.
  intro.
  split.
  - destruct H.
    * auto.
    * destruct H. right. assumption.
  - destruct H.
    * auto.
    * destruct H. right. assumption.
Qed.

Lemma ex5 : (A /\ B) \/ (A /\ C) <-> A /\ (B \/ C).
Proof.
 red.
 split.
 -intro. split.
  * destruct H.
   +destruct H. assumption.
   +destruct H. assumption.
  * destruct H.
   + destruct H. left. assumption.
   + destruct H. right. assumption.
 -intro. destruct H. destruct H0.
  * left. auto.
  * right. auto.
Qed.

Lemma ex6 : (A \/ B) /\ (A \/ C) <-> A \/ (B /\ C) .
Proof.
 red.
 split.
 -intro. destruct H. destruct H.
  * left. assumption. 
  * destruct H0.
   + left. assumption.
   + right. auto.
 -intro. split.
  * destruct H.
   +left. assumption.
   +destruct H. right. assumption.
  * destruct H.
   +left. assumption.
   +right. destruct H. assumption.
Qed.


End LogicaP.

Section LogicaPO.
Variables (A:Set). 
Variables (P : A->Prop) (P1 : A->A->Prop) (Q : A->Prop) (R : A->Prop).
Variables x y : A.

Lemma ep1 : (exists x, P(x) /\ Q(x)) -> (exists x, P(x)) /\ (exists x, Q(x)).
 intro. split.
 -destruct H. exists x0. destruct H. assumption.
 -destruct H. exists x0. destruct H. assumption.
Proof.
Qed.

Lemma ep2 : (exists x,forall y, P1 x y) -> forall y,exists x, P1 x y.
Proof.
  intro. intro.
  destruct H.
  exists x0.
  apply H.
Qed.

Lemma ep3 : (exists x, P(x)) -> (forall x y , P(x) -> Q(y)) -> forall y, Q(y).
Proof.
 intro. intro. intro.
 destruct H.
 apply H0 with x0.
 exact H.
Qed.

Lemma ep4 :(forall x, Q(x) -> R(x)) -> (exists x, P(x) /\ Q(x)) -> exists x, P(x) /\ R(x) .
Proof.
 intro. intro. destruct H0.
 destruct H0.
 exists x0.
 split.
 -exact H0.
 -apply H. exact H1.
Qed.

Lemma ep5 : (forall x, P(x) -> Q(x)) -> (exists x, P(x)) -> exists y, Q(y).
Proof.
 intro. intro. destruct H0.
 exists x0. apply H. exact H0.
Qed.

Lemma ep6 : (exists x, P(x)) \/ (exists x, Q(x)) <-> (exists x, P(x) \/ Q(x)).
Proof.
 red. split.
 - intro H. destruct H.
  * destruct H. exists x0. left.  exact H.
  * destruct H. exists x0. right.  exact H.
 - intro H. destruct H. destruct H.
  * left. exists x0. exact H.
  * right. exists x0. exact H.
Qed.

End LogicaPO.

Section LogicaC.

Variables (A:Set). 
Variables (P : A->Prop).
Variables C D :Prop.

Axiom terceiroExclusivo: forall A :Prop, A \/ ~A.
Axiom terceiroExclusivo2: forall A:Prop, ~(A /\ ~A).

Lemma ec1 : ((C -> D) -> C) -> C.
Proof.
 intro.
 destruct terceiroExclusivo with C.
 *assumption.
 *apply H. intro. contradiction.
Qed.

Lemma ec2 : ~~ C ->C.
Proof.
  intro.
  destruct terceiroExclusivo with C.
  *assumption.
  *contradiction.
Qed.

Lemma ec3 : ~(forall x,P x)->exists x, ~P x.
Proof.
destruct terceiroExclusivo with (exists x, ~P x).
- intros. assumption.
-intros. elim H0. intro. assert (forall x, ~~ P x ->P x).
  +clear H H0. intro. destruct terceiroExclusivo with (P x0).
    *intro. assumption.
    *intro. contradiction.
  + apply H1. intro. apply H. exists x. assumption.
Qed.
 
End LogicaC.

























