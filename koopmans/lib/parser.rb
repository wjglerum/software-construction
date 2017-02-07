require 'parslet'

# parser for forms
class Parser < Parslet::Parser
  rule(:spaces) do
    match('\s').repeat(1)
  end
  rule(:spaces?) do
    spaces.maybe
  end

  def self.symbols(symbols)
    symbols.each do |name, symbol|
      rule(name) { str(symbol) >> spaces? }
    end
  end

  symbols left_brace: '{',
          right_brace: '}',
          left_parenthesis: '(',
          right_parenthesis: ')',
          quote: '"',
          colon: ':'


  # question(s) with optional expression
  rule(:label) do
    str('"') >> (str('"').absent? >> any).repeat.as(:label) >> str('"') >> spaces?
  end

  rule(:variable_assignment) do
    variable.as(:variable) >> colon
  end

  rule(:type) do
    (str('boolean') | str('money') | str('integer') | str('string')).as(:type) >> spaces?
  end

  rule(:variable) do
    match('\w+').repeat(1).as(:variable) >> spaces?
  end

  rule(:arithmetic) do
    match('[-+/*]').as(:arithmetic) >> spaces?
  end

  rule(:expression) do
    left_parenthesis >> (variable >> (arithmetic >> variable).repeat).repeat.as(:expression) >> right_parenthesis
  end

  rule(:equal_to) do
    str('=') >> spaces?
  end

  rule(:question) do
    (label >> variable_assignment >> type >> (equal_to >> expression).maybe).as(:question)
  end

  # if block
  rule(:condition) do
    left_parenthesis >> variable.as(:condition) >> right_parenthesis
  end

  rule(:block) do
    left_brace >> (question | if_statement).repeat.as(:block) >> right_brace
  end

  rule(:if_statement) do
    (str('if') >> spaces? >> condition >> block).as(:if_statement)
  end

  # form
  rule(:form) do
    (spaces? >> str('form') >> spaces? >> variable >> block).as(:form)
  end

  root :question
end
