package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.ByteCodeMethod;

public class CommentPseudoInstruction implements Instruction
{ String commentText;

  public CommentPseudoInstruction(String commentText)
  { this.commentText = commentText;
  }

  // Called when adding to primary method
  public void simulateLimits(ByteCodeMethod method)
  {
  }

  public String byteCode()
  { return "  ; " + commentText.replace("\n", "\\n");
  }
}
