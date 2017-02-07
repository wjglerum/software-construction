module Parser.Expression exposing (..)

import AST exposing (..)
import Combine exposing (..)
import Combine.Num exposing (int)
import List exposing (foldr)
import Parser.Token exposing (variableName)
import Combine.Extra exposing (trimmed)


expression : Parser s Expression
expression =
    lazy <|
        \() -> foldr chainl atom expressions


expressions : List (Parser s (Expression -> Expression -> Expression))
expressions =
    [ orOp, andOp, comparisonOp, relationalOp, addOp, multiplyOp ]


andOp : Parser s (Expression -> Expression -> Expression)
andOp =
    AndExpression <$ string "&&"


orOp : Parser s (Expression -> Expression -> Expression)
orOp =
    OrExpression <$ string "||"


comparisonOp : Parser s (Expression -> Expression -> Expression)
comparisonOp =
    choice
        [ EqualToExpression <$ string "=="
        , NotEqualToExpression <$ string "!="
        ]


relationalOp : Parser s (Expression -> Expression -> Expression)
relationalOp =
    choice
        [ GreaterThanOrEqualExpression <$ string ">="
        , LessThanOrEqualExpression <$ string "<="
        , GreaterThanExpression <$ string ">"
        , LessThanExpression <$ string "<"
        ]


addOp : Parser s (Expression -> Expression -> Expression)
addOp =
    choice
        [ PlusExpression <$ string "+"
        , MinusExpression <$ string "-"
        ]


multiplyOp : Parser s (Expression -> Expression -> Expression)
multiplyOp =
    choice
        [ MultiplyExpression <$ string "*"
        , DivideExpression <$ string "/"
        ]


atom : Parser s Expression
atom =
    trimmed
        (choice
            [ Integer <$> int
            , Var <$> variableName
            , Boolean <$> (True <$ string "true" <|> False <$ string "false")
            , ParensExpression <$> parens expression
            ]
        )
