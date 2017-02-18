package ast;

import javax.swing.plaf.nimbus.State;
import java.util.List;
import java.util.ArrayList;

public class Block extends Node { // TODO rename block
	
	private List<Question> questions;
	private List<Statement> statements;
	
	public List<Question> getQuestions() {
		return questions;
	}

	public List<Statement> getStatements() {
		return statements;
	}
	
	public Block() {
		this.questions = new ArrayList<Question>();
		this.statements = new ArrayList<Statement>();
	}
	
	public void addQuestion(Question question) {
		this.questions.add(question);
	}
	
	public void addStatement(Statement statement) {
		this.statements.add(statement);
	}
}