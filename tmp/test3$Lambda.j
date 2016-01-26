.class test3$Lambda
.super rmk35/partIIProject/backend/runtimeValues/LambdaValue

.field private x Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;

.method public <init>()V
  .limit stack 2
  .limit locals 1
  aload_0
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/LambdaValue/<init>()V
  aload_0
  aload_1
  putfield test3$Lambda/x Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;
  return
.end method

.method public static <clinit>()V
  .limit stack 0
  .limit locals 0

  return
.end method

.method public run(Ljava/util/ArrayList;)Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;
  .limit stack 3
  .limit locals 2
  aload_1
  dup
  iconst_0
  invokeinterface java/util/List/get(I)Ljava/lang/Object; 2
  astore_1
  pop
  ; ClosureIdentifierStatement Get
  aload_0
  getfield test3$Lambda/x Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;
  areturn
.end method

