Require Import List.
Require Import ZArith.

Print list.

Set Implicit Arguments.


Fixpoint sum (l:list nat) {struct l} : nat :=
    match l with
      | nil => 0
      | x :: xs => x + sum xs
    end.

Eval compute in sum nil.

Eval compute in sum (1 :: 2 :: 3 :: nil).

Print "++".
Print rev.
Print length.
Print map.


Lemma ex1 : forall (l1 l2: list nat), sum (l1 ++ l2) = sum l1 + sum l2 .
Proof.
intro. intro. induction l1.
-simpl. reflexivity.
-simpl. rewrite IHl1. SearchRewrite(_+(_ + _)). rewrite Nat.add_assoc. reflexivity.
Qed.

Lemma ex2 : forall (A:Type) (l:list A), length (rev l) = length l.
Proof.
intros. induction l.
-simpl. reflexivity.
-simpl. SearchRewrite(length(_)). rewrite app_length. 
   simpl. rewrite IHl. SearchRewrite(S _ ). rewrite Nat.add_1_r. reflexivity.
Qed.

Lemma ex3 :forall (A B:Type) (f:A->B) (l:list A), rev (map f l) = map f (rev l).
Proof.
intros. induction l.
-simpl. reflexivity.
-simpl. rewrite IHl. SearchRewrite(map _ (_ ++ _)). rewrite map_app. simpl. reflexivity.
Qed.


Inductive In (A:Type) (y:A) : list A -> Prop :=
| InHead : forall xs:list A, In y (cons y xs)
| InTail : forall (x:A) (xs:list A), In y xs -> In y (cons x xs).

Lemma ex4: forall (A:Type) (a b : A) (l : list A), In b (a :: l) -> a = b \/ In b l.
Proof.
intros. inversion H.
-left. reflexivity.
-right. assumption.
Qed.


Lemma ex5:forall (A:Type) (l1 l2: list A) (x:A), In x l1 \/ In x l2 -> In x (l1 ++ l2).
Proof.
intros. destruct H.
-induction l1.
 *inversion H.
 *simpl. apply ex4 in H. destruct H.
   +rewrite H. apply InHead.
   +apply InTail. apply IHl1. assumption.
-induction l1.
 *simpl. assumption.
 *simpl. apply InTail. assumption.
Qed.


Lemma ex6: forall (A:Type) (x:A) (l:list A), In x l -> In x (rev l).
Proof.
intros. induction l.
-simpl. assumption.
-simpl. apply ex5. apply ex4 in H. destruct H. 
 *right. rewrite H. apply InHead.
 *left. apply IHl. assumption.
Qed.


Lemma ex7:forall (A B:Type) (y:B) (f:A->B) (l:list A), In y (map f l) -> exists x, In x l /\ y = f x.
Proof.
 intros.
 induction l.
-inversion H.
- simpl in H. apply ex4 in H. destruct H.
 *exists a. split.
  +constructor.
  +auto.
 *apply IHl in H. destruct H. destruct H. exists x. split.
  +apply InTail. assumption.
  +assumption.
Qed.

Inductive Prefix (A:Type) : list A -> list A -> Prop :=
| PreNil : forall l:list A, Prefix nil l
| PreCons : forall (x:A) (l1 l2:list A), Prefix l1 l2 -> Prefix (x::l1) (x::l2).

Lemma ex8:forall (A:Type) (l1 l2:list A), Prefix l1 l2 -> length l1 <= length l2.
Proof.
intros. induction H. 
-simpl. SearchPattern(0<=_). apply Nat.le_0_l.
-simpl. apply Peano.le_n_S. assumption.
Qed.


Lemma ex9:forall l1 l2, Prefix l1 l2 -> sum l1 <= sum l2.
Proof.
intro. intro. intro. induction H.
-simpl. apply Nat.le_0_l.
-simpl. Search (_ + _ <= _ + _). apply PeanoNat.Nat.add_le_mono_l. assumption.
Qed. 

Lemma ex10:forall (A:Type) (l1 l2:list A) (x:A), (In x l1) /\ (Prefix l1 l2) -> In x l2.
Proof.
intros. destruct H. induction H0.
-inversion H.
-apply ex4 in H. destruct H.
 +rewrite H. apply InHead.
 +apply InTail. apply IHPrefix. assumption.
Qed.

Inductive SubList (A:Type) : list A -> list A -> Prop :=
| SLnil : forall l:list A, SubList nil l
| SLcons1 : forall (x:A) (l1 l2:list A), SubList l1 l2 -> SubList (x::l1) (x::l2)
| SLcons2 : forall (x:A) (l1 l2:list A), SubList l1 l2 -> SubList l1 (x::l2).

Lemma ex11: SubList (1::3::nil) (3::1::2::3::4::nil).
Proof.
   apply SLcons2. apply SLcons1. apply SLcons2. apply SLcons1. apply SLnil.
Qed.


Lemma ex12: forall (A:Type)(l1 l2 l3 l4:list A), SubList l1 l2 -> SubList l3 l4 -> SubList (l1++l3) (l2++l4).
Proof.
  intros.
  induction H.
  - rewrite app_nil_l. 
    induction l. 
    * rewrite app_nil_l. assumption. 
    * simpl. apply SLcons2. assumption.
  - simpl. apply SLcons1. assumption.
  - simpl. apply SLcons2. assumption.
Qed.

Lemma ex13: forall (A:Type) (l1 l2:list A), SubList l1 l2 -> SubList (rev l1) (rev l2).
Proof.
  intros.
  induction H.
  -simpl. apply SLnil.
  - simpl. 
    apply ex12. (* Usei o exercio anterior para me facilitar a vida*)
      + assumption.
      + apply SLcons1. apply SLnil.
  - simpl. Search (_ = _ ++ nil ). rewrite app_nil_end with (l:=rev l1).
    apply ex12.
      + assumption.
      + apply SLnil.
Qed.

Lemma ex14: forall (A:Type) (x:A) (l1 l2:list A), SubList l1 l2 -> In x l1 -> In x l2.
Proof.
intros. induction H.
-inversion H0.
-apply ex4 in H0. destruct H0.
 *rewrite H0. apply InHead.
 *apply InTail. apply IHSubList. assumption.
- apply InTail. apply IHSubList. assumption.
Qed.












