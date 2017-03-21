require 'require_all'
require 'pp'

require_rel 'lib'

# dealing with adversity in the Constructivist style

def read_file(file_name)
  File.read(file_name)
rescue
  NotificationTable.store(Notification::Error.new('Error while reading QL file'))
  'form _ {}'
end

def form_parser(ql_contents)
  QL::Parser::FormParser.new.parse(ql_contents)
rescue
  NotificationTable.store(Notification::Error.new('Error while creating parse tree'))
  { form: { id: '_', body: {} } }
end

def form_transformer(ql_parse_tree)
  QL::Parser::FormTransformer.new.apply(ql_parse_tree)
rescue
  NotificationTable.store(Notification::Error.new('Error while creating AST'))
  QL::AST::Form.new('_', [])
end

def type_checker(ql_ast)
  QL::TypeChecker::TypeChecker.new.check(ql_ast)
rescue
  NotificationTable.store(Notification::Error.new('Error while type checking'))
end

def question_frame_builder(ql_ast)
  ql_ast.accept(QL::GUI::FormBuilder.new)
rescue
  NotificationTable.store(Notification::Error.new('Error while building question frames'))
  []
end

def run_gui(question_frames)
  gui = QL::GUI::GUI.new(question_frames)
  gui.render
rescue
  NotificationTable.store(Notification::Error.new('Runtime error'))
  render_errors
end

def render_errors
  Tk.messageBox(
    type: 'ok',
    icon: 'error',
    title: 'Errors found!',
    message: NotificationTable.errors.map(&:message).join("\n")
  )
end

def render_warnings
  Tk.messageBox(
    type: 'ok',
    icon: 'warning',
    title: 'Warnings found!',
    message: NotificationTable.warnings.map(&:message).join("\n")
  )
end

ql_contents = read_file('examples/simple_questionnaire.ql')
ql_parse_tree = form_parser(ql_contents)
ql_ast = form_transformer(ql_parse_tree)
type_checker(ql_ast)
question_frames = question_frame_builder(ql_ast)

if NotificationTable.errors.empty?
  unless NotificationTable.warnings.empty?
    render_warnings
  end
  run_gui(question_frames)
else
  render_errors
end
