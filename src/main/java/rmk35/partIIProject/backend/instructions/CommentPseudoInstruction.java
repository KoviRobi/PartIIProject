package rmk35.partIIProject.backend.instructions;

import rmk35.partIIProject.backend.OutputClass;

public class CommentPseudoInstruction implements Instruction
{ String commentText;

  public CommentPseudoInstruction(String commentText)
  { this.commentText = commentText;
  }

  // Called when adding to primary method
  public void simulateLimits(OutputClass outputClass)
  {
  }

  public String byteCode()
  { return "  ; " + commentText;
  }
}
