/*
    Project: Patnáctka
    File: flp16-log.pl
    Author: Jakub Pelikán
    Login:  xpelik14
    Date:  16.5.2016
*/

/*Fronta close*/
:- dynamic fClose.

/*Hlavni funkce main*/
main :- 
    prompt(_, ''),
    read_lines(Inp),
    split_lines(Inp,InpLines),
    input_to_number(InpLines, InpNum),
    !,
    check_data(InpNum, Solution),
    !,
    find_optimal_solution(InpNum, Solution),
    halt.

main :- write("Exit Failure\n").

/*Reads line from stdin, terminates on LF or EOF.*/
read_line(L,C) :-
    get_char(C),
    (isEOFEOL(C), L = [], !;
        read_line(LL,_),% atom_codes(C,[Cd]),
        [C|LL] = L).

/*Tests if character is EOF or LF.*/
isEOFEOL(C) :-
    C == end_of_file;
    (char_code(C,Code), Code==10).

read_lines(Ls) :-
    read_line(L,C),
    ( C == end_of_file, Ls = [] ;
      read_lines(LLs), Ls = [L|LLs]
    ).

/*rozdeli radek na podseznamy*/
split_line([],[[]]) :- !.
split_line([' '|T], [[]|S1]) :- !, split_line(T,S1).
split_line([32|T], [[]|S1]) :- !, split_line(T,S1).    % aby to fungovalo i s retezcem na miste seznamu
split_line([H|T], [[H|G]|S1]) :- split_line(T,[G|S1]). % G je prvni seznam ze seznamu seznamu G|S1

/*vstupem je seznam radku (kazdy radek je seznam znaku)*/
split_lines([],[]).
split_lines([L|Ls],[H|T]) :- split_lines(Ls,T), split_line(L,H).

/*Prevedeni vstupnich znaku na cisla*/
input_to_number([],[]).
input_to_number([X|XS],[Line|Next]) :-
    line_to_number(X,Line),
    input_to_number(XS,Next).

/*Prevedeni radku vstupu na cisla*/
line_to_number([], []).

line_to_number([['*']|XS], ['*'|Next]) :-
    line_to_number(XS, Next).

line_to_number([[X|[]]|XS], [Num|Next]) :-
    atom_number(X, Num),
    line_to_number(XS, Next).

line_to_number([X|XS], [Y|Next]) :-
    cislo(X,Y),
    line_to_number(XS, Next).

/** prevede seznam cislic na cislo */
cislo(N,X) :- cislo(N,0,X).
cislo([],F,F).
cislo([H|T],F,X) :- atom_number(H, Num), FT is 10*F+Num, cislo(T,FT,X).
cislo([],F,F,_).

/*Kontrola korektniho vstupu*/
check_data([], _) :-!, write('Empty input'), fail.
check_data([First|Other], Solution) :-
    !,
    check_lines_leng([First|Other]),
    !,
    list_length([First|Other], Column),
    list_length(First, Row),
    Items is Row*Column-1,
    numlist(1, Items, Lis),
    append(Lis, ['*'], Solution),
    !,
    check_data_line([First|Other],Solution).

/*Kontrola korektnostnosti radku vstupu*/
check_data_line([],[]).
check_data_line([Line|Other],FinList) :- 
    !,
    check_line_item(Line,FinList, NewFineList), !,
    check_data_line(Other,NewFineList).

/*Kontrola korektnostnosti jednotlivych polozek radku vstupu*/
check_line_item([],Res,Res).
check_line_item([],[],[]).
check_line_item([Num|Other],FinList, NewList) :- 
    select(Num,FinList, FinListNew),
    !,
    check_line_item(Other,FinListNew, NewList).
check_line_item(_,_,_) :- !, write("spatne znaky na vstupu\n"), fail.

/*Kontrola stejne delky vstupnich radku*/
check_lines_leng([_|[]]).
check_lines_leng([First|[Second|Other]]) :- 
    \+ proper_length(Other,1), 
    same_length(First, Second),
    check_lines_leng([Second|Other]).
check_lines_leng([First|[Second|_]]) :- 
    same_length(First, Second).
check_lines_leng(_) :- 
    write("Ruzna delka radku\n"), !, fail.
    
/*Vypocet delky seznamu*/
list_length([],0).
list_length([_|T],L+1) :- list_length(T,L).

/*Test zda je stav hlavolamu jeho resenim*/
is_solution([],[]).
is_solution([[]|XXS],YS) :- 
    is_solution(XXS,YS).
