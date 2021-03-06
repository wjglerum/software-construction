require 'parslet'

class Parser < Parslet::Parser
  rule(:space) { match('\s').repeat(1) }
  rule(:space?) { space.maybe }

  rule(:lparen) { str('(') >> space? }
  rule(:rparen) { str(')') >> space? }
  rule(:quote) { str('"') >> space? }
  rule(:qmark) { str('?') >> space? }
  rule(:hashrocket) { space? >> str('=>') >> space? }

  rule(:string) { quote >> match['^"'].repeat.as(:string) >> quote }
  rule(:integer) { match['0-9'].repeat(1).as(:integer) >> space? }
  rule(:boolean) { (str('true') | str('false')).as(:boolean) >> space? }
  rule(:literal) { string | integer | boolean }
  rule(:operator) { match['*/+-'].as(:operator) >> space? }

  rule(:comment) { str('#') >> any.repeat >> space? }

  rule(:type) { (str('text') | str('bool') | str('number') | str('money')).as(:type) >> space? }

  rule(:identifier) { (match['a-zA-Z'] >> match['a-zA-z0-9'].repeat).as(:identifier) >> space? }
  # TODO: Fix left recursion and allow nested expressions on left
  rule(:expression) do
    lparen >> expression >> rparen |
    # infix_expression(literal,
    #   [match['*/'], 2, :left],
    #   [match['+-'], 1, :left]
    # ) |
    ((literal | identifier).as(:left) >> operator >> expression.as(:right)) |
    literal |
    identifier
  end
  rule(:block) { (if_statement | question).repeat }

  rule(:if_statement) { (str('if') >> space >> expression.as(:condition) >> block.as(:if_true) >> (str('else') >> space >> block.as(:if_false)).maybe >> str('end')).as(:if_statement) >> space? }

  rule(:question) { (string >> type >> identifier >> (hashrocket >> expression.as(:value)).maybe).as(:question) }

  rule(:form) { (str('form') >> space >> identifier >> block.as(:body) >> str('end')).as(:form) >> space? }

  root :form
end
