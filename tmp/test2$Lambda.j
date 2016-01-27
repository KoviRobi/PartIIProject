.class test2$Lambda
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

.method public run(Ljava/util/List;)Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;
  .limit stack 6
  .limit locals 2
  aload_1
  pop
  ; ApplicationStatement
  ; GlobalIdentifierStatement Get
  getstatic test/raise Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;

  checkcast rmk35/partIIProject/backend/runtimeValues/LambdaValue
  new java/util/ArrayList
  dup
  iconst_1
  invokenonvirtual java/util/ArrayList/<init>(I)V
  dup
  ; StringValueStatement
  new rmk35/partIIProject/backend/runtimeValues/StringValue
  dup
  ldc "help"
  invokenonvirtual rmk35/partIIProject/backend/runtimeValues/StringValue/<init>(Ljava/lang/String;)V
  invokeinterface java/util/List/add(Ljava/lang/Object;)Z 2
  pop
  invokevirtual rmk35/partIIProject/backend/runtimeValues/LambdaValue/run(Ljava/util/List;)Lrmk35/partIIProject/backend/runtimeValues/RuntimeValue;
  areturn
.end method

