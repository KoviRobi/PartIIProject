package rmk35.partIIProject.middle.astMacroMatchVisitor.astMatchVisitorReturn;

public interface ASTMatchVisitorReturn
{ public <T> T accept(ASTMatchVisitorReturnVisitor<T> visitor);
}