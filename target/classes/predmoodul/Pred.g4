grammar Pred;

koguvalem
    : abidef* predvalem;

abidef
    : predtahis ':=' predvalem
    ;

predtahis
    : predsymbol defargumendid*
    ;

predsymbol
    : identifikaator
    ;

identifikaator
    : TAHT (TAHT |ARV)*
    ;

defargumendid
    : '(' indiviidmuutuja (',' indiviidmuutuja)* ')'
    ;

indiviidmuutuja
    : TAHT
    ;

predvalem
    : ekvvalem ('~' ekvvalem)*
    ;

ekvvalem
    : implvalem ('->' implvalem)*
    ;

implvalem
    : disjvalem ('v' disjvalem)*
    ;

disjvalem
    : konjvalem ('&' konjvalem)*
    ;

konjvalem
    : ('-' | iga | eks)* korgvalem
    ;

iga
    : 'A' indiviidmuutuja
    ;

eks
    : 'E' indiviidmuutuja
    ;

korgvalem
    : '(' predvalem ')' | atomaarnevalem
    ;

atomaarnevalem
   : predsymbol termargumendid*
   | term '=' term
   ;

termargumendid
    : '(' term (',' term)* ')'
     ;

term
    : pmterm ('+' pmterm)*
    | pmterm ('-' pmterm)*
    ;

pmterm
    : kjterm ('*' kjterm)*
    | kjterm ('/' kjterm)*
    ;


kjterm
    : '0' | '1' | indiviidmuutuja | '(' term ')'
    ;

ARV
    : [0-9];

TAHT
    : [a-zA-Z]
    ;

WS: [ \t\r\n]+ -> skip;