is_solution([[X|XS]|XXS],[X|YS]) :- 
    is_solution([XS|XXS],YS).
is_solution(_,_) :- 
    !,fail.

/*Vyhledani reseni a vypsani cesty*/
find_optimal_solution(Game, Solution) :-
    compute_price(Game,Solution,Step),
    find_solution([[Step,Game]],Solution, Path),
    write_path(Path).
find_optimal_solution(_, _) :- write('\n\nNo Solution\n\n').
    
/*Vyhledani reseni a vraceni cesty*/
find_solution([[_,Game]|_], Solution,[Game]) :- 
    retractall(fClose),
    is_solution(Game, Solution).
find_solution([[ActualPrice,Game]|Open], Solution, NewPath) :- 
    assert(fClose(Game)),
    compute_price(Game,Solution,ParPrice),
    bagof(Z, (
            nextStep(Game, Z),
            \+ fClose(Z)
        )
        ,ChildList),
    add_all_child(Open,ChildList,ActualPrice+1-ParPrice, Solution,NewOpen),
    !,
    find_solution(NewOpen, Solution, Path),
    create_path(ChildList,Game, Path, NewPath).
find_solution([], _, _) :- 
    !,fail.
find_solution([[_,_]|Open], Solution, NewPath) :- 
    find_solution(Open, Solution, NewPath).

/*Vytvoreni cesty pri navratu od reseni*/
create_path([],_, Actual, Actual).
create_path([Child|_], Actual, [Child|Path], [Actual,Child|Path]).
create_path([_|Other], Actual, Path, NewPath):-
    create_path(Other, Actual,Path,NewPath).

/*Generovani dalsich kroku*/
nextStep(Game, NewGame) :-
    leftStep(Game, NewGame).
nextStep(Game, NewGame) :-
    bottomStep(Game, NewGame).
nextStep(Game, NewGame) :-
    rightStep(Game, NewGame).
nextStep(Game, NewGame) :-
    topStep(Game, NewGame).
nextStep(_,_) :- !,fail.

/*Generovani kroku doleva*/
leftStep([X|XS],[X|Other]) :-
    \+ member('*',X),
    leftStep(XS, Other).
leftStep([X|XS],[Y|XS]) :-
    !,
    member('*',X),
    moveLeft(X,Y).

/*Generovani kroku doprava*/
rightStep([X|XS],[X|Other]) :-
    \+ member('*',X),
    rightStep(XS, Other).
rightStep([X|XS],[Y|XS]) :-
    !,
    member('*',X),
    moveRight(X,Y).

/*Generovani kroku nahoru*/
topStep([_|[]],_) :- 
    !, fail.
topStep([X,Y|XS],[X|Other]) :-
    \+ member('*',Y),
    topStep([Y|XS], Other).
topStep([X,Y|XS],[NewX,NewY|XS]) :-
    !,
    member('*',Y),
    moveUp(X,Y, NewX, NewY).

/*Generovani kroku dolu*/
bottomStep([_|[]],_) :- 
    !, fail.
bottomStep([X,Y|XS],[X|Other]) :-
    \+ member('*',X),
    bottomStep([Y|XS], Other).
bottomStep([X,Y|XS],[NewX,NewY|XS]) :-
    !,
    member('*',X),
    moveDown(X,Y, NewX, NewY).

/*Krok vlevo*/
moveLeft([X|[]], X) :- 
    X=='*', !, fail.
moveLeft([X,Y|XS], [Y,X|XS]) :-
    Y=='*'.
moveLeft([X,Y|XS], [X|New]) :-
    moveLeft([Y|XS], New).

/*Krok vpravo*/
moveRight([X|[]], X) :- 
    !, fail.
moveRight([X,Y|XS], [Y,X|XS]) :-
    X=='*'.
moveRight([X,Y|XS], [X|New]) :-
    moveRight([Y|XS], New).

/*Krok dolu*/
moveDown([X|XS], [Y|YS], [Y|XS], [X|YS]) :-
    X=='*', !.
moveDown([X|XS], [Y|YS], [X|NewX], [Y|NewY]) :-
    moveDown(XS, YS, NewX, NewY).

/*Krok nahoru*/
moveUp([X|XS], [Y|YS], [Y|XS], [X|YS]) :-
    Y=='*', !.
moveUp([X|XS], [Y|YS], [X|NewX], [Y|NewY]) :-
    moveUp(XS, YS, NewX, NewY).    

