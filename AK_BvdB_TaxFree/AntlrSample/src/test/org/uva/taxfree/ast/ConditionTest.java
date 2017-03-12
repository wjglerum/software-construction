package test.org.uva.taxfree.ast;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.uva.taxfree.ast.AstBuilder;
import org.uva.taxfree.gui.MessageList;
import org.uva.taxfree.model.environment.SymbolTable;
import org.uva.taxfree.model.node.blocks.FormNode;

import java.io.File;
import java.io.IOException;

public class ConditionTest {

    @Test
    public void testLiteralExpression() throws Exception {
        assertSemantics("conditionLiteralForm.txt", 0, "A valid literal condition");
    }

    @Test
    public void testVariableLiteralExpression() throws Exception {
        assertSemantics("conditionVariableLiteralForm.txt", 0, "A valid variable literal condition");
    }

    @Test
    public void testSimpleBooleanExpression() throws Exception {
        assertSemantics("calculationBooleanExpressionForm.txt", 0, "A valid boolean condition");
    }

    @Test
    public void testSimpleIntegerExpression() throws Exception {
        assertSemantics("calculationIntegerExpressionForm.txt", 0, "A valid integer condition");
    }

    @Test
    public void testSimpleUniformExpression() throws Exception {
        assertSemantics("calculationUniformExpressionForm.txt", 0, "A valid uniform condition");
    }

    @Test
    public void testNestedExpression() throws Exception {
        assertSemantics("calculationNestedExpressionForm.txt", 0, "A valid nested condition");
    }

    @Test
    public void testInvalidConditionExpression() throws Exception {
        assertSemantics("invalidConditionForm.txt", 1, "An invalid condition due to it's types");
    }

    private void assertSemantics(String fileName, int expectedErrorAmount, String description) throws IOException {
        boolean expectedValid = 0 == expectedErrorAmount;
        FormNode ast = createAst(fileName);
        MessageList semanticsMessages = new MessageList();
        SymbolTable symbolTable = new SymbolTable();
        ast.fillSymbolTable(symbolTable);
        ast.checkSemantics(symbolTable, semanticsMessages);
        Assert.assertEquals(semanticsMessages.isEmpty(), expectedValid, "Expecting errors: " + description);
        Assert.assertEquals(semanticsMessages.size(), expectedErrorAmount, "Invalid error amount");
    }

    private FormNode createAst(String fileName) throws IOException {
        AstBuilder builder = new AstBuilder(testFile(fileName));
        return builder.generateTree();
    }

    private File testFile(String fileName) {
        return new File("src\\test\\org\\uva\\taxfree\\ast\\conditionTestFiles\\" + fileName);
    }

}