package ast;

public class Form extends Node {
	
	private final String id;
	private Block block;

	public String getId() {
		return id;
	}
	
	public Block getBlock() {
		return block;
	}

	public Form(String id, Block block, int line) {
		this.id = id;
		this.block = block;
		this.setLine(line);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	
}