/*Pridani vsech moznych kroku do fronty Open a odstraneni tech s horsim ohodnocenim*/
add_all_child(Open,[],_,_,Open).
add_all_child(Open,[Child|Other],ActualPrice,Solution,NewOpen) :-
    compute_price(Child,Solution,Heur),
    Price is ActualPrice+Heur,
    add_child(Open, [Price,Child], TmpOpen),
    remove_dup_child(TmpOpen, [Price,Child], TmpOpen1),
    add_all_child(TmpOpen1,Other,ActualPrice,Solution, NewOpen).

/*Pridani kroku do fronty Open*/
add_child([],New,[New]).
add_child([[OPrice,Game]|Open],[Price,Game],[[OPrice,Game]|Open]) :- 
    OPrice=<Price.
add_child([[OPrice,OGame]|Open],[Price,Game],[[Price,Game],[OPrice,OGame]|Open]) :-
    OPrice>=Price.
add_child([[OPrice,OGame]|Open],[Price,Game],[[OPrice,OGame]|NewOpen]) :- 
    add_child(Open,[Price,Game],NewOpen).

/*Odstraneni kroku s horsim ohodnocenim*/
remove_dup_child([],_,[]).
remove_dup_child([[OPrice,Game]|Open],[Price,Game],NewOpen) :- 
    OPrice>Price,
    remove_dup_child(Open,[Price,Game],NewOpen).
remove_dup_child([[OPrice,OGame]|Open],[Price,Game],[[OPrice,OGame]|NewOpen]) :- 
    remove_dup_child(Open,[Price,Game],NewOpen).

/*Vypocet heuristiky pro open volba mezi heuristickymi funkcemi*/
compute_price(Step,Sol,Price) :- 
    !,
    compute_manh_price(Step,Step,Sol,Price).
   % compute_pos_price(Step,Sol,Price).

/*Vypocet heuristiky pro open, pomoci manhattanskych vzdalenosti*/
compute_manh_price([[]],_,_,0) :-!.
compute_manh_price([[]|Other],Step,Sol,Price) :- 
    !,compute_manh_price(Other,Step,Sol,Price).
compute_manh_price([[X|XS]|Other],Step,Sol,Price) :- 
    !, get_price(X, Step, Sol, 0,0,0,0,0,0, TmpPrice),
    !, compute_manh_price([XS|Other], Step, Sol, NewPrice),
    !, Price is NewPrice + TmpPrice.
get_price(_,[], [], _, _, PosX1, PosY1, PosX2, PosY2, Price) :- !,
    Price is abs(PosX1-PosX2) + abs(PosY1-PosY2).
get_price(X,[[]|Other], Sol, _, AxtY, PosX1, PosY2, PosX2, PosY1, Price) :- 
    get_price(X,Other, Sol, 0, AxtY+1, PosX1, PosY2, PosX2, PosY1, Price).
get_price(X,[[X|_]|_], [X|_], _, _, _, _, _, _, 0).
get_price(X,[[X|XS]|Other], [_|Sol], ActX, AxtY, _, _, PosX2, PosY2, Price) :-    
    get_price(X,[XS|Other], Sol, ActX+1, AxtY, ActX, AxtY, PosX2, PosY2, Price).
get_price(X,[[_|XS]|Other], [X|Sol], ActX, AxtY, PosX1, PosY1, _, _, Price) :- 
    get_price(X,[XS|Other], Sol, ActX+1, AxtY, PosX1, PosY1, ActX, AxtY, Price).
get_price(X,[[_|XS]|Other], [_|Sol], ActX, AxtY, PosX1, PosY1, PosX2, PosY2, Price) :-
    get_price(X,[XS|Other], Sol, ActX+1, AxtY, PosX1, PosY1, PosX2, PosY2, Price).

/*Vypocet heuristiky pro open, pomoci poctu kamenu na nespravne pozici*/
compute_pos_price([[]|XXS],YS,Price) :- 
    !,compute_pos_price(XXS,YS, Price).
compute_pos_price([[X|XS]|XXS],[X|YS], Price) :- 
    !,compute_pos_price([XS|XXS],YS, Price).
compute_pos_price([[_|XS]|XXS],[_|YS], Price+1) :- 
    !,compute_pos_price([XS|XXS],YS, Price).
compute_pos_price(_,_,0) :- 
    !.

/*Vypsani reseni na standardni vystup*/
write_path([]).
write_path([Step|Other]) :-
    write_step(Step),
    write_path(Other).

/*Vypsani jednotlivych kroku reseni*/
write_step([]) :- write('\n').  
write_step([Line|Other]) :-
    atomic_list_concat(Line, ' ', Atom), 
    write(Atom),
    write('\n'),
    write_step(Other).