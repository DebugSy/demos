grammar Calculator;

line : expr EOF ;
expr : '(' expr ')'          # parenExpr
     | expr ('*'|'/') expr   # multOrDiv
     | expr ('+'|'-') expr   # addOrSubtract
     | FLOAT                 # float ;

WS   : [ \t\n\r]+ -> skip ;
FLOAT   : DIGIT+ '.' DIGIT* EXPONENT?
        | '.' DIGIT+ EXPONENT?
        | DIGIT+ EXPONENT? ;

fragment DIGIT  : '0'..'9';
fragment EXPONENT   : ('e' | 'E') ('+'|'-')? DIGIT+ ;