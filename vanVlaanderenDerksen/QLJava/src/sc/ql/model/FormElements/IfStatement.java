package sc.ql.model.FormElements;

import java.util.List;
import sc.ql.model.FormElement;
import sc.ql.model.ConditionalBlock;

public class IfStatement implements FormElement {
	private final List<ConditionalBlock> conditional_blocks;
	private final List<FormElement> form_elements;
	
	public IfStatement(List<ConditionalBlock> conditional_blocks, List<FormElement> form_elements) {
		this.conditional_blocks = conditional_blocks;
		this.form_elements = form_elements;
	}
	
	public List<ConditionalBlock> getConditionalBlocks() {
		return this.conditional_blocks;
	}
	
	public List<FormElement> getFormElements() {
		return this.form_elements;
	}
}