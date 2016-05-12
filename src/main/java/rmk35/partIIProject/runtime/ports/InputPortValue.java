package rmk35.partIIProject.runtime.ports;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;

import rmk35.partIIProject.frontend.SourceInfo;

import rmk35.partIIProject.middle.ASTVisitor;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;
import rmk35.partIIProject.backend.instructions.NonVirtualCallInstruction;
import static rmk35.partIIProject.backend.instructions.types.StaticConstants.voidType;

import java.io.InputStream;
import java.io.IOException;

public class InputPortValue extends PortValue
{ InputStream port;

  public InputPortValue(InputStream port)
  { this(port, null);
  }
  public InputPortValue(InputStream port, SourceInfo sourceInfo)
  { super(port, sourceInfo);
    this.port = port;
  }

  @Override
  public <T> T accept(ASTVisitor<T> visitor) throws SyntaxErrorException
  { return visitor.visit(this);
  }

  public InputStream getInputStream() { return port; }

  public int read()
  { try
    { return port.read();
    } catch (IOException e)
    { throw new RuntimeException(e);
    }
  }

  public int read(byte[] b)
  { try
    { return port.read(b);
    } catch (IOException e)
    { throw new RuntimeException(e);
    }
  }

  public int read(byte[] b, int off, int len)
  { try
    { return port.read(b, off, len);
    } catch (IOException e)
    { throw new RuntimeException(e);
    }
  }

  public int available()
  { try
    { return port.available();
    } catch (IOException e)
    { throw new RuntimeException(e);
    }
  }
}
