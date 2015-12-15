package rmk35.partIIProject.backend;

import java.util.Map;

public abstract class Statement
{ public abstract void generateOutput(Map<IdentifierValue, Definition> definitions,
                                      Map<IdentifierValue, Macro> macros,
                                      OutputClass output);
}
