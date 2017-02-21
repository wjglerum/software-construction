module QLS.Parser.Stylesheet exposing (..)

import Combine exposing (..)
import Combine.Extra exposing (trimmed, whitespace1)
import Parser.Token exposing (quotedString, variableName)
import QLS.AST exposing (..)


stylesheet : Parser s Stylesheet
stylesheet =
    succeed Stylesheet
        <*> (string "stylesheet" *> whitespace1 *> variableName)
        <*> (whitespace1 *> pages)


pages : Parser s (List Page)
pages =
    sepBy whitespace1 page


page : Parser s Page
page =
    succeed Page
        <*> (string "page" *> regex "[A-Z][a-zA-Z0-9]*")
        <*> sections
        <*> defaults


sections : Parser s (List Section)
sections =
    sepBy whitespace1 section


section : Parser s Section
section =
    lazy <|
        \() ->
            succeed Section
                <*> (string "section" *> whitespace1 *> quotedString)
                <*> (whitespace *> sectionChildren)


sectionChildren : Parser s (List SectionChild)
sectionChildren =
    lazy <|
        \() ->
            or multiSectionChildBlock
                (List.singleton <$> singleChildBlock)


singleChildBlock : Parser s SectionChild
singleChildBlock =
    Field <$> question


multiSectionChildBlock : Parser s (List SectionChild)
multiSectionChildBlock =
    lazy <|
        \() -> braces (trimmed (many sectionChild))


sectionChild : Parser s SectionChild
sectionChild =
    lazy <|
        \() -> subSection <|> field


subSection : Parser s SectionChild
subSection =
    SubSection <$> section


field : Parser s SectionChild
field =
    Field <$> question


question : Parser s Question
question =
    succeed Question
        <*> (string "question" *> whitespace1 *> variableName)
        <*> (whitespace1 *> configurations)


configurations : Parser s a
configurations =
    fail "To be implemented"


defaults : Parser s a
defaults =
    fail "To be implemented"