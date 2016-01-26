.class test1$Lambda
.super rmk35/partIIProject/backend/runtimeValues/LambdaValue



.method public <init>()V
  .limit stack 1
  .limit locals 1
  aload_0
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/LambdaValue/<init>()V
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
  ; LocalIdentifier Get
  aload_1
  areturn
.end method

