package rmk35.partIIProject.backend;

import java.util.Map;

public abstract class Statement
{ public abstract void generateOutput(Map<Identifier, Definition> definitions,
                                      Map<Identifier, Macro> macros,
                                      OutputClass output);
}
