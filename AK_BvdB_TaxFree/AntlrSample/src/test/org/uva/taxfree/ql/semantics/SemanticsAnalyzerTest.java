package test.org.uva.taxfree.ql.semantics;

import org.testng.annotations.Test;
import test.org.uva.taxfree.ql.SemanticsTester;

public class SemanticsAnalyzerTest extends SemanticsTester {
    @Test
    public void testHasDuplicateQuestionLabels() throws Exception {
        assertSemantics("duplicateQuestionLabelForm.txt", 1, "Duplicate question label expected");
    }

    @Test
    public void testHasMultipleDuplicateQuestionLabels() throws Exception {
        assertSemantics("duplicateQuestionLabelsForm.txt", 2, "Duplicate question labels expected");
    }

    @Test
    public void testHasDuplicateDeclarations() throws Exception {
        assertSemantics("duplicateQuestionIdForm.txt", 1, "Duplicate question id expected");
    }

    @Test
    public void testHasMultipleDuplicateQuestionIds() throws Exception {
        assertSemantics("duplicateQuestionIdsForm.txt", 2, "Duplicate question id");
    }

    @Test
    public void testHasDuplicateQuestionIdsAndLabels() throws Exception {
        assertSemantics("duplicateQuestionIdsAndLabelsForm.txt", 4, "Duplicated questions and labels");
    }

    @Test
    public void testUndefinedDeclarationSingle() throws Exception {
        assertSemantics("undefinedDeclarationSingle.txt", 1, "Undefined declaration should throw an error");
    }

    @Test
    public void testUndefinedDeclarationMultiple() throws Exception {
        assertSemantics("undefinedDeclarationMultiple.txt", 2, "Multiple conditions with same variable trigger multiple errors");
    }

    @Test
    public void testUndefinedDeclarations() throws Exception {
        assertSemantics("undefinedDeclarations.txt", 11, "Undefined declarations");
    }

    @Test
    void testCyclicDependency() throws Exception {
        assertSemantics("cyclicDependencyCalculations.txt", 2, "Cyclic dependency in calculation");
    }

    @Test
    void testConstantCondition() throws Exception {
        assertSemantics("constantCondition.txt", 1, "A constant condition should display a warning");
    }

    @Test
    void testConstantConditions() throws Exception {
        assertSemantics("constantConditions.txt", 2, "Multiple constant conditions should display multiple warning");
    }

    // TODO: Would be a nice to have?
    @Test
    public void testCyclicIndirectDependency() throws Exception {
        assertSemantics("cyclicIndirectDependency.txt", 0, "If directly depends on internals");
    }

    @Test
    public void testCyclicInnerDependency() throws Exception {
        assertSemantics("cyclicInnerDependency.txt", 1, "If indirectly depends on internals");
    }

    @Test
    public void testInvalidError() throws Exception {
        assertSemantics("invalidError.txt", 0, "Invalidly marked as erroneous");
    }

    @Test
    void testConstantCalculation() throws Exception {
        assertSemantics("constantCalculation.txt", 1, "A constant calculation should display a warning");
    }

    @Test
    void testConstantCalculations() throws Exception {
        assertSemantics("constantCalculations.txt", 3, "Mutltiple constant calculations should display multiple warning");
    }

    @Override
    protected String fileDirectory() {
        return "semanticErrors";
    }
}